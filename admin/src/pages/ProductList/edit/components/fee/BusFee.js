import { Checkbox, Col, Row, Select, Space, Tag } from "antd";

const BusFee = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, trip, standar } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = `巴士：${payload.trip}${payload.standar}`;
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
                    <Tag>巴士</Tag>
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
                        <Select.Option value="旅游巴士">旅游巴士</Select.Option>
                    </Select>
                </Space>
            </Col>
        </Row>
    )
}

export default BusFee;


