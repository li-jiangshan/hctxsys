package com.hctxsys.controller.adminmall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.service.YskGoodPriceSerivceImpl;



@Controller
@RequestMapping("/goodPrice")
public class YskGoodPriceController {
	
	@Autowired
	private YskGoodPriceSerivceImpl yskGoodPriceSerivceImpl;
	
	
	
	@PostMapping(path = "/all/{goodId}")
	public @ResponseBody StringBuffer getAttrList(@PathVariable String goodId ,@RequestParam String specArr) {
		List<?> allListk = yskGoodPriceSerivceImpl.spillkey(specArr);//网络      内存      屏幕       颜色
		List<?> allList1 = yskGoodPriceSerivceImpl.spill1(specArr);//网络:3G,内存:16G,屏幕:触屏,颜色:黑色
		List<?> allList2 = yskGoodPriceSerivceImpl.spill2(specArr);//3G,16G,触屏,白色
		StringBuffer sb = new StringBuffer();
		sb.append("<table class=\"table table-bordered\" id=\"spec_input_tab\">");
		sb.append("<tr>");
		//第一行
		for (Object object : allListk) {
			String name = yskGoodPriceSerivceImpl.getAttrName(Integer.valueOf(object.toString()));
			sb.append("<td><b>"+name+"</b></td>");
		}
		sb.append("<td><b>价格</b></td><td><b>库存</b></td></tr>");
		//第一行 end
		//第2行
		for (int i = 0;i<allList1.size();i++) {
			sb.append("<tr>");
			//String[] split2 = allList2.get(i).toString().split(",");//3G,16G,触屏,白色
			String[] split1 = allList1.get(i).toString().split(",");//网络:3G	内存:16G		屏幕:触屏		颜色:黑色
			for (String string : split1) {
				int m = string.indexOf(":");
				String key = string.substring(0, m);
				String val = string.substring(m+1, string.length());
				sb.append("<td><input class=\"read\" readonly=\"true\" name=\"item["+key+"][]\" value=\""+val+"\"></td>");
			}
			YskGoodPriceEntity entity = yskGoodPriceSerivceImpl.getPriceAndStore(Integer.valueOf(goodId), allList2.get(i).toString());
			if(entity==null) {
				sb.append("<td><input class=\"price\" name=\"price[]\" value=\"\" onkeyup=\"this.value=this.value.replace(/[^\\d.]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d.]/g,'')\"></td>");
			}else {
				sb.append("<td><input class=\"price\" name=\"price[]\" value=\""+entity.getPrice()+"\" onkeyup=\"this.value=this.value.replace(/[^\\d.]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d.]/g,'')\"></td>");
			}
			if(entity==null) {
				sb.append("<td><input class=\"kucun\" name=\"store[]\" value=\"\" onkeyup=\"this.value=this.value.replace(/[^\\d.]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d.]/g,'')\">");
			}else {
				sb.append("<td><input class=\"kucun\" name=\"store[]\" value=\""+entity.getStore()+"\" onkeyup=\"this.value=this.value.replace(/[^\\d.]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d.]/g,'')\">");
			}
			sb.append("<input type=\"hidden\" name=\"att_text[]\" value=\""+allList1.get(i).toString()+"\"><input type=\"hidden\" name=\"att_value[]\" value=\""+allList2.get(i).toString()+"\"></td></tr>");
		}
		sb.append("</table>");
		//List<YskGoodPriceEntity> pricelist = yskGoodPriceSerivceImpl.getPriceList(Integer.valueOf(goodId));
		return sb;
	}
	
}
