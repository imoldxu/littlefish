import MyImageUpload from "@/components/MyImageUpload";
import { Card, Checkbox, Col, Input, Row, Select, Space, TimePicker } from "antd";
import moment from "moment";

const HotelTodo = (props) => {
    const { value = {}, onChange } = props

    const handleChange = (obj) => {
        onChange({
            durationUnit: '小时',
            ...value,
            ...obj,
        })
    }

    return (
        <Card title="住宿">
            <Row align="middle" gutter={[16, 16]}>
                <Col><span>时间：</span>
                    <TimePicker format='HH:mm' minuteStep={10} defaultValue={value.time ? moment(value.time, 'HH:mm') : undefined} onChange={(value) => handleChange({ time: value.format('HH:mm') })} />
                </Col>
                <Col>
                    <Space><span>酒店名称：</span>
                        <Input placeholder="请输入酒店名称,如：xx酒店或同级酒店" 
                            onBlur={e =>
                                handleChange({ hotelName: e.target.value })
                            }
                            defaultValue={value.hotelName}>    
                        </Input>
                    </Space>
                </Col>
                <Col>
                    <Space>
                        <span>描述：</span>
                        <Input.TextArea placeholder="请输入描述信息" defaultValue={value.desc}
                            onBlur={e =>
                                handleChange({ desc: e.target.value })
                            } maxLength="500"
                            style={{width: "320px", height:"120px"}}
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

export default HotelTodo;