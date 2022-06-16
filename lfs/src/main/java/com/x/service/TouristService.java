package com.x.service;

import java.util.List;

import com.x.data.bo.AddTouristBo;
import com.x.data.bo.ModifyTouristBo;
import com.x.data.po.Tourist;

public interface TouristService {

	public List<Tourist> getMyTourists(String uid);
	
	public Tourist addMyTourist(String uid, AddTouristBo addTouristBo);
	
	public Tourist modifyMyTourist(String uid, ModifyTouristBo modifyTouristBo);
	
	public void delMyTourist(String uid, String touristId);
}
