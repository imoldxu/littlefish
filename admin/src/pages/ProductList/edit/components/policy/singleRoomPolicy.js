import { Checkbox, Col, Input, InputNumber, Radio, Row, Select, Space, Tag } from "antd";

const SingleRoomPolicy = (props)=>{

    const { value={}, onChange } = props;
    const { checked=false, selected=1 } = value;
    
    const callOnChange = (payload) => {
        if (onChange) {
            if(payload.checked){
                switch(payload.selected){
                    case 1:
                        payload.text = "本产品可拼房。报价是按照2成人入驻1间房计算的价格，如您的订单产生单房，将安排您与其他同性客人拼房入住。如您要求享受单房，请在页面中选择所需房间数或单人房差选项"
                        break;
                    case 2:
                        payload.text = `本产品不可拼房。报价是按照2成人入驻1间房计算的价格，请在页面中选择所需房间数或单人房差选项`
                        break;
                    default:
                        payload.text = "本产品可拼房。报价是按照2成人入驻1间房计算的价格，如您的订单产生单房，将安排您与其他同性客人拼房入住。如您要求享受单房，请在页面中选择所需房间数或单人房差选项"
                        break;
                }
                onChange(payload);
            }else{                
                onChange(payload)
            }
        }
    }

    const handleChange =(selected)=>{
        let payload = { ...value }
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
                <Radio.Group disabled={!checked} 
                    defaultValue={selected}
                    onChange={(e)=>handleChange(e.target.value)}>
                    <Space direction="vertical">
                        <Radio value={1}>本产品可拼房。报价是按照2成人入驻1间房计算的价格，如您的订单产生单房，将安排您与其他同性客人拼房入住。如您要求享受单房，请在页面中选择所需房间数或单人房差选项</Radio>
                        <Radio value={2}>本产品不可拼房。报价是按照2成人入驻1间房计算的价格，请在页面中选择所需房间数或单人房差选项</Radio>
                    </Space>
                </Radio.Group>
            </Col>
        </Row>
    )
}

export default SingleRoomPolicy;


