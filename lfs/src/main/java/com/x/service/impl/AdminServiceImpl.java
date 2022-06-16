//package com.x.lfs.service.impl;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Service;
//
//import com.x.lfs.data.bo.AddAdminBo;
//import com.x.lfs.data.po.Admin;
//import com.x.lfs.service.AdminService;
//
//import ma.glasnost.orika.MapperFacade;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//	@Autowired
//	MongoTemplate mongoTemplate;
//	@Autowired
//	MapperFacade orikaMapper;
//	
//	@Override
//	public Admin login(String phone, String password) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("phone").is(phone).and("password").is(password));
//		
//		Admin admin = mongoTemplate.findOne(query, Admin.class);
//		
//		return admin;
//	}
//
//	@Override
//	public Admin register(AddAdminBo addAdminBo) {
//		Admin admin = orikaMapper.map(addAdminBo, Admin.class);
//		admin.setPassword("7dd75c55c0f3a84969cacc5fcdbbd980");
//		admin.setState(Admin.STATE_VALID);
//		admin.setCreatetime(new Date());
//		admin = mongoTemplate.insert(admin);
//		
//		//admin = mongoTemplate.findById(admin.getId(), Admin.class);
//		return admin;
//	}
//
//}
