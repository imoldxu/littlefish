import { useState } from 'react'
import { message, Button, Modal, Space } from 'antd';
import { FileImageOutlined, UploadOutlined } from '@ant-design/icons';
import AliyunOssUpload from '@/components/AliyunOssUpload';
import ImageLibModal from '@/components/ImageLibModal';

export default (props)=> {
  const [visible, setVisible] = useState(false)
  
  const { value, onChange, maxCount } =props; //只要其他的属性，value和onChange不要，免得覆盖组件内的onChange
  const uploadValue = value? value : []
  return (
    <>
      <Space>
        <AliyunOssUpload dir='images/' {...props} >
          <Button icon={<UploadOutlined />}>点击上传</Button> 
        </AliyunOssUpload>
        {
          (maxCount && value)? 
          value.length < maxCount ?
          <Button icon={<FileImageOutlined></FileImageOutlined>} onClick={()=>{
            setVisible(true)
          }}>图库</Button> : null
          : <Button icon={<FileImageOutlined></FileImageOutlined>} onClick={()=>{
            setVisible(true)
          }}>图库</Button>
        }
      </Space>
      {visible ? <ImageLibModal visible={visible}
        maxCount={maxCount-uploadValue.length}
        onCancel={()=>{setVisible(false)}}
        onOk={(v)=>{
          
          onChange([...uploadValue, ...v]);
          setVisible(false) 
        }}>
      </ImageLibModal>: null}
    </>
  );
}