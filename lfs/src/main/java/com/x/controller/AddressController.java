package com.x.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.commons.util.SessionUtil;
import com.x.constant.ErrorCode;
import com.x.data.bo.AddAddressBo;
import com.x.data.bo.ModifyAddressBo;
import com.x.data.po.Address;
import com.x.data.vo.AddressVo;
import com.x.data.vo.UserVo;
import com.x.exception.HandleException;
import com.x.service.AddressService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	AddressService addressService;
	@Autowired
	MapperFacade orikaMapper;
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加地址", notes = "添加地址")
	public AddressVo addAddress(
			@ApiParam(name = "addAddressBo", value = "添加地址信息") @RequestBody @Valid AddAddressBo addAddressBo,
			HttpServletRequest request, HttpServletResponse response) {
		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_USER_KEY, UserVo.class);
		
		Address entity = orikaMapper.map(addAddressBo, Address.class);
		entity.setUid(user.getId());
		addressService.save(entity);
		AddressVo result = orikaMapper.map(entity, AddressVo.class);
		return result;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public AddressVo modifyAddress(
			@ApiParam(name = "modifyAddressBo", value = "修改地址信息") @RequestBody @Valid ModifyAddressBo modifyAddressBo,
			HttpServletRequest request, HttpServletResponse response) {
		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_USER_KEY, UserVo.class);
		UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", modifyAddressBo.getId()).eq("uid",user.getId());
		
		Address entity = orikaMapper.map(modifyAddressBo, Address.class);
		boolean result = addressService.update(entity, updateWrapper);
		if(result) {
			return orikaMapper.map(entity, AddressVo.class);
		}else {
			throw new HandleException(ErrorCode.DOMAIN_ERROR, "更新失败");
		}
	}
	
	@RequestMapping(path="/default", method = RequestMethod.GET)
	@ApiOperation(value = "获取默认地址", notes = "获取默认地址")
	public AddressVo getDefaultAddress(HttpServletRequest request, HttpServletResponse response) {
		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_USER_KEY, UserVo.class);
		
		QueryWrapper<Address> queryWrapper = new QueryWrapper<Address>();
		queryWrapper.eq("uid", user.getId());//TODO，加上默认字段
		Address entity = addressService.getOne(queryWrapper);
		AddressVo result = orikaMapper.map(entity, AddressVo.class);
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取用户的地址列表", notes = "获取用户的地址列表")
	public List<AddressVo> getAddressList(HttpServletRequest request, HttpServletResponse response) {
		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_USER_KEY, UserVo.class);
		
		QueryWrapper<Address> queryWrapper = new QueryWrapper<Address>();
		queryWrapper.eq("uid", user.getId());
		List<Address> addressList = addressService.list(queryWrapper);
		
		List<AddressVo> result = orikaMapper.mapAsList(addressList, AddressVo.class);
		return result;
	}
	
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除地址", notes = "删除地址")
	public void delAddress(@PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_USER_KEY, UserVo.class);
		
		QueryWrapper<Address> queryWrapper = new QueryWrapper<Address>();
		queryWrapper.eq("id", id).eq("uid", user.getId());//TODO，加上默认字段
		boolean result = addressService.remove(queryWrapper);
		if(!result) {
			throw new HandleException(ErrorCode.DOMAIN_ERROR, "删除失败");
		}
		return;
	}
}
