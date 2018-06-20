package com.hctxsys.service.api;

import com.hctxsys.entity.*;
import com.hctxsys.repository.*;
import com.hctxsys.util.*;
import com.hctxsys.vo.api.*;
import com.mysql.jdbc.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("apiUserService")
public class ApiUserServiceImpl {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCheckInfoRepository userCheckInfoRepository;
    @Autowired
    private UserWealthRepository userWealthRepository;
    @Autowired
    private GoodCarRepository carRepository;
    @Autowired
    private GoodPriceRepository priceRepository;
    @Autowired
    private ShopNewRepository shopNewRepository;
    @Autowired
    private UserAddressRepository addressRepository;
    @Autowired
    private UserCheckInfoRepository checkInfoRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ApiImgServiceImpl imgService;

    /**
     * 根据用户ID查询购物车
     *
     * @param userID
     * @return
     */
    public JsonResult getUserCar(Integer userID) {
        JsonResult returnVo = new JsonResult();
        List<YskGoodCarEntity> byUid = carRepository.findByUid(Integer.valueOf(userID));
        YskUserAddressEntity addressEntity = addressRepository.findByUserIdAndIsDefault(userID, (byte) 1);
        UserVo userVo = new UserVo();
        if (byUid.size() == 0) {//无购物数据
            returnVo.setCode(0);
            returnVo.setMessage("购物车为空");
            return returnVo;
        }
        for (YskGoodCarEntity car : byUid) {
            if (car.getPriceId() != null) {
                Optional<YskGoodPriceEntity> byId = priceRepository.findById(car.getPriceId());
                if (!byId.isPresent()) {
                    continue;
                }
                YskGoodPriceEntity yskGoodPriceEntity = byId.get();
                YskGoodEntity good = new YskGoodEntity();
                BeanUtils.copyProperties(car.getGoodEntity(), good);
                good.setGoodPrice(yskGoodPriceEntity.getPrice());//设置规格项对应价格
                car.setAttrValue(yskGoodPriceEntity.getGoodAttrValue());//设置规格项
                car.setGoodEntity(good);
            }
        }
        if (addressEntity != null) {
            userVo.setAddressEntity(addressEntity);
            userVo.setIsDefault(1);
        }
        returnVo.setCode(1);
        returnVo.setMessage("查询成功");
        userVo.setCarList(byUid);
        returnVo.setData(userVo);
        return returnVo;
    }

    /**
     * 删除购物车
     *
     * @param
     * @return
     */
    @Transactional
    public JsonResult deleteCar(Integer id) {
        JsonResult jsonResult = new JsonResult();
        try {
            carRepository.deleteById(Integer.valueOf(id));
            jsonResult.setCode(1);
            jsonResult.setMessage("删除成功");
            return jsonResult;
        } catch (Exception e) {
            jsonResult.setMessage("删除失败");
            jsonResult.setCode(0);
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
            return jsonResult;
        }
    }

    /**
     * 获取用户收获地址
     *
     * @param userID
     * @return
     */
    @Transactional
    public JsonResult getAddress(Integer userID) {
        JsonResult returnVo = new JsonResult();
        List<YskUserAddressEntity> all = addressRepository.findAllByUserId(Integer.valueOf(userID));
        UserVo userVo = new UserVo();
        if (all.size() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("您还没有收货地址");
            return returnVo;
        }
        returnVo.setMessage("收货地址");
        returnVo.setCode(1);
        userVo.setAddressList(all);
        returnVo.setData(userVo);
        return returnVo;
    }

    /**
     * 添加收获地址，根据默认标识判断是否添加默认地址
     *
     * @param params 地址信息
     * @return
     */
    @Transactional
    public JsonResult addUserAddress(YskUserAddressEntity params) {
        JsonResult returnVo = new JsonResult();
        if (params.getZipcode() == null) {//数据库中字段不能为空，默认为''
            params.setZipcode("");
        }
        try {
            if (params.getIsDefault() == 1) {
                YskUserAddressEntity byIsDefault = addressRepository.findByUserIdAndIsDefault(params.getUserId(), Byte.valueOf("1"));//查询用户原来默认地址
                if (byIsDefault != null) {
                    byIsDefault.setIsDefault(Byte.valueOf("0"));
                    addressRepository.saveAndFlush(byIsDefault);//将原先默认设为非默认
                }
            }
            addressRepository.save(params);//插入新地址
            returnVo.setCode(1);
            returnVo.setMessage("添加成功");
            return returnVo;
        } catch (Exception e) {
            e.printStackTrace();
            returnVo.setMessage("添加失败");
            returnVo.setCode(0);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
            return returnVo;
        }
    }

    /**
     * 获取地址信息
     *
     * @param id 地址ID
     * @return
     */
    @Transactional
    public JsonResult getOneUserAddress(Integer id) {
        JsonResult returnVo = new JsonResult();
        YskUserAddressEntity addressEntity = addressRepository.findById(Integer.valueOf(id)).get();
        UserVo userVo = new UserVo();
        returnVo.setCode(1);
        returnVo.setMessage("获取成功");
        userVo.setAddressEntity(addressEntity);
        returnVo.setData(userVo);
        return returnVo;
    }

    /**
     * 设置默认地址
     *
     * @param id 地址ID
     * @return
     */
    @Transactional
    public JsonResult setDefault(Integer id) {
        JsonResult jsonResult = new JsonResult();
        try {
            YskUserAddressEntity yskUserAddressEntity = addressRepository.findById(id).get();
            YskUserAddressEntity oldDefault = addressRepository.findByUserIdAndIsDefault(yskUserAddressEntity.getUserId(), (byte) 1);
            if (oldDefault != null) {
                oldDefault.setIsDefault((byte) 0);
                addressRepository.saveAndFlush(oldDefault);
            }
            yskUserAddressEntity.setIsDefault((byte) 1);
            addressRepository.saveAndFlush(yskUserAddressEntity);
            jsonResult.setCode(1);
            jsonResult.setMessage("设置成功");
            return jsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            jsonResult.setCode(0);
            jsonResult.setMessage("设置失败");
            return jsonResult;
        }
    }

    /**
     * 编辑地址
     *
     * @param addressEntity 地址信息
     * @return
     */
    @Transactional
    public JsonResult editUserAddress(YskUserAddressEntity addressEntity) {

        JsonResult returnVo = new JsonResult();
        if (addressEntity.getZipcode() == null) {//数据库中字段不能为空，默认为''
            addressEntity.setZipcode("");
        }
        try {
            if (addressEntity.getIsDefault() == 1) {
                YskUserAddressEntity isDefault = addressRepository.findByUserIdAndIsDefault(addressEntity.getUserId(), Byte.valueOf("1"));
                if (isDefault != null) {
                    isDefault.setIsDefault(Byte.valueOf("0"));
                    addressRepository.saveAndFlush(isDefault);
                }
            }
            addressRepository.saveAndFlush(addressEntity);
            returnVo.setCode(1);
            returnVo.setMessage("编辑成功");
            return returnVo;
        } catch (Exception e) {
            e.printStackTrace();
            returnVo.setCode(0);
            returnVo.setMessage("编辑失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
            return returnVo;
        }
    }

    @Transactional
    public JsonResult delAddress(Integer id) {
        JsonResult jsonResult = new JsonResult();
        try {
            addressRepository.deleteById(id);
            jsonResult.setCode(1);
            jsonResult.setMessage("删除成功");
            return jsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            jsonResult.setCode(0);
            jsonResult.setMessage("删除失败");
            return jsonResult;
        }
    }

    /**
     * 获取用户最新动态
     *
     * @return
     */
    @Transactional
    public JsonResult getShopNew() {
        JsonResult jsonResult = new JsonResult();
        List<YskShopnewEntity> top = shopNewRepository.findTop50ByIdIsNotNullOrderByIdDesc();
        if (top.size() == 0) {
            jsonResult.setCode(0);
            jsonResult.setMessage("暂无数据");
            return jsonResult;
        }
        jsonResult.setCode(1);
        jsonResult.setMessage("获取成功");
        jsonResult.setData(top);
        return jsonResult;
    }

    /**
     * 个人转企业
     *
     * @param checkinfoEntity
     * @return
     */
    @Transactional
    public JsonResult userChange(ApiUserVo checkinfoEntity) {
        JsonResult jsonResult = new JsonResult();
        YskUserEntity user = userRepository.findById(checkinfoEntity.getUserid()).get();
        YskUserCheckinfoEntity checkUser = checkInfoRepository.findById(checkinfoEntity.getUserid()).get();
        String v = redisTemplate.opsForValue().get(Contant.MOBILE_VERIFICATION_CODE + checkinfoEntity.getMobile());
        if (v == null || !v.equals(checkinfoEntity.getVerificationCode())) {
            jsonResult.setCode(0);
            jsonResult.setMessage("验证码错误或过期");
            return jsonResult;
        }
        if (user.getUserType() == 1) {
            jsonResult.setCode(0);
            jsonResult.setMessage("非个人用户");
            return jsonResult;
        }
        if (user.getLevel() < 1) {
            jsonResult.setCode(0);
            jsonResult.setMessage("只有VIP用户才能升级为企业用户");
            return jsonResult;
        }
        if (StringUtils.isNullOrEmpty(checkUser.getIdcard())) {
            jsonResult.setCode(0);
            jsonResult.setMessage("请先进行个人认证");
            return jsonResult;
        }
        try {
            redisTemplate.delete(Contant.MOBILE_VERIFICATION_CODE + checkinfoEntity.getMobile());
            user.setUserType((byte) 1);
            checkUser.setUserType((byte) 1);
            checkUser.setCompanyName(checkinfoEntity.getCompanyName());
            checkUser.setCompanyLicense(checkinfoEntity.getCompanyLicense());
            checkUser.setCompanyOrganize(checkinfoEntity.getCompanyOrganize());
            checkInfoRepository.saveAndFlush(checkUser);
            userRepository.saveAndFlush(user);
            jsonResult.setCode(1);
            jsonResult.setMessage("转换申请已提交");
            return jsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(0);
            jsonResult.setMessage("转换失败");
            return jsonResult;
        }
    }

    /**
     * 查询用户个人信息
     *
     * @param userid
     * @return
     */
    @Transactional
    public JsonResult userInfo(Integer userid) {
        JsonResult jsonResult = new JsonResult();
        YskUserEntity user = null;
        try {
            user = userRepository.findById(userid).get();
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(0);
            jsonResult.setMessage("用户不存在");
            return jsonResult;
        }
        jsonResult.setCode(1);
        jsonResult.setMessage("查询成功");
        jsonResult.setData(user);
        return jsonResult;
    }

    /**
     * 更新用户email
     *
     * @param user 用户信息
     * @return
     */
    @Transactional
    public JsonResult updateEmail(YskUserEntity user) {
        JsonResult jsonResult = new JsonResult();
        YskUserEntity u = userRepository.findById(user.getUserid()).get();
        u.setEmail(user.getEmail());
        try {
            userRepository.saveAndFlush(u);
            jsonResult.setCode(1);
            jsonResult.setMessage("修改成功");
            return jsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(0);
            jsonResult.setMessage("修改失败");
            return jsonResult;
        }
    }

    @Transactional
    public JsonResult updateHeadImg(ApiUserId apiUserId) {
        JsonResult jsonResult = imgService.saveImg(apiUserId);
        try {
            YskUserEntity user = userRepository.findById(apiUserId.getUserId()).get();
            user.setHeadImg(String.valueOf(jsonResult.getData()));
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(0);
            jsonResult.setMessage("上传失败");
            return jsonResult;
        }
        jsonResult.setMessage("上传成功");
        return jsonResult;


    }

    @Transactional
    public JsonResult userWallet(ApiUserId user) {
        JsonResult jsonResult = new JsonResult();
        try {
            YskUserWealthEntity u = userWealthRepository.findById(user.getUserId()).get();
            ApiWealthVo wealthVo = new ApiWealthVo();
            wealthVo.setBankNum(u.getBankList().size());
            wealthVo.setWealth(u);
            jsonResult.setCode(1);
            jsonResult.setMessage("获取成功");
            jsonResult.setData(wealthVo);
            return jsonResult;
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode(0);
            jsonResult.setMessage("获取失败");
            return jsonResult;
        }
    }

    //发送手机验证码
    @Transactional
    public String getVerifyingCode(ApiUserVo vo) throws Exception {

        String verificationCode = String.valueOf(Math.round(Math.random() * 1000000));

        String content = "【华彩天下】您的验证码为" + verificationCode + "，5分钟内有效";
        String code = this.sendMsg(vo.getMobile(), content);
        if ("0".equals(code)) {
            return verificationCode;
        }
        return "-1";
//		return verificationCode;

    }

    //注册用户
    @Transactional
    public JsonResult registerUser(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();

        String password = vo.getLoginPwd();

        //校验手机号是否注册
        List<YskUserEntity> listByMobile = userRepository.findByMobile(vo.getMobile());
        if (listByMobile.size() > 0) {
            returnVo.setCode(0);
            returnVo.setMessage("手机号已注册");
            return returnVo;
        }
        //校验账号是否注册
        List<YskUserEntity> listByAccount = userRepository.findByAccount(vo.getAccount());
        if (listByAccount.size() > 0) {
            returnVo.setCode(0);
            returnVo.setMessage("账号名已存在");
            return returnVo;
        }

        /**1. 保存用户信息 */
        YskUserEntity userEntity = new YskUserEntity();
        BeanUtils.copyProperties(vo, userEntity);//将Vo数据存入Entity中
        //推荐人信息
        List<YskUserEntity> listParent = new ArrayList<YskUserEntity>();
        //如果推荐人账号或手机存在
        if (!StringUtils.isNullOrEmpty(vo.getParent())) {
            //校验是否为手机号
            if (CheckUtils.isCheckMobile(vo.getParent())) {
                listParent = userRepository.findByMobile(vo.getParent());
            } else {
                listParent = userRepository.findByAccount(vo.getParent());
            }
            if (listParent.size() == 0) {
                returnVo.setCode(0);
                returnVo.setMessage("分享人不存在");
                return returnVo;
            }
            //分享人信息
            YskUserEntity parentEntity = listParent.get(0);
            userEntity.setPid(parentEntity.getUserid()); //上级id
            userEntity.setGid(parentEntity.getPid()); //上上级id
            userEntity.setGgid(parentEntity.getGid()); //上上上级id
            userEntity.setDeep(parentEntity.getDeep() + 1);  //层级
            if ("0".equals(parentEntity.getPath())) {
                userEntity.setPath("-" + parentEntity.getUserid() + "-"); //层级
            } else {
                userEntity.setPath(parentEntity.getUserid() + "-"); //层级
            }
        } else {
            userEntity.setPid(0);  //上级id
            userEntity.setGid(0); //上上级id
            userEntity.setGgid(0); //上上上级id
            userEntity.setDeep(0); //层级
            userEntity.setPath("0"); //层级
        }
        userEntity.setUserType(Byte.valueOf(vo.getUserType()));
        userEntity.setStatus((byte) 1); //用户锁定
        userEntity.setLoginSalt(PswUtils.getSalt()); //加密时间盐值
        userEntity.setLoginPwd(PswUtils.getCipher(password, userEntity.getLoginSalt())); //加密密码
        userEntity.setRegDate(MyUtils.getSecondTimestamp(new Date())); //注册时间

        userEntity.setAreaCity("");
        userEntity.setAreaDistrict("");
        userEntity.setAreaProvince("");
        userEntity.setEmail("");
        userEntity.setIdcard("");
        userEntity.setSafetyPwd("");
        userEntity.setSafetySalt("");
        userEntity.setUsername("");
        userEntity.setJfDaysign(0);
        userEntity.setSignTotal(0);

        YskUserEntity saveUserEntity = userRepository.save(userEntity);

        /**2. 保存用户认证信息 */
        YskUserCheckinfoEntity userCheckinfoEntity = new YskUserCheckinfoEntity();
        BeanUtils.copyProperties(vo, userCheckinfoEntity);//将Vo数据存入Entity中
        userCheckinfoEntity.setUid(saveUserEntity.getUserid());
        userCheckinfoEntity.setUserType(Byte.valueOf(vo.getUserType()));//修改用户类型
        userCheckinfoEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //插入时间
        userCheckInfoRepository.saveAndFlush(userCheckinfoEntity);

        /**3. 保存用户财富信息 */
        YskUserWealthEntity userWealthEntity = new YskUserWealthEntity();
        userWealthEntity.setUid(saveUserEntity.getUserid());
        userWealthEntity.setHuabao(new BigDecimal(0.00));
        userWealthEntity.setIntegral(new BigDecimal(0.00));
        userWealthEntity.setKucunIntegral(new BigDecimal(0.00));
        userWealthEntity.setMoney(new BigDecimal(0.00));
        userWealthEntity.setTotalHuabao(new BigDecimal(0.00));
        userWealthEntity.setTotalIntegral(new BigDecimal(0.00));
        userWealthEntity.setTotalMoney(new BigDecimal(0.00));
        userWealthEntity.setUptime(String.valueOf(MyUtils.getSecondTimestamp(new Date())));
        userWealthEntity.setUptimeing(new Timestamp(System.currentTimeMillis()));

        userWealthRepository.saveAndFlush(userWealthEntity);

        ApiUserLoginVo loginVo = new ApiUserLoginVo();
        BeanUtils.copyProperties(saveUserEntity, loginVo);
        String uuid = UuidUtils.getUUID();
        loginVo.setSessionId(uuid);
        loginVo.setUserType(String.valueOf(saveUserEntity.getUserType()));
        loginVo.setIsSetSafePwd("0");//注册没有设置过安全密码
        returnVo.setData(loginVo);

        return returnVo;

    }

    //登录用户
    @Transactional
    public JsonResult loginUser(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();

        List<YskUserEntity> userList = new ArrayList<YskUserEntity>();
        //校验是否为手机号
        if (CheckUtils.isCheckMobile(vo.getAccount())) {
            userList = userRepository.findByMobile(vo.getAccount());
        } else {
            userList = userRepository.findByAccount(vo.getAccount());
        }

        if (userList.size() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("用户名或手机不存在");
            return returnVo;
        }
        //登录人信息
        YskUserEntity userEntity = userList.get(0);

        String password = PswUtils.getCipher(vo.getLoginPwd(), userEntity.getLoginSalt()); //加密密码

        if (!password.equals(userEntity.getLoginPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("密码不正确");
            return returnVo;
        }

        if (userEntity.getStatus() != 1) {
            returnVo.setCode(0);
            returnVo.setMessage("用户已被锁定，请联系管理员");
            return returnVo;
        }

        ApiUserLoginVo loginVo = new ApiUserLoginVo();
        BeanUtils.copyProperties(userEntity, loginVo);
        String uuid = UuidUtils.getUUID();
        loginVo.setSessionId(uuid);
        loginVo.setUserType(String.valueOf(userEntity.getUserType()));
        //是否设置过安全密码
        if (StringUtils.isNullOrEmpty(userEntity.getSafetyPwd())) { //没设置过
            loginVo.setIsSetSafePwd("0");
        } else {
            loginVo.setIsSetSafePwd("1");
        }
        //YskUserEntity user = this.getUser(vo.getAccount(), vo.getLoginPwd());
        ApiLoginVo apiLoginVo = new ApiLoginVo();
        apiLoginVo.setUserEntity(userEntity);
        apiLoginVo.setApiUserLoginVo(loginVo);

        returnVo.setMessage("登录成功");

        returnVo.setData(apiLoginVo);

        return returnVo;
    }

    //找回密码
    @Transactional
    public JsonResult findPwd(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();
        //通过手机号获取用户信息
        List<YskUserEntity> userList = userRepository.findByMobile(vo.getMobile());

        if (userList.size() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("手机号未注册");
            return returnVo;
        }
        //登录人信息
        YskUserEntity userEntity = userList.get(0);
        userEntity.setLoginSalt(PswUtils.getSalt()); //加密时间盐值
        userEntity.setLoginPwd(PswUtils.getCipher(vo.getLoginPwd(), userEntity.getLoginSalt())); //加密密码
        userRepository.saveAndFlush(userEntity);
        return returnVo;
    }

    //修改密码
    @Transactional
    public JsonResult updatePwd(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();
        //查询登录用户信息
        Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUserid());

        if (!opUserEntity.isPresent()) {
            returnVo.setCode(0);
            returnVo.setMessage("用户不存在");
            return returnVo;
        }

        YskUserEntity userEntity = opUserEntity.get();

        String password = PswUtils.getCipher(vo.getOldLoginPwd(), userEntity.getLoginSalt()); //加密密码

        if (!password.equals(userEntity.getLoginPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("密码不正确");
            return returnVo;
        }

        userEntity.setLoginSalt(PswUtils.getSalt()); //加密时间盐值
        userEntity.setLoginPwd(PswUtils.getCipher(vo.getLoginPwd(), userEntity.getLoginSalt())); //加密密码
        userRepository.saveAndFlush(userEntity);
        return returnVo;
    }

    //保存安全密码
    @Transactional
    public JsonResult saveSafePassword(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();
        //通过手机号获取用户信息
        List<YskUserEntity> userList = userRepository.findByMobile(vo.getMobile());

        if (userList.size() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("手机号未注册");
            return returnVo;
        }
        //登录人信息
        YskUserEntity userEntity = userList.get(0);
        userEntity.setSafetySalt(PswUtils.getSalt()); //加密时间盐值
        userEntity.setSafetyPwd(PswUtils.getCipher(vo.getSafetyPwd(), userEntity.getSafetySalt())); //加密密码
        userRepository.saveAndFlush(userEntity);
        return returnVo;
    }

    //修改密码
    @Transactional
    public JsonResult updateSafePassword(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();
        //查询登录用户信息
        Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUserid());

        if (!opUserEntity.isPresent()) {
            returnVo.setCode(0);
            returnVo.setMessage("用户不存在");
            return returnVo;
        }

        YskUserEntity userEntity = opUserEntity.get();

        String password = PswUtils.getCipher(vo.getOldSafetyPwd(), userEntity.getSafetySalt()); //加密密码

        if (!password.equals(userEntity.getSafetyPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("密码不正确");
            return returnVo;
        }

        userEntity.setSafetySalt(PswUtils.getSalt()); //加密时间盐值
        userEntity.setSafetyPwd(PswUtils.getCipher(vo.getSafetyPwd(), userEntity.getSafetySalt())); //加密密码
        userRepository.saveAndFlush(userEntity);
        return returnVo;
    }

    //发送短信
    private String sendMsg(String mobile, String content) throws Exception {

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://smssh1.253.com/msg/send/json");
        post.addRequestHeader("Content-Type", "application/json");//在头文件中设置转码
//  		NameValuePair[] data ={ 
//  				new NameValuePair("account", "CN1352462"),
//  				new NameValuePair("password", "oNrLOt36Xxaf3a"),
//  				new NameValuePair("msg",content),
//  				new NameValuePair("phone", mobile),
//  				new NameValuePair("report","true")};
        String data = "{\"account\":\"CN1352462\","
                + "\"password\":\"oNrLOt36Xxaf3a\","
                + "\"msg\":\"" + content + "\","
                + "\"phone\":\"" + mobile + "\","
                + "\"report\":\"true\"}";

        System.out.println(data);

        RequestEntity entity = new StringRequestEntity(data, "application/json", "UTF-8");
        post.setRequestEntity(entity);

        client.executeMethod(post);

        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }

        String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));

        System.out.println(result); //打印返回消息状态

        post.releaseConnection();
        JSONObject jsonObject = JSONObject.fromObject(result);

        return jsonObject.getString("code");
    }

    /**
     * 查找用户的分享人
     *
     * @param userId
     * @return
     */
    @Transactional
    public JsonResult findShareUser(Integer userId) {

        JsonResult result = new JsonResult();

        ApiUserChildrenVo returnVo = new ApiUserChildrenVo();

        if (userId < 1 || null == userId) {
            result.setCode(0);
            result.setMessage("用户id不能小于1");
            return result;
        }
        Optional<YskUserEntity> findById = userRepository.findById(userId);
        if (findById.isPresent()) {
            int pid = findById.get().getPid();
            if (pid == 0) {
                result.setCode(1);
                result.setMessage("该用户没有分享人");
                return result;
            }
            YskUserEntity yskUserEntity = userRepository.findById(pid).get();
            returnVo.setAccount(yskUserEntity.getAccount());
            returnVo.setUsername(yskUserEntity.getUsername());
            returnVo.setMobile(yskUserEntity.getMobile());
            returnVo.setEmail(yskUserEntity.getEmail());
            result.setMessage("查找分享人成功");
            result.setData(returnVo);
            return result;
        }
        result.setCode(0);
        result.setMessage("用户不存在");
        return result;
    }

    /**
     * 根据用户id查找用户分享下级
     *
     * @param userId
     * @return
     */
    @Transactional
    public ApiUserChildrenVo findChildren(Integer userId) {

        ApiUserChildrenVo returnVo = new ApiUserChildrenVo();

        YskUserEntity userEntity = userRepository.findByUserid(userId);
        YskUserEntity entity = userRepository.findByUserid(userEntity.getPid());
        if (entity != null) {
            returnVo.setAccount(entity.getAccount()); // 我的分享人账号
        }

        //统计我邀请人信息
        List<YskUserEntity> userZeroListP = userRepository.findByPidAndLevel(userId, (byte) 0); //我邀请的一级消费商
        List<YskUserEntity> userZeroListG = userRepository.findByGidAndLevel(userId, (byte) 0); //我邀请的二级消费商
        returnVo.setZeroSize(userZeroListP.size() + userZeroListG.size()); //我邀请的消费商统计

        List<YskUserEntity> userOneListP = userRepository.findByPidAndLevel(userId, (byte) 1); //我邀请的一级vip用户
        List<YskUserEntity> userOneListG = userRepository.findByGidAndLevel(userId, (byte) 1); //我邀请的二级vip用户
        returnVo.setOneSize(userOneListP.size() + userOneListG.size()); //我邀请的vip用户统计

        List<YskUserEntity> userTwoListP = userRepository.findByPidAndLevel(userId, (byte) 2); //我邀请的一级联盟商家
        List<YskUserEntity> userTwoListG = userRepository.findByGidAndLevel(userId, (byte) 2); //我邀请的二级联盟商家
        returnVo.setTwoSize(userTwoListP.size() + userTwoListG.size()); //我邀请的联盟商家统计

        return returnVo;
    }

    /**
     * 查找下级
     *
     * @param userId 用户id
     * @return
     */
    public JsonResult getChildrenByLevel(Integer userId, Byte level) {

        JsonResult result = new JsonResult();

        List<YskUserEntity> userListP = userRepository.findByPidAndLevel(userId, (byte) level); //我邀请的一级消费商
        List<YskUserEntity> userListG = userRepository.findByGidAndLevel(userId, (byte) level); //我邀请的二级消费商
        List<ApiUserChildrenVo> returnList = new ArrayList<ApiUserChildrenVo>();
        for (int i = 0; i < userListP.size(); i++) {
            ApiUserChildrenVo pVo = new ApiUserChildrenVo();
            pVo.setAccount(userListP.get(i).getAccount());
            pVo.setUsername(userListP.get(i).getUsername());
            pVo.setMobile(userListP.get(i).getMobile());
            pVo.setEmail(userListP.get(i).getEmail());
            returnList.add(pVo);
        }
        for (int i = 0; i < userListG.size(); i++) {
            ApiUserChildrenVo gVo = new ApiUserChildrenVo();
            gVo.setAccount(userListG.get(i).getAccount());
            gVo.setUsername(userListG.get(i).getUsername());
            gVo.setMobile(userListG.get(i).getMobile());
            gVo.setEmail(userListG.get(i).getEmail());
            returnList.add(gVo);
        }
        result.setData(returnList);
        return result;
    }

    //查询用户校验信息
    @Transactional
    public JsonResult getUserCheckInfo(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();
        //查询登录用户信息
        Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUserid());

        if (!opUserEntity.isPresent()) {
            returnVo.setCode(0);
            returnVo.setMessage("用户不存在");
            return returnVo;
        }

        YskUserEntity userEntity = opUserEntity.get();
        ApiUserVo userVo = new ApiUserVo();
        YskUserCheckinfoEntity userCheckEntity = userCheckInfoRepository.findById(vo.getUserid()).get();
        userVo.setUserType(String.valueOf(userEntity.getUserType())); //用户类型
        userVo.setIsCheckMobile(String.valueOf(userCheckEntity.getIsCheckMobile())); //是否验证手机
        userVo.setIsCheckUser(String.valueOf(userCheckEntity.getIsCheckUser())); //是否进行个人认证
        userVo.setIsCheckCompany(String.valueOf(userCheckEntity.getIsCheckCompany())); //是否进行企业认证
        userVo.setUsername(userEntity.getUsername());//姓名
        userVo.setIdcard(userCheckEntity.getIdcard()); //证件号
        userVo.setIdcarStartdate(userCheckEntity.getIdcarStartdate()); //证件开始日期
        userVo.setIdcarEndtdate(userCheckEntity.getIdcarEndtdate()); //证件结束日期
        userVo.setIdcardType(userCheckEntity.getIdcardType()); //0-大陆身份证 1-非大陆身份证
        userVo.setCountry(userCheckEntity.getCountry()); //国家
        userVo.setProvince(userCheckEntity.getProvince()); //省
        userVo.setCity(userCheckEntity.getCity()); //市
        userVo.setDistrict(userCheckEntity.getDistrict()); //区
        userVo.setIdcardImgFace(userCheckEntity.getIdcardImgFace()); //身份证正面照
        userVo.setIdcardImgBack(userCheckEntity.getIdcardImgBack());  //身份证反面照
        userVo.setIdcardImgHand(userCheckEntity.getIdcardImgHand());    //手持身份证照
        returnVo.setData(userVo);
        return returnVo;
    }

    //认证用户手机信息
    @Transactional
    public JsonResult updateIsCheckMobile(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();
        //查询登录用户信息
        Optional<YskUserCheckinfoEntity> opUserCheckEntity = userCheckInfoRepository.findById(vo.getUserid());

        if (!opUserCheckEntity.isPresent()) {
            returnVo.setCode(0);
            returnVo.setMessage("用户不存在");
            return returnVo;
        }

        YskUserCheckinfoEntity userCheckEntity = opUserCheckEntity.get();
        userCheckEntity.setIsCheckMobile((byte) 1);
        userCheckInfoRepository.saveAndFlush(userCheckEntity);
        return returnVo;
    }

    //认证用户个人信息
    @Transactional
    public JsonResult updateIsCheckUser(ApiUserVo vo, Boolean isUser) {

        JsonResult returnVo = new JsonResult();

        //查询登录用户信息
        Optional<YskUserEntity> opUserEntity = userRepository.findById(vo.getUserid());

        if (!opUserEntity.isPresent()) {
            returnVo.setCode(0);
            returnVo.setMessage("用户不存在");
            return returnVo;
        }

        YskUserEntity userEntity = opUserEntity.get();

        YskUserCheckinfoEntity checkInfo = userEntity.getCheckinfoEntity();

        if (checkInfo.getIsCheckMobile() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("请先进行手机认证");
            return returnVo;
        }

        if (isUser) {
            userEntity.setUsername(vo.getUsername());
        } else {
            userEntity.setUsername(vo.getCompanyName());
        }
        userRepository.saveAndFlush(userEntity);

        YskUserCheckinfoEntity userCheckEntity = userCheckInfoRepository.findById(vo.getUserid()).get();
        BeanUtils.copyProperties(vo, userCheckEntity);//将Vo数据存入Entity中
        userCheckEntity.setCountry("中国");
        userCheckEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
        if (isUser) {
            userCheckEntity.setIsCheckUser((byte) 1); //审核
        } else {
            userCheckEntity.setIsCheckCompany((byte) 1); //审核
        }
        userCheckInfoRepository.saveAndFlush(userCheckEntity);

        return returnVo;
    }

    //通过手机查询用户信息
    @Transactional
    public JsonResult getUserInfoByMobile(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();

        ApiUserVo userVo = new ApiUserVo();

        //校验手机号是否注册
        List<YskUserEntity> listByMobile = userRepository.findByMobile(vo.getMobile());
        if (listByMobile.size() == 0) {
            userVo.setUsername("用户不存在");
            userVo.setAccount("");
            returnVo.setData(userVo);
            return returnVo;
        }

        userVo.setUsername(listByMobile.get(0).getUsername());
        userVo.setAccount(listByMobile.get(0).getAccount());
        returnVo.setData(userVo);

        return returnVo;
    }
  	
/*  	public YskUserEntity getUser(String account,String loginPwd) {
  		//YskUserEntity yskUserEntity = userRepository.findByAccountAndLoginPwd(account, loginPwd);
  		List<YskUserEntity> list = userRepository.findByMobile(account);
  		YskUserEntity yskUserEntity = list.get(0);
  		return yskUserEntity;
  	}*/
}
