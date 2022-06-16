import MyImageUpload from '@/components/MyImageUpload';
import { getCouponTemplateDetail } from '@/services/coupon';
import { deleteDealer } from '@/services/dealer';
import { getGoodsDetail, queryGoods } from '@/services/goods';
import { urlStr2OssFileObj } from '@/utils/aliOss';
import { checkTwoPointNum, regFenToYuan, regYuanToFen } from '@/utils/money';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { Button, Checkbox, Col, DatePicker, Descriptions, Divider, Input, InputNumber, message, Modal, Row, Select, Space, Spin, TimePicker, Transfer } from 'antd';
import Form from 'antd/lib/form/Form';
import FormItem from 'antd/lib/form/FormItem';
import FormList from 'antd/lib/form/FormList';
import TextArea from 'antd/lib/input/TextArea';
import moment from 'moment';
import React, { useEffect, useRef, useState } from "react";
import { useAccess } from 'umi';

const { Option } = Select;

const layout = {
    labelCol: {
        span: 6,
    },
    wrapperCol: {
        span: 18,
    },
}

const userOptions = [
{ label: '新用户', value: 'newUser' },
{ label: '普通用户', value: 'normalUser' },
{ label: '铜牌用户', value: 'copperUser'},
{ label: '银牌用户', value: 'silverUser'},
{ label: '金牌用户', value: 'goldUser' },
{ label: '体验用户', value: 'experienceUser' },
{ label: '合伙用户', value: 'partnerUser' }
]

const parseRule = (rule, ruleKeys)=>{
    const {compositeType, key, rules} =  rule
    if(compositeType==0){
        ruleKeys.push(key)
    } else if(compositeType==1){
        rules.forEach(childRule => {
            parseRule(childRule, ruleKeys)
        });
    } else {
        rules.forEach(childRule => {
            parseRule(childRule, ruleKeys)
        });
    }
}

const buildRule = (ruleKeys)=>{
    let compositeType = 0
    let key = ""
    let rules = []
    if(ruleKeys.indexOf('newUser')>-1){
        if(ruleKeys.length>1){
            compositeType = 1 
            rules = [
                {
                   compositeType: 0,
                   key: 'newUser',
                   rules: [],
                },
                buildRule(ruleKeys.filter(v=>v!='newUser'))
            ]
        }else{
            key = ruleKeys[0]
        }
    }else{
        if(ruleKeys.length>1){
            compositeType = 2
            key = ""
            rules = ruleKeys.map(r=>{return buildRule([r])})
        }else{
            compositeType = 0
            key = ruleKeys[0]
            rules = []
        }
    }

    return {
        compositeType,
        key,
        rules,
    }
}

const CouponModal = (props) => {
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
    const [goodsList, setGoodsList] = useState([]);
    const [targetKeys, setTargetKeys] = useState([]);
    const [matchGoodsType, setMatchGoodsType] = useState(1)
    const [validType, setValidType] = useState(1)
    const [couponType, setCouponType] = useState(1)

    const onTargetGoodsChange = (newTargetKeys, direction, moveKeys) => {
        console.log(newTargetKeys, direction, moveKeys);
        setTargetKeys(newTargetKeys);
    };

    const { id } = values;
    useEffect(async()=>{
        try{
            if(id){
                const template = await getCouponTemplateDetail({id})
                let initValue = {
                    ...template
                }
                delete initValue.cover
                delete initValue.claimStartTime
                delete initValue.claimEndTime
                delete initValue.validTime
                delete initValue.matchUserRules
                delete initValue.minAmount
                delete initValue.faceValue
                //处理图片
                if(template.cover){
                    const coverFile = urlStr2OssFileObj(template.cover, 0);
                    initValue.covers = [coverFile]
                }
                //处理领取时间范围
                initValue.rangeTime = [
                    moment(template.claimStartTime, "YYYY-MM-DD hh:mm:ss"),moment(template.claimEndTime, "YYYY-MM-DD hh:mm:ss")
                ]
                if(template.validTime){
                    initValue.validTime = moment(template.validTime, "YYYY-MM-DD hh:mm:ss")
                }
                //处理用户限定规则
                initValue.userLimits = new Array()
                if(template.matchUserRules){
                    parseRule(template.matchUserRules, initValue.userLimits)
                }
                //处理金额
                if(template.minAmount){
                    initValue.minAmount = regFenToYuan(template.minAmount)
                }
                if(template.faceValue){
                    initValue.faceValue = regFenToYuan(template.faceValue)
                }
                if(template.matchGoods){
                    setGoodsList(template.matchGoods)
                    setTargetKeys(template.matchGoods.map(goods=>goods.id))
                }
                setCouponType(initValue.type)
                setMatchGoodsType(initValue.matchGoodsType)
                setValidType(initValue.validType)
                setInitValue(initValue)
            }
            setInit(true)
        }catch(e){
            message.error(e.message)
        }
    },[id])

    const title = id ? "修改优惠券" : "新建优惠券"              

    const onOk = () => {

        formRef.current
            .validateFields()
            .then(values => {

                if (handleCommit) {
                    setLoading(true)
                    try {
                        let payload = {...values}
                        delete payload.covers;
                        delete payload.userLimits;
                        delete payload.rangeTime;
                        //处理图片
                        payload.cover = values.covers[0].url
                        //处理时间
                        payload.claimStartTime = values.rangeTime[0].format('YYYY-MM-DD hh:mm:ss')
                        payload.claimEndTime = values.rangeTime[1].format('YYYY-MM-DD hh:mm:ss')
                        payload.validTime = values.validTime.format('YYYY-MM-DD hh:mm:ss')
                        //处理用户匹配规则
                        payload.matchUserRules = buildRule(values.userLimits)
                        //处理金额
                        if(values.minAmount){
                            payload.minAmount = regYuanToFen(values.minAmount, 100)
                        }
                        if(values.faceValue){
                            payload.faceValue = regYuanToFen(values.faceValue, 100)
                        }
                        if(values.matchGoods){
                            payload.matchGoods = values.matchGoods.map(v=>v.id)
                        }
                        console.log(payload)
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
                name="coupon-template"
                initialValues={{ ...initValue }}
                preserve={false}>
                {
                    id ? (<FormItem name="id" noStyle>
                        <Input type="hidden"></Input>
                    </FormItem>) : ('')
                }

                <FormItem name="name" label="券名称" rules={[{ required: true }]} hasFeedback>
                    <Input placeholder="券名称" ></Input>
                </FormItem>
                <FormItem name="covers" label="展示图片"
                    // rules={[{ required: true }]}
                    hasFeedback>
                    <MyImageUpload listType="picture-card" maxCount={1} crop={true}></MyImageUpload>
                </FormItem>
                <FormItem name="introduction" label="描述"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Input></Input>
                </FormItem>
                <FormItem name="type" label="类型"
                    rules={[{ required: true }]}
                    hasFeedback
                >
                    <Select onChange={v=>setCouponType(v)}>
                        <Option value={1}>免单券</Option>
                        <Option value={2}>满减券</Option>
                        <Option value={3}>折扣券</Option>
                    </Select>
                </FormItem>
                {couponType!=1?
                    <FormItem name="minAmount" label="最低消费额"
                        rules={[{ required: true }]}
                        hasFeedback>
                        <Input addonAfter="元"></Input>
                    </FormItem> : null
                }
                {
                couponType!=3?
                <FormItem name="faceValue" label="优惠券面值"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Input addonAfter="元"></Input>
                </FormItem>: null
                }
                {
                    couponType==3?
                    <FormItem name="discount" label="折扣"
                        rules={[{ required: true }]}
                        hasFeedback>
                        <InputNumber min={0.01} max={9.99}></InputNumber>折
                    </FormItem>:null
                }
                <FormItem name="total" label="发行量"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <InputNumber></InputNumber>
                </FormItem>
                <FormItem name="validType" label="有效期类型"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Select onChange={v=>setValidType(v)}>
                        <Option value={1}>时间有效</Option>
                        <Option value={2}>时长有效</Option>
                    </Select>
                </FormItem>
                {
                validType==1?
                    <FormItem name="validTime" label="使用截止时间"
                    rules={[{ required: true }]}
                    hasFeedback>
                        <DatePicker showTime/>
                    </FormItem>
                    :
                    <FormItem name="validDuration" label="领取后使用时长"
                    rules={[{ required: true }]}
                    hasFeedback>
                        <Input addonAfter="秒"></Input>
                    </FormItem>
                }
                <FormItem name="claimLimitNum" label="领取限制"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Input addonAfter="张"></Input>
                </FormItem>
                <FormItem name="claimDayLimitNum" label="每日领取限制"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Input addonAfter="张"></Input>
                </FormItem>
                <FormItem name="rangeTime" label="领取时间"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <DatePicker.RangePicker 
                        showTime={{
                            hideDisabledOptions: true,
                            //defaultValue: [moment('00:00:00', 'HH:mm:ss'), moment('11:59:59', 'HH:mm:ss')],
                          }}
                          format="YYYY-MM-DD HH:mm:ss"
                    />
                </FormItem>

                <FormItem name="userLimits" label="匹配用户"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Checkbox.Group options={userOptions}></Checkbox.Group>
                </FormItem>
                <FormItem name="matchGoodsType" label="商品匹配类型"
                    rules={[{ required: true }]}
                    hasFeedback>
                    <Select onChange={v=>setMatchGoodsType(v)}>
                        <Option value={1}>全部</Option>
                        <Option value={2}>按分类</Option>
                        <Option value={3}>指定商品</Option>
                    </Select>
                </FormItem>
                {
                    matchGoodsType == 2 ? 
                    <FormItem name="matchGoodsCategory" label="商品分类"
                        rules={[{ required: true }]}
                        hasFeedback>
                        <Select >
                            <Option value={1}>保健品</Option>
                            <Option value={2}>饮品</Option>
                        </Select>
                    </FormItem> : <></>
                }
                {
                     matchGoodsType == 3 ? 
                    <FormItem name="matchGoods" label="指定商品"
                        rules={[{ required: true }]}
                        hasFeedback>
                        <Transfer
                            showSearch
                            filterOption={(inputValue, option)=>{
                                return option.name.indexOf(inputValue) > -1;//按名字包含来搜索过滤
                            }}
                            listStyle={{
                                width: 152,
                                height: 300,
                            }}
                            onSearch={async (dir, value) => {
                                console.log('search:', dir, value);//搜索框内容变化时的回调
                                if(dir==='left'){
                                    const {data} = await queryGoods({name:value, current:1, pageSize:100})
                                    setGoodsList(data)
                                }
                            }}
                            rowKey={record => record.id}
                            dataSource={goodsList}
                            targetKeys={targetKeys}
                            onChange={onTargetGoodsChange}
                            render={item => item.name}
                            oneWay={false}
                            pagination={{pageSize: 20}}
                        />
                    </FormItem>: <></>
                }
            </Form> : <Spin></Spin>
            }
        </Modal>
    );
};

export default CouponModal;
