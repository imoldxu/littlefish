package com.x.data.pojo.easyrules;

import java.util.List;

import lombok.Data;

@Data
public class RuleDes {

	String key; //rule的key值
	
	Integer CompositeType; //0,single,1 UnitRuleGroup, 2 ActivationRuleGroup, 3 ConditionalRuleGroup
	
	List<RuleDes> rules; //组合的规则
}
