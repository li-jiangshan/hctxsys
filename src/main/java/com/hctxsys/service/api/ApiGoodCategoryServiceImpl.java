package com.hctxsys.service.api;

import com.hctxsys.entity.YskGoodCategoryEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.repository.GoodCategoryRepository;
import com.hctxsys.repository.GoodRepository;
import com.hctxsys.vo.api.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("apiGoodCategorySerivce")
public class ApiGoodCategoryServiceImpl {
	
    @Autowired
    private GoodCategoryRepository goodCategoryRepository;
    @Autowired
    private GoodRepository goodRepository;

    /**
     * 调用递归查商品分类
     * @return
     */
    @Transactional
    public JsonResult getCategory() {
        JsonResult jsonResult=new JsonResult();
        List<YskGoodCategoryEntity> allList = goodCategoryRepository.findAll();
        List<YskGoodCategoryEntity> topList = goodCategoryRepository.findAllByPid((short) 0);
        if(topList.size()==0) {
            jsonResult.setMessage("暂无数据");
            jsonResult.setCode(0);
            return jsonResult;
        }
        this.get(allList,topList);//递归
        jsonResult.setCode(1);
        jsonResult.setMessage("查询成功");
        jsonResult.setData(topList);
        return jsonResult;
    }
    
    private void get(List<YskGoodCategoryEntity> allList,List<YskGoodCategoryEntity> topList) {
        for (YskGoodCategoryEntity top : topList) {//遍历父分类
            List<YskGoodCategoryEntity> sonList = new ArrayList<>();//定义父分类的子集合
            for (YskGoodCategoryEntity one : allList) {
                if(one.getPid()==top.getId()) {//如果子分类的父ID与全部数据中有相等
                    sonList.add(one);//子集合添加元素
                }
            }
            top.setCategoryList(sonList);//将子集合放入父分类里
            if(sonList.size()!=0){//如果子集合长度不为0，则继续遍历，子集合变为父集合
                get(allList,sonList);
            }
        }
    }

    //查询有商品的分类
    @Transactional
    public JsonResult getCategoryHasGood() {
    	
        JsonResult jsonResult=new JsonResult();
        
        //查询所有三级分类
        List<YskGoodCategoryEntity> threeList = goodCategoryRepository.findAllByLevel((byte) 3);
        //查询所有二级分类
        List<YskGoodCategoryEntity> twoList = goodCategoryRepository.findAllByLevel((byte) 2);
        //查询所有一级分类
        List<YskGoodCategoryEntity> oneList = goodCategoryRepository.findAllByLevel((byte) 1);
        
        //有商品的三级分类
        List<YskGoodCategoryEntity> hasGoodThreeList = new ArrayList<YskGoodCategoryEntity>();
        
        for (int i = 0; i < threeList.size(); i++) {
        	//查询每个分类是否有商品
        	List<YskGoodEntity> goodList = goodRepository.findByCategoryIdAndStatus(threeList.get(i).getId(), (int)1);
        	if (goodList.size() > 0) { //如果有商品新列表
        		hasGoodThreeList.add(threeList.get(i));
        	}
		}
        //有商品的二级分类
        List<YskGoodCategoryEntity> hasGoodTwoList = this.getHasGoodCategory(hasGoodThreeList, twoList);
        
        //有商品的一级分类
        List<YskGoodCategoryEntity> hasGoodOneList = this.getHasGoodCategory(hasGoodTwoList, oneList);
        
        jsonResult.setCode(1);
        jsonResult.setMessage("查询成功");
        jsonResult.setData(hasGoodOneList);
        return jsonResult;
    }
    
    private List<YskGoodCategoryEntity> getHasGoodCategory(List<YskGoodCategoryEntity> hasGoodList, List<YskGoodCategoryEntity> upperList) {
    	 //根据二级分类 进行分类
        Map<Integer, List<YskGoodCategoryEntity>> mapTwo = new HashMap<Integer, List<YskGoodCategoryEntity>>();
        //将相同二级分类的三级分类放在同一个key中
        for(YskGoodCategoryEntity goodCategoryEntity: hasGoodList){
            if(mapTwo.get(Integer.valueOf(goodCategoryEntity.getPid()))==null){
                List<YskGoodCategoryEntity> list = new ArrayList<YskGoodCategoryEntity>();
                list.add(goodCategoryEntity);
                mapTwo.put(Integer.valueOf(goodCategoryEntity.getPid()), list);
            }else{
                List<YskGoodCategoryEntity> list =mapTwo.get(Integer.valueOf(goodCategoryEntity.getPid()));
                list.add(goodCategoryEntity);          
            }
        }

        //有商品的三级分类的二级分类
        List<YskGoodCategoryEntity> returnList = new ArrayList<YskGoodCategoryEntity>();
        //通过二级分类id生成有商品的三级分类的二级分类的list
        for(Integer key:mapTwo.keySet()){
        	for (int i = 0; i < upperList.size(); i++) {
        		if (key == upperList.get(i).getId()) {
        			upperList.get(i).setCategoryList(mapTwo.get(key));
        			returnList.add(upperList.get(i));
        			break;
        		}
			}
        }
        
        return returnList;
    }
}
