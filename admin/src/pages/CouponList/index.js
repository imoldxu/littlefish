import { useModel } from "@/.umi/plugin-model/useModel";
import { PageContainer } from "@ant-design/pro-layout";
import ProTable from "@ant-design/pro-table";
import { Button, message, Space } from "antd";
import { useRef, useState } from "react";
import CouponModal from "./couponModal";
import { PlusOutlined } from "@ant-design/icons";
import { addCouponTemplate, deleteCouponTemplate, modifyCouponTemplate, queryCouponTemplate } from "@/services/coupon";
import { CouponType } from "@/utils/bankendData";


export default ()=>{

  const [infoModalVisible, handleInfoModalVisible] = useState(false);
  const [infoFormValues, setInfoFormValues] = useState({});
  
  const actionRef = useRef();

  async function handleCommit(values) {
    try {
        if (values.id) {
            await modifyCouponTemplate(values)
        } else {
            await addCouponTemplate(values)
        }
        message.success('提交成功')
        handleInfoModalVisible(false)
        actionRef.current.reload()
    } catch (e) {
        message.error(e.message)
    }
  }

  const columns = [
    // {
    //   title: '门店编号',
    //   dataIndex: 'id',
    //   fieldProps: {
    //     onKeyUp: (e)=>{
    //       var keycode = window.event?e.keyCode:e.which;
    //       if(keycode==13){//回车
    //           actionRef.current.reload();
    //       }
    //     }
    // }
    // },
    {
      title: '名称',
      dataIndex: 'name',
      order:2,
      fieldProps: {
        onKeyUp: (e)=>{
          var keycode = window.event?e.keyCode:e.which;
          if(keycode==13){//回车
              actionRef.current.reload();
          }
        }
      }
    },
    {
      title: '总发行量',
      dataIndex: 'total',
      search: false,
    },
    {
      title: '库存',
      dataIndex: 'stock',
      search: false,
    },
    {
      title: '类型',
      dataIndex: 'type',
      valueEnum: CouponType,
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        search: false,
    },
    {
      title:'创建时间',
      dataIndex:'dateRange',
      hideInTable:true,
      valueType:"dateRange"
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => {
        return (
        <Space>
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
                    await deleteCouponTemplate(record.id)
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
        )}
    },
  ];

  return (
    <PageContainer>
      <ProTable
        headerTitle="门店列表"
        actionRef={actionRef}
        //defaultData={orderList?.data}
        //pagination={{current: orderList?.current, pageSize: orderList?.pageSize, total: orderList?.total}}
        rowKey="key"
        request={async (params) => {
            try{
                if(params.dateRange && params.dateRange.length>1){
                params.startTime = params.dateRange + " 00:00:00";
                params.endTime = params.dateRange + " 23:59:59";
                }

                const resp = await queryCouponTemplate(params)
                return resp
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
                  setInfoFormValues({})
                  handleInfoModalVisible(true);
              }}
          >
              <PlusOutlined />新建</Button>,
      ]}
      />
      {
        infoModalVisible ? <CouponModal
        handleCommit={handleCommit}
        handleInfoModalVisible={() => handleInfoModalVisible(false)}
        visible={infoModalVisible}
        values={infoFormValues}
       ></CouponModal> : null
      }
    </PageContainer>
  );
}