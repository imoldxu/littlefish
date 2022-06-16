import { Button, Checkbox, Col, Descriptions, Divider, Input, message, Modal, Row, Select } from 'antd';
import Form from 'antd/lib/form/Form';
import FormItem from 'antd/lib/form/FormItem';
import FormList from 'antd/lib/form/FormList';
import React, { useEffect, useRef, useState } from "react";

const { Option } = Select;
//TODO: 角色配置更智能可以从后台获取
const roleOptions = [{ label: '系统管理员', value: 'admin' },
{ label: '老板', value: 'boss' },
{ label: '管理员', value: 'manager' },
{ label: '财务', value: 'finance'},
{ label: '操作员', value: 'operator' }
]

const layout = {
    labelCol: {
        span: 4,
    },
    wrapperCol: {
        span: 20,
    },
}

const StaffModal = (props) => {
    const {
        handleCommit,
        handleCancel,
        visible,
        values,
    } = props;

    const formRef = useRef()
    const [isLoading, setLoading] = useState(false)

    const onOk = () => {
        formRef.current
            .validateFields()
            .then(async (values) => {

                if (handleCommit) {
                    setLoading(true)
                    try {
                        await handleCommit(values)
                        message.success('提交成功')
                    } catch(e){
                        message.error(e.message, 3)
                    } finally {
                        setLoading(false)
                    }
                }
            })
            .catch(info => {
                console.log('校验失败:', info);
            });
    }

    return (
        <Modal
            destroyOnClose={true}
            title="新建管理员"
            visible={visible}
            onOk={onOk}
            confirmLoading={isLoading}
            onCancel={handleCancel}
        >
            <Form {...layout}
                ref={formRef}
                name="user"
                preserve={false}>
                <FormItem name="name" label="姓名"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Input placeholder="请输入用户姓名"></Input>
                </FormItem>
                <FormItem name="phone" label="登录账号"
                    rules={[{ required: true }]}
                    hasFeedback
                >
                    <Input placeholder="请输入登录账号" maxLength="11"></Input>
                </FormItem>
                <FormItem name="roles" label="角色"
                    rules={[{ required: true }]}
                    hasFeedback>
                    {/* <Select>
                        <Option value="admin">系统管理员</Option>
                        <Option value="boss">老板</Option>
                        <Option value="manager">业务管理员</Option>
                        <Option value="operator">操作员</Option>
                    </Select> */}
                    <Checkbox.Group options={roleOptions}></Checkbox.Group>
                </FormItem>
            </Form>
        </Modal>
    );
};

export default StaffModal;
