import { Checkbox, Col, Radio, Row, Select, Space, Tag } from "antd";

const MealAdultPolicy = (props) => {

    const { value = {}, onChange } = props;
    const { checked = false, breakfast = 0, lunch = 0, dinner = 0 } = value;

    const callOnChange = (payload) => {
        if (onChange) {
            if (payload.checked) {
                const total = payload.breakfast + payload.lunch + payload.dinner
                if(total==0){
                    payload.text = "不含餐"
                }else{
                    const breakfastText = payload.breakfast > 0 ? `${payload.breakfast}早餐` : ""
                    const lunchText = payload.lunch > 0 ? `${payload.lunch}午餐` : ""
                    const dinnerText = payload.dinner > 0 ? `${payload.dinner}晚餐` : ""
                    payload.text = `成人包含${total}次:${breakfastText}${lunchText}${dinnerText}`
                }
                onChange(payload);
            } else {
                onChange(payload)
            }
        }
    }

    const handleChange = (v) => {
        let payload = { ...value, ...v }
        callOnChange(payload);
    }


    const handleEnable = (checked) => {
        let payload = { breakfast:0, lunch:0, dinner:0, ...value }
        payload.checked = checked;
        callOnChange(payload);
    }

    return (
        <Row align="middle" gutter="12" wrap={false}>
            <Col>
                <Checkbox checked={checked} onChange={e => {
                    handleEnable(e.target.checked)
                }}></Checkbox>
            </Col>
            <Col>
                <Space size="large">
                    <Tag color="blue">成人</Tag>
                    <Space size="small">
                        <Select
                            disabled = {true} 
                            //disabled={!checked}
                            //defaultValue={breakfast}
                            value={breakfast}
                            style={{ width: "80px" }}
                            onChange={(v) => handleChange({ breakfast: v })}>
                            <Select.Option value={0}>0</Select.Option>
                            <Select.Option value={1}>1</Select.Option>
                            <Select.Option value={2}>2</Select.Option>
                            <Select.Option value={3}>3</Select.Option>
                            <Select.Option value={4}>4</Select.Option>
                            <Select.Option value={5}>5</Select.Option>
                            <Select.Option value={6}>6</Select.Option>
                            <Select.Option value={7}>7</Select.Option>
                            <Select.Option value={8}>8</Select.Option>
                            <Select.Option value={9}>9</Select.Option>
                        </Select>
                        <span>早餐</span>
                    </Space>
                    <Space size="small">
                        <Select
                            disabled = {true}  
                            //disabled={!checked}
                            //defaultValue={lunch}
                            value={lunch}
                            style={{ width: "80px" }}
                            onChange={(v) => handleChange({ lunch: v })}>
                            <Select.Option value={0}>0</Select.Option>
                            <Select.Option value={1}>1</Select.Option>
                            <Select.Option value={2}>2</Select.Option>
                            <Select.Option value={3}>3</Select.Option>
                            <Select.Option value={4}>4</Select.Option>
                            <Select.Option value={5}>5</Select.Option>
                            <Select.Option value={6}>6</Select.Option>
                            <Select.Option value={7}>7</Select.Option>
                            <Select.Option value={8}>8</Select.Option>
                            <Select.Option value={9}>9</Select.Option>
                        </Select>
                        <span>午餐</span>
                    </Space>
                    <Space size="small">
                        <Select 
                            disabled = {true} 
                            //disabled={!checked}
                            //defaultValue={dinner}
                            value={dinner}
                            style={{ width: "80px" }}
                            onChange={(v) => handleChange({ dinner: v })}>
                            <Select.Option value={0}>0</Select.Option>
                            <Select.Option value={1}>1</Select.Option>
                            <Select.Option value={2}>2</Select.Option>
                            <Select.Option value={3}>3</Select.Option>
                            <Select.Option value={4}>4</Select.Option>
                            <Select.Option value={5}>5</Select.Option>
                            <Select.Option value={6}>6</Select.Option>
                            <Select.Option value={7}>7</Select.Option>
                            <Select.Option value={8}>8</Select.Option>
                            <Select.Option value={9}>9</Select.Option>
                        </Select>
                        <span>晚餐</span>
                    </Space>
                </Space>
            </Col>
        </Row>
    )
}

export default MealAdultPolicy;