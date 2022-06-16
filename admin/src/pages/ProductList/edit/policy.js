import { Button, Card, Col, InputNumber, message, Row, Select, Space, Tabs, TimePicker } from 'antd';
import React, { useCallback, useRef, useState } from "react";
import BabyLimitPolicy from './components/policy/babyLimitPolicy';
import AgedLimitPolicy from './components/policy/agedLimitPolicy';
import JuvenilesLimitPolicy from './components/policy/JuvenilesLimitPolicy';
import OtherLimitPolicy from './components/policy/otherLimitPolicy';
import CrowdLimitPolicy from './components/policy/crowdLimitPolicy';
import GravidaLimitPolicy from './components/policy/gravidaLimitPolicy';
import CancelPolicy from './components/policy/cancelPolicy';
import NoticePolicy from './components/policy/noticePolicy';
import ConfirmPolicy from './components/policy/confirmPolicy';
import MinimumMemberPolicy from './components/policy/minimumMemberPolicy';
import ProCard from '@ant-design/pro-card';
import { ProFormItem } from '@ant-design/pro-form';
import TabPane from '@ant-design/pro-card/lib/components/TabPane';
import FormItem from 'antd/lib/form/FormItem';
import MyBraftEditor from '@/components/MyBraftEditor';
import EmptyLine from './components/EmptyLine';

const Policy = (props) => {

    return (

        <Tabs defaultActiveKey="1" size="large">
            <Tabs.TabPane tab="人员限制" key="1">
                <EmptyLine />
                <ProCard title="年龄限制">
                    <ProFormItem name={["policy", "limit", "age", "juveniles"]} 
                        // rules={[{
                        //     validator: (rule, value) => {
                        //         return Promise.resolve();
                        //     }
                        // }]}
                    >
                        <JuvenilesLimitPolicy></JuvenilesLimitPolicy>
                    </ProFormItem>
                    <ProFormItem name={["policy", "limit", "age", "aged"]} 
                        // rules={[{
                        //     validator: (rule, value) => {
                        //         return Promise.resolve();
                        //     }
                        // }]}
                    >
                        <AgedLimitPolicy></AgedLimitPolicy>
                    </ProFormItem>
                    <ProFormItem name={["policy", "limit", "age", "baby"]} 
                        // rules={[{
                        //     validator: (rule, value) => {
                        //         return Promise.resolve();
                        //     }
                        // }]}
                    >
                        <BabyLimitPolicy></BabyLimitPolicy>
                    </ProFormItem>
                </ProCard>
                <ProCard title="人群限制">
                    <ProFormItem name={["policy", "limit", "group", "crowd"]} 
                        // rules={[{
                        //     validator: (rule, value) => {
                        //         return Promise.resolve();
                        //     }
                        // }]}
                    >
                        <CrowdLimitPolicy></CrowdLimitPolicy>
                    </ProFormItem>
                    <ProFormItem name={["policy", "limit", "group", "gravida"]} 
                        // rules={[{
                        //     validator: (rule, value) => {
                        //         return Promise.resolve();
                        //     }
                        // }]}
                    >
                        <GravidaLimitPolicy></GravidaLimitPolicy>
                    </ProFormItem>
                </ProCard>
                <ProCard title="其他限制">
                    <ProFormItem name={["policy", "limit", "other"]} 
                        // rules={[{
                        //     validator: (rule, value) => {
                        //         return Promise.resolve();
                        //     }
                        // }]}
                    >
                        <OtherLimitPolicy></OtherLimitPolicy>
                    </ProFormItem>
                </ProCard>
            </Tabs.TabPane>
            <Tabs.TabPane tab="服务信息" key="2">
                <EmptyLine />
                <FormItem
                    label="确认政策"
                    name={["policy", "service", "confirm"]}
                >
                    <ConfirmPolicy></ConfirmPolicy>
                </FormItem>
                <FormItem
                    label="通知书/确认单"
                    name={["policy", "service", "notice"]}
                >
                    <NoticePolicy></NoticePolicy>
                </FormItem>
                <FormItem
                    label="成团政策"
                    name={["policy", "team", "minimumiMember"]}
                >
                    <MinimumMemberPolicy></MinimumMemberPolicy>
                </FormItem>
            </Tabs.TabPane>
            <Tabs.TabPane tab="违约条款" key="3">
                <EmptyLine />
                <FormItem
                    label="用户违约"
                    name={["policy","cancel", "user"]}
                >
                    <CancelPolicy key="user"></CancelPolicy>
                </FormItem>
                <FormItem
                    label="商家违约"
                    name={["policy", "cancel", "store"]}
                >
                    <CancelPolicy key="store"></CancelPolicy>
                </FormItem>    
            </Tabs.TabPane>
            {/* <Tabs.TabPane tab="使用说明" key="4">
                <EmptyLine />
                <FormItem
                    label="使用说明"
                    name={["policy", "instructions"]}
                >
                    <MyBraftEditor></MyBraftEditor>
                </FormItem>
            </Tabs.TabPane> */}
        </Tabs>

    )


}

export default Policy;