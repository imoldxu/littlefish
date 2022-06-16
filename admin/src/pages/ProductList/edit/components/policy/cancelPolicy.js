import { Button, Card, Col, InputNumber, message, Row, Select, Space, TimePicker } from 'antd';
import React, { useCallback, useRef, useState } from "react";
import { MinusCircleOutlined, PlusCircleOutlined, PlusOutlined } from '@ant-design/icons';
// 直接拖动相关
import { DndProvider, useDrag, useDrop } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import update from 'immutability-helper';

const CancelPolicy = (props)=>{

    const { value=[], onChange } = props

     //直接拖动相关 
     const moveRow = useCallback(
        (dragIndex, hoverIndex) => {
            const dragRow = value[dragIndex];
            console.log('onchange drageIndex ',dragIndex, hoverIndex)
            onChange(
                update(value, {
                    $splice: [
                        [dragIndex, 1],
                        [hoverIndex, 0, dragRow],
                    ],
                }),
            );
        },
        [value],
    );
    
    const CancelItem = ({ value, onChange }) => {
        
        const handeChange = (modifyObj)=>{
            onChange({
                ...value,
                ...modifyObj,
            })
        }

        return (<Space>
            <span>行程前</span>
            <InputNumber value={value?.day} min={0} max={30} addonAfter="日" onBlur={e=>handeChange({day: e.target.value})}></InputNumber>
            <span>违约金</span>
            <InputNumber value={value?.precent} min={0} max={100} addonAfter="%" onBlur={e=>handeChange({precent: e.target.value})}></InputNumber>
            <MinusCircleOutlined onClick={() => deleteItem(value)} />     
        </Space>)
    }

    const DragableCancelItem = ({ index, item }) => {
        const ref = useRef()
        const [, drag] = useDrag({
            type: 'cancelItem',
            item: { index },
            collect: monitor => ({
                isDragging: monitor.isDragging(),
            }),
        });
        const [{ dropClassName, canDrop, isOver }, drop] = useDrop({
            accept: 'cancelItem',
            collect: monitor => {
                const { index: dragIndex } = monitor.getItem() || {};
                if (dragIndex === index) {
                    return {};
                }
                return {
                    isOver: monitor.isOver(),
                    canDrop: monitor.canDrop(),
                    dropClassName: dragIndex < index ? ' drop-over-downward' : ' drop-over-upward',
                };
            },
            drop: (item, monitor) => {
                moveRow(item.index, index);
            },
        });
        drop(drag(ref))

        return (
        <div ref={ref} className={`${isOver ? dropClassName : ''}`}>
            <CancelItem value={item} onChange={handleItemChange}></CancelItem>
        </div>)
    };

    //** 处理item变化 */
    const handleItemChange = (item)=>{
        const items = value.map(i => {
            if(i.key == item.key){
                return item;
            }else{
                return i;
            }
        })
        onChange(items)
    }

    //** 处理item删除 */
    const deleteItem = (item)=>{
        const items = value.filter(i => i.key != item.key)
        onChange(items)
    }

    return (
        <DndProvider backend={HTML5Backend}>
            <Space direction="vertical">
            {
                value.map((item, index) => {
                    return <DragableCancelItem key={index} index={index} item={item}></DragableCancelItem>
                })
            }
                <Button
                    type="dashed"
                    onClick={(e) => 
                        onChange([...value, {key: Date.now()}])
                    }
                    icon={<PlusOutlined />}
                >
                    添加取消政策
                </Button>
            </Space>    
        </DndProvider>
    )


}

export default CancelPolicy;