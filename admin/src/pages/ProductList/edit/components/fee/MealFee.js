import ProCard from "@ant-design/pro-card";
import { ProFormItem } from "@ant-design/pro-form";
import MealAdultPolicy from "./MealAdultPolicy";
import MealChildPolicy from "./MealChildPolicy";

export default function MealFee(props) {

    return (
        <ProCard title="餐食">
            <ProFormItem name={["includeFee", "mealFee", "mealAdultPolicy"]} rules={[{
                validator: (rule, value) => {

                    return Promise.resolve();

                }
            }]}>
                <MealAdultPolicy></MealAdultPolicy>
            </ProFormItem>
            <ProFormItem name={["includeFee", "mealFee", "mealChildPolicy"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <MealChildPolicy></MealChildPolicy>
            </ProFormItem>
        </ProCard>
    )

}