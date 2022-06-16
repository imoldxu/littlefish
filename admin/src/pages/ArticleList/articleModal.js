import MyImageUpload from '@/components/MyImageUpload';
import { getArticle } from '@/services/article';
import { urlStr2OssFileObj } from '@/utils/aliOss';
import { checkTwoPointNum, regFenToYuan, regYuanToFen } from '@/utils/money';
import { Button, Col, Descriptions, Divider, Input, message, Modal, Row, Select, Spin } from 'antd';
import Form from 'antd/lib/form/Form';
import FormItem from 'antd/lib/form/FormItem';
import React, { useEffect, useRef, useState } from "react";
import { useAccess } from 'umi';

const { Option } = Select;

const layout = {
    labelCol: {
        span: 4,
    },
    wrapperCol: {
        span: 20,
    },
}

const ArticleModal = (props) => {
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
    useEffect(async ()=>{
        try{
            if(id){
                const article = await getArticle({id})
                let initValue = {
                    ...article
                }
                initValue.covers = article?.covers?.map((cover, index)=>{
                   return urlStr2OssFileObj(cover, index)
                })
                setInitValue(initValue)
            }
            setInit(true)
        }catch(e){
            message.error(e.message)
        }finally{
            
        }
    },[id])

    const title = id ? "修改文章" : "发布文章"

    const onOk = () => {

        formRef.current
            .validateFields()
            .then(values => {

                if (handleCommit) {
                    setLoading(true)
                    try {
                        let payload = {...values}
                        payload.covers = values.covers.map(cover=>{
                            return cover.url
                        })
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
                name="store"
                initialValues={{ ...initValue }}
                preserve={false}>
                {
                    id ? (<FormItem name="id" noStyle>
                        <Input type="hidden"></Input>
                    </FormItem>) : ('')
                }
                <FormItem name="title" label="文章标题" rules={[{ required: true }]} hasFeedback>
                    <Input placeholder="文章标题" ></Input>
                </FormItem>
                <FormItem name="covers" label="封面"
                    // rules={[{ required: true }]}
                    hasFeedback
                >
                    <MyImageUpload listType="picture-card" maxCount={3} crop={true}></MyImageUpload>
                </FormItem>
                <FormItem name="type" label="分类"
                    rules={[{ required: true }]}
                    hasFeedback
                >
                    <Select>
                        <Option value={1}>健康讲堂</Option>
                        <Option value={2}>公益活动</Option>
                    </Select>
                </FormItem>
                <FormItem name="content" label="内容"
                    rules={[{ required: true }]}
                    hasFeedback
                >
                    <Input.TextArea placeholder="文章内容" ></Input.TextArea>
                </FormItem>
            </Form> : <Spin></Spin>
            }
        </Modal>
    );
};

export default ArticleModal;
