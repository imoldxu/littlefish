package com.x.lfs.service;

import java.util.List;

import com.x.lfs.data.bo.AddTouristBo;
import com.x.lfs.data.bo.ModifyTouristBo;
import com.x.lfs.data.po.Tourist;

public interface TouristService {

	public List<Tourist> getMyTourists(String uid);
	
	public Tourist addMyTourist(String uid, AddTouristBo addTouristBo);
	
	public Tourist modifyMyTourist(String uid, ModifyTouristBo modifyTouristBo);
	
	public Tourist delMyTourist(String uid, String touristId);
}
