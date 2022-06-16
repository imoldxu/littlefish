import { Calendar, Space } from "antd";
import { useEffect } from "react";

export default function Price(props){

    useEffect(()=>{
        
    })

    function getDatePrice(date){
        return  {
            adultPrice: 1000000,
            childPrice: 220000,
            singleRoomPrice: 35000,
        }
    }

    function dateCellRender(value) {
        const date = value.date()
        const datePrice = getListData(value);
        return (
          <Space direction="vertical">
              <span>{`成人价：${datePrice.adultPrice}`}</span>
              <span>{`儿童价：${datePrice.childPrice}`}</span>
              <span>{`单房差：${datePrice.singleRoomPrice}`}</span>
          </Space>
        );
    }

    function handlePanelChange(date, mode){

    }

    function handleDateSelect(date){

    }

    return (
        <Calendar dateCellRender={dateCellRender}
            onPanelChange={handlePanelChange}
            onSelect={handleDateSelect}
        />
    )
}