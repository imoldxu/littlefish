import { list, signUrl, urlStr2OssFileObj } from '@/utils/aliOss';
import { BookOutlined, MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { PageLoading } from '@ant-design/pro-layout';
import { Badge, Button, Col, Descriptions, Image, List, message, Modal, Row, Select, Space, Spin } from 'antd';
import React, { useEffect, useRef, useState } from "react";

/**
 * 非公共图片库弹窗
 */
export default (props) => {
    const {
        onOk,
        onCancel,
        visible,
        maxCount,
    } = props;

    const [isInit, setInit] = useState(false)
    const [dataSource, setDataSource] = useState([])
    const [selected, setSelected] = useState([])
    const [marker, setMarker] = useState()

    useEffect(async()=>{
        try{
            const result = await list('images/')
            setMarker(result.nextMarker)
            setDataSource(result.objects)
            setInit(true)
        }catch(e){
            message.error(e.message)
        }
    },[])              

    return (
        <Modal
            width={680}
            title='图库'
            visible={visible}
            onOk={()=> {
                //FIXME：是在这里转换，还是在外面转换
                onOk (selected.map(item=>{
                    return {
                        uid: item.name.substring(item.name.lastIndexOf('/') + 1, item.name.lastIndexOf('.')),
                        name: item.name.substring(item.name.lastIndexOf('/') + 1),
                        status: 'done',
                        thumbUrl: signUrl(item.name),
                        url: item.name,
                    }
                }))
            }}
            onCancel={onCancel}
        >
            {isInit ?
            <List
                grid={{ gutter: 16, column: 5 }}
                dataSource={dataSource}
                pagination={{
                    onChange: async page => {
                      if(page*20>dataSource.length && marker != null){
                        const result = await list('images/', marker)
                        setMarker(result.nextMarker)
                        setDataSource([...dataSource, ...result.objects])
                      }
                    },
                    pageSize: 20,
                }}
                renderItem={(item) => {
                    const imgUrl = signUrl(item.name)
                    return (
                        <List.Item onClick={()=>{
                            const index = dataSource.findIndex((data)=>data.name==item.name)
                            if(item.selected){
                                dataSource[index].selected = false;
                                setSelected(selected.filter((data)=>data.name!=item.name))
                            }else{
                                dataSource[index].selected = true;
                                if(maxCount && selected.length>=maxCount){
                                    message.error("选择的图片数量不能大于"+maxCount)
                                    return 
                                }    
                                setSelected([...selected, item])
                            }
                            setDataSource(dataSource)
                        }}>
                            {item.selected?
                            <Badge.Ribbon text={<BookOutlined color="red" />} >
                                <Image src={imgUrl} width={120} height={120} preview={false}></Image>
                            </Badge.Ribbon>
                            : <Image src={imgUrl} width={120} height={120} preview={false}></Image>
                            }
                        </List.Item>
                    )}
                }
            /> : <PageLoading />
            }
        </Modal>
    );
};
