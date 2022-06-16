import MyImageUpload from "@/components/MyImageUpload";
import { addSku, delSku, getSkus, modifySku } from "@/services/goods";
import { urlStr2OssFileObj } from "@/utils/aliOss";
import { regFenToYuan, regYuanToFen } from "@/utils/money";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons"
import ProForm from "@ant-design/pro-form";
import { PageContainer } from "@ant-design/pro-layout";
import { EditableProTable } from "@ant-design/pro-table";
import { Button, Form, Image, Input, message, Space } from "antd"
import FormItem from "antd/lib/form/FormItem";
import { useEffect, useRef, useState } from "react";
import { useHistory, useLocation } from "umi";

const formItemLayout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 16,
  },
};
const formItemLayoutWithOutLabel = {
  wrapperCol: {
    span: 24,
  },
};

export default () => {

  const [editableKeys, setEditableRowKeys] = useState()
  const formRef = useRef()
  const propsFormRef = useRef()
  const [columns, setColumns] = useState([...prefixColumns, ...suffixColumns])

  const location = useLocation()
  const { query } = location;
  const { goodsId, name } = query;

  const [initValue, setInitValue] = useState([])
  const [isInit, setInit] = useState(false)
  const [initProps, setInitProps] = useState([])

  const prefixColumns = [
    {
      title: '名称',
      dataIndex: 'name',
      formItemProps: () => {
        return {
          rules: [{ required: true, message: '此项为必填项' }],
        };
      },
    },
    {
      title: '图片',
      dataIndex: 'images',
      renderFormItem: () => {
        return (<MyImageUpload listType="picture-card" maxCount="1" crop={true}></MyImageUpload>)
      },
      render: (_, row) => {
        return row?.images?.map(imageUrl => <Image src={imageUrl.thumbUrl} height={64} />)
      },
    },
    {
      title: '价格',
      dataIndex: 'price',
      valueType: 'money',
      formItemProps: () => {
        return {
          rules: [{ required: true, message: '此项为必填项' }],
        };
      },
    },
  ]
  const suffixColumns = [
    {
      title: '库存',
      dataIndex: 'stock',
      valueType: 'digit',
      formItemProps: () => {
        return {
          rules: [{ required: true, message: '此项为必填项' }],
        };
      },
    },
    {
      title: '状态',
      key: 'state',
      dataIndex: 'state',
      // valueType: 'select',
      valueType: 'switch', //true false
      // valueEnum: {
      //   false: {
      //     text: '下架',
      //     status: 'Error',
      //   },
      //   true: {
      //     text: '上架',
      //     status: 'Success',
      //   },
      // },
      render: (_, row) => {
        return row?.state? '在售' : '停售'
      },
      formItemProps: () => {
        return {
          rules: [{ required: true, message: '此项为必填项' }],
        };
      },
    },
    {
      title: '操作',
      valueType: 'option',
      width: 200,
      render: (text, record, index, action) => [
        <a
          key="editable"
          onClick={() => {
            action?.startEditable?.(index);
          }}
        >
          编辑
        </a>,
        <a
          key="delete"
          onClick={async () => {
            await delSku({ id: record.id })
            const tableDataSource = formRef.current?.getFieldValue('skus');
            formRef.current?.setFieldsValue({
              skus: tableDataSource.filter((item) => item.id !== record.id),
            });
          }}
        >
          删除
        </a>,
      ],
    },
  ]

  useEffect(async () => {
    try {
      const rawSkus = await getSkus({ id: goodsId })
      let initProps = new Set()
      const initSkus = rawSkus.map(sku => {
        let initValue = {
          id: sku.id,
          name: sku.name,
          stock: sku.stock,
          state: sku.state == 1 ? true : false,
        }
        initValue.price = regFenToYuan(sku.price)
        //转换
        if (sku?.image) {
          const image = urlStr2OssFileObj(sku.image, 0)
          initValue.images = [image]
        }
        //处理SKU的属性字段
        const { properties } = sku
        const propsObj = JSON.parse(properties)
        for (const key in propsObj) {
          initProps.add(key)
          initValue[key] = propsObj[key]
        }

        return initValue
      });
      setInitValue(initSkus)
      setInitProps(initProps)
      generateSkuTable({ properties: [...initProps] })
      setInit(true)
    } catch (e) {
      message.error(e.message)
    }
  }, [goodsId])

  const generateSkuTable = (value) => {
    console.log(value)
    const newColumns = [...prefixColumns, ...value.properties.filter(v => {
      return v != undefined
    }).map(v => {
      return {
        title: v,
        dataIndex: v,
      }
    }), ...suffixColumns];
    setColumns(newColumns)
  }

  return (
    <PageContainer
      loading={!isInit}
    >
      {isInit ? <>
        <Form onFinish={generateSkuTable}
          ref={propsFormRef}
          initialValues={{ properties: [...initProps] }}
          {...formItemLayout}
          onValuesChange={(_, values) => generateSkuTable(values)}
        >
          <Form.List name="properties">
            {(fields, { add, remove }) => (
              <>
                <Form.Item>
                  <Button type="dashed" onClick={() => add()} icon={<PlusOutlined />}>
                    添加属性
                  </Button>
                </Form.Item>
                {fields.map(({ key, name, fieldKey, ...restField }) => (
                  <>
                    <Space align="center">
                      <Form.Item
                        {...restField}
                        label="属性名"
                        name={[name]}
                        fieldKey={[fieldKey]}
                        rules={[{ required: true }]}
                      >
                        <Input placeholder="属性名称" />
                      </Form.Item>
                      <Form.Item>
                        <MinusCircleOutlined onClick={() => {
                          const properties = propsFormRef.current?.getFieldValue('properties')
                          const propName = properties[name]

                          const tableDataSource = formRef.current?.getFieldValue('skus');
                          const skus = tableDataSource.map((data) =>{
                            if(data[propName]){
                              delete data[propName]
                              const { id } = data;
                              if (id) {
                                //处理属性值
                                let payload = {};
                                let props = {};
                                for (var key in data) {
                                  //过滤掉固定的key,剩下的为sku的附加属性值
                                  if (key != 'id' && key != 'name' && key != 'price' && key != 'stock' && key != 'state' && key != 'images' && key != 'index') {
                                    props[key] = data[key]
                                  }
                                }
                                payload.properties = JSON.stringify(props)
                                payload.id = id
                                modifySku(payload).catch(e=>{
                                  message.error('sku删除对应属性失败，请手动保存')
                                })
                              }
                            }
                            return data;
                          });
                          formRef.current?.setFieldsValue({
                            skus
                          });
                          remove(name) //删除value
                        }
                        } />
                      </Form.Item>
                    </Space>
                    {/* <Form.List 
                                     {...restField}
                                     name={[name, 'value']}
                                     fieldKey={[fieldKey, 'value']}
                                     rules={[{ required: true }]}
                                    >
                                    {(fields, { add, remove }) => (
                                        <Space align="center">
                                            {fields.map(({ key, name, fieldKey, ...restField }, index) => (
                                                <Space align="center">
                                                <Form.Item
                                                    {...restField}
                                                    {...formItemLayoutWithOutLabel}
                                                    //{...(index === 0 ? formItemLayout : formItemLayoutWithOutLabel)}
                                                    //label={index === 0 ? '属性值' : '' }
                                                    name={[name]}
                                                    fieldKey={[fieldKey]}
                                                    rules={[{ required: true}]}
                                                    hasFeedback>
                                                    <Input placeholder="请输入属性值" ></Input>
                                                </Form.Item>
                                                <Form.Item>
                                                    <MinusCircleOutlined onClick={() => remove(name)} />
                                                </Form.Item>
                                                </Space>
                                            ))}
                                            <Form.Item>
                                            <Button type="dashed" onClick={() => add()} icon={<PlusOutlined />}>
                                                添加属性值
                                            </Button>
                                            </Form.Item>
                                        </Space>
                                    )}
                                </Form.List> */}
                  </>
                ))}

              </>
            )}
          </Form.List>
          {/* <Form.Item>
                <Button type="primary" htmlType="submit">
                    Submit
                </Button>
            </Form.Item> */}
        </Form>
        <ProForm
          //onFinish={handleFinish}
          //defaultValue={{skus:[...initValue]}}
          initialValues={{ skus: [...initValue] }}
          formRef={formRef}
          submitter={false}
        >
          <Form.Item name="skus">
            <EditableProTable
              //rowKey="id"
              headerTitle="SKU列表"

              recordCreatorProps={
                {
                  position: 'bottom',
                  record: {},//() => ({ id: 'new'+(Math.random() * 1000000).toFixed(0) }),
                  creatorButtonText: '新增Sku',
                }
              }
              // toolBarRender={() => []}
              columns={columns}
              // request={async () => ({
              //   data: defaultData,
              //   total: 3,
              //   success: true,
              // })}
              // value={dataSource}
              // onChange={setDataSource}
              editable={{
                type: 'multiple',
                editableKeys,
                //此处不要编辑模式下的删除
                actionRender: (row, config, defaultDom) => [defaultDom.save, defaultDom.cancel],
                onSave: async (rowKey, data, row) => {
                  const { id } = data;
                  let payload = {
                    name: data.name,
                    stock: data.stock,
                    goodsId: goodsId
                  }
                  payload.image = data.images[0]?.url //处理图片
                  payload.price = regYuanToFen(data.price, 100)//处理价格
                  payload.state = data.state ? 1 : 0; //转换状态
                  //处理属性值
                  let props = {};
                  for (var key in data) {
                    //过滤掉固定的key,剩下的为sku的附加属性值
                    if (key != 'id' && key != 'name' && key != 'price' && key != 'stock' && key != 'state' && key != 'images' && key != 'index') {
                      props[key] = data[key]
                    }
                  }
                  payload.properties = JSON.stringify(props)

                  if (id) {
                    payload.id = id
                    await modifySku(payload)
                  } else {
                    const sku = await addSku(payload)
                    //新建的sku需要将其id值刷新到对应的行上
                    const tableDataSource = formRef.current?.getFieldValue('skus');
                    formRef.current?.setFieldsValue({
                      skus: tableDataSource.map((item, index) => {
                        if (rowKey !== index) {
                          item.id = sku.id
                          return item
                        } else {
                          return item
                        }
                      }),
                    });
                  }
                },
                onChange: setEditableRowKeys,
              }}
            >
            </EditableProTable>
          </Form.Item>
          {/* <Form.Item>
                <Button type="primary" htmlType="submit">提交</Button>
            </Form.Item> */}
        </ProForm></>
        : null}
    </PageContainer>
  )

}