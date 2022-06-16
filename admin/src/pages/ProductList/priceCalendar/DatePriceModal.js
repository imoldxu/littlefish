import { regYuanToFen } from "@/utils/money"
import ProForm, { ProFormItem, ProFormMoney } from "@ant-design/pro-form"
import { Checkbox, DatePicker, Form, InputNumber, Modal, Radio, Space } from "antd"
import FormItem from "antd/lib/form/FormItem"
import { useRef, useState } from "react"

const weekOptions = [
    { label: "周一", value: 1 },
    { label: "周二", value: 2 },
    { label: "周三", value: 3 },
    { label: "周四", value: 4 },
    { label: "周五", value: 5 },
    { label: "周六", value: 6 },
    { label: "周日", value: 0 },
]

const layout = {
    labelCol: {
        span: 8,
    },
    wrapperCol: {
        span: 16,
    },
}

export default function DatePriceModal(props) {

    const [cycleTime, setCycleTime] = useState(1)
    const [isLoading, setLoading] = useState(false)
    const formRef = useRef()

    const {
        closeModal, //关闭Modal
        commit,
        visible, //是否可见
    } = props;

    const onOk = () => {
        formRef.current
            .validateFields()
            .then(values => {
                console.log(values)
                setLoading(true)
                try {
                    let {dateRange, ...payload} = values
                    payload.adultPrice = regYuanToFen(values.adultPrice, 100)
                    payload.childPrice = regYuanToFen(values.childPrice, 100)
                    payload.singleRoomPrice = regYuanToFen(values.singleRoomPrice, 100)
                    payload.startDate = dateRange[0].format("YYYY-MM-DD") 
                    payload.endDate = dateRange[1].format("YYYY-MM-DD") 
                    commit(payload)
                    closeModal()
                } catch (e) {
                    console.log(e)
                    message.error(e.message?? e, 3)
                } finally {
                    setLoading(false)
                }
            })
            .catch(info => {
                console.log('校验失败:', info);
            });
    }

    return (
        <Modal
            width={680}
            destroyOnClose={true}
            title="设置价格"
            visible={visible}
            onOk={onOk}
            confirmLoading={isLoading}
            onCancel={closeModal}
        >
            <Form 
                {...layout}
                ref={formRef}
                layout="horizontal"
                name="datePrice"
                // initialValues={{  }}
                preserve={false}>
                <FormItem label="时间">
                    <Space direction="vertical">   
                        <FormItem noStyle>
                            <Radio.Group onChange={(e) => setCycleTime(e.target.value)} defaultValue={1}>
                                <Radio value={1}>按周</Radio>
                                <Radio value={2}>按日期</Radio>
                            </Radio.Group>
                        </FormItem>
                        {/* 按周显示可以特定的设置周几 */}
                        {cycleTime == 1 ? 
                            (<FormItem name="dayOfWeeks" noStyle rules={[{ required: true, message:"请选择至少一天" }]}>
                                <Checkbox.Group options={weekOptions}></Checkbox.Group>
                            </FormItem>)
                            : null
                        }
                        <FormItem name="dateRange" noStyle rules={[{ required: true, message:"请选择时间范围" }]} >
                            <DatePicker.RangePicker />
                        </FormItem>
                    </Space> 
                </FormItem>
                <FormItem name="adultPrice" label="成人价" rules={[{ required: true, message:"请输入成人价格" }]} >
                    <InputNumber min={0} addonAfter="元"></InputNumber>    
                </FormItem>
                <FormItem name="childPrice" label="儿童价" rules={[{ required: true, message:"请输入儿童价格" }]} >
                    <InputNumber min={0} addonAfter="元"></InputNumber>    
                </FormItem>
                <FormItem name="singleRoomPrice" label="单房差" rules={[{ required: true, message:"请输入单房差" }]} >
                    <InputNumber min={0} addonAfter="元"></InputNumber>    
                </FormItem>
            </Form>
        </Modal>
    )
}