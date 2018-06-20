package com.hctxsys.controller.api;

import com.hctxsys.entity.YskGoodCarEntity;
import com.hctxsys.entity.YskUserAddressEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.service.api.ApiUserServiceImpl;
import com.hctxsys.util.CheckUtils;
import com.hctxsys.util.Contant;
import com.hctxsys.util.IdCardUtil;
import com.hctxsys.vo.api.ApiLoginVo;
import com.hctxsys.vo.api.ApiUserChildrenVo;
import com.hctxsys.vo.api.ApiUserId;
import com.hctxsys.vo.api.ApiUserLoginVo;
import com.hctxsys.vo.api.ApiUserVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api/user")
public class ApiUserController {

    @Autowired
    private ApiUserServiceImpl userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 根据用户ID查询购物车
     *
     * @param userEntity
     * @return
     */
    @PostMapping("/userCar")
    @ResponseBody
    public JsonResult getUserCar(@RequestBody ApiUserId userEntity) {
        JsonResult returnVo = new JsonResult();
        if (userEntity.getUserId() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("用户ID不能为空");
            return returnVo;
        }
        return userService.getUserCar(userEntity.getUserId());
    }

    /**
     * 根据购物车编号删除购物车信息
     *
     * @param carEntity 购物车
     * @return
     */
    @PostMapping("/delUserCar")
    @ResponseBody
    public JsonResult delUserCar(@RequestBody YskGoodCarEntity carEntity) {
        JsonResult returnVo = new JsonResult();
        if (carEntity.getId() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("购物车编号不能为空");
        }
        return userService.deleteCar(carEntity.getId());

    }

    /**
     * 根据用户ID查询收获地址
     *
     * @param addressEntity
     * @return
     */
    @PostMapping("/userAddress")
    @ResponseBody
    public JsonResult getUserAddress(@RequestBody YskUserAddressEntity addressEntity) {
        JsonResult returnVo = new JsonResult();
        if (addressEntity.getUserId() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("用户ID不能为空");
            return returnVo;
        }
        return userService.getAddress(addressEntity.getUserId());

    }

    /**
     * 添加收获地址，根据默认标识判断是否添加默认地址
     *
     * @param params 地址参数
     * @return
     */
    @PostMapping("/addAddress")
    @ResponseBody
    public JsonResult addUserAddress(@RequestBody YskUserAddressEntity params) {
        JsonResult returnVo = new JsonResult();
        if (0 == params.getUserId()) {
            returnVo.setCode(0);
            returnVo.setMessage("用户ID不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(params.getUserName())) {
            returnVo.setCode(0);
            returnVo.setMessage("用户名不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(params.getProvince())) {
            returnVo.setCode(0);
            returnVo.setMessage("省份不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(params.getCity())) {
            returnVo.setCode(0);
            returnVo.setMessage("城市不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(params.getDistrict())) {
            returnVo.setCode(0);
            returnVo.setMessage("区不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(params.getDetailAddress())) {
            returnVo.setCode(0);
            returnVo.setMessage("详细地址不能为空");
            return returnVo;
        }
        if (!CheckUtils.isCheckMobile(params.getUserMobile())) {
            returnVo.setCode(0);
            returnVo.setMessage("手机格式不正确");
            return returnVo;
        }
        return userService.addUserAddress(params);
    }

    /**
     * s设置默认地址
     *
     * @param addressEntity 地址
     * @return
     */
    @RequestMapping("setDefault")
    @ResponseBody
    public JsonResult setDefault(@RequestBody YskUserAddressEntity addressEntity) {
        return userService.setDefault(addressEntity.getId());
    }

    /**
     * 获取地址信息
     *
     * @param addressEntity 地址
     * @return
     */
    @PostMapping("/getOneAddress")
    @ResponseBody
    public JsonResult getOneAddress(@RequestBody YskUserAddressEntity addressEntity) {
        JsonResult returnVo = new JsonResult();
        if (addressEntity.getId() == 0) {
            returnVo.setCode(0);
            returnVo.setMessage("地址ID不能为空");
            return returnVo;
        }
        return userService.getOneUserAddress(addressEntity.getId());
    }

    @PostMapping("/editAddress")
    @ResponseBody
    public JsonResult editUserAddress(@RequestBody YskUserAddressEntity addressEntity) {
        JsonResult returnVo = new JsonResult();
        if (0 == addressEntity.getUserId()) {
            returnVo.setCode(0);
            returnVo.setMessage("用户ID不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(addressEntity.getUserName())) {
            returnVo.setCode(0);
            returnVo.setMessage("用户名不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(addressEntity.getProvince())) {
            returnVo.setCode(0);
            returnVo.setMessage("省份能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(addressEntity.getCity())) {
            returnVo.setCode(0);
            returnVo.setMessage("城市不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(addressEntity.getDistrict())) {
            returnVo.setCode(0);
            returnVo.setMessage("区不能为空");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(addressEntity.getDetailAddress())) {
            returnVo.setCode(0);
            returnVo.setMessage("详细地址不能为空");
            return returnVo;
        }
        if (!CheckUtils.isCheckMobile(addressEntity.getUserMobile())) {
            returnVo.setCode(0);
            returnVo.setMessage("手机格式不正确");
            return returnVo;
        }
        return userService.editUserAddress(addressEntity);
    }

    @PostMapping("/delAddress")
    @ResponseBody
    public JsonResult delAddress(@RequestBody YskUserAddressEntity addressEntity) {
        JsonResult jsonResult = new JsonResult();
        if (addressEntity.getId() == 0) {
            jsonResult.setCode(0);
            jsonResult.setMessage("地址ID不能为空");
        }
        return userService.delAddress(addressEntity.getId());
    }

    /**
     * 获取用户最新动态
     *
     * @return
     */
    @PostMapping("shopNew")
    @ResponseBody
    public JsonResult getShopNew() {
        return userService.getShopNew();
    }

    /**
     * 个人转企业
     *
     * @param checkinfoEntity 个人信息
     * @return
     */
    @PostMapping("userChange")
    @ResponseBody
    public JsonResult userChange(@RequestBody ApiUserVo checkinfoEntity) {
        JsonResult jsonResult = new JsonResult();
        if (checkinfoEntity.getUserid() == null) {
            jsonResult.setCode(0);
            jsonResult.setMessage("用户ID不能为空");
            return jsonResult;
        }
        if (!CheckUtils.isCheckMobile(checkinfoEntity.getMobile())) {
            jsonResult.setCode(0);
            jsonResult.setMessage("手机号格式错误");
            return jsonResult;
        }
        return userService.userChange(checkinfoEntity);
    }

    /**
     * 查询个人信息
     *
     * @param userEntity 用户
     * @return
     */
    @PostMapping("userInfo")
    @ResponseBody
    public JsonResult userInfo(@RequestBody YskUserEntity userEntity) {
        JsonResult jsonResult = new JsonResult();
        if (userEntity.getUserid() == null) {
            jsonResult.setCode(0);
            jsonResult.setMessage("用户ID不能为空");
            return jsonResult;
        }
        return userService.userInfo(userEntity.getUserid());
    }

    /**
     * 更新用户email
     *
     * @param userEntity 用户信息
     * @return
     */
    @PostMapping("updateEmail")
    @ResponseBody
    public JsonResult updateEmail(@RequestBody YskUserEntity userEntity) {
        JsonResult jsonResul = new JsonResult();
        if (userEntity.getUserid() == null) {
            jsonResul.setCode(0);
            jsonResul.setMessage("用户ID不能为空");
            return jsonResul;
        }
        if (!CheckUtils.checkEmail(userEntity.getEmail())) {
            jsonResul.setCode(0);
            jsonResul.setMessage("email格式错误");
            return jsonResul;
        }
        return userService.updateEmail(userEntity);
    }

    @PostMapping("updateHeadImg")
    @ResponseBody
    public JsonResult updateHeadImg(@RequestBody ApiUserId userid) {
        JsonResult jsonResult = new JsonResult();
        if (userid.getUserId() == 0) {
            jsonResult.setCode(0);
            jsonResult.setMessage("用户名不能为空");
            return jsonResult;
        }
        return userService.updateHeadImg(userid);
    }

    /**
     * 个人钱包
     *
     * @param user
     * @return
     */
    @PostMapping("userWallet")
    @ResponseBody
    public JsonResult userWallet(@RequestBody ApiUserId user) {
        JsonResult jsonResult = new JsonResult();
        if (user.getUserId() == 0) {
            jsonResult.setCode(0);
            jsonResult.setMessage("用户名不能为空");
            return jsonResult;
        }
        return userService.userWallet(user);
    }

    //获取验证码
    @RequestMapping(value = "/getVerifyingCode", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getVerifyingCode(@RequestBody ApiUserVo vo) throws Exception {
        JsonResult returnVo = this.checkMobile(vo);
        //如果校验失败返回错误信息
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        //从缓存中读取验证码
        String verificationCode = redisTemplate.opsForValue().get(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile());
        //如果缓存中不存在验证码 获取新验证码
        if (StringUtils.isNullOrEmpty(verificationCode)) {
            verificationCode = userService.getVerifyingCode(vo);
            if ("-1".equals(verificationCode)) {
                returnVo.setCode(0);
                returnVo.setMessage("发送验证码失败");
                return returnVo;
            }
            //将短信验证码信息放入redis中
            redisTemplate.opsForValue().set(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile(), verificationCode, 5, TimeUnit.MINUTES);
        }
        //TODO: 正式使用短信服务器之后不往前台返回验证码
//  		System.out.println(verificationCode);
//  		ApiUserVo data = new ApiUserVo();
//  		data.setVerificationCode(verificationCode);
//  		returnVo.setData(data);
        return returnVo;
    }

    //注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(@RequestBody ApiUserVo vo, HttpServletRequest request) {

        JsonResult returnVo = this.checkRegisterInfo(vo);
        //如果校验失败返回错误信息
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        //校验验证码是否正确
        returnVo = this.checkVerificationCode(vo);
        if (returnVo.getCode() == 0) {
            return returnVo;
        }

        //获取访问ip
        if (request.getHeader("x-forwarded-for") == null) {
            vo.setRegIp(request.getRemoteAddr());
        } else {
            vo.setRegIp(request.getHeader("x-forwarded-for"));
        }

        returnVo = userService.registerUser(vo);
        
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        //清除手机验证码
        redisTemplate.delete(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile());

        ApiUserLoginVo userInfo = (ApiUserLoginVo) returnVo.getData();
        
        //将登陆信息放入缓存中
        this.addUserSessionInfo(userInfo.getSessionId(), userInfo.getUserid().toString(), userInfo.getAccount());

        return returnVo;
    }

    //登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();
        //校验账号是否为空
        if (StringUtils.isNullOrEmpty(vo.getAccount())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入账号或手机号");
            return returnVo;
        }
        //校验密码是否为空
        if (StringUtils.isNullOrEmpty(vo.getLoginPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入密码");
            return returnVo;
        }

        returnVo = userService.loginUser(vo);
        //YskUserEntity user = userService.getUser(vo.getAccount(), vo.getAccount());
        //ApiUserLoginVo userInfo = (ApiUserLoginVo) returnVo.getData();
        ApiLoginVo userInfo = (ApiLoginVo) returnVo.getData();
        if (userInfo == null) return returnVo;
        //将登陆信息放入缓存中
        this.addUserSessionInfo(userInfo.getApiUserLoginVo().getSessionId(), userInfo.getApiUserLoginVo().getUserid().toString(), userInfo.getApiUserLoginVo().getAccount());

        return returnVo;
    }

    /**
     * 登出
     *
     * @param vo
     * @return
     */
    @PostMapping("logout")
    @ResponseBody
    public JsonResult loginOut(@RequestBody YskUserEntity user) {
        try {
            //清除原登录信息
            redisTemplate.delete(Contant.LOGIN_STATUS + user.getAccount());
            return new JsonResult(1, "退出成功");
        } catch (Exception e) {
            return new JsonResult(0, "退出失败");
        }

    }

    //找回密码
    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findPassword(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = this.checkPwdInfo(vo, true);
        //如果校验失败返回错误信息
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        //校验验证码是否正确
        returnVo = this.checkVerificationCode(vo);
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        returnVo = userService.findPwd(vo);
        //清除手机验证码
        redisTemplate.delete(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile());

        return returnVo;
    }

    //修改密码
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updatePassword(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = this.checkPwdInfo(vo, false);
        //如果校验失败返回错误信息
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        returnVo = this.checkUserid(vo);
        //校验用户账号是否为空
        if (returnVo.getCode() == 0) {
            return returnVo;
        }

        returnVo = userService.updatePwd(vo);

        return returnVo;
    }

    //设置安全密码
    @RequestMapping(value = "/saveSafePassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveSafePassword(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = this.checkSafePwdInfo(vo, true);
        //如果校验失败返回错误信息
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        //校验验证码是否正确
        returnVo = this.checkVerificationCode(vo);
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        returnVo = userService.saveSafePassword(vo);
        //清除手机验证码
        redisTemplate.delete(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile());

        return returnVo;
    }

    //修改安全密码
    @RequestMapping(value = "/updateSafePassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateSafePassword(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = this.checkSafePwdInfo(vo, false);
        //如果校验失败返回错误信息
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        returnVo = this.checkUserid(vo);
        //校验用户账号是否为空
        if (returnVo.getCode() == 0) {
            return returnVo;
        }

        returnVo = userService.updateSafePassword(vo);

        return returnVo;
    }

    //校验注册信息
    private JsonResult checkRegisterInfo(ApiUserVo vo) {

        JsonResult returnVo = this.checkPwdInfo(vo, true);

        //如果校验失败返回错误信息
        if (returnVo.getCode() == 0) {
            return returnVo;
        }

        //校验用户类型是否为空
        if (StringUtils.isNullOrEmpty(vo.getUserType())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入用户类型");
            return returnVo;
        }
        //校验账号是否为空
        if (StringUtils.isNullOrEmpty(vo.getAccount())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入账号");
            return returnVo;
        }
        //校验账号是否正确
        if (!CheckUtils.isCheckAccount(vo.getAccount())) {
            returnVo.setCode(0);
            returnVo.setMessage("账号只能为6-20位数字和字母");
            return returnVo;
        }
        //如果用户为机构
        if ("1".equals(vo.getUserType())) {
            //校验公司名称是否为空
            if (StringUtils.isNullOrEmpty(vo.getCompanyName())) {
                returnVo.setCode(0);
                returnVo.setMessage("请输入公司名称");
                return returnVo;
            }
            //校验营业执照是否为空
            if (StringUtils.isNullOrEmpty(vo.getCompanyLicense())) {
                returnVo.setCode(0);
                returnVo.setMessage("请输入营业执照");
                return returnVo;
            }
            //校验组织机构是否为空
            if (StringUtils.isNullOrEmpty(vo.getCompanyOrganize())) {
                returnVo.setCode(0);
                returnVo.setMessage("请选择组织机构");
                return returnVo;
            }
        }
        return returnVo;
    }

    //校验安全密码信息
    private JsonResult checkSafePwdInfo(ApiUserVo vo, Boolean isSaveSafePwdFlag) {

        JsonResult returnVo = new JsonResult();
        if (isSaveSafePwdFlag) {
            returnVo = this.checkMobile(vo);
            //如果校验失败返回错误信息
            if (returnVo.getCode() == 0) {
                return returnVo;
            }
        } else {
            //校验旧安全密码是否为空
            if (StringUtils.isNullOrEmpty(vo.getOldSafetyPwd())) {
                returnVo.setCode(0);
                returnVo.setMessage("请输入旧密码");
                return returnVo;
            }
        }
        //校验安全密码是否为空
        if (StringUtils.isNullOrEmpty(vo.getSafetyPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入密码");
            return returnVo;
        }
        //校验确认安全密码是否为空
        if (StringUtils.isNullOrEmpty(vo.getConfirmSafetyPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入确认密码");
            return returnVo;
        }
        //校验两次安全密码是否一致
        if (!vo.getSafetyPwd().equals(vo.getConfirmSafetyPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("两次密码不一致");
            return returnVo;
        }
        return returnVo;
    }

    //校验密码信息
    private JsonResult checkPwdInfo(ApiUserVo vo, Boolean isFindPwdFlag) {

        JsonResult returnVo = new JsonResult();
        if (isFindPwdFlag) {
            returnVo = this.checkMobile(vo);
            //如果校验失败返回错误信息
            if (returnVo.getCode() == 0) {
                return returnVo;
            }
        } else {
            //校验旧密码是否为空
            if (StringUtils.isNullOrEmpty(vo.getOldLoginPwd())) {
                returnVo.setCode(0);
                returnVo.setMessage("请输入旧密码");
                return returnVo;
            }
        }
        //校验密码是否为空
        if (StringUtils.isNullOrEmpty(vo.getLoginPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入密码");
            return returnVo;
        }
        //校验确认登录密码是否为空
        if (StringUtils.isNullOrEmpty(vo.getConfirmLoginPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入确认密码");
            return returnVo;
        }
        //校验两次密码是否一致
        if (!vo.getLoginPwd().equals(vo.getConfirmLoginPwd())) {
            returnVo.setCode(0);
            returnVo.setMessage("两次密码不一致");
            return returnVo;
        }
        return returnVo;
    }

    //校验手机号
    private JsonResult checkMobile(ApiUserVo vo) {

        JsonResult returnVo = new JsonResult();
        //校验手机号是否为空
        if (StringUtils.isNullOrEmpty(vo.getMobile())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入手机号");
            return returnVo;
        }
        //校验手机号是否正确
        if (!CheckUtils.isCheckMobile(vo.getMobile())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入正确的手机号");
            return returnVo;
        }

        return returnVo;
    }

    /**
     * 根据用户id找分享人
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/share", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findShareUser(@RequestBody YskUserEntity vo) {
        JsonResult result = userService.findShareUser(vo.getUserid());
        return result;
    }


    /**
     * 查找下级
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/children", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findChildren(@RequestBody YskUserEntity vo) {
        JsonResult result = new JsonResult();
        if (vo.getUserid() < 1 || null == vo.getUserid()) {
            result.setCode(0);
            result.setMessage("id不能小于1");
            return result;
        }
        ApiUserChildrenVo children = userService.findChildren(vo.getUserid());
        result.setCode(1);
        result.setMessage("查找我邀请的人成功");
        result.setData(children);
        return result;
    }

    /**
     * 根据等级查询我邀请的人列表
     *
     * @param userId 用户id
     * @param level  等级
     * @return
     */
    @RequestMapping(value = "/byLevel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult findByLevel(@RequestBody YskUserEntity vo) {
        JsonResult result = userService.getChildrenByLevel(vo.getUserid(), vo.getLevel());
        return result;
    }

    //查询用户认证信息
    @RequestMapping(value = "/getUserCheckInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getUserCheckInfo(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = this.checkUserid(vo);
        //校验用户账号是否为空
        if (returnVo.getCode() == 0) {
            return returnVo;
        }

        returnVo = userService.getUserCheckInfo(vo);

        return returnVo;
    }

    //进行手机认证
    @RequestMapping(value = "/updateIsCheckMobile", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateIsCheckMobile(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = this.checkUserid(vo);
        //校验用户账号是否为空
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        //校验验证码是否正确
        returnVo = this.checkVerificationCode(vo);
        if (returnVo.getCode() == 0) {
            return returnVo;
        }

        returnVo = userService.updateIsCheckMobile(vo);
        //清除手机验证码
        redisTemplate.delete(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile());

        return returnVo;
    }

    //进行个人认证
    @RequestMapping(value = "/updateIsCheckUser", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateUserCheckMobile(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = this.checkUserid(vo);
        //校验用户账号是否为空
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        //校验认证信息是否正确
        returnVo = this.checkUserInfo(vo);
        if (returnVo.getCode() == 0) {
            return returnVo;
        }

        returnVo = userService.updateIsCheckUser(vo, true);

        return returnVo;
    }
    
    //进行企业认证
    @RequestMapping(value = "/updateIsCheckCompany", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateIsCheckCompany(@RequestBody ApiUserVo vo) {

        JsonResult returnVo = this.checkUserid(vo);
        //校验用户账号是否为空
        if (returnVo.getCode() == 0) {
            return returnVo;
        }
        //校验认证信息是否正确
        returnVo = this.checkCompanyInfo(vo);
        if (returnVo.getCode() == 0) {
            return returnVo;
        }

        if (vo.getIsLegal() != 1) {
        	vo.setManageParent(vo.getLegalName());
        }

        returnVo = userService.updateIsCheckUser(vo, false);

        return returnVo;
    }

    //通过手机查询用户信息
    @RequestMapping(value = "/getUserInfoByMobile", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getUserInfoByMobile(@RequestBody ApiUserVo vo) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isNullOrEmpty(vo.getMobile())) {
            jsonResult.setCode(0);
            jsonResult.setMessage("手机号不能为空");
            return jsonResult;
        }
        return userService.getUserInfoByMobile(vo);
    }

    //校验验证码
    private JsonResult checkVerificationCode(ApiUserVo vo) {
        JsonResult returnVo = new JsonResult();
        //校验验证码是否为空
        if (StringUtils.isNullOrEmpty(vo.getVerificationCode())) {
            returnVo.setCode(0);
            returnVo.setMessage("请输入验证码");
            return returnVo;
        }
        String verificationCode = redisTemplate.opsForValue().get(Contant.MOBILE_VERIFICATION_CODE + vo.getMobile());
        //校验验证码是否正确
        if (verificationCode == null || !verificationCode.equals(vo.getVerificationCode())) {
            returnVo.setCode(0);
            returnVo.setMessage("验证码错误或已过期");
            return returnVo;
        }
        return returnVo;
    }

    //校验用户id是否为空
    private JsonResult checkUserid(ApiUserVo vo) {
        JsonResult returnVo = new JsonResult();
        if (vo.getUserid() == null) {
            returnVo.setCode(0);
            returnVo.setMessage("用户id为空");
            return returnVo;
        }
        return returnVo;
    }

    //校验用户认证相关信息
    private JsonResult checkUserInfo(ApiUserVo vo) {
        JsonResult returnVo = new JsonResult();
        if (StringUtils.isNullOrEmpty(vo.getUsername())) {
            returnVo.setCode(0);
            returnVo.setMessage("请填写姓名");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(vo.getIdcard())) {
            returnVo.setCode(0);
            returnVo.setMessage("请填写身份证");
            return returnVo;
        }
        if (vo.getIdcarStartdate() == null || vo.getIdcarEndtdate() == null) {
            returnVo.setCode(0);
            returnVo.setMessage("请选择证件有效期");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(vo.getProvince()) || StringUtils.isNullOrEmpty(vo.getCity()) || StringUtils.isNullOrEmpty(vo.getDistrict())) {
            returnVo.setCode(0);
            returnVo.setMessage("请选择所在区域");
            return returnVo;
        }
        //如果是大陆身份证 校验合法性
        if (vo.getIdcardType() == 0 && !IdCardUtil.isIDCard(vo.getIdcard())) {
            returnVo.setCode(0);
            returnVo.setMessage("身份证号格式错误");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(vo.getIdcardImgFace()) || StringUtils.isNullOrEmpty(vo.getIdcardImgBack()) || StringUtils.isNullOrEmpty(vo.getIdcardImgHand())) {
            returnVo.setCode(0);
            returnVo.setMessage("请上传所有图片");
            return returnVo;
        }
        return returnVo;
    }
    
    //校验企业用户认证相关信息
    private JsonResult checkCompanyInfo(ApiUserVo vo) {
        JsonResult returnVo = new JsonResult();
        if (StringUtils.isNullOrEmpty(vo.getCompanyName())) {
            returnVo.setCode(0);
            returnVo.setMessage("请填写公司名称");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(vo.getCreditNo())) {
            returnVo.setCode(0);
            returnVo.setMessage("请填写社会信用代码");
            return returnVo;
        }
        if (vo.getIsThreeCard() == 0 && StringUtils.isNullOrEmpty(vo.getTaxNo())) {
            returnVo.setCode(0);
            returnVo.setMessage("请填写税务登记证");
            return returnVo;
        }
        if (vo.getIsThreeCard() == 0 && StringUtils.isNullOrEmpty(vo.getOrganizeNo())) {
            returnVo.setCode(0);
            returnVo.setMessage("请填写组织机构证");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(vo.getLegalName())) {
            returnVo.setCode(0);
            returnVo.setMessage("请填写法人姓名");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(vo.getCompanyType())) {
            returnVo.setCode(0);
            returnVo.setMessage("请选择公司类型");
            return returnVo;
        }
        
        if (StringUtils.isNullOrEmpty(vo.getProvince()) || StringUtils.isNullOrEmpty(vo.getCity()) || StringUtils.isNullOrEmpty(vo.getDistrict())) {
            returnVo.setCode(0);
            returnVo.setMessage("请选择所在区域");
            return returnVo;
        }
        if (vo.getIsLegal() == 1 && StringUtils.isNullOrEmpty(vo.getManageParent())) {
            returnVo.setCode(0);
            returnVo.setMessage("请填写经营负责人");
            return returnVo;
        }
        if (StringUtils.isNullOrEmpty(vo.getIdcardImgFace()) || 
        		StringUtils.isNullOrEmpty(vo.getIdcardImgBack()) || 
        		 StringUtils.isNullOrEmpty(vo.getIdcardImgHand()) || 
        		 StringUtils.isNullOrEmpty(vo.getCompanyLicenseImg()) ) {
            returnVo.setCode(0);
            returnVo.setMessage("请上传所有图片");
            return returnVo;
        }
        return returnVo;
    }

    //将用户登录信息放入缓存
    private void addUserSessionInfo(String sessionId, String userid, String account) {
        //等过账号查询是否已经登录
        String loginSession = redisTemplate.opsForValue().get(Contant.LOGIN_STATUS + account);
        if (loginSession != null) {
            //清除原登录信息
            redisTemplate.delete(Contant.LOGIN_STATUS + loginSession);
        }
        //注入新的session信息
        redisTemplate.opsForValue().set(Contant.LOGIN_STATUS + account, sessionId);
        redisTemplate.opsForValue().set(Contant.LOGIN_STATUS + sessionId, userid);
    }

}
