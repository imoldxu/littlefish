import { UserLevel } from "@/utils/bankendData"
import { Avatar, Button, Descriptions, Drawer, Image, List, message, Spin } from "antd"
import { useEffect, useState } from "react"
import VirtualList from 'rc-virtual-list';
import { queryReplyComment } from "@/services/comment";
import { getArticle } from "@/services/article";

const ReplyCommentDrawer = (props)=>{

    const [isLoading, setLoading] = useState(true)
    const { visible, onClose, value } = props
    const [comments, setComments] = useState([]);
    const [current, setCurrent] = useState(0);

    const ContainerHeight = 400;

    useEffect(async()=>{
        try{
            setLoading(true)
            appendData()
            setLoading(false)
        }catch(e){
            message.error(e.message)
        }
    },[value.id])

    const appendData= async() =>{
        const pageData = await queryReplyComment({commentId: value.id, current: current+1, pageSize:20})
        const {data} = pageData
        setCurrent(current+1)
        setComments(comments.concat(data))
    }

    const onScroll = e => {
        if (e.target.scrollHeight - e.target.scrollTop === ContainerHeight) {
            appendData()
        }
    };

    return (
        <Drawer
        width={640}
        placement="right"
        closable={false}
        onClose={onClose}
        visible={visible}
        >
            {isLoading? <Spin></Spin> : 
                <div>
                    <div>评论回复</div>
                    <List itemLayout="vertical">
                        <VirtualList
                            data={comments}
                            height={ContainerHeight}
                            itemHeight={47}
                            itemKey="id"
                            onScroll={onScroll}
                        >
                            {item => (
                            <List.Item key={item.id} >
                                <List.Item.Meta
                                avatar={<Avatar src={item.fromAvatar} />}
                                title={item.fromName}
                                //description={item.createTime}
                                />
                                <div>
                                    <span>{item.content}</span>
                                    <div>
                                        <span style={{ marginLeft: "4px", fontSize: "10px", color:"#c6c6c6" }}>{item.createTime}</span>
                                    </div>
                                </div>
                            </List.Item>
                            )}
                        </VirtualList>
                    </List>
                </div>
            }
        </Drawer>
    )

}

export default ReplyCommentDrawer