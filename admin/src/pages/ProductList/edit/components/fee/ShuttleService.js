import { Checkbox, Col, Row, Select, Space, Tag } from "antd";

const ShuttleService = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, standar=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                const { standar } = payload;
                const standarText = standar==1? "专":"拼";  
                payload.text = `目的地${standarText}车接送机(站)服务`;
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
        let payload = { standar:1, ...value }
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
                    <span>目的地</span>
                    <Select disabled={!checked} style={{ width: 120 }} onChange={(v)=>{
                        handleStandarChange(v)
                    }}
                        defaultValue={standar}
                    >
                        <Select.Option value={1}>专</Select.Option>
                        <Select.Option value={2}>拼</Select.Option>
                    </Select>
                    <span>车接送机(站)服务</span>
                </Space>
            </Col>
        </Row>
    )
}

export default ShuttleService;


