import BraftEditor from "braft-editor";
import { ContentUtils } from 'braft-utils';
import { list as listImages, signUrl, upload } from '@/utils/aliOss';
import AliyunOssUpload from "@/components/AliyunOssUpload"
import { useEffect, useState } from "react"
import 'braft-editor/dist/index.css'
import Table from 'braft-extensions/dist/table'
import { Button, Modal } from "antd"
import { CloudUploadOutlined } from "@ant-design/icons"
import './index.css'
import TextArea from "antd/lib/input/TextArea";
import usePublicMediaModel from "@/models/publicMedia";
import { useModel } from "umi";

// 表格，感觉有问题
// const options = {
//     defaultColumns: 3, // 默认列数
//     defaultRows: 3, // 默认行数
//     withDropdown: true, // 插入表格前是否弹出下拉菜单
//     columnResizable: true, // 是否允许拖动调整列宽，默认false
//     exportAttrString: 'border="1" style="border-collapse: collapse"', // 指定输出HTML时附加到table标签上的属性字符串
//     //includeEditors: ['editor-id-1'], // 指定该模块对哪些BraftEditor生效，不传此属性则对所有BraftEditor有效
//     //excludeEditors: ['editor-id-2']  // 指定该模块对哪些BraftEditor无效
//   }
// BraftEditor.use(Table(options))

export default (props) => {

    const { value, onChange } = props;

    const [editorState, setEditorState] = useState(BraftEditor.createEditorState(value))
    const { getMedias, addMedia} = useModel('publicMedia',model => ({ getMedias: model.getMedias, addMedia: model.addMedia }) )
    const [contentWidth, setContentWidth] = useState("100%")

    const myUploadFn = async (param) => {
      
        
        try{

            param.progress(10)

            const result = await upload('images/', param.file, true)

            param.progress(100)

            param.success({
                url: result.url,
                id: result.name.substring(result.name.lastIndexOf('/') + 1, result.name.lastIndexOf('.')),
                type: 'IMAGE',
                meta: {
                  id: result.name.substring(result.name.lastIndexOf('/') + 1, result.name.lastIndexOf('.')),
                  //title: 'xxx',
                  //alt: 'xxx',
                  //loop: true, // 指定音视频是否循环播放
                  //autoPlay: true, // 指定音视频是否自动播放
                  //controls: true, // 指定音视频是否显示控制栏
                  //poster: 'http://xxx/xx.png', // 指定视频播放器的封面
                }
            })

            addMedia({
                url: result.url,
                id: result.name.substring(result.name.lastIndexOf('/') + 1, result.name.lastIndexOf('.')),
                type: 'IMAGE'})
            
        }catch(e){
            console.log(e)
            param.error({
                msg: '上传失败'
            })
        }
    }

    const extendControls = [
        'separator',
        {
            key: 'normalView',
            type: 'button',
            text: '正常尺寸',
            onClick: ()=>{
                setContentWidth("100%")
            }
        },
        {
            key: 'phoneView',
            type: 'button',
            text: '手机尺寸',
            onClick: ()=>{
                setContentWidth("320px")
            }
        },
        'separator',
        {
            key: 'preview-modal',
            type: 'modal',
            text: '预览',
            onClick: () => {console.log('preview')},
            modal: {
                id: 'preview-modal', // 必选属性，传入一个唯一字符串即可
                title: '预览', // 指定弹窗组件的顶部标题
                //className: 'preview-modal', // 指定弹窗组件样式名
                //width: 320, // 指定弹窗组件的宽度
                //height: 320, // 指定弹窗组件的高度
                showFooter: false, // 指定是否显示弹窗组件底栏
                showCancel: false, // 指定是否显示取消按钮
                showConfirm: false, // 指定是否显示确认按钮
                confirmable: true, // 指定确认按钮是否可用
                showClose: true, // 指定是否显示右上角关闭按钮
                closeOnBlur: true, // 指定是否在点击蒙层后关闭弹窗(v2.1.24)
                //closeOnConfirm: true, // 指定是否在点击确认按钮后关闭弹窗(v2.1.26)
                //closeOnCancel: true, // 指定是否在点击取消按钮后关闭弹窗(v2.1.26)
                //cancelText: '取消', // 指定取消按钮文字
                //confirmText: '确定', // 指定确认按钮文字
                //bottomText: null, // 指定弹窗组件底栏左侧的文字，可传入jsx
                //onConfirm: () => {}, // 指定点击确认按钮后的回调函数
                //onCancel: () => {}, // 指定点击取消按钮后的回调函数
                //onClose: () => {}, // 指定弹窗被关闭后的回调函数
                //onBlur: () => {}, // 指定蒙层被点击时的回调函数
                children: // 指定弹窗组件的内容组件
                    <div style={{width:320, height:480, overflow: "auto"}}>
                        <div className="x-html-container" dangerouslySetInnerHTML={{ __html: editorState.toHTML() }}></div>, 
                    </div>
            }
        },
        {
            key: 'antd-uploader',
            type: 'component',
            component: (
                <AliyunOssUpload
                    style={{display:'block'}}
                    dir='images/'
                    accept="image/*"
                    showUploadList={false}
                    isPublic={true}
                    onUploadSuccess={(file, host) => {
                        setEditorState(ContentUtils.insertMedias(editorState, [{
                            type: 'IMAGE',
                            url: host +"/"+ file.url,
                        }]))
                    }
                    }>
                    {/* 这里的按钮最好加上type="button"，以避免在表单容器中触发表单提交，用Antd的Button组件则无需如此 */}
                    <Button className="control-item button upload-button" icon={<CloudUploadOutlined />}>
                    插入图片
                    </Button>
                </AliyunOssUpload>
            )
        },
    ]
    const excludeControls = []
    return (
        <>
            <BraftEditor
                //id="x-braft-editor"
                //className="my-editor"
                value={editorState}
                defaultValue={editorState}
                onChange={(state) => {
                    setEditorState(state)
                    if (onChange) {
                        onChange(state)
                    }
                }}
                // stripPastedStyles={true}
                placeholder="请输入正文内容"
                contentStyle={{height: "300px", backgroundColor:"white", width:contentWidth, margin: "0 auto", border: "solid 1px #e5e5e5" }}
                controlBarStyle={{backgroundColor:"white", border: "solid 1px #e5e5e5"}}
                //controls={controls}
                //excludeControls={excludeControls}
                extendControls={extendControls}
                media={{
                    items: getMedias(), //items在初始化后会复制一份，在medias没有变化时不会更新item,更新items会影响选择状态
                    uploadFn: myUploadFn,
                    //onchange会在任何变化推送整个items的内容
                }}
            >
            </BraftEditor>
            {/* 添加一个Modal是为了引入antd Modal的css,不然会导致Upload裁剪Modal样式丢失 */}
            <Modal></Modal>
        </>
    )
}