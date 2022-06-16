import { Checkbox, Col, Row, Select, Space, Tag } from "antd";

const HotelStandar = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, standar="2人" } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = `酒店标准${payload.standar?? "2人"}间`;
                onChange(payload);
            }else{                
                onChange(payload)
            }
            
        }
    }

    const handleStandarChange =(standar)=>{
        let payload = { ...value }
        payload.standar = standar;
        callOnChange(payload);
    }


    const handleEnable = (checked)=>{
        let payload = { standar:2, ...value }
        payload.checked = checked;
        callOnChange(payload);
    }

    return (
        <Row align="middle" gutter="12">
            <Col flex="20px">
            <Checkbox checked={checked} onChange={e=>{
                handleEnable(e.target.checked)
            }}></Checkbox>
            </Col>
            <Col flex="auto">
                <Space>
                    <span>酒店标准</span>
                    <Select style={{ width: 120 }} 
                        disabled={!checked}
                        defaultValue={standar} 
                        onChange={(v)=>{
                            handleStandarChange(v)
                        }}
                        >
                        <Select.Option value="1人">1人</Select.Option>
                        <Select.Option value="2人">2人</Select.Option>
                        <Select.Option value="3人">3人</Select.Option>
                        <Select.Option value="4人">4人</Select.Option>
                        <Select.Option value="5人">5人</Select.Option>
                        <Select.Option value="6人">6人</Select.Option>
                    </Select>
                    <span>间</span>
                </Space>
            </Col>
        </Row>
    )
}

export default HotelStandar;


