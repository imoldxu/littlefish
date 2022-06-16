import MyImageUpload from "@/components/MyImageUpload";
import { Card, Checkbox, Col, Form, Input, Row, Select, Space, TimePicker } from "antd";
import moment from "moment";
import { useCallback, useEffect, useMemo } from "react";

const MealTodo = (props) => {
    const { value = {}, onChange } = props

    const handleChange = (obj) => {
        onChange({
            durationUnit: '小时',
            mealType: 'breakfast',
            mealAdult: true,
            ...value,
            ...obj,
        })
    }

    return (
        <Card title="餐食" >
            <Row align="middle" gutter={[16, 16]}>
                <Col>
                    <span>时间：</span>
                    <TimePicker format='HH:mm' minuteStep={10} defaultValue={value.time ? moment(value.time, 'HH:mm') : undefined} onChange={(value) => handleChange({ time: value.format('HH:mm') })} />
                </Col>
                <Col><Space><span>持续时间：</span><Input.Group compact>
                    <Input type="number" defaultValue={value.duration} style={{width: "70%"}}
                        onBlur={e => handleChange({ duration: e.target.value })}
                    />
                    <Select defaultValue={value.durationUnit ?? "小时"} onChange={v => handleChange({ durationUnit: v })} >
                        <Select.Option value="分钟">分钟</Select.Option>
                        <Select.Option value="小时">小时</Select.Option>
                    </Select>
                </Input.Group></Space></Col>
                <Col>
                    <Space>
                        <span>类型：</span>
                        <Select defaultValue={value.mealType ?? "breakfast"} onChange={v => handleChange({ mealType: v })}>
                            <Select.Option value="breakfast">早餐</Select.Option>
                            <Select.Option value="lunch">午餐</Select.Option>
                            <Select.Option value="dinner">晚餐</Select.Option>
                        </Select>
                    </Space>
                </Col>
                <Col>
                    <Checkbox defaultChecked={value.mealAdult ?? true}
                        onChange={e => handleChange({ mealAdult: e.target.checked })}
                    >成人是否含餐</Checkbox>
                </Col>
                <Col>
                    <Checkbox defaultChecked={value.mealChild ?? false}
                        onChange={e => handleChange({ mealChild: e.target.checked })}
                    >儿童是否含餐</Checkbox>
                </Col>
                <Col>
                    <Space>
                        <span>描述：</span>
                        <Input.TextArea placeholder="请输入描述信息" defaultValue={value.desc}
                            style={{width: "320px", height:"120px"}}
                            onBlur={e =>
                                handleChange({ desc: e.target.value }) 
                            } maxLength="500"
                            showCount 
                        ></Input.TextArea>
                    </Space>
                </Col>
                <Col>
                    <Space>
                        <span>附图：</span>
                        <MyImageUpload value={value.images}
                            onChange={v => handleChange({ images: v })}
                            listType="picture-card"
                            maxCount={4}
                            crop={true}
                        ></MyImageUpload>
                    </Space>
                </Col>
            </Row>
        </Card>
    )
}

export default MealTodo;