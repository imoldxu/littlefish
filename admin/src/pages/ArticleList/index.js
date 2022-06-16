import { message, Image, Space, Button } from "antd";
import { useRef, useState } from "react";
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
//import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import { queryArticle, addArticle, modifyArticle, delArticle } from '@/services/article';
import ArticleModal from "./articleModal";
import { PlusOutlined } from "@ant-design/icons";
import { ArticleType } from "@/utils/bankendData";
import DetailDrawer from "./detailDrawer";

export default ()=>{

  const [infoModalVisible, handleInfoModalVisible] = useState(false);
  const [infoFormValues, setInfoFormValues] = useState({});
  const actionRef = useRef();
  const [detailVisible, setDetailVisible] = useState(false)
  const [detailValue, setDetailValue] = useState()

  async function handleCommit(values) {
    try {
        if (values.id) {
            await modifyArticle(values)
        } else {
            await addArticle(values)
        }
        message.success('提交成功')
        handleInfoModalVisible(false)
        actionRef.current.reload()
    } catch (e) {
        message.error(e.message)
    }
  }

  const columns = [
    {
      title: '文章标题',
      dataIndex: 'title',
    },
    {
      title: '封面',
      dataIndex: 'cover',
      search: false,
      render: (_, record)=>{
        const {covers} = record
        return covers?.length > 0 ? <Image src={covers[0]} height={64} ></Image>:null
      }
    },
    {
      title: '分类',
      dataIndex: 'type',
      valueEnum: ArticleType,
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      search: false,
    },
    {
      title:'时间范围',
      dataIndex:'dateRange',
      hideInTable:true,
      valueType:"dateRange"
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <Space>
          <a
                onClick={() => {
                    setDetailValue(record)
                    setDetailVisible(true)
                }}
            >
                详情
          </a>
          <a
                onClick={() => {
                    setInfoFormValues(record)
                    handleInfoModalVisible(true)
                }}
            >
                修改
          </a>
          <a
                onClick={async () => {
                  try{
                    await delArticle(record.id)
                    message.success('删除成功')
                    actionRef.current.reload()
                  }catch(e){
                    message.error(e.message)
                  }
                }}
            >
                删除
          </a>
        </Space>
      ),
    },
  ];

  return (
    <PageContainer>
      <ProTable
        headerTitle="文章列表"
        actionRef={actionRef}
        //defaultData={prescriptionList?.data}
        //pagination={{current: prescriptionList?.current, pageSize: prescriptionList?.pageSize, total: prescriptionList?.total}}
        rowKey="key"
        manualRequest={true}
        request={async(params) => {
          try{
            if(params.dateRange && params.dateRange.length>1){
              params.startdate = params.dateRange[0];
              params.enddate = params.dateRange[1];
            } 
            const resp = await queryArticle(params) 
            return resp
          }catch(e){
              message.error(e.message, 3)
            }
          }
        }
        columns={columns}
        toolBarRender={() => [
          <Button
              type="primary"
              key="new"
              onClick={() => {
                  setInfoFormValues({})
                  handleInfoModalVisible(true);
              }}
          >
              <PlusOutlined />发布文章</Button>,
        ]}
      />
      {infoModalVisible &&
      <ArticleModal
        handleCommit={handleCommit}
        handleInfoModalVisible={() => handleInfoModalVisible(false)}
        visible={infoModalVisible}
        values={infoFormValues}
       ></ArticleModal>}
       {detailVisible &&
        <DetailDrawer
          value={detailValue}
          visible={detailVisible}
          onClose={()=>setDetailVisible(false)}        
        >
        </DetailDrawer>}
    </PageContainer>
  );

}