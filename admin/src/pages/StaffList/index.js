import { Button, message, Space } from "antd";
import { useRef, useState } from "react";
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
//import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import { register, enableStaff, disableStaff, resetPassword, queryStaff } from '@/services/staff';
import StaffModal from "./staffModal";
import { PlusOutlined } from "@ant-design/icons";

export default ()=>{

  const [modalVisible, setModalVisible] = useState(false);
  
  const actionRef = useRef();

  const columns = [
    {
      title: '用户名',
      dataIndex: 'name',
    },
    {
      title: '登录账号',
      dataIndex: 'phone',
    },
    {
      title: '角色',
      dataIndex: 'role',
      render: (_, record) => {
        let roleName = ''
        record.roles.forEach((role, index) => {
          if(index!=0){
            roleName = roleName+','+role.name
          }else{
            roleName = role.name
          }
        });
        return roleName
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      search: false,
    },
    {
      title: '状态',
      dataIndex: 'state',
      valueEnum: {
        1: {
            text: '正常',
            status: 'Success',
        },
        0: {
            text: '停用',
            status: 'Warning',
        }, 
    }
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <Space size="large">
          <a
            onClick={async () => {
              const hide = message.loading('重置密码中')
              try{
                await resetPassword({id: record.id});
                message.success('重置密码成功',3)
              }catch(e){
                message.error(e.message,3)
              }finally{
                hide()
              }
            }}
          >
            重置密码
          </a>
          {
            record.state == 1 ? (
              <a
                onClick={async () => {
                  const hide = message.loading('处理中')
                  try{
                    await disableStaff({id: record.id, state: 0});
                    actionRef.current.reload()
                  }catch(e){
                    message.error(e.message,3)
                  }finally{
                    hide()
                  }
                }}
              >
                停用
              </a>
            ):(
              <a
                onClick={async() => {
                  const hide = message.loading('提交中')
                  try{
                    await enableStaff({id: record.id});
                    actionRef.current.reload()
                  }catch(e){
                    message.error(e.message,3)
                  }finally{
                    hide()
                  }
                }}
              >
                启用
              </a>
            )
          }
        </Space>
      ),
    },
  ];

  return (
    <PageContainer>
      <ProTable
        headerTitle="用户列表"
        actionRef={actionRef}
        rowKey="key"
        request={async (params) => {
            try{
                return await queryStaff(params)
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
                  setModalVisible(true);
              }}
          >
              <PlusOutlined />新建
          </Button>,
      ]}
      />
      <StaffModal
        handleCommit={async (values)=>{
          await register(values)
          setModalVisible(false)
          actionRef.current.reload()
        }}
        handleCancel={()=>{
          setModalVisible(false)
        }}
        visible={modalVisible} 
      />
    </PageContainer>
  );

}