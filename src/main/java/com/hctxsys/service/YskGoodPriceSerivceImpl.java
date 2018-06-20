package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskGoodAttributeEntity;
import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.repository.YskGoodAttributeRepository;
import com.hctxsys.repository.YskGoodPriceRepository;


@Service("yskGoodPriceSerivce")
public class YskGoodPriceSerivceImpl {
	@Autowired
	YskGoodPriceSerivceImpl yskGoodPriceSerivceImpl;
	
	@Autowired 
	private YskGoodAttributeRepository yskGoodAttributeRepository;

	@Autowired 
	private YskGoodPriceRepository yskGoodPriceRepository;
	
	/**
	 * @param id  
	 * @return
	 */
	public List<YskGoodPriceEntity> getPriceList(int goodId){
		List<YskGoodPriceEntity> list = yskGoodPriceRepository.findByPriceId(goodId);
		return list;
	}
	
	/**
	 * 通过id  获取属性名
	 * @param id
	 * @return
	 */
	public String getAttrName(int id) {
		YskGoodAttributeEntity good = yskGoodAttributeRepository.findById(id);
		return good.getAttrName();
	}
	
	public YskGoodPriceEntity getPriceAndStore(int goodId,String goodAttrValue) {
		YskGoodPriceEntity entity = yskGoodPriceRepository.getPriceAndStore(goodId, goodAttrValue);
		return entity;
	}
	/**
	 * 笛卡尔
	 * @param list
	 * @return
	 */
	public ArrayList Dikaerji0(ArrayList list) {  
		  
        ArrayList a0 = (ArrayList) list.get(0);// l1  
        ArrayList result = new ArrayList();// 组合的结果  
        ArrayList result1 = new ArrayList();// 组合的结果  去符号
        for (int i = 1; i < list.size(); i++) {  
            ArrayList a1 = (ArrayList) list.get(i);  
            ArrayList temp = new ArrayList();  
            // 每次先计算两个集合的笛卡尔积，然后用其结果再与下一个计算  
            for (int j = 0; j < a0.size(); j++) {  
                for (int k = 0; k < a1.size(); k++) {  
                    ArrayList cut = new ArrayList();  
  
                    if (a0.get(j) instanceof ArrayList) {  
                        cut.addAll((ArrayList) a0.get(j));  
                    } else {  
                        cut.add(a0.get(j));  
                    }  
                    if (a1.get(k) instanceof ArrayList) {  
                        cut.addAll((ArrayList) a1.get(k));  
                    } else {  
                        cut.add(a1.get(k));  
                    }  
                    temp.add(cut);  
                }  
            }  
            a0 = temp;  
            if (i == list.size() - 1) {  
                result = temp;  
            }  
        }  
        for (Object object : result) {
			String string = object.toString().replace("[", "");
			string = string.toString().replace("]", "");
			string = string.toString().replace(" ", "");
			result1.add(string);
		}
        return result1;  
    }
	
	/**
	 * [网络:3G, 内存:16G, 屏幕:触屏, 颜色:黑色]
	 * @param specArr
	 * @return
	 */
	public List spill1(String specArr) {
		String[] split = specArr.split("],");
		ArrayList list = new ArrayList();
		ArrayList list1 = new ArrayList();
		for (String string : split) {
			ArrayList arrayList = new ArrayList();
			String replace = string.replace("{", "");
			replace = replace.replace("}", "");
			replace = replace.replace("[", "");
			replace = replace.replace("]", "");
			String str = replace.replace("\"", "");
			//list.add(replace);
			String[] kk = str.split(":");
			int i = kk[1].indexOf(",");
			if(i!=-1) {
				String[] vs = kk[1].split(",");
				for (String ss : vs) {
					String name = getAttrName(Integer.valueOf(kk[0]));
					arrayList.add(name+":"+ss);
				}
			}else {
				String name = getAttrName(Integer.valueOf(kk[0]));
				arrayList.add(name+":"+kk[1]);
			}
			list.add(arrayList);
		}
		if(list.size()==1) {
			ArrayList l = new ArrayList();
			l.add("");
			list.add(l);
		}
		ArrayList allList = yskGoodPriceSerivceImpl.Dikaerji0(list);
		return allList;
	}
	/**
	 * [3G, 16G, 触屏, 黑色]
	 * @param specArr
	 * @return
	 */
	public List spill2(String specArr) {
		String[] split = specArr.split("],");
		ArrayList list = new ArrayList();
		ArrayList list1 = new ArrayList();
		for (String string : split) {
			ArrayList arrayList = new ArrayList();
			String replace = string.replace("{", "");
			replace = replace.replace("}", "");
			replace = replace.replace("[", "");
			replace = replace.replace("]", "");
			String str = replace.replace("\"", "");
			String[] kk = str.split(":");
			int i = kk[1].indexOf(",");
			if(i!=-1) {
				String[] vs = kk[1].split(",");
				for (String ss : vs) {
					arrayList.add(ss);
				}
			}else {
				arrayList.add(kk[1]);
			}
			list.add(arrayList);
		}
		if(list.size()==1) {
			ArrayList l = new ArrayList();
			l.add("");
			list.add(l);
		}
		ArrayList allList = yskGoodPriceSerivceImpl.Dikaerji0(list);
		return allList;
	}
	/**网络      内存      屏幕       颜色
	 * @param specArr
	 * @return
	 */
	public List spillkey(String specArr) {
		String[] split = specArr.split("],");
		ArrayList list = new ArrayList();
		for (String string : split) {
			ArrayList arrayList = new ArrayList();
			String replace = string.replace("{", "");
			replace = replace.replace("}", "");
			replace = replace.replace("[", "");
			replace = replace.replace("]", "");
			String str = replace.replace("\"", "");
			//list.add(replace);
			String[] kk = str.split(":");
			list.add(kk[0]);
		}
		return list;
	}
}
