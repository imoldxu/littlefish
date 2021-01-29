package com.x.lfs.service;

import java.util.List;

import com.x.lfs.context.bo.AddTouristBo;
import com.x.lfs.context.bo.ModifyTouristBo;
import com.x.lfs.entity.Tourist;

public interface TouristService {

	public List<Tourist> getMyTourists(String uid);
	
	public Tourist addMyTourist(String uid, AddTouristBo addTouristBo);
	
	public Tourist modifyMyTourist(String uid, ModifyTouristBo modifyTouristBo);
	
	public Tourist delMyTourist(String uid, String touristId);
}
