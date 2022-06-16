package com.x.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path="/price")
@Api("价格测算")
public class PriceController {

	@GetMapping
	@ApiOperation(value = "预测", notes = "预测")
	public Double forecast(@ApiParam(name="myprice", value="我方价格") @RequestParam(name="myprice") Integer myprice,
			@ApiParam(name="ratio", value="维保比例") @RequestParam(name="ratio") Double ratio,
			@ApiParam(name="distance", value="分差") @RequestParam(name="distance") Double distance,
			@ApiParam(name="chratio", value="彩虹维保比例") @RequestParam(name="chratio") Double chratio
			) {
		double i=150;
		for(; i<300; i++) {
			double eny = 0;
			double me = 0;
			if(myprice>i) {
				me += i/myprice*100*0.75;
				eny += 75;
			}else if(myprice<i) {
				me += 75;
				eny += myprice/i*100*0.75;
			}else {
				me += 75;
				eny += 75;
			}
			
			double myweibao = myprice*ratio;
			double enyweibao = i*chratio;
			if(myweibao>enyweibao) {
				me += enyweibao/myweibao*100*0.25;
				eny += 25;
			}else if(myweibao<enyweibao) {
				me += 25;
				eny += myweibao/enyweibao*100*0.25;
			}else {
				me +=25;
				eny += 25;
			}
			double ret = (eny-me)*0.4;
			System.out.println(ret);
			if(ret<distance) {
				break;
			}
		}
		return i;
	}
	
	
}
