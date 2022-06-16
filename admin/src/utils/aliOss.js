import { getSTS } from '@/services/aliyun'
import OSS from 'ali-oss'

export const urlStr2OssFileObj = (urlStr, index) => {
    const url = new URL(urlStr)
    return {
        uid: index,
        name: url.pathname.substring(url.pathname.lastIndexOf('/') + 1),
        status: 'done',
        thumbUrl: urlStr,
        url: url.pathname.substring(1),
    }
}

export let client = undefined

getSTS().then(info=>{
    client = new OSS({
        // yourRegion填写Bucket所在地域。以华东1（杭州）为例，Region填写为oss-cn-hangzhou。
        region: 'oss-cn-chengdu',
        // 从STS服务获取的临时访问密钥（AccessKey ID和AccessKey Secret）。
        accessKeyId: info.accessKeyId,
        accessKeySecret: info.accessKeySecret,
        // 从STS服务获取的安全令牌（SecurityToken）。
        stsToken: info.securityToken,
        refreshSTSToken: async () => {
        // 向您搭建的STS服务获取临时访问凭证。
            const info = await getSTS();
            return {
            accessKeyId: info.accessKeyId,
            accessKeySecret: info.accessKeySecret,
            stsToken: info.securityToken
            }
        },
        // 刷新临时访问凭证的时间间隔，单位为毫秒。
        //refreshSTSTokenInterval: 3000000,
        // 填写Bucket名称。
        bucket: 'lfs-image',
    });
})

export const list = async (dir, marker, maxKeys=100 )=>{
    try {
      // 默认最多返回1000个文件,默认100个
      let result = await client.list({
          prefix: dir,
          marker: marker,
          'max-keys': maxKeys,
          // 设置正斜线（/）为文件夹的分隔符。
          delimiter: '/'});
      console.log(result);
        
      return result;
    } catch (e) {
      console.log(e);
    }
}

export const signUrl = (pathName)=>{
    const {bucket, region} = client.options
    return "http://"+bucket+"."+region+".aliyuncs.com/"+pathName;
    // 对于私有文件才需要签名
    // const url = client.signatureUrl(pathName);
    // return url;
}

export const upload = async (dir, file, isPublic)=>{
    const headers = {
        // 指定该Object被下载时网页的缓存行为。
        // 'Cache-Control': 'no-cache', 
        // 指定该Object被下载时的名称。
        // 'Content-Disposition': 'oss_download.txt', 
        // 指定该Object被下载时的内容编码格式。
        // 'Content-Encoding': 'UTF-8', 
        // 指定过期时间。
        // 'Expires': 'Wed, 08 Jul 2022 16:57:01 GMT', 
        // 指定Object的存储类型。
        // 'x-oss-storage-class': 'Standard', 
        // 指定Object的访问权限。
        'x-oss-object-acl': isPublic? 'public-read' : 'default', 
        // 设置Object的标签，可同时设置多个标签。
        // 'x-oss-tagging': 'Tag1=1&Tag2=2', 
        // 指定CopyObject操作时是否覆盖同名目标Object。此处设置为true，表示禁止覆盖同名Object。
        // 'x-oss-forbid-overwrite': 'true', 
    };

    const suffix = file.name.slice(file.name.lastIndexOf('.'));
    const filename = (9999999999999-Date.now()) + '000' + suffix

    const result = await client.put(dir+filename, file, {headers})
    return result;
}