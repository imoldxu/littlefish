import ProCard from "@ant-design/pro-card";
import { ProFormItem } from "@ant-design/pro-form";
import { Card, Col, Radio, Row, Select, Space, Tag } from "antd";
import HotelAdultPolicy from "./HotelAdultPolicy";
import HotelChildPolicy from "./HotelChildPolicy";
import HotelStandar from "./HotelStandar";

export default function HotelFee(props) {

    return (
        <ProCard title="住宿">
            <ProFormItem name={["includeFee", "hotelFee", "hotelAdultPolicy"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <HotelAdultPolicy></HotelAdultPolicy>
            </ProFormItem>
            <ProFormItem name={["includeFee", "hotelFee", "hotelStandar"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <HotelStandar></HotelStandar>
            </ProFormItem>
            <ProFormItem name={["includeFee", "hotelFee", "hotelChildPolicy"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <HotelChildPolicy></HotelChildPolicy>
            </ProFormItem>
        </ProCard>
    )
}