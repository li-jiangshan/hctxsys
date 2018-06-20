package com.hctxsys.service.api;

import com.hctxsys.entity.YskUpdateUserinfoEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.UpdateUserInfoRepository;
import com.hctxsys.repository.YsKUserRepository;
import com.hctxsys.util.CheckUtils;
import com.hctxsys.util.Contant;
import com.hctxsys.util.DateUtils;
import com.hctxsys.vo.api.ApiWorkOrderVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("apiWorkOrderService")
public class ApiWorkOrderServiceImpl {
    @Autowired
    private UpdateUserInfoRepository userInfoRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private YsKUserRepository userRepository;

    @Transactional
    public JsonResult update(ApiWorkOrderVo workOrderVo) {
        try {
            YskUserEntity user = userRepository.findById(workOrderVo.getUid()).get();
            switch (workOrderVo.getUpdateType()) {
                case 1: {//修改手机
                    String verificationCode = redisTemplate.opsForValue().get(Contant.MOBILE_VERIFICATION_CODE + user.getMobile());
                    if (StringUtils.isNullOrEmpty(workOrderVo.getCode())) return new JsonResult(0, "验证码不能为空");
                    if (!workOrderVo.getCode().equals(verificationCode)) return new JsonResult(0, "验证码不存在或过期");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getNewInfo())) return new JsonResult(0, "新手机号不能为空");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getImg())) return new JsonResult(0, "请上传证件照");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getImgBack())) return new JsonResult(0, "请上传身份证背面照");
                    if (!CheckUtils.isCheckMobile(workOrderVo.getNewInfo())) return new JsonResult(0, "新手机号格式错误");
                    Long count = userRepository.countByMobileEquals(workOrderVo.getNewInfo());
                    if (count > 0) return new JsonResult(0, "手机号已被使用");
                    YskUpdateUserinfoEntity info = new YskUpdateUserinfoEntity();
                    info.setUid(workOrderVo.getUid());
                    info.setUpdateType(workOrderVo.getUpdateType());
                    info.setNewInfo(workOrderVo.getNewInfo());
                    info.setContent(workOrderVo.getContent());
                    info.setImg(workOrderVo.getImg());
                    info.setImgBack(workOrderVo.getImgBack());
                    info.setStatus(0);
                    info.setCreateTime(Integer.parseInt(DateUtils.getTime()));
                    userInfoRepository.saveAndFlush(info);
                    redisTemplate.delete(Contant.MOBILE_VERIFICATION_CODE + workOrderVo.getNewInfo());
                    break;
                }
                case 2: {
                    if(user.getUserType()!=0) return new JsonResult(0,"非个人用户");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getNewInfo())) return new JsonResult(0, "请输入新姓名");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getContent())) return new JsonResult(0, "请输入问题描述");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getImg())) return new JsonResult(0, "请上传证件照");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getImgBack())) return new JsonResult(0, "请上传身份证背面照");
                    YskUpdateUserinfoEntity info = new YskUpdateUserinfoEntity();
                    info.setUid(workOrderVo.getUid());
                    info.setUpdateType(workOrderVo.getUpdateType());
                    info.setNewInfo(workOrderVo.getNewInfo());
                    info.setContent(workOrderVo.getContent());
                    info.setImg(workOrderVo.getImg());
                    info.setImgBack(workOrderVo.getImgBack());
                    info.setStatus(0);
                    info.setCreateTime(Integer.parseInt(DateUtils.getTime()));
                    userInfoRepository.saveAndFlush(info);
                    break;
                }
                case 3: {
                    if(user.getUserType()!=1) return new JsonResult(0,"非企业用户");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getNewInfo())) return new JsonResult(0, "请输入新公司名称");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getContent())) return new JsonResult(0, "请输入问题描述");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getImg())) return new JsonResult(0, "请上传证件照");
                    YskUpdateUserinfoEntity info = new YskUpdateUserinfoEntity();
                    info.setUid(workOrderVo.getUid());
                    info.setUpdateType(workOrderVo.getUpdateType());
                    info.setNewInfo(workOrderVo.getNewInfo());
                    info.setContent(workOrderVo.getContent());
                    info.setImg(workOrderVo.getImg());
                    info.setStatus(0);
                    info.setCreateTime(Integer.parseInt(DateUtils.getTime()));
                    userInfoRepository.saveAndFlush(info);
                    break;
                }
                case 4: {
                    if(user.getUserType()!=1) return new JsonResult(0,"非企业用户");
                    if (StringUtils.isNullOrEmpty(workOrderVo.getMaxDistributionIntegral())) return new JsonResult(0, "请输入发放积分最大额度"); //发放积分最大额度
                    if (StringUtils.isNullOrEmpty(workOrderVo.getCountDistributionIntegral())) return new JsonResult(0, "请输入发放积分次数"); //发放积分次数
                    if (StringUtils.isNullOrEmpty(workOrderVo.getImg())) return new JsonResult(0, "请上传凭证");
                    YskUpdateUserinfoEntity info = new YskUpdateUserinfoEntity();
                    info.setUid(workOrderVo.getUid());
                    info.setUpdateType(workOrderVo.getUpdateType());
                    info.setMaxDistributionIntegral(workOrderVo.getMaxDistributionIntegral());
                    info.setCountDistributionIntegral(workOrderVo.getCountDistributionIntegral());
                    info.setImg(workOrderVo.getImg());
                    info.setStatus(0);
                    info.setCreateTime(Integer.parseInt(DateUtils.getTime()));
                    userInfoRepository.saveAndFlush(info);
                    break;
                }

            }
            return new JsonResult(1, "提交成功，等待审核");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "提交失败");
        }
    }
    public JsonResult select(YskUpdateUserinfoEntity info) {
        List<YskUpdateUserinfoEntity> infoList = userInfoRepository.selectAllByStatus(info.getUid(), info.getStatus());
        List<Map<String,Object>> list=new ArrayList<>();
        if(infoList.size()==0) return new JsonResult(0,"暂无数据");
        for (YskUpdateUserinfoEntity userinfoEntity : infoList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",userinfoEntity.getId());
            map.put("newInfo",userinfoEntity.getNewInfo());
            map.put("content",userinfoEntity.getContent());
            map.put("status",userinfoEntity.getStatus());
            map.put("updateType",userinfoEntity.getUpdateType());
            map.put("maxDistributionIntegral",userinfoEntity.getMaxDistributionIntegral());
            map.put("countDistributionIntegral",userinfoEntity.getCountDistributionIntegral());
            list.add(map);
        }
        return new JsonResult(1,"查询成功",list);
    }
    @Transactional
    public JsonResult delete(YskUpdateUserinfoEntity info) {
        try {
            userInfoRepository.deleteById(info.getId());
            return new JsonResult(1,"删除成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"删除失败");
        }
    }
}
