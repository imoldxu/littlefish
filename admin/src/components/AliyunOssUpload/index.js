import { useState, useEffect } from 'react'
import { Upload, message, Button, Space } from 'antd';
import ImgCrop from 'antd-img-crop';
import { FileImageOutlined, UploadOutlined } from '@ant-design/icons';
import { getSign } from '@/services/aliyun';

export default (props) => {
  const [OSSData, setOSSData] = useState({})

  const {
    value,
    onChange,
    dir,
    isPublic=true,
    onUploadSuccess, //上传成功的回调
    children,    //upload的子元素，如Button
    crop = false,//默认是否需要裁减
    cropProps = {}, //剪切的属性，默认是方形，可以缩放
    ...uploadProps //剩余属性都作为upload的属性
  } = props; 

  useEffect(async () => {
    await init();
  }, [])

  const init = async () => {
    try {
      const OSSData = await getSign({dir});
      setOSSData(OSSData)
    } catch (error) {
      message.error(error.msg);
    }
  };

  const handleChange = ({ file, fileList, event }) => {
    console.log('Aliyun OSS:', file, fileList, event);
    //处理文件上传成功，文件、上传的host
    //if (event?.percent == 100 && onUploadSuccess) {
    if(file.status === 'done' && onUploadSuccess){
      onUploadSuccess(file, OSSData.host)
    }
    if (onChange) {
      onChange([...fileList]);
    }
  };

  const onRemove = file => {

    const files = value.filter(v => v.url !== file.url);

    if (onChange) {
      onChange(files);
    }
  };

  const getExtraData = file => {

    const filename = generateOssfileName(file)

    const data = {
      key: OSSData.dir + filename,
      OSSAccessKeyId: OSSData.accessId,
      policy: OSSData.policy,
      signature: OSSData.signature,
      success_action_status: '200', //让服务端返回200，否则默认返回204。
      "x-oss-object-acl": isPublic? 'public-read' : 'default',
      //callback: OSSData.callback,
    };
    return data;
  };

  //为给file在上传和onchange的url路径一致，利用file的uid来计算fileName  
  const generateOssfileName = (file)=>{
    const suffix = file.name.slice(file.name.lastIndexOf('.'));
    const now = file.uid.slice(file.uid.indexOf('-', 8)+1,file.uid.lastIndexOf('-'))
    const index = file.uid.slice(file.uid.lastIndexOf('-')+1)
    const filename = (9999999999999-now) + '' + (999-index) + suffix; //取时间的倒序作为图片的id,可以让图片list时按倒序显示
    return filename;
  }

  /**
   * 此方法返回的file将作为form的数据
   * @param  file 
   * @returns file
   */
  const beforeUpload = async file => {
    const expire = OSSData.expire * 1000;

    if (expire < Date.now()) {
      await init();
    }

    const filename = generateOssfileName(file)
    file.url = OSSData.dir + filename;

    return file;
  };

  const myProps = {
    name: 'file',
    fileList: value,
    action: OSSData.host,
    onChange: handleChange,
    onRemove: onRemove,
    data: getExtraData,
    beforeUpload: beforeUpload,
    ...uploadProps
  };
  const { maxCount } = props;
  return 
  crop ?
  (<ImgCrop {...cropProps} 
      //ImgCrop将接管Upload的beforeUpload弹出Modal，会触发beforeCrop，beforeCrop返回的file将影响getExtraData传入的file
    >
      <Upload {...myProps}>
        {
          (maxCount && value) ?
            value.length < maxCount ?
              children  : null
            :  children 
        }
      </Upload>
    </ImgCrop>) 
    : 
    (<Upload {...myProps}>
       {
         (maxCount && value) ?
           value.length < maxCount ?
             children  : null
           :  children 
       }
    </Upload>);
}