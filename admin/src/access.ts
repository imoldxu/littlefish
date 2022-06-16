/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser | undefined }) {
  const { currentUser } = initialState || {};
  if(currentUser){
    const {roles} = currentUser;
    //角色处理
    let admin = false;
    let tollman = false;
    let manager = false;
    roles.forEach( role =>{
      const {key} = role;
      if(key === 'admin'){
        admin = true
      } else if(key === 'manager'){
        manager = true
      } else if(key === 'tollman'){
        tollman = true
      }
    })
    return {
      admin,
      manager,
      tollman,
    }
  } else{
    return {
      admin: false,
      manager: false,
      tollman: false,
    }
  }
}
