import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

const BabyLimitPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, ageValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = "此线路因服务能力有限，无法接待婴儿（不满"+payload.ageValue+"周岁)出行";
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleInputChange = (v)=>{
        let payload = { ...value }
        payload.ageValue = v;
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
                    <span>此线路因服务能力有限，无法接待婴儿（不满</span>
                    <Select disabled={!checked} 
                        style={{width:"120px"}}
                        defaultValue={ageValue}
                        options={[{label: 1, value:1},{label: 2, value:2},{label: 3, value:3},{label: 4, value:4}]}
                        onChange={(v)=>handleInputChange(v)}>
                    </Select>
                    <span>周岁)出行</span>
                </Space>
                
            </Col>
        </Row>
    )
}

export default BabyLimitPolicy;


