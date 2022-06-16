import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

/**
 * 人群限制策略
 * @param {} props 
 * @returns 
 */
const CrowdLimitPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch (payload.selected) {
                    case 1:
                        payload.text = "此线路因服务能力有限，仅接受持有中国有效身份证件的客人预定(不含中国香港、中国澳门和中国台湾),敬请谅解";
                        break;
                    case 2:
                        payload.text = "此线路因服务能力有限，仅接受持有中国有效身份证件的客人预定,敬请谅解";
                        break;
                    default:
                        payload.text = "此线路因服务能力有限，仅接受持有中国有效身份证件的客人预定(不含中国香港、中国澳门和中国台湾),敬请谅解";
                        break;
                }
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleChange =(selected)=>{
        let payload = { selected:1, ...value }
        payload.selected = selected;
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
                <Radio.Group disabled={!checked} 
                    defaultValue={selected}
                    onChange={(e)=>handleChange(e.target.value)}>
                    <Space direction="vertical">
                        <Radio value={1}>此线路因服务能力有限，仅接受持有中国有效身份证件的客人预定(不含中国香港、中国澳门和中国台湾),敬请谅解</Radio>
                        <Radio value={2}>此线路因服务能力有限，仅接受持有中国有效身份证件的客人预定,敬请谅解</Radio>
                    </Space>
                </Radio.Group>
                </Space>
            </Col>
        </Row>
    )
}

export default CrowdLimitPolicy;


