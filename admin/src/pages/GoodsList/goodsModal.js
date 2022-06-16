import MyBraftEditor from '@/components/MyBraftEditor';
import MyImageUpload from '@/components/MyImageUpload';
import { getGoodsDetail } from '@/services/goods';
import { urlStr2OssFileObj } from '@/utils/aliOss';
import { checkTwoPointNum, regFenToYuan, regYuanToFen } from '@/utils/money';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { Button, Col, Descriptions, Divider, Input, message, Modal, Row, Select, Space, Spin } from 'antd';
import Form from 'antd/lib/form/Form';
import FormItem from 'antd/lib/form/FormItem';
import FormList from 'antd/lib/form/FormList';
import TextArea from 'antd/lib/input/TextArea';
import React, { useEffect, useRef, useState } from "react";
import { useAccess } from 'umi';

const { Option } = Select;

const layout = {
    labelCol: {
        span: 2,
    },
    wrapperCol: {
        span: 22,
    },
}

const GoodsModal = (props) => {
    const {
        handleCommit,
        handleInfoModalVisible,
        visible,
        values,
    } = props;

    const formRef = useRef()
    const [isLoading, setLoading] = useState(false)
    const [isInit, setInit] = useState(false)
    const [initValue, setInitValue] = useState({})

    const { id } = values;
    useEffect(async()=>{
        try{
            if(id){
                const goods = await getGoodsDetail({id})
                let initValue = {
                    ...goods
                }
                initValue.covers = goods?.covers?.map((cover, index)=>{
                    return urlStr2OssFileObj(cover, index)
                })
                initValue.descriptionEditor = goods.description;
                initValue.specEditor = goods.spec;
                initValue.introductionEditor = goods.introduction;
                setInitValue(initValue)
            }
            setInit(true)
        }catch(e){
            message.error(e.message)
        }
    },[id])

    const title = id ? "修改商品" : "新建商品"
//const priceYuan = regFenToYuan(price)
                

    const onOk = () => {

        formRef.current
            .validateFields()
            .then(values => {

                // values.price = regYuanToFen(values.price, 100)

                if (handleCommit) {
                    setLoading(true)
                    try {
                        let {introductionEditor, specEditor, descriptionEditor, ...payload} = values 
                        //let payload = {...values}
                        payload.covers = values.covers.map(cover=>{
                            return cover.url
                        })
                        payload.introduction = introductionEditor.toHTML()
                        payload.description = descriptionEditor.toHTML()
                        payload.spec = specEditor.toHTML()
                        
                        handleCommit(payload)
                    } catch (e) {
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
            width={1024}
            destroyOnClose={true}
            title={title}
            visible={visible}
            onOk={onOk}
            confirmLoading={isLoading}
            onCancel={handleInfoModalVisible}
        >
            {isInit?
            <Form {...layout}
                ref={formRef}
                name="goods"
                initialValues={{ ...initValue }}
                preserve={false}>
                {
                    id ? (<FormItem name="id" noStyle>
                        <Input type="hidden"></Input>
                    </FormItem>) : ('')
                }

                <FormItem name="name" label="商品名称" rules={[{ required: true }]} hasFeedback>
                    <Input placeholder="商品名称" ></Input>
                </FormItem>

                <FormItem name="category" label="分类"
                    rules={[{ required: true }]}
                    hasFeedback
                >
                    <Select >
                        <Option value={1}>保健品</Option>
                        <Option value={2}>饮品</Option>
                    </Select>
                </FormItem>

                <FormItem name="tags" label="标签"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Select mode="tags" placeholder="请输入特点标签" maxTagCount="4" maxTagTextLength="4">
                    </Select>
                </FormItem>

                <FormItem name="covers" label="展示图片"
                    // rules={[{ required: true }]}
                    hasFeedback>
                    <MyImageUpload 
                     //cropProps={{aspect:2}}  设置宽/高 比，默认是1
                     listType="picture-card" maxCount={6} crop={true}></MyImageUpload>
                </FormItem>

                <FormItem name="introductionEditor" label="图文介绍"
                    rules={[
                        {
                            required: true
                        },
                        {
                            validateTrigger: "onBlur",
                            validator: (_, value) =>  value.isEmpty() ? Promise.reject(new Error('请输入正文内容')) : Promise.resolve() 
                        }
                    ]}
                    hasFeedback>
                    <MyBraftEditor></MyBraftEditor>   
                </FormItem>

                <FormItem name="specEditor" label="规格"
                    rules={[
                        {
                            required: true
                        },
                        {
                            validateTrigger: "onBlur",
                            validator: (_, value) =>  value.isEmpty() ? Promise.reject(new Error('请输入正文内容')) : Promise.resolve() 
                        }
                    ]}
                    hasFeedback>
                    <MyBraftEditor></MyBraftEditor>   
                </FormItem>

                <FormItem name="descriptionEditor" label="描述"
                    rules={[
                        {
                            required: true
                        },
                        {
                            validateTrigger: "onBlur",
                            validator: (_, value) =>  value.isEmpty() ? Promise.reject(new Error('请输入正文内容')) : Promise.resolve() 
                        }
                    ]}
                    hasFeedback>
                    <MyBraftEditor></MyBraftEditor>   
                </FormItem>

            </Form> : <Spin></Spin>
            }
        </Modal>
    );
};

export default GoodsModal;
