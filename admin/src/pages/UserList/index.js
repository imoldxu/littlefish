import { Image, message, Space } from "antd";
import { useRef, useState } from "react";
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
//import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import { queryUser } from '@/services/user';
import { regFenToYuan } from "@/utils/money";
import DetailDrawer from "./detailDrawer";

export default ()=>{

  const [detailVisible, setDetailVisible]=useState(false)
  const [detailValue, setDetailValue]=useState()
  const actionRef = useRef();

  const columns = [
    {
      title: '会员昵称',
      dataIndex: 'nick',
      ellipsis: true,
    },
    {
      title: '头像',
      dataIndex: 'headImgUrl',
      search: false,
      render: (_, record) => {
          const { headImgUrl } = record
          return (<Image src={headImgUrl} height={64} ></Image>)
      }
    },
    {
      title: '会员等级',
      dataIndex: 'level',
      valueEnum: {
        1: {
            text: '普通',
        },
        2: {
            text: '铜牌',
        },
        3: {
          text: '银牌',
        },
        4: {
            text: '金牌',
        },
        5: {
            text: '体验用户',
        },
        6: {
            text: '合伙用户',
        },
      }
    },
    {
      title: '注册时间',
      dataIndex: 'createTime',
      search: false,
      ellipsis: true,
    },
    {
      title: '最近一次登录时间',
      dataIndex: 'lastLoginTime',
      search: false,
      ellipsis: true,
    },
    {
      title: '渠道',
      dataIndex: 'dealerName',
      ellipsis: true,
    },
    {
      title: '销售',
      dataIndex: 'sellerName',
      ellipsis: true,
    },
    {
      title:'注册时间',
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
                    setDetailValue(record)
                    setDetailVisible(true)
                }}
            >
                详情
          </a>
        </Space>
        )}
    },
  ];

  return (
    <PageContainer>
      <ProTable
        headerTitle="会员列表"
        actionRef={actionRef}
        rowKey="id"
        request={async (params) => {
            try{
                if(params.dateRange && params.dateRange.length>1){
                params.startTime = params.dateRange[0] + " 00:00:00";
                params.endTime = params.dateRange[1] + " 23:59:59";
                }
                return await queryUser(params)
            }catch(e){
                message.error(e.message, 3)
            }
          }
        }
        columns={columns}
        manualRequest={true}
      />
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