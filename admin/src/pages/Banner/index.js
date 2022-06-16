import { Button, message, Popconfirm, Space } from "antd";
import { useRef, useState } from "react";
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
//import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import { addBanner, getBanner, modifyBanner } from '@/services/banner';
import { Loading3QuartersOutlined, PlusOutlined } from "@ant-design/icons";
import BannerModal from "./bannerModal";

export default ()=>{

  const actionRef = useRef();

  const [modalVisible, setModalVisible] = useState(false)
  const [modalValue, setModalValue] = useState({})

  const handleCommit = async (values) => {
    const hide = message.loading('提交中')
    try{
        values.id? await modifyBanner(values) : await addBanner(values)
        message.success("提交成功", 3)    
        actionRef.current.reload();
        setModalVisible(false)
        setModalValue({})
    }catch(e){
        message.error(e.message, 3)
    }finally{
        hide()
    }
  };

  const columns = [
    {
        title: '名称',
        dataIndex: 'name',
    },
    {
        title: '活动图片',
        dataIndex: 'resUrl',
        search: false,
        valueType: 'image',
    },
    {
        title: '跳转地址',
        dataIndex: 'redirectUrl',
        search: false,
    },
    {
        title: '开始时间',
        dataIndex: 'startDate',
        search: false,
    },
    {
        title: '结束时间',
        dataIndex: 'endDate',
        search: false,
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        search: false,
    },
    {
        title: '操作',
        dataIndex: 'option',
        valueType: 'option',
        render: (_, record) => {
            return (
                <Space>
                    <a onClick={()=>{
                        setModalValue(record)
                        setModalVisible(true)
                    }}>
                        修改
                    </a>
                </Space>
            )
        }
    },
    ];

  return (
    <PageContainer>
      <ProTable
        headerTitle="广告列表"
        actionRef={actionRef}
        rowKey="rowKey"
        request={async (params) => {  
            try{
                return await getBanner(params)
            }catch(e){
                message.error(e.message, 3)
            }
          }
        }
        columns={columns}
        manualRequest={true}
        toolBarRender={() => [
            <Button
                type="primary"
                key="new"
                onClick={() => {
                    setModalValue({})
                    setModalVisible(true);
                }}
            >
                <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="新建" />
            </Button>,
        ]}
      />
      <BannerModal key="modal"
        handleCommit={handleCommit}
        handleCancel={()=>{
            setModalVisible(false)
            setModalValue({})
        }}
        visible={modalVisible}
        values={modalValue}
      ></BannerModal>
    </PageContainer>
  );

}