import ProCard from "@ant-design/pro-card"
import { ProFormItem } from "@ant-design/pro-form"
import GuideService from "./GuideService"
import NationalGuideService from "./NationalGuideService"

export default (props) => {

    return (
        <ProCard title="随团服务人员">
            <ProFormItem name={["includeFee", "guideFee", "guideService"]} rules={[{
                validator: (rule, value) => {

                    return Promise.resolve();

                }
            }]}>
                <GuideService></GuideService>
            </ProFormItem>
            <ProFormItem name={["includeFee", "guideFee", "nationalGuideService"]} rules={[{
                validator: (rule, value) => {
                    return Promise.resolve();
                }
            }]}>
                <NationalGuideService></NationalGuideService>
            </ProFormItem>
        </ProCard>
    )
}