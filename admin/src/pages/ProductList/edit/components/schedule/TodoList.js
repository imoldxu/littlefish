import AliyunOSSUpload from '@/components/AliyunOssUpload';
import { addGroupTour } from '@/services/groupTour';
import { Button, Card, Col, Descriptions, Dropdown, Image, Input, InputNumber, Menu, message, Row, Select, Space, TimePicker } from 'antd';
import React, { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { MinusCircleOutlined, PlusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { EditableProTable } from '@ant-design/pro-table';
import moment from 'moment';
// 直接拖动相关
import { DndProvider, useDrag, useDrop } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import update from 'immutability-helper';
import MealTodo from './meal';
import HotelTodo from './hotel';
import ScenicSpotTodo from './scenicSpotTodo';
import TrafficTodo from './traffic';
import FreeTodo from './free';
import ShoppingTodo from './shopping';
// 此为有拖拽手柄的排序
// import { sortableContainer, sortableElement, sortableHandle } from 'react-sortable-hoc';
// import { MenuOutlined } from '@ant-design/icons';
// import arrayMove from 'array-move';

export default function TodoList(props) {

    // const [dataSource, setDataSource] = useState(props.value ? props.value : []);

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
    
    const TodoItem = ({ item }) => {

        const {type} = item
        if (type == "1") {
            return <MealTodo value={item} onChange={handleItemChange}></MealTodo>
        }
        if (type == "2") {
            return <HotelTodo value={item} onChange={handleItemChange}></HotelTodo>
        }
        if (type == "3") {
            return <ScenicSpotTodo value={item} onChange={handleItemChange}></ScenicSpotTodo>
        }
        if (type == "4") {
            return <TrafficTodo value={item} onChange={handleItemChange}></TrafficTodo>
        }
        if (type == "5") {
            return <FreeTodo value={item} onChange={handleItemChange}></FreeTodo>
        }
        if (type == "6") {
            return <ShoppingTodo value={item} onChange={handleItemChange}></ShoppingTodo>
        }
    }

    const DragableTodoItem = ({ index, item }) => {
        const ref = useRef()
        const [, drag] = useDrag({
            type: 'todoItem',
            item: { index },
            collect: monitor => ({
                isDragging: monitor.isDragging(),
            }),
        });
        const [{ dropClassName, canDrop, isOver }, drop] = useDrop({
            accept: 'todoItem',
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
            <Space>
                <TodoItem item={item}></TodoItem>
                <MinusCircleOutlined onClick={() => deleteItem(item)} />     
            </Space>
               
        </div>)
    };
    

    // 手柄拖动相关
    // const DragHandle = sortableHandle(() => <MenuOutlined style={{ cursor: 'grab', color: '#999' }} />);
    // const SortableItem = sortableElement(props => <tr {...props} />);
    // const SortableContainer = sortableContainer(props => <tbody {...props} />);
    // const onSortEnd = ({ oldIndex, newIndex }) => {
    //     if (oldIndex !== newIndex) {
    //       const newData = arrayMove([].concat(dataSource), oldIndex, newIndex).filter(el => !!el);
    //       console.log('Sorted items: ', newData);
    //       setDataSource(newData);
    //     }
    // };
    // const DraggableContainer = props => (
    //     <SortableContainer
    //       useDragHandle
    //       disableAutoscroll
    //       helperClass="row-dragging"
    //       onSortEnd={onSortEnd}
    //       {...props}
    //     />
    //   );
    // const DraggableBodyRow = ({ className, style, ...restProps }) => {
    //     // function findIndex base on Table rowKey props and should always be a right array index
    //     const index = dataSource?.findIndex(x => x.index === restProps['data-row-key']);
    //     // let index = 0
    //     // dataSource?.forEach(x =>{ 
    //     //     if(x.index === restProps['data-row-key']){
    //     //         index = x.index
    //     //     }
    //     // })
    //     return <SortableItem index={index} {...restProps} />;
    // };

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

    const menu = (
        <Menu onClick={({ key }) => 
            onChange([...value, {type: key, key: Date.now()}])
        }>
            <Menu.Item key="1">
                餐饮
            </Menu.Item>
            <Menu.Item key="2">
                酒店
            </Menu.Item>
            <Menu.Item key="3">
                景点
            </Menu.Item>
            <Menu.Item key="4">
                交通
            </Menu.Item>
            <Menu.Item key="5">
                自由活动
            </Menu.Item>
            <Menu.Item key="6">
                购物
            </Menu.Item>
        </Menu>
    );

    //获取组件的keys
    const keys = value.map(v=>v.key);

    return (
        
        <DndProvider backend={HTML5Backend}>
            <Space direction="vertical">
            {
                //只有在增减、调换顺序的时候才重绘组件。否则value.map会导致子组件被卸载再重绘，卸载会导致upload上传失败
                useMemo(()=>{
                    return value.map((item, index) => {
                        return <DragableTodoItem key={index} index={index} item={item}></DragableTodoItem>
                    })
                },[value.length, ...keys]) //此处必须将keys展开，否则是按对象进行比较而不是对象下的值。
            }
            <Dropdown overlay={menu}>
                <Button
                    type="dashed"
                    icon={<PlusOutlined />}
                >
                    添加行程
                </Button>
            </Dropdown>
            </Space>    
        </DndProvider>
            
    )

}