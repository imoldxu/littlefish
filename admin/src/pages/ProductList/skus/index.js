import { addSku, querySku, modifySku } from "@/services/product";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons"
import ProCard from "@ant-design/pro-card";
import { PageContainer, PageLoading } from "@ant-design/pro-layout";
import { EditableProTable } from "@ant-design/pro-table";
import { Button, Checkbox, Form, Image, Input, message, Space, Tooltip } from "antd"
import FormItem from "antd/lib/form/FormItem";
import { useEffect, useRef, useState } from "react";
import { history, useHistory, useLocation } from "umi";

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

const Skus = () => {

  const [editableKeys, setEditableRowKeys] = useState()
  const [isLoading, setLoading] = useState(true)
  
  const { query } = history.location;
  const { product_id, title } = query;

  const skuOptions = [
    { label: '出团人数', value: '出团人数' },
    { label: '交通车型', value: '交通车型' },
    { label: '住宿标准', value: '住宿标准' },
  ];

  const prefixColumns = [
    {
      title: '名称',
      dataIndex: 'name',
      formItemProps: () => {
        return {
          rules: [{ required: true, message: '此项为必填项' }],
        };
      },
      // readonly,
    }
  ]
  const suffixColumns = [
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
        return row?.state ? <span style={{color:"green"}}>在售</span> : <span>停售</span>
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
        // <a
        //   key="delete"
        //   onClick={async () => {
        //     //await delSku({ id: record.id })
        //   }}
        // >
        //   删除
        // </a>,
        <a
        key="priceCalendar"
        onClick={() => {
          history.push("/product/sku/price-calendar?sku_id="+record.id+"&product_id="+product_id+"&name="+record.name+"&title="+title)
          }}
        >
          班期管理
        </a>
      ],
    },
  ]

  const [columns, setColumns] = useState([...prefixColumns, ...suffixColumns])
  const [dataSource, setDataSource] = useState([])
  const [initProps, setInitProps] = useState([])

  useEffect(async () => {
    if (product_id) {
      try{
        const rawSkus = await querySku({productId: product_id})

        let rawProps = new Set()
        const initSkus = rawSkus.map(sku => {
          let initValue = {
            id: sku.id,
            name: sku.name,
            state: sku.state == 1 ? true : false,
          }
          //处理SKU的属性字段
          const { properties } = sku 
          for (const key in properties) {
            rawProps.add(key)
            initValue[key] = properties[key]
          }

          return initValue
        });
        setDataSource(initSkus)
        setInitProps(Array.from(rawProps))
        generateSkuTable([...Array.from(rawProps)])
        setLoading(false)
      }catch(e){
        message.error(e.message)
      }
    }
    setLoading(false)
  }, [product_id])

  const generateSkuTable = (value) => {
    const newColumns = [...prefixColumns, ...value.filter(v => {
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
    <PageContainer>
      {!isLoading ? <ProCard title={title}>
        <Tooltip placement="topLeft" title="增减套餐属性需要编辑每一个套餐并保存才可以生效">
        <FormItem label="套餐属性">
          <Checkbox.Group options={skuOptions} defaultValue={initProps} onChange={values => generateSkuTable(values)}>
          </Checkbox.Group>
        </FormItem>
        </Tooltip>
        <EditableProTable
          rowKey="index"
          headerTitle="套餐列表"
          recordCreatorProps={
            {
              position: 'bottom',
              record: () => ({ index: dataSource.length }), //必须设置index，匹配rowKey，否则添加之后会有残留
              creatorButtonText: '新增套餐',
            }
          }
          // toolBarRender={() => []}
          columns={columns}
          // request={async (params) => {
          // }}
          value={dataSource}
          onChange={setDataSource}
          editable={{
            type: 'multiple',
            editableKeys,
            //此处不要编辑模式下的删除
            actionRender: (row, config, defaultDom) => [defaultDom.save, defaultDom.cancel],
            onSave: async (rowKey, data, row) => {
              const { id } = data;
              let payload = {
                name: data.name,
                productId: product_id,
              }
              payload.state = data.state ? 1 : 0; //转换状态
              //处理属性值
              let props = {};
              for (var key in data) {
                //过滤掉固定的key,剩下的为sku的附加属性值
                if (key != 'id' && key != 'name' && key != 'state' && key != 'index') {
                  props[key] = data[key]
                }
              }
              payload.properties = props

              if (id) {
                payload.id = id
                await modifySku(payload)
                // setDataSource(dataSource.map((item, index) => {
                //   if (rowKey == index) {
                //     return data
                //   } else {
                //     return item
                //   }
                // }));
                return data;
              } else {
                const sku = await addSku(payload)
                data.id = sku.id
                data.productId = sku.productId
                //新建的sku需要将其id值刷新到对应的行上
                //setDataSource([...dataSource, data]);
                return data
              }
            },
            onChange: setEditableRowKeys,
          }}
        >
        </EditableProTable>
      </ProCard> : <PageLoading></PageLoading>
      }
    </PageContainer>
  )

}

export default Skus;