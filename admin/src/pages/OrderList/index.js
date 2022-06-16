import { message, Popconfirm, Space } from "antd";
import { useRef, useState } from "react";
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import { queryOrder, deliver } from '@/services/order';
import { querySalesRecord } from '@/services/salesRecord';
import RefundModal from './refundModal'
import { regFenToYuan } from "@/utils/money";
import DetailDrawer from "./detailDrawer";
import { OrderState, PayMode } from "@/utils/bankendData";

export default ()=>{

  // const [refundModalVisible, setRefundModalVisible] = useState(false)
  // const [refundModalValue, setRefundModalValue] = useState()
  const [detailVisible, setDetailVisible] = useState(false)
  const [detailId, setDetailId] = useState()

  const actionRef = useRef();

//  const [orderList, setOrderList] = useState([])
  
  // async function commitRefund(values){
  //     try{
  //       await refundDrug(values)
  //       setRefundModalValue(null)
  //       setRefundModalVisible(false)
  //       message.success('提交成功')
  //     }catch(e){
  //       message.error(e.message, 3)
  //     }finally{
  //       actionRef.current.reload()
  //     }
  // }

  //打开退货modal
  // const handleRefund = async (order) =>{
  //     const hide = message.loading('加载中')
  //     try{
  //         const pageResult = await querySalesRecord({prescriptionno: order.prescriptionno, current:1, pageSize: 100})
  //         setRefundModalValue({order:order, records:pageResult.data})
  //         setRefundModalVisible(true)
  //     }catch(e){
  //         message.error(e.message, 3)
  //     }finally{
  //         hide()
  //     }
  // }

  // const gotoPrint = async (pid) => {
  //   if (!history) return;
  //   setTimeout(() => {
  //     history.push('/print?id='+pid);
  //   }, 1500);
  // };

  const handleDeliver = async (orderno, pid) => {
    const hide = message.loading('确认发货中')
    try{
        await deliver({orderno:orderno})
        //gotoPrint(pid)
    }catch(e){
        message.error(e.message, 3)
    }finally{
        hide()
        actionRef.current.reload();
    }
  };

  const columns = [
    {
      title: '订单号',
      dataIndex: 'orderNo',
    },
    {
      title: '订单金额',
      dataIndex: 'totalAmount',
      search: false,
      render: (_,record)=> (<span>{regFenToYuan(record.totalAmount)}元</span>)
    },
    {
        title: '支付方式',
        dataIndex: 'payMode',
        valueEnum: PayMode,
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        search: false,
    },
    {
        title: '失效时间',
        dataIndex: 'invalidTime',
        search: false,
    },
    {
        title: '状态',
        dataIndex: 'state',
        valueEnum: OrderState,
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
        const {state, orderno, amount} = record
        return <Space>
        {
          state == 2 ?
            <a
              onClick={() => {
                  handleDeliver(record.orderno, record.prescriptionid);
              }}
            >
              发货
            </a> : state == 3 ?
            <a
              onClick={() => {
                  handleRefund(record);
              }}
            >
              退货
            </a>
          : null
        }
        </Space>
      }
    },
  ];

  return (
    <PageContainer>
      <ProTable
        headerTitle="交易列表"
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

                const resp = await queryOrders(params)
                return resp
            }catch(e){
                message.error(e.message, 3)
            }
          }
        }
        columns={columns}
        manualRequest={true}
      />
      { 
        detailVisible &&
        <DetailDrawer
          value={detailValue}
          visible={detailVisible}
          onClose={()=>setDetailVisible(false)}        
        >
        </DetailDrawer>
      }
      {/* {
          refundModalValue && (
            <RefundModal 
            handleCommit={commitRefund}
            handleCancel={()=>setRefundModalVisible(false)}
            visible={refundModalVisible}
            values={refundModalValue}>
            </RefundModal>
          )
      } */}
    </PageContainer>
  );

}