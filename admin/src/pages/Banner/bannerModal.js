import { checkTwoPointNum, regFenToYuan, regYuanToFen } from '@/utils/money';
import { Button, Col, DatePicker, Descriptions, Divider, Input, message, Modal, Row, Select, Space, Statistic } from 'antd';
import Form from 'antd/lib/form/Form';
import FormItem from 'antd/lib/form/FormItem';
import Text from 'antd/lib/typography/Text';
import React, { useRef, useState } from "react";
import AliyunOssUpload from '@/components/AliyunOssUpload'

const layout = {
    labelCol: {
        span: 5,
    },
    wrapperCol: {
        span: 19,
    },
}

const BannerModal = (props) => {
    const {
        handleCommit,
        handleCancel,
        visible,
        values,
    } = props;

    const formRef = useRef()
    const [isLoading, setLoading] = useState(false)

    const { id } = values;

    const title = id ? "修改广告" : "新建广告"

    const onOk = () => {

        formRef.current
            .validateFields()
            .then(async values => {
                if (handleCommit) {
                    setLoading(true)
                    try{
                        let payload = {
                            ...values,
                            resUrl: values.ossFiles[0].url 
                        }
                        delete payload.ossFiles 
                        await handleCommit(payload)
                    } catch(e){
                        message.error(e.message, 3)
                    } finally{
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
            title={title}
            visible={visible}
            onOk={onOk}
            onCancel={handleCancel}
            // footer={[
            //     <Button key="back" onClick={handleCancel}>
            //       关闭
            //     </Button>,
            // ]}
        >
            <Form {...layout}
                ref={formRef}
                name="banner"
                initialValues={{ ...values }}
                preserve={false}>
                {
                    id ? (<FormItem name="id" noStyle>
                        <Input type="hidden"></Input>
                    </FormItem>) : ('')
                }
                <FormItem name="name" label="名称" rules={[{ required: true }]} hasFeedback>
                    <Input placeholder="名称" ></Input>
                </FormItem>
                <FormItem name="ossFiles" label="广告图片" rules={[{ required: true }]} hasFeedback>
                    <AliyunOssUpload dir='images/' maxCount={1} listType="picture"></AliyunOssUpload>
                </FormItem>
                <FormItem name="redirectUrl" label="跳转地址">
                    <Input placeholder="请输入跳转地址" ></Input>
                </FormItem>
                <FormItem name="rangeDate" label="生效时间"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <DatePicker.RangePicker></DatePicker.RangePicker>
                </FormItem>
            </Form>
        </Modal>
    );
};

export default BannerModal;
