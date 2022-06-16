import { list } from "@/utils/aliOss"
import { useState } from "react"

export default function usePublicMediaModel() {

    const [mediaItems, setMediaItems] = useState([])
    const [isInit, setInited] = useState(false)

    function getMedias() {
        if (isInit) {
            return mediaItems;
        } else {
            //设置延迟的原因是ossClient可能还没有初始化，初始化媒体库,媒体库就显示最近1000条
            setTimeout(()=>{
                list("images/", null, 1000).then(result=>{
                    const items = result?.objects?.map(o => {
                        return {
                            id: o.name.substring(o.name.lastIndexOf('/') + 1, o.name.lastIndexOf('.')),
                            type: 'IMAGE',
                            url: o.url
                        }
                    }) ?? []
                    setMediaItems(items)
                    setInited(true)
                })
            }, 5000)
            return mediaItems;
        } 
    }

    function addMedia(media){
        setMediaItems([media, ...mediaItems])
    }

    return {
        getMedias,
        addMedia,
    }
}