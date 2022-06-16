import { Button, Image, message, Space, Upload } from "antd";
import { useRef, useState } from "react";
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
//import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import { queryGroupTour, addGroupTour } from '@/services/groupTour';
import { PlusOutlined, UploadOutlined } from "@ant-design/icons";
import GroupTourModal from './edit'
import { regFenToYuan, regYuanToFen } from "@/utils/money";
import { changeProductState, queryProduct } from "@/services/product";

export default () => {

    const [infoModalVisible, handleInfoModalVisible] = useState(false);
    const [infoFormValues, setInfoFormValues] = useState({});
    const [uploadFile, setUploadFile] = useState([]);

    const actionRef = useRef();

    async function handleCommit(values) {
        try {
            if (values.id) {
                await modifyDrug(values)
            } else {
                await addDrug(values)
            }
            message.success('提交成功')
            handleInfoModalVisible(false)
            actionRef.current.reload()
        } catch (e) {
            message.error(e.message)
        }
    }

    const columns = [
        {
            title: '编号',
            dataIndex: 'id',
            ellipsis: true,
        },
        {
            title: '名称',
            dataIndex: 'title',
            ellipsis: true,
        },
        {
            title: '封面',
            dataIndex: 'cover',
            search: false,
            render: (_, record)=>{
                const {imageUrls} = record
                return (<Image src={imageUrls?.[0]} height={64}></Image>)
            }
        },
        {
            title: '出发地',
            dataIndex: 'departPlace',
            search: false,
            ellipsis: true,
        },
        {
            title: '目的地',
            dataIndex: 'destination',
            search: false,
            ellipsis: true,
        },
        {
            title: '产品类型',
            dataIndex: 'type',
            ellipsis: true,
            valueEnum: {
                1: {
                    text: '跟团游',
                },
                2: {
                    text: '自由行',
                }
            }
        },
        {
            title: '行程天数',
            dataIndex: 'days',
            ellipsis: true,
        },
        // {
        //     title: '库存',
        //     dataIndex: 'stock',
        //     search: false,
        // },
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
                <Space size="small">
                    <a  onClick={() => {
                            history.push("/product/edit?id="+record.id)
                        }}
                    >
                        修改
                    </a>
                    <a  onClick={() => {
                            history.push("/product/sku?product_id="+record.id+"&title="+record.title)
                        }}
                    >
                        套餐管理
                    </a>
                    {
                        record.state === 1 ?
                            (<a onClick={async () => {
                                    const hide = message.loading("下架中")
                                    try {
                                        await changeProductState({ id: record.id, state: 0 })
                                    } catch (e) {
                                        message.error(e.message)
                                    } finally {
                                        hide()
                                    }
                                    actionRef.current.reload()
                                }}
                            >
                                下架
                            </a>) :
                            (<a onClick={async () => {
                                    const hide = message.loading("上架中")
                                    try {
                                        await changeProductState({ id: record.id, state: 1 })
                                    } catch (e) {
                                        message.error(e.message)
                                    } finally {
                                        hide()
                                    }
                                    actionRef.current.reload()
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
                headerTitle="产品库"
                actionRef={actionRef}
                rowKey="key"
                request={async (params) => {
                    try{
                        return await queryProduct(params)
                    }catch(e){
                        message.error(e.message, 3)
                    }
                }}
                columns={columns}
                toolBarRender={() => [
                    <Button
                        type="primary"
                        key="new"
                        onClick={() => {
                            history.push({
                                pathname:"/product/new",
                                query: {
                                }
                            })
                            // setInfoFormValues({})
                            // handleInfoModalVisible(true);
                        }}
                    >
                        <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="新建" />
                    </Button>,
                ]}
            />
            {/* <GroupTourModal
                handleCommit={handleCommit}
                handleInfoModalVisible={() => handleInfoModalVisible(false)}
                visible={infoModalVisible}
                values={infoFormValues}
            ></GroupTourModal> */}
        </PageContainer>
    );

}