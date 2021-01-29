import { Button, Cell, Form, Input } from "annar"

import APIFunction from '@/services'

export default (props)=>{

    const { addContact, modifyContact} = APIFunction

    function commit(values){
        if(values.id){
            modifyContact(values)
        }else{
            addContact(values)
        }
    }

    return (
        <View className="x-page">
            <Form onFinish={commit}>
                <Form.Item name="id" noStyle>
                    <Input display="none"></Input>
                </Form.Item>
                <Form.Item noStyle name="name" rules={[{ required: true }]}>
                    <Cell.Input label="姓名" placeholder="请输入姓名" required
                        icon="people" align="center"
                    ></Cell.Input>
                </Form.Item>
                <Form.Item noStyle name="idno" rules={[{ required: true }]}>
                    <Cell.Input label="身份证号" placeholder="请输入姓名" required
                        icon="vipcard" align="center"
                    ></Cell.Input>
                </Form.Item>
                <Form.Item noStyle name="phone" rules={[]}>
                    <Cell.Input label="联系电话" placeholder="请输入联系电话"
                        icon="phone" align="center"
                    ></Cell.Input>
                </Form.Item>
                <Button type="primary" nativeType="submit"></Button>
            </Form>
        </View>
    )


}