import AliyunOSSUpload from '@/components/AliyunOssUpload';
import { addGroupTour } from '@/services/groupTour';
import { checkTwoPointNum, regFenToYuan, regYuanToFen } from '@/utils/money';
import { Button, Card, Col, Descriptions, Divider, Input, InputNumber, message, Modal, Radio, Row, Select, Tabs, Timeline, TimePicker } from 'antd';
import { history } from 'umi';
import Form from 'antd/lib/form/Form';
import FormItem from 'antd/lib/form/FormItem';
import FormList from 'antd/lib/form/FormList';
import React, { useCallback, useRef, useState } from "react";
import { useAccess } from 'umi';
import { PageContainer } from '@ant-design/pro-layout';
import ProCard from '@ant-design/pro-card';
import ProForm, { ProFormGroup, StepsForm } from '@ant-design/pro-form';
import { PlusCircleOutlined } from '@ant-design/icons';
import TodoList from './components/schedule/TodoList';
import MyImageUpload from '@/components/MyImageUpload';

const { TabPane } = Tabs;

export default function ScheduleTab(props) {

    const [tab, setTab] = useState('Day1')

    const { fields } = props

    return (
        <ProCard
            tabs={{
                tabPosition: 'left',
                defaultActiveKey: 'Day1',
                activeKey: tab,
                onChange: (key) => {
                    setTab(key);
                },
            }}
        >
            {
                fields.map(({ key, name, fieldKey, ...restField }, index) => (
                    <ProCard.TabPane key={`Day${name+1}`} tab={`Day${name+1}`}
                        // style={{width:'900px'}}
                        >
                        <FormItem
                            {...restField}
                            name={[name, "plan"]}
                            fieldKey={[fieldKey, "plan"]}
                            label={`Day${name+1}主要安排`}
                            rules={[{ required: true }]}
                            >
                            <Input placeholder="请输入大致安排，如：成都-九寨沟"></Input>
                        </FormItem>
                        <FormItem name={[name, "todoList"]}
                            fieldKey={[fieldKey, "todoList"]}
                            >
                            <TodoList></TodoList>
                        </FormItem>
                    </ProCard.TabPane>
                ))
            }
        </ProCard>
    )

}