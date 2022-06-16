import AliyunOssUpload from "@/components/AliyunOssUpload"
import MyImageUpload from "@/components/MyImageUpload"
import { Button, Form, Input, Select, Space } from "antd"
import FormItem from "antd/lib/form/FormItem"
import { useMemo, useState } from "react"

export default ()=>{

    const [v, setV] = useState([])
    const [v1, setV1] = useState([])

    return (
        <Space direction="vertical">
            <MyImageUpload listType="picture-card" maxCount={2} onChange={v=>setV(v)}
                value={v}
            />
            <AliyunOssUpload dir="images/"
                listType="picture-card"
                maxCount={2}
                value={v1}
                onChange={v1=>setV1(v1)}
                >
                <Button>上传</Button>
            </AliyunOssUpload>
        </Space>
    )
}