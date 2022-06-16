import { Button, Image, message, Space, Tag, Upload } from "antd";
import { useRef, useState } from "react";
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
//import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import { addGoods, downGoods, modifyGoods, queryGoods, upGoods } from '@/services/goods';
import { PlusOutlined, UploadOutlined } from "@ant-design/icons";
import GoodsModal from './goodsModal'
import { regFenToYuan, regYuanToFen } from "@/utils/money";
import { GoodsCategory } from "@/utils/bankendData";

export default () => {

    const [infoModalVisible, handleInfoModalVisible] = useState(false);
    const [infoFormValues, setInfoFormValues] = useState({});
    // const [uploadFile, setUploadFile] = useState([]);

    const actionRef = useRef();

    async function handleDown(id) {
        const hide = message.loading("下架中")
        try {
            await downGoods({ id: id })
        } catch (e) {
            message.error(e.message)
        } finally {
            hide()
        }
        actionRef.current.reload()
    }

    async function handleUp(id) {
        const hide = message.loading("上架中")
        try {
            await upGoods({ id: id })
        } catch (e) {
            message.error(e.message)
        } finally {
            hide()
        }
        actionRef.current.reload()
    }

    async function handleCommit(values) {
        try {
            if (values.id) {
                await modifyGoods(values)
            } else {
                await addGoods(values)
            }
            message.success('提交成功')
            handleInfoModalVisible(false)
            actionRef.current.reload()
        } catch (e) {
            message.error(e.message)
        }
    }

    const columns = [
        // {
        //     title: '药品名称',
        //     dataIndex: 'keys',
        //     hideInTable: true,
        //     fieldProps:{
        //         placeholder: "请输入药品名称或简称的拼音首字母"
        //     }
        // },
        {
            title: '商品名称',
            dataIndex: 'name',
            ellipsis: true,
        },
        {
            title: '商品封面',
            dataIndex: 'cover',
            render: (_, record)=>{
                const {covers} = record
                return (<Image src={covers[0]} height={64}></Image>)
            }
        },
        {
            title: '商品分类',
            dataIndex: 'category',
            valueEnum: GoodsCategory,
        },
        {
            title: '商品标签',
            dataIndex: 'tag',
            search: false,
            ellipsis: true,
            render: (_, record) => {
                 const  {tags} = record
                 return tags.map((tag, index)=>{
                    return (<Tag key={index}>{tag}</Tag>)
                 }) 
                }
        },
        {
            title: '商品价格',
            dataIndex: 'tag',
            search: false,
            ellipsis: true,
            render: (_, record) => {
                 record
                 return (<span>{`${regFenToYuan(record.minPrice)}元-${regFenToYuan(record.maxPrice)}元`}</span>) 
                }
        },
        {
            title: '状态',
            dataIndex: 'state',
            valueEnum: {
                0: {
                    text: '停售',
                    status: 'Error',
                },
                1: {
                    text: '在售',
                    status: 'Success',
                }
            }
        },
        {
            title: '操作',
            dataIndex: 'option',
            valueType: 'option',
            render: (_, record) => (
                <Space>
                    <a
                        onClick={() => {
                            setInfoFormValues(record)
                            handleInfoModalVisible(true)
                        }}
                    >
                        修改
          </a>
          <a
                        onClick={() => {
                            history.push({pathname:'/goods-list/sku', query:{goodsId: record.id}})
                        }}
                    >
                        sku管理
          </a>
                    {
                        record.state === 1 ?
                            (<a
                                onClick={() => {
                                    handleDown(record.id);
                                }}
                            >
                                下架
                            </a>) :
                            (<a
                                onClick={() => {
                                    handleUp(record.id);
                                }}
                            >
                                上架
                            </a>)
                    }
                </Space>

            ),
        },
    ];

    return (
        <PageContainer>
            <ProTable
                headerTitle="商品列表"
                actionRef={actionRef}
                rowKey="key"
                request={async (params) => {
                    try{
                        return await queryGoods(params)
                    }catch(e){
                        message.error(e.message, 3)
                    }
                }
                }
                columns={columns}
                toolBarRender={() => [
                    // <Upload
                    //     name="file"
                    //     fileList={uploadFile}
                    //     action='/api/Goods/excel'
                    //     onChange={(info) => {
                    //         if (info.file.status !== 'uploading') {
                    //             console.log(info.file, info.fileList);
                    //         }
                    //         if (info.file.status === 'done') {
                    //             setUploadFile([])
                    //             message.success(`${info.file.name} 上传成功`);
                    //             actionRef.current.reload()
                    //         } else if (info.file.status === 'error') {
                    //             message.error(`${info.file.name} 上传失败`);
                    //         }
                    //     }}
                    // >
                    //     <Button type="primary" key="upload">
                    //         <UploadOutlined /> <FormattedMessage id="pages.searchTable.upload" defaultMessage="上传" />
                    //     </Button>
                    // </Upload>,
                    <Button
                        type="primary"
                        key="new"
                        onClick={() => {
                            setInfoFormValues({})
                            handleInfoModalVisible(true);
                        }}
                    >
                        <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="新建" />
                    </Button>,
                ]}
            />
            {infoModalVisible?
                <GoodsModal
                    handleCommit={handleCommit}
                    handleInfoModalVisible={() => handleInfoModalVisible(false)}
                    visible={infoModalVisible}
                    values={infoFormValues}
                ></GoodsModal>:null
            }
        </PageContainer>
    );

}