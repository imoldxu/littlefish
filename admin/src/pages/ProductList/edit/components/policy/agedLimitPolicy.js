import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

/**
 * 老年人限制政策
 * @param {} props 
 * @returns 
 */
const AgedLimitPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, ageValue } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                payload.text = "此线路行程强度较大，请确保身份健康适宜旅游，如出行人中有"+payload.ageValue+"周岁（含）以上老人，须至少有1位18周岁-69周岁亲友陪同方可参团，敬请谅解";
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
                <Space wrap={true}>
                    <span>此线路行程强度较大，请确保身份健康适宜旅游，如出行人中有</span>
                    <Select disabled={!checked} 
                        style={{width:"120px"}}
                        defaultValue={ageValue}
                        options={[{label: 60, value:60},{label: 65, value:65},{label: 70, value:70},{label: 75, value:75},{label: 80, value:80}]}
                        onChange={(v)=>handleInputChange(v)}>
                    </Select>
                    <span>周岁(含)以上老人，须至少有1位18周岁-69周岁亲友陪同方可参团，敬请谅解</span>
                </Space>
                
            </Col>
        </Row>
    )
}

export default AgedLimitPolicy;


