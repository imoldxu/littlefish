import request from '../utils/request'
import config from '../utils/config'
import api from './api'

const apiPrefix = config.apiPrefix

const gen = params => {
  let url = apiPrefix + params
  let method = 'GET'

  const paramsArray = params.split(' ')
  if (paramsArray.length === 2) {
    method = paramsArray[0]
    url = apiPrefix + paramsArray[1]
  }

  return function(data) {
    return request({
      url,
      data,
      method,
    })
  }
}

const APIFunction = {}
for (const key in api) {
  APIFunction[key] = gen(api[key])
}

// APIFunction.queryWeather = params => {
//   params.key = 'i7sau1babuzwhycn'
//   return request({
//     url: `${apiPrefix}/weather/now.json`,
//     data: params,
//   })
// }

// APIFunction.uploadPrices = parmas => {
    
//     //let file = document.getElementsByName('img')[0].files[0];
    
//     const {file, providerId} = parmas

//     let formData = new FormData();
//     formData.append("file",file,file.name);
//     formData.append("providerId",providerId)
     
//     // const config = {
//     //   headers: { "Content-Type": "multipart/form-data" },
//     // };
     
//     let options = {
//       url: "/api/v1/excelprice",
//       method: "POST",
//       data: formData,
//       headers: { "Content-Type": "multipart/form-data" },
//       responseType: 'blob',
//     }

//     return request(options)
// };

export default APIFunction
