import ProCard from "@ant-design/pro-card";
import { ProFormItem } from "@ant-design/pro-form";
import { Card, Col, Row, Select, Space, Tag } from "antd";
import AirplainFee from "./AirplainFee";
import BusFee from "./BusFee";
import TrainFee from "./TrainFee";

export default function BigTrafficFee(props) {

    return (
        <ProCard title="大交通">
            <ProFormItem name={["includeFee", "bigTraffic", "airplainFee"]} rules={[{
                validator: (rule, value) => {
                    if (value && value.checked) {
                        if (!value.trip) {
                            return Promise.reject('请输入往返')
                        } else if (!value.standar) {
                            return Promise.reject('请输入规格')
                        } else {
                            return Promise.resolve();
                        }
                    } else {
                        return Promise.resolve();
                    }
                }
            }]}>
                <AirplainFee></AirplainFee>
            </ProFormItem>
            <ProFormItem name={["includeFee", "bigTraffic", "trainFee"]} rules={[{
                validator: (rule, value) => {
                    if (value && value.checked) {
                        if (!value.trip) {
                            return Promise.reject('请输入往返')
                        } else if (!value.standar) {
                            return Promise.reject('请输入规格')
                        } else {
                            return Promise.resolve();
                        }
                    } else {
                        return Promise.resolve();
                    }
                }
            }]}>
                <TrainFee></TrainFee>
            </ProFormItem>
            <ProFormItem name={["includeFee", "bigTraffic", "busFee"]} rules={[{
                validator: (rule, value) => {
                    if (value && value.checked) {
                        if (!value.trip) {
                            return Promise.reject('请输入往返')
                        } else if (!value.standar) {
                            return Promise.reject('请输入规格')
                        } else {
                            return Promise.resolve();
                        }
                    } else {
                        return Promise.resolve();
                    }
                }
            }]}>
                <BusFee></BusFee>
            </ProFormItem>
        </ProCard>
    )
}