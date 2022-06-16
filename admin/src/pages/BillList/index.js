import { message, Space, Statistic } from "antd";
import { useRef, useState } from "react";
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
//import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import { queryBill } from '@/services/bill';
import { regFenToYuan } from "@/utils/money";
import { PayMode } from "@/utils/bankendData";

export default ()=>{

  const actionRef = useRef();

  const columns = [
    {
        title: '支付方式',
        dataIndex: 'payMode',
        valueEnum: PayMode,
    },
    {
        title: '支付流水号',
        dataIndex: 'paySerialNum',
    },
    {
        title: '账单金额',
        dataIndex: 'amount',
        search: false,
        render: (_,record)=> {
          if(record.type===1){  
            return (
              <span style={{color:"green"}}>{regFenToYuan(record.amount)}元</span>
              )
          }else{
              return (
                  <span style={{color:"red"}}>{regFenToYuan(0-record.amount)}元</span>
              )  
          }
          }
    },
    {
        title: '关联订单号',
        dataIndex: 'orderNo',
    },
    {
      title: '账单时间',
      dataIndex: 'createTime',
      search: false,
    },
    {
      title:'时间范围',
      dataIndex:'dateRange',
      hideInTable:true,
      valueType:"dateRange"
    },
  ];

  return (
    <PageContainer>
      <ProTable
        headerTitle="账单明细"
        actionRef={actionRef}
        rowKey="key"
        request={async (params) => {
            try{
              if(params.dateRange && params.dateRange.length>1){
                params.startTime = params.dateRange[0] + " 00:00:00";
                params.endTime = params.dateRange[1] + " 23:59:59";
              }
              return await queryBill(params)
            }catch(e){
              message.error(e.message,3)
            }
          }
        }
        columns={columns}
        manualRequest={true}
      />
    </PageContainer>
  );

}