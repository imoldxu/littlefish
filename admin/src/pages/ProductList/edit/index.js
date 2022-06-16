import AliyunOSSUpload from '@/components/AliyunOssUpload';
import { addGroupTour } from '@/services/groupTour';
import { checkTwoPointNum, regFenToYuan, regYuanToFen } from '@/utils/money';
import { Button, Card, Col, Descriptions, Divider, Input, InputNumber, message, Modal, Radio, Row, Select, Space, Tabs, Timeline, TimePicker } from 'antd';
import { history } from 'umi';
import Form from 'antd/lib/form/Form';
import FormItem from 'antd/lib/form/FormItem';
import FormList from 'antd/lib/form/FormList';
import React, { useEffect, useRef, useState } from "react";
import { PageContainer } from '@ant-design/pro-layout';
import ProCard from '@ant-design/pro-card';
import ProForm, { ProFormGroup, ProFormTextArea, StepsForm } from '@ant-design/pro-form';
import ScheduleTab from './scheduleTab';
import Checkbox from 'antd/lib/checkbox/Checkbox';
import FeeInfo from './FeeInfo';
import MyImageUpload from '@/components/MyImageUpload';
import MyBraftEditor from '@/components/MyBraftEditor';
import Policy from './policy';
import CityCascader from '@/components/CityCascader';
import EmptyLine from './components/EmptyLine';
import { editorState2Html } from '@/utils/braftHtmlConvert';
import services from '@/services';
import { addProduct, getProduct, modifyProduct } from '@/services/product';
import { parseValueToMoment } from '@ant-design/pro-utils';
import { urlStr2OssFileObj } from '@/utils/aliOss';
import BraftEditor from 'braft-editor';


const { Option } = Select;
const { TabPane } = Tabs;

const layout = {
    labelCol: {
        span: 4,
    },
    wrapperCol: {
        span: 20,
    },
}

const EditProduct = (props) => {

    const [isLoading, setLoading] = useState(true)
    const [product, setProduct] = useState({})
    //const [activeKey, setActiveKey] = useState(0)

    const { query } = history.location;
    const { id } = query;
    useEffect(async ()=>{
        if(id){
            try{
                const resp = await getProduct({id:id})

                const formValue = toFormValue(resp)

                setProduct(formValue)
            }catch(e){
                message.error(e.message)
                history.goBack()
            }
        }
        setLoading(false)
    },[id])

    //将后台数据转换为formValue
    const toFormValue = (values) => {
        let payload = {...values}

        //处理轮播图片
        payload.imageUrls = values.imageUrls?.map(url => {
            return urlStr2OssFileObj(url)
        })
        //处理图文介绍
        payload.schedules = values.schedules?.map(schedule => {
            return {
                plan: schedule.plan,
                todoList: schedule.todoList?.map(todo => {
                    let todoObj = {...todo}
                    todoObj.images = todo.images? todo.images.map(url => {
                        return urlStr2OssFileObj(url)
                    }): []
                    return todoObj
                })
            }
        })
        //将内容转换为BraftState
        payload.introduction = BraftEditor.createEditorState(values.introduction)
        //将内容转换为BraftState
        payload.instructions = BraftEditor.createEditorState(values.instructions)

        return payload;
    }

    //处理数据
    const handleValues = (values) => {

        let payload = {...values}

        //处理轮播图片
        payload.imageUrls = values.imageUrls?.map(ossImage => {
            return ossImage.url
        })
        //处理图文介绍,可能为空
        if(values.introduction){ 
            payload.introduction = editorState2Html(values.introduction)
        }
        //处理行程
        payload.schedules = preHandleSchedule(values.schedules)

        //处理包含的费用
        if(values.includeFee){
            payload.includeFee = preHandleFee(values.includeFee)
        }
        //处理政策信息
        if(values.policy){
            payload.policy = preHandlePolicy(values.policy)
        }
        //处理使用说明
        if(values.instructions){
            payload.instructions = editorState2Html(values.instructions)
        }

        return payload
    }

    //预处理行程
    const preHandleSchedule = (schedules) => {
        return schedules.map(schedule => {
            //console.log(schedule)
            return {
                plan: schedule.plan,
                todoList: schedule.todoList?.map(todo => {
                    let todoObj = {...todo}
                    todoObj.images = todo.images? todo.images.map(ossImage => {
                        return ossImage.url
                    }): []
                    return todoObj
                })
            }
        })
    }

    //预处理费用信息，只保留预选中的
    const preHandleFee = (includeFee) =>{
        let payload = {...includeFee}

        const {bigTraffic={}, hotelFee={}, guideFee={}, localTraffic={}, mealFee={}} = includeFee;
        for(let key in bigTraffic){
            if(!bigTraffic[key].checked){
                delete payload.bigTraffic[key]
            }
        }
        for(let key in hotelFee){
            if(!hotelFee[key].checked){
                delete payload.hotelFee[key]
            }
        }
        for(let key in guideFee){
            if(!guideFee[key].checked){
                delete payload.guideFee[key]
            }
        }
        for(let key in localTraffic){
            if(!localTraffic[key].checked){
                delete payload.localTraffic[key]
            }
        }
        for(let key in mealFee){
            if(!mealFee[key].checked){
                delete payload.mealFee[key]
            }
        }
        return payload;
    }

    //预处理费用信息，只保留预选中的
    const preHandlePolicy = (policy) =>{
        let payload = {...policy}

        const {limit, service, team, cancel} = policy;
        //处理年龄限制
        for(let key in limit.age){
            if(!limit.age[key].checked){
                delete payload.limit.age[key]
            }
        }
        //处理人群限制
        for(let key in limit.group){
            if(!limit.group[key].checked){
                delete payload.limit.group[key]
            }
        }
        
        //处理确认政策
        for(let key in services.confirm){
            if(!services.confirm[key].checked){
                delete payload.services.confirm[key]
            }
        }
        //处理通知书政策
        for(let key in services.notice){
            if(!services.notice[key].checked){
                delete payload.services.notice[key]
            }
        }

        //处理最小成团的政策
        for(let key in team){
            if(!team[key].checked){
                delete payload.team[key]
            }
        }
        
        //处理取消政策
        // for(let key in cancel.user){
        //     if(!cancel.user[key].checked){
        //         delete payload.cancel.user[key]
        //     }
        // }
        // for(let key in cancel.store){
        //     if(!cancel.store[key].checked){
        //         delete payload.cancel.store[key]
        //     }
        // }
        
        return payload;
    }

    return (
        <PageContainer>
            {!isLoading &&
            <ProCard>
                <StepsForm
                    onFormFinish={(name, { values, forms }) => {
                        if (name === 'base') {
                            const { base, schedule } = forms
                            const { days } = values;
                            const numOfday = parseInt(days)
                            //根据天数处理schedule的内容
                            const oldSchedules = schedule.getFieldValue('schedules') || []
                            let newSchedules = oldSchedules;
                            if (oldSchedules.length > numOfday) {
                                newSchedules = oldSchedules.filter(day => day.name < numOfday)
                            }
                            for (let i = 0; i < numOfday; i++) {
                                if (i >= newSchedules.length) {
                                    newSchedules.push({ name: i, key: i, isListField: true, fieldKey: i })
                                }
                            }
                            schedule.setFieldsValue({ schedules: newSchedules })
                        }
                        if (name === 'schedule') {
                            const { schedule, fee } = forms
                            const { schedules } = values;
                            //根据schedule的内容设置餐食的数据
                            let adultBreakfast = 0;
                            let adultLunch = 0;
                            let adultDinner = 0;
                            let childBreakfast = 0;
                            let childLunch = 0;
                            let childDinner = 0;

                            schedules.forEach(daySchedule => {
                                daySchedule.todoList?.forEach(todo => {
                                    if(todo.type == 1){//是否是餐饮
                                        if(todo.mealType=='breakfast'){
                                            if(todo.mealAdult){
                                                adultBreakfast += 1;
                                            }
                                            if(todo.mealChild){
                                                childBreakfast +=1;
                                            }
                                        }else if(todo.mealType=='lunch'){
                                            if(todo.mealAdult){
                                                adultLunch += 1;
                                            }
                                            if(todo.mealChild){
                                                childLunch +=1;
                                            }
                                        }else if(todo.mealType=='dinner'){
                                            if(todo.mealAdult){
                                                adultDinner += 1;
                                            }
                                            if(todo.mealChild){
                                                childDinner +=1;
                                            }
                                        }
                                    } 
                                })                               
                            });

                            const genText = (breakfast, lunch, dinner, isAdult)=>{
                                const total = breakfast + lunch + dinner;
                                const breakfastText = breakfast > 0 ? `${breakfast}早餐` : ""
                                const lunchText = lunch > 0 ? `${lunch}午餐` : ""
                                const dinnerText = dinner > 0 ? `${dinner}晚餐` : ""
                                return `${isAdult?'成人':'儿童'}包含${total}次:${breakfastText}${lunchText}${dinnerText}`
                            }

                            fee.setFieldsValue({
                                includeFee:{ 
                                    mealFee: {
                                        mealAdultPolicy: {
                                            breakfast: adultBreakfast,
                                            lunch: adultLunch,
                                            dinner: adultDinner,
                                            checked: true,
                                            text: genText(adultBreakfast, adultLunch, adultDinner, true)
                                        },
                                        mealChildPolicy: {
                                            breakfast: childBreakfast,
                                            lunch: childLunch,
                                            dinner: childDinner,
                                            checked: true,
                                            text: genText(childBreakfast, childLunch, childDinner, false)
                                        } 
                                    },
                                }
                            })
                        }
                    }}
                    onFinish={async (values) => {
                        //TODO: 提交
                        console.log(values);
                        const payload = handleValues(values)
                        console.log(payload);
                        try {
                            if(payload.id){
                                await modifyProduct(payload)
                            }else{
                                await addProduct(payload)
                            }
                        } catch (e) {
                            message.error(e.message, 3)
                        } finally {
                            setLoading(false)
                        }
                        message.success('提交成功')
                        history.goBack();
                    }}
                >
                    <StepsForm.StepForm name="base" title="基本信息"
                        initialValues={{ ...product }}
                    >
                        <EmptyLine></EmptyLine>
                        {
                            product?.id ? (<FormItem name="id" noStyle>
                                <Input type="hidden"></Input>
                            </FormItem>) : null
                        }
                        <FormItem name="title" label="产品名称"
                            rules={[{ required: true, message:"请输入名称" }]}
                            hasFeedback
                        >
                            <Input placeholder="产品名称" ></Input>
                        </FormItem>
                        <ProForm.Group>
                            <FormItem name="departPlace" label="出发地"
                                //rules={[{ required: true }]}
                                hasFeedback
                            >
                                {/* <Input placeholder="出发地" ></Input> */}
                                <CityCascader></CityCascader>
                            </FormItem>
                            <FormItem name="destination" label="目的地"
                                //rules={[{ required: true }]}
                                hasFeedback
                            >
                                {/* <Input placeholder="目的地" ></Input> */}
                                <CityCascader></CityCascader>
                            </FormItem>
                            <FormItem label="行程天数" 
                                //name="days" 
                                //rules={[{ required: true }]}
                                >
                                <Input.Group compact >
                                    <FormItem name="days"
                                        //rules={[{ required: true, message:"请输入行程天数" }]} 
                                        hasFeedback noStyle>
                                        <Input style={{ width: '20%' }} suffix="天" ></Input>
                                    </FormItem>
                                    <FormItem name="nights"
                                        // rules={[{ required: true, message:"请输入行程天数" }]} 
                                        hasFeedback noStyle>
                                        <Input style={{ width: '20%' }} suffix="晚"></Input>
                                    </FormItem>
                                </Input.Group>
                            </FormItem>
                        </ProForm.Group>
                        <ProForm.Group>
                            <FormItem name="imageUrls" label="展示图片"
                                // rules={[{ required: true }]}
                                hasFeedback>
                                <MyImageUpload listType="picture-card" maxCount={6} crop={true}></MyImageUpload>
                            </FormItem>
                        </ProForm.Group>
                        <ProForm.Group>
                            <FormItem name="tags" label="产品特点"
                                // rules={[{ required: true }]}
                                hasFeedback>
                                <Select mode="tags" style={{ minWidth: 600 }} placeholder="请输入特点标签" maxTagCount="4" maxTagTextLength="4">
                                </Select>
                            </FormItem>
                        </ProForm.Group>
                    </StepsForm.StepForm>
                    <StepsForm.StepForm title="图文介绍" name="introduction"
                        initialValues={{...product}}
                    >
                        <FormItem name="introduction" label="图文介绍"
                            // rules={[{ required: true }]}
                            hasFeedback>
                            <MyBraftEditor></MyBraftEditor>
                        </FormItem>
                    </StepsForm.StepForm>
                    <StepsForm.StepForm title="行程" name="schedule"
                        initialValues={{...product}}
                    >
                        <EmptyLine />
                        <FormList name="schedules">
                            {(fields, { }) => (
                                <ScheduleTab fields={fields}></ScheduleTab>
                            )}
                        </FormList>
                    </StepsForm.StepForm>
                    <StepsForm.StepForm title="费用说明" name="fee"
                        initialValues={{...product}}
                    >
                        <FeeInfo></FeeInfo>        
                    </StepsForm.StepForm>
                    {/* <StepsForm.StepForm title="设置价格" name="datePrice"
                        initialValues={{...product}}
                    >
                        <CalendarPrice></CalendarPrice>               
                    </StepsForm.StepForm>
                    <StepsForm.StepForm title="套餐" name="skus"
                        initialValues={{...product}}
                        >
                        <Skus></Skus>
                    </StepsForm.StepForm> */}
                    <StepsForm.StepForm title="订购须知" name="notice"
                        initialValues={{...product}}
                        >
                        <Policy></Policy>
                    </StepsForm.StepForm>
                    {/* 使用说明放出来是为了处理MyBraftEditor的对象被转换处理之后丢失了toHtml的操作 */}
                    <StepsForm.StepForm title="使用说明" name="instructions"
                        initialValues={{...product}}
                        >
                        <FormItem
                            label="使用说明"
                            name="instructions"
                        >
                            <MyBraftEditor></MyBraftEditor>
                        </FormItem>
                    </StepsForm.StepForm>
                </StepsForm>
            </ProCard>
            }
        </PageContainer>
    );
};

export default EditProduct;
