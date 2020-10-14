import React, {Component}  from 'react'
import { View, Image, Button } from '@tarojs/components'
import dva from '../../utils/dva'

import './index.scss'


class Login extends Component {

    handleLogin = (res)=>{
        console.log(res)
        const dispatch = dva.getDispatch()
        dispatch({type:'user/authGetUserInfo', payload:{needGoBack:true}})
    }

    render(){
        return(
            <View>
                <View class='header'>
                    <Image src='../../asset/image/login.png'></Image>
                </View>
                <Button class='bottom' type='primary' open-type="getUserInfo" onGetUserInfo={this.handleLogin}></Button>
            </View>
        )
    }
}

export default Login;