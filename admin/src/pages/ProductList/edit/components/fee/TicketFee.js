import ProCard from "@ant-design/pro-card";
import { ProFormItem } from "@ant-design/pro-form";
import TicketAdultPolicy from "./TicketAdultPolicy";
import TicketChildPolicy from "./TicketChildPolicy";

export default function TicketFee(props) {

    return (
        <ProCard title="门票及地面项目">
            <ProFormItem name={["includeFee", "ticketFee", "ticketAdultPolicy"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <TicketAdultPolicy></TicketAdultPolicy>
            </ProFormItem>
            <ProFormItem name={["includeFee", "ticketFee", "ticketChildPolicy"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <TicketChildPolicy></TicketChildPolicy>
            </ProFormItem>
        </ProCard>
    )

}