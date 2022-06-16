import { Checkbox, Col, Row, Select, Space, Tag } from "antd";

const AirplainFee = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, trip, standar } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = `飞机：${payload.trip}${payload.standar}(已含机建、燃油税)`;
                onChange(payload);
            }else{                
                onChange(payload)
            }
            
        }
    }

    const handleTripChange =(trip)=>{
        let payload = { ...value }
        payload.trip = trip;
        callOnChange(payload);
    }

    const handleStandarChange =(standar)=>{
        let payload = { ...value }
        payload.standar = standar;
        callOnChange(payload);
    }


    const handleEnable = (checked)=>{
        let payload = { ...value }
        payload.checked = checked;
        callOnChange(payload);
    }

    return (
        <Row align="middle" gutter="12" wrap={false}>
            <Col>
            <Checkbox checked={checked} onChange={e=>{
                handleEnable(e.target.checked)
            }}></Checkbox>
            </Col>
            <Col>
                <Space>
                    <Tag>飞机</Tag>
                    <Select disabled={!checked} style={{ width: 120 }} onChange={(v)=>{
                        handleTripChange(v)
                    }}
                        defaultValue={trip}
                    >
                        <Select.Option value="往返">往返</Select.Option>
                        <Select.Option value="去程">去程</Select.Option>
                        <Select.Option value="回程">回程</Select.Option>
                    </Select>
                    <Select disabled={!checked} style={{ width: 120 }} defaultValue={standar}
                        onChange={(v)=>{
                            handleStandarChange(v)
                        }}
                        >
                        <Select.Option value="经济舱机票">经济舱机票</Select.Option>
                        <Select.Option value="头等舱机票">头等舱机票</Select.Option>
                    </Select>
                    <span>(已含机建、燃油税)</span>
                </Space>
            </Col>
        </Row>
    )
}

export default AirplainFee;


