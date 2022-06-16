import ProForm, { ProFormItem, ProFormMoney } from "@ant-design/pro-form"
import { Checkbox, DatePicker, Form, InputNumber, Modal, Radio } from "antd"
import FormItem from "antd/lib/form/FormItem"
import { useRef, useState } from "react"

const weekOptions = [
    { label: "每天", value: 0 },
    { label: "周一", value: 1 },
    { label: "周二", value: 2 },
    { label: "周三", value: 3 },
    { label: "周四", value: 4 },
    { label: "周五", value: 5 },
    { label: "周六", value: 6 },
    { label: "周日", value: 7 },
]

const layout = {
    labelCol: {
        span: 2,
    },
    wrapperCol: {
        span: 22,
    },
}

export default function DatePriceModal(props) {

    const [cycleTime, setCycleTime] = useState(1)
    const [isLoading, setLoading] = useState(false)
    const formRef = useRef()

    const {
        closeModal, //关闭Modal
        visible, //是否可见
        skuId,  //修改的skuId
    } = props;

    const onOk = () => {
        formRef.current
            .validateFields()
            .then(values => {

                console.log(values)
                // values.price = regYuanToFen(values.price, 100)

                setLoading(true)
                try {
                    // let {introductionEditor, specEditor, descriptionEditor, ...payload} = values 
                    // //let payload = {...values}
                    // payload.covers = values.covers.map(cover=>{
                    //     return cover.url
                    // })
                    
                    //commit(payload)
                    closeModal()
                } catch (e) {
                    message.error(e.message, 3)
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
            width={1024}
            destroyOnClose={true}
            title="设置价格"
            visible={visible}
            onOk={onOk}
            confirmLoading={isLoading}
            onCancel={closeModal}
        >
            <ProForm {...layout}
                ref={formRef}
                name="goods"
                initialValues={{ ...initValue }}
                preserve={false}>
                <ProFormItem>
                    <Radio.Group onChange={(e) => setCycleTime(e.target.value)}>
                        <Radio value={1}>按周</Radio>
                        <Radio value={2}>按日期</Radio>
                    </Radio.Group>
                </ProFormItem>
                {/* 按周显示可以特定的设置周几 */}
                {cycleTime == 1 ? 
                    (<ProFormItem>
                        <Checkbox.Group options={weekOptions}></Checkbox.Group>
                    </ProFormItem>)
                    : null
                }
                <ProFormItem name="dateRange">
                    <DatePicker.RangePicker />
                </ProFormItem>
                <ProFormMoney name="adultPrice" label="成人价" locale="zh-CN"
                    min={0}>
                </ProFormMoney>
                <ProFormMoney name="childPrice" label="儿童价" locale="zh-CN"
                    min={0}>
                </ProFormMoney>
                <ProFormMoney name="singleRoomPrice" label="单房差" locale="zh-CN"
                    min={0}>
                </ProFormMoney>
            </ProForm>
        </Modal>
    )
}