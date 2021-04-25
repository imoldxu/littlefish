package com.x.lfs.config.shiro;

//import java.util.HashSet;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
//import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.x.lfs.data.po.Admin;
import com.x.lfs.data.po.Role;
import com.x.lfs.service.AdminService;

public class MyAdminRealm extends AuthorizingRealm{

	@Autowired
	private AdminService adminService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Admin admin = (Admin) principals.fromRealm(this.getClass().getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        //设置该用户拥有的角色
        admin.getRoles().forEach(role->{
        	info.addRole(role.getName());
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
			Admin admin = adminService.login(tk.getUsername(), new String(tk.getPassword())); 
	        if(admin == null){
	            throw new UnknownAccountException(); //没找到账号
	        }
	        
	        if(Admin.STATE_INVALID == admin.getState()){
	            throw new LockedAccountException(); //账号被锁定
	        }
	        
	        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
	                admin,
	                admin.getPassword(),
	                this.getClass().getName());
			return authenticationInfo;
		}else {
			return null;
		}
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		//若是其他的Token方式，在此判断是否是对应的类型，返回支持还是不支持
		if(token instanceof UsernamePasswordToken) {
			return true;
		}
		return false;
	}
	
}
