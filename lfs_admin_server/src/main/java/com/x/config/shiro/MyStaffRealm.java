package com.x.config.shiro;

//import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.x.data.dto.StaffDto;
import com.x.context.SpringContextUtil;
import com.x.data.dto.RoleDto;
import com.x.data.pojo.enums.CommonState;
import com.x.service.StaffService;

public class MyStaffRealm extends AuthorizingRealm{
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		StaffDto staff = (StaffDto) principals.fromRealm(this.getClass().getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        Set<RoleDto> roles = staff.getRoles();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        //设置该用户拥有的角色
        roles.forEach(role->{
        	info.addRole(role.getKey());
        	role.getPermissions().forEach(permission->{
        		info.addStringPermission(permission.getName());
        	});
        });
        return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if(token instanceof UsernamePasswordToken) {
			UsernamePasswordToken tk = (UsernamePasswordToken)token;
			//FIXME: 此处不能使用autowired来注入，否则会导致bean被提前注入，不能为bean生成代理对象，最终导致bean注入的普通对象，可能会导致事务等切面无效
			StaffService staffService = SpringContextUtil.getBean(StaffService.class);
			StaffDto staff = staffService.login(tk.getUsername(), new String(tk.getPassword())); 
	        if(staff == null){
	            throw new UnknownAccountException(); //没找到账号
	        }
	        
	        if(CommonState.INVALID == staff.getState()){
	            throw new LockedAccountException(); //账号被锁定
	        }
	        
	        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
	        		staff,
	        		staff.getPassword(),
	                this.getClass().getName());
			return authenticationInfo;
		}else {
			return null;
		}
	}

//	@Override
//	public boolean supports(AuthenticationToken token) {
//若是其他的Token方式，在此判断是否是对应的类型，返回支持还是不支持
//		if(token.getPrincipal() instanceof StaffDto ) {
//			return true;
//		}
//		return super.supports(token);
//	}
	
}
