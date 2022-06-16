import { UserLevel } from "@/utils/bankendData"
import { Avatar, Button, Descriptions, Drawer, Image, List, message, Spin } from "antd"
import { useEffect, useState } from "react"
import VirtualList from 'rc-virtual-list';
import { queryComment } from "@/services/comment";
import { getArticle } from "@/services/article";
import ReplyCommentDrawer from "./replyCommentDrawer";

const DetailDrawer = (props)=>{

    const [isLoading, setLoading] = useState(true)
    const [detail, setDetail] = useState({})
    const { visible, onClose, value } = props
    const [comments, setComments] = useState([]);
    const [total, setTotal] = useState(0);
    const [current, setCurrent] = useState(0);
    
    const [replyVisible, setReplyVisible] = useState(false)
    const [selectedComment, setSelectedComment] = useState(undefined)

    const ContainerHeight = 400;

    useEffect(async()=>{
        try{
            const {id} = value;
            setLoading(true)
            const detail = await getArticle({id})
            setDetail(detail)
            appendData()
            setLoading(false)
        }catch(e){
            message.error(e.message)
        }
    },[value.id])

    const appendData= async() =>{
        
        const pageData = await queryComment({targetId: value.id, type:1, current: current+1, pageSize:20})
        const {data, total} = pageData
        setCurrent(current+1)
        setComments(comments.concat(data))
        setTotal(total)
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
            {isLoading? <Spin></Spin> : <>
                <div style={{backgroundColor: '#F3F3F3'}} dangerouslySetInnerHTML={{ __html: detail.content }}></div>
                <div>
                    <div>评论({total}条)</div>
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
                                        <Button size='mini' color="default" 
                                             style={{ '--border-radius': '12px', fontSize: "10px" }}
                                             onClick={()=>{
                                                 setReplyVisible(true)
                                                 setSelectedComment(item)
                                             }}
                                             >{`${item.replyNum}回复>`}</Button>
                                        <span style={{ marginLeft: "4px", fontSize: "10px", color:"#c6c6c6" }}>{item.createTime}</span>
                                    </div>
                                </div>
                            </List.Item>
                            )}
                        </VirtualList>
                    </List>
                </div>
            </>
            }
            {
                replyVisible &&
            <ReplyCommentDrawer
                value={selectedComment}
                visible={replyVisible}
                onClose={()=>setReplyVisible(false)}      
            ></ReplyCommentDrawer>
            }
        </Drawer>
    )

}

export default DetailDrawer