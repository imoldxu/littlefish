package com.x.lfs.service;

import com.x.lfs.data.bo.AddAdminBo;
import com.x.lfs.data.po.Admin;

public interface AdminService {

	public Admin login(String phone, String password);

	public Admin register(AddAdminBo addAdminBo);

}
