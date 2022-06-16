import ProCard from "@ant-design/pro-card";
import { ProFormItem } from "@ant-design/pro-form";
import LocalTrafficService from "./LocalTrafficService";
import ShuttleService from "./ShuttleService";
import WithGroupTrafficService from "./WithGroupTrafficService";

export default function LocalTraffic(props) {

    return (
        <ProCard title="地面交通">
            <ProFormItem name={["includeFee", "localTrafficFee", "withGroupTrafficService"]} rules={[{
                validator: (rule, value) => {

                    return Promise.resolve();

                }
            }]}>
                <WithGroupTrafficService></WithGroupTrafficService>
            </ProFormItem>
            <ProFormItem name={["includeFee", "localTrafficFee", "localTrafficService"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <LocalTrafficService></LocalTrafficService>
            </ProFormItem>
            <ProFormItem name={["includeFee", "localTrafficFee", "shuttleService"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <ShuttleService></ShuttleService>
            </ProFormItem>
        </ProCard>
    )
}