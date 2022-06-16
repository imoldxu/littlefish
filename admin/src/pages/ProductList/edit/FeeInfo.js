import ProCard from "@ant-design/pro-card";
import ProForm, { ProFormGroup, ProFormItem } from "@ant-design/pro-form";
import { Card, Col, Form, Input, Row, Select, Space, Tabs } from "antd";
import Checkbox from "antd/lib/checkbox/Checkbox";
import FormItem from "antd/lib/form/FormItem";
import EmptyLine from "./components/EmptyLine";
import AirplainFee from "./components/fee/AirplainFee";
import GuideFee from "./components/fee/GuideFee";
import HotelFee from "./components/fee/HotelFee";
import LocalTraffic from "./components/fee/LocalTraffic";
import MealFee from "./components/fee/MealFee";
import TicketFee from "./components/fee/TicketFee";
import TrafficFee from "./components/fee/TrafficFee";

export default function FeeInfo(props) {

    return (
        <Tabs defaultActiveKey="1" size="large">            
            <Tabs.TabPane tab="费用包含" key="1">
                <EmptyLine />
                <Space direction="vertical">
                    <TrafficFee></TrafficFee>
                    <HotelFee></HotelFee>
                    <MealFee></MealFee>
                    <TicketFee></TicketFee>
                    <LocalTraffic></LocalTraffic>
                    <GuideFee></GuideFee>
                </Space>
            </Tabs.TabPane>
            <Tabs.TabPane tab="费用不含" key="2">
                <EmptyLine />
                <FormItem label="当地交通" name={["excludeFee","localTraffic"]}>
                    <Input.TextArea style={{height:"200px"}}></Input.TextArea>
                </FormItem>
                <FormItem label="景点门票" name={["excludeFee","scspot"]}>
                    <Input.TextArea style={{height:"200px"}}></Input.TextArea>
                </FormItem>
                <FormItem label="其他" name={["excludeFee","other"]}>
                    <Input.TextArea style={{height:"200px"}}></Input.TextArea>
                </FormItem>
            </Tabs.TabPane>   
        </Tabs>
    )

}