import { Cascader } from "antd"
import city from "./antd-city"

export default (props)=>{
    const {value, onChange} = props

    return (
    <Cascader style={{minWidth: "120px"}}
        options={city}
        value={value} //mybe defaultValue={value}
        expandTrigger="hover"
        displayRender={(label)=>{               //显示最后一个
            return label[label.length - 1];
        }}
        onChange={onChange}
    ></Cascader>)
}