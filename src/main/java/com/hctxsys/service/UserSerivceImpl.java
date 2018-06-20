package com.hctxsys.service;

import com.hctxsys.entity.*;
import com.hctxsys.repository.*;
import com.hctxsys.util.*;
import com.hctxsys.vo.MemberVo;
import com.hctxsys.vo.UserVo;
import com.hctxsys.vo.WorkOrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("userSerivce")
public class UserSerivceImpl {
    @Autowired
    private EntityManagerUtils managerUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserEntityRepository entityRepository;
    @Autowired
    UserWealthRepository wealthRepository;
    @Autowired
    private YsKUserRepository repository;
    @Autowired
    MoneyRechargeRepository moneyRechargeRepository;
    @Autowired
    private MoneyDetailRepository detailRepository;
    @Autowired
    private UserCheckInfoRepository checkInfoRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UpdateUserInfoRepository updateUserInfoRepository;
    @Autowired
    private IntegralDetailRepository integralDetailRepository;
    /**
     * 根际条件动态查询数据，将分页后结果返回用户列表页面
     *
     * @param result 分页信息的实体类
     * @return result 分页信息的实体类
     */
    public TableResult indexTable(TableResult result) {
        PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());//获取分页对象
        Page<UserEntity> usersPage = entityRepository.findAll(new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();//条件集合
                query.orderBy(criteriaBuilder.desc(root.get("userid")));
                if (null != result.getLevel()) {//等级查询
                    predicates.add(criteriaBuilder.equal(root.get("level"), Byte.valueOf(String.valueOf(result.getLevel()))));
                }
                if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
                    String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.greaterThan(root.get("regDate"), Long.valueOf(date)));
                }
                if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
                    String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.lessThan(root.get("regDate"), Long.valueOf(date) + 60 * 60 * 24));//将时间加到查询时间的后一天0点
                }
                if (null != result.getTypeText() && !("".equals(result.getTypeText()))) {//按类型条件查询
                    if ("account".equals(result.getType())) {//按账号模糊查
                        predicates.add(criteriaBuilder.like(root.get("account"), '%' + result.getTypeText() + '%'));
                    }
                    else if ("mobile".equals(result.getType())) {//按手机模糊查
                        predicates.add(criteriaBuilder.like(root.get("mobile"), result.getTypeText() + '%'));
                    } else {
                        predicates.add(criteriaBuilder.like(root.get("username"),  result.getTypeText() + '%'));
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, request);
        List<UserEntity> all = entityRepository.selectAll();
        List<MemberVo> VoList = new ArrayList<>();//前台展示类
        Map<Integer, UserEntity> userMap = new HashMap<>();//根据主键存放User实体类
        for (UserEntity user : all) {//List转成Map
            userMap.put(user.getUserid(), user);
        }
        for (UserEntity user : usersPage) {
            MemberVo userVo = new MemberVo();
            BeanUtils.copyProperties(user, userVo);//将Entity数据存入Vo中
            if (user.getPid() > 0) {//根据Pid判断是否有父节点，有则设置父节点账号名称
                UserEntity yskUserEntity = userMap.get(user.getPid());
                userVo.setParentName(yskUserEntity.getAccount());
            }
            VoList.add(userVo);
        }
        TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(usersPage.getTotalElements());//设置总记录数
        tableResult.setRows(VoList);//设置分页后的集合
        tableResult.setPageCount(Long.valueOf(usersPage.getTotalPages()));//设置总页数
        return tableResult;
    }

    /**
     * 根际用户ID返回用户数据
     *
     * @param id 用户ID
     * @return 用户展示实体类
     */
    public UserVo editForm(String id) {
        UserVo userVo = new UserVo();
        YskUserEntity yskUserEntity = userRepository.findById(Integer.valueOf(id)).get();
        if (yskUserEntity.getPid() > 0) {
            YskUserEntity parent = userRepository.findById(Integer.valueOf(yskUserEntity.getPid())).get();
            userVo.setParentName(parent.getAccount());
        }
        BeanUtils.copyProperties(yskUserEntity, userVo);
        return userVo;
    }

    /**
     * 根际用户ID进行状态改变，上锁与解锁
     *
     * @param id 用户ID
     * @return 返回结果集
     */
    public Result lockUser(String id) {
        YskUserEntity yskUserEntity = userRepository.findById(Integer.valueOf(id)).get();
        int status = yskUserEntity.getStatus();
        switch (status) {
            case 0:
                yskUserEntity.setStatus(Byte.valueOf("1"));
                break;
            case 1:
                yskUserEntity.setStatus(Byte.valueOf("0"));
                break;
        }
        YskUserEntity user = userRepository.saveAndFlush(yskUserEntity);
        if (String.valueOf(user.getStatus()).equals(status)) return new Result(500, "操作失败");
        return new Result(200, "操作成功，页面即将自动跳转");
    }

    /**
     * 根际前台用户信息进行用户更新操作
     *
     * @param userVo 前台用户实体类
     * @return 结果集
     */
    public Result editUpdate(UserVo userVo) {
        YskUserEntity yskUserEntity = userRepository.findById(userVo.getUserid()).get();
        if (!"".equals(userVo.getUsername())) yskUserEntity.setUsername(userVo.getUsername());
        if (!"".equals(userVo.getMobile())) yskUserEntity.setMobile(userVo.getMobile());
        if((!"".equals(userVo.getLoginPwd()))&&null!=userVo.getLoginPwd()) {
            yskUserEntity.setLoginSalt(PswUtils.getSalt());//根际时间获取加盐值
            yskUserEntity.setLoginPwd(PswUtils.getCipher(userVo.getLoginPwd(),yskUserEntity.getLoginSalt()));//加盐值与明文进行加密，生成密码密文
        }
        if((!"".equals(userVo.getSafetyPwd()))&&null!=userVo.getSafetyPwd()) {
            yskUserEntity.setSafetySalt(PswUtils.getSalt());//根际时间获取加盐值
            yskUserEntity.setSafetyPwd(PswUtils.getCipher(userVo.getSafetyPwd(),yskUserEntity.getSafetySalt()));
        }
        userRepository.saveAndFlush(yskUserEntity);
        return new Result(200,"修改成功");
    }

    /**
     * 根据用户ID返回用户实体类
     *
     * @param id 用户ID
     * @return 前台用户实体类
     */
    public UserVo getById(String id) {
        YskUserEntity yskUserEntity = userRepository.findById(Integer.valueOf(id)).get();
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(yskUserEntity, userVo);
        return userVo;
    }

    /**
     * 更新用户代理
     *
     * @param userVo 前台传来用户实体类
     * @return 更新结果
     */
    public Result updateArea(UserVo userVo) {
        YskUserEntity one = userRepository.findById(userVo.getUserid()).get();
        one.setLevel(userVo.getLevel());
        one.setAreaType(userVo.getAreaType());
        one.setAreaProvince(userVo.getAreaProvince());
        one.setAreaCity(userVo.getAreaCity());
        one.setAreaDistrict(userVo.getAreaDistrict());
        userRepository.saveAndFlush(one);
        return new Result(200, "设置成功");
    }

    /**
     * 根据用户ID更新财富值
     *
     * @param userid 用户ID
     * @param type   计算类型 +或-
     * @param field  更新财富类型 积分，华宝，金额
     * @param num    更新数量
     * @return
     */
    @Transactional
    public Result updateFruits(String userid, String type, String field, String num) {
        try {
            if (Double.valueOf(num) < 0) {
                return new Result(500, "输入数据格式错误");
            }
            YskUserWealthEntity wealthEntity = wealthRepository.findById(Integer.valueOf(userid)).get();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
            if ("add".equals(type)) {
                switch (field) {
                    case "Money": {
                        YskMoneyDetailEntity moneyDetail = new YskMoneyDetailEntity();
                        YskMoneyRechargeEntity rechargeEntity = new YskMoneyRechargeEntity();
                        //money_recharge
                        rechargeEntity.setUid(Integer.valueOf(userid));
                        rechargeEntity.setCreateTime(Integer.valueOf(time));
                        rechargeEntity.setStatus((byte) 1);
                        rechargeEntity.setMoneyRecord(wealthEntity.getMoney().add(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        rechargeEntity.setMoney(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        rechargeEntity.setContent("后台充值" + BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        rechargeEntity.setFromType((byte) 1);
                        rechargeEntity.setType("admin");
                        rechargeEntity.setTypeName("后台充值");
                        //money_detail
                        moneyDetail.setContent("后台充值" + BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        moneyDetail.setFromType((byte) 1);
                        moneyDetail.setType("admin");
                        moneyDetail.setTypeName("后台充值");
                        moneyDetail.setUid(Integer.valueOf(userid));
                        moneyDetail.setCreateTime(Integer.valueOf(time));
                        moneyDetail.setStatus((byte) 1);
                        moneyDetail.setMoneyRecord(wealthEntity.getMoney().add(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        moneyDetail.setMoney(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        moneyDetail.setOrderNo("");

                        detailRepository.saveAndFlush(moneyDetail);
                        moneyRechargeRepository.saveAndFlush(rechargeEntity);
                        wealthEntity.setMoney(wealthEntity.getMoney().add(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        break;
                    }
                    case "Integral": {
                        YskIntegralDetailEntity integralDetail = new YskIntegralDetailEntity();
                        //integra_deatil
                        integralDetail.setContent("后台充值" + BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        integralDetail.setFromType((byte) 1);
                        integralDetail.setType("admin");
                        integralDetail.setTypeName("后台充值");
                        integralDetail.setUid(Integer.valueOf(userid));
                        integralDetail.setCreateTime(Integer.valueOf(time));
                        integralDetail.setStatus((byte) 1);
                        integralDetail.setMoneyRecord(wealthEntity.getIntegral().add(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        integralDetail.setMoney(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));

                        wealthEntity.setIntegral(wealthEntity.getIntegral().add(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        integralDetailRepository.saveAndFlush(integralDetail);
                        break;
                    }
                    case "Huabao":
                        wealthEntity.setHuabao(wealthEntity.getHuabao().add(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        break;

                }
            } else {
                switch (field) {
                    case "Money": {
                        YskMoneyRechargeEntity rechargeEntity = new YskMoneyRechargeEntity();
                        //money_recharge
                        rechargeEntity.setUid(Integer.valueOf(userid));
                        rechargeEntity.setCreateTime(Integer.valueOf(time));
                        rechargeEntity.setStatus((byte) 1);
                        rechargeEntity.setMoneyRecord(wealthEntity.getMoney().subtract(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        rechargeEntity.setMoney(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        rechargeEntity.setContent("后台扣减" + BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        rechargeEntity.setFromType((byte) 2);
                        rechargeEntity.setType("admin");
                        rechargeEntity.setTypeName("后台扣减");
                        //money_detail
                        YskMoneyDetailEntity moneyDetail = new YskMoneyDetailEntity();
                        moneyDetail.setContent("后台扣减" + BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        moneyDetail.setFromType((byte) 2);
                        moneyDetail.setType("admin");
                        moneyDetail.setTypeName("后台扣减");
                        moneyDetail.setUid(Integer.valueOf(userid));
                        moneyDetail.setCreateTime(Integer.valueOf(time));
                        moneyDetail.setStatus((byte) 1);
                        moneyDetail.setMoneyRecord(wealthEntity.getMoney().subtract(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        moneyDetail.setMoney(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        moneyDetail.setOrderNo("");

                        wealthEntity.setMoney(wealthEntity.getMoney().subtract(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        detailRepository.saveAndFlush(moneyDetail);
                        moneyRechargeRepository.saveAndFlush(rechargeEntity);

                        break;
                    }
                    case "Integral": {
                        YskIntegralDetailEntity integralDetail = new YskIntegralDetailEntity();
                        //integeral_detail
                        integralDetail.setContent("后台扣减" + BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        integralDetail.setFromType((byte) 2);
                        integralDetail.setType("admin");
                        integralDetail.setTypeName("后台扣减");
                        integralDetail.setUid(Integer.valueOf(userid));
                        integralDetail.setCreateTime(Integer.valueOf(time));
                        integralDetail.setStatus((byte) 1);
                        integralDetail.setMoneyRecord(wealthEntity.getIntegral().subtract(BigDecimal.valueOf(Double.valueOf(num))));
                        integralDetail.setMoney(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP));
                        integralDetailRepository.saveAndFlush(integralDetail);

                        wealthEntity.setIntegral(wealthEntity.getIntegral().subtract(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        break;
                    }
                    case "Huabao":
                        wealthEntity.setHuabao(wealthEntity.getHuabao().subtract(BigDecimal.valueOf(Double.valueOf(num)).setScale(2, RoundingMode.HALF_UP)));
                        break;
                }

            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sf.format(new Date());
            Timestamp timestamp = DateUtils.getTimestamp(format, sf);
            wealthEntity.setUptimeing(timestamp);
            wealthRepository.saveAndFlush(wealthEntity);
            return new Result(200, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(500, "服务器异常");
        }
    }

    /**
     * 根际条件动态查询数据，将分页后结果返回充值记录页面
     *
     * @param result 分页信息的实体类
     * @return result 分页信息的实体类
     */
    public TableResult RechargeIndex(TableResult result) {
        PageRequest pageable = PageRequest.of(result.getPageNumber(), result.getPageSize());
        List<YskMoneyRechargeEntity> all = moneyRechargeRepository.findAll(new Specification<YskMoneyRechargeEntity>() {
            @Override
            public Predicate toPredicate(Root<YskMoneyRechargeEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();//条件集合
                query.orderBy(criteriaBuilder.desc(root.get("id")));
                if(null!=result.getRecharge()&&!("".equals(result.getRecharge()))) {
                    predicates.add(criteriaBuilder.equal(root.get("type"),result.getRecharge()));
                }
                if(null!=result.getRechargeType()&&!("".equals(result.getRechargeType()))) {
                    predicates.add(criteriaBuilder.equal(root.get("paytype"),result.getRechargeType()));
                }
                if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
                    String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.greaterThan(root.get("createTime"), Long.valueOf(date)));//时间大于
                }
                if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
                    String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.lessThan(root.get("createTime"), Long.valueOf(date)+ 60 * 60 * 24));//时间小于
                }
                if (null != result.getTypeText() && !("".equals(result.getTypeText()))) {//按类型条件查询
                    if ("username".equals(result.getType())) {//按姓名模糊查
                        predicates.add(criteriaBuilder.like(root.get("username"), '%' + result.getTypeText() + '%'));
                    }
                    else if ("mobile".equals(result.getType())) {//按手机模糊查
                        predicates.add(criteriaBuilder.like(root.get("mobile"), result.getTypeText() + '%'));
                    }
                }
                predicates.add(criteriaBuilder.equal(root.get("status"),1));//状态为已通过审核
//                root.join("userEntity",JoinType.LEFT);//左联User表，必须有对应关系（一对一，一对多）否则报错
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
        int begin=result.getPageNumber()*10;//分页起始位置
        int end=begin+10;//分页结束位置
        if(end>all.size()) end=all.size();
        List<YskMoneyRechargeEntity> subList = all.subList(begin, end);//分页List
        TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result,tableResult);//查询信息复制
        if(all.size()%result.getPageSize()==0) {
            tableResult.setPageCount(Long.valueOf(all.size()/result.getPageSize()));
        }
        else {
            tableResult.setPageCount(Long.valueOf(all.size()/result.getPageSize()+1));
        }
        tableResult.setTotal(Long.valueOf(all.size()));//设置总记录数
        tableResult.setRows(subList);//设置当前页集合
        BigDecimal pagesum=new BigDecimal(0);
        BigDecimal sum=new BigDecimal(0);
        for (YskMoneyRechargeEntity yskMoneyRechargeEntity : subList) {
            pagesum=pagesum.add(yskMoneyRechargeEntity.getMoney());
        }
        for (YskMoneyRechargeEntity yskMoneyRechargeEntity : all) {
            sum=sum.add(yskMoneyRechargeEntity.getMoney());
        }
        tableResult.setPageSum(pagesum);//当前页金额
        tableResult.setSum(sum);//全页金额
        return tableResult;
    }
    /**
     *
     * @param result 分页信息的实体类
     * @param status 审核状态 null待审核 2通过审核 3拒绝审核
     * @return 分页信息的实体类
     */
    public TableResult shopStatusIndex(TableResult result,String status) {
        StringBuilder sql = new StringBuilder();
        sql.append("select u.userid,u.userType,u.username,u.mobile,c.province,c.city,c.district,c.createTime,c.isCheckCompany,c.isCheckUser from YskUserEntity u left join u.checkinfoEntity c ");
        if (null != result.getLevel()) {//等级查询
            sql.append("where u.userType="+result.getLevel());
        }
        if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
            String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
            if(managerUtils.checkWhere(sql.toString())) {
                sql.append(" and c.createTime>"+date);
            }
            else {
                sql.append("where c.createTime>"+date);
            }
        }
        if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
            String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
            if(managerUtils.checkWhere(sql.toString())) {
                sql.append(" and c.createTime<"+date);
            }
            else {
                sql.append("where c.createTime<"+date);
            }
        }
        if (null != result.getTypeText() && !("".equals(result.getTypeText()))) {//按类型条件查询
            if ("username".equals(result.getType())) {//按用户名模糊查
                if(managerUtils.checkWhere(sql.toString())) {
                    sql.append(" and u.username like '"+"%"+result.getTypeText()+"%'");
                }
                else {
                    sql.append("where u.username like '"+"%"+result.getTypeText()+"%'");
                }
            }
            if ("mobile".equals(result.getType())) {//按手机模糊查
                if(managerUtils.checkWhere(sql.toString())) {
                    sql.append(" and u.mobile like '"+"%"+result.getTypeText()+"%'");
                }
                else {
                    sql.append("where u.mobile like '"+"%"+result.getTypeText()+"%'");
                }
            }
        }
        List<Object[]> usersList = managerUtils.indexTable(sql);
        List<YskUserEntity> usersPage=new ArrayList<>();
        for (Object[] objects : usersList) {
            YskUserEntity userEntity = new YskUserEntity();
            userEntity.setUserid((int)objects[0]);
            userEntity.setUserType((byte)objects[1]);
            userEntity.setUsername(String.valueOf(objects[2]));
            userEntity.setMobile(String.valueOf(objects[3]));
            YskUserCheckinfoEntity checkinfoEntity = new YskUserCheckinfoEntity();
            checkinfoEntity.setProvince(String.valueOf(objects[4]));
            checkinfoEntity.setCity(String.valueOf(objects[5]));
            checkinfoEntity.setDistrict(String.valueOf(objects[6]));
            checkinfoEntity.setCreateTime((int)objects[7]);
            checkinfoEntity.setIsCheckCompany((byte)objects[8]);
            checkinfoEntity.setIsCheckUser((byte)objects[9]);
            userEntity.setCheckinfoEntity(checkinfoEntity);
            usersPage.add(userEntity);
        }
        Collections.reverse(usersPage);
        List<YskUserEntity> checkPending=new ArrayList<>();//待审核List
        List<YskUserEntity> auditPass=new ArrayList<>();//通过审核List
        List<YskUserEntity> refuse=new ArrayList<>();//拒绝审核List
        for (YskUserEntity userEntity : usersPage) {
            if(userEntity.getUserType()==0) {//个人审核状态
                if(userEntity.getCheckinfoEntity().getIsCheckUser()==1) {
                    checkPending.add(userEntity);//添加待审核
                }
                else if(userEntity.getCheckinfoEntity().getIsCheckUser()==2) {
                    auditPass.add(userEntity);//添加通过审核
                }
                else if(userEntity.getCheckinfoEntity().getIsCheckUser()==3) {
                    refuse.add(userEntity);//添加已拒绝
                }
            }
            else {//企业审核状态
                if(userEntity.getCheckinfoEntity().getIsCheckCompany()==1) {
                    checkPending.add(userEntity);//添加待审核
                }
                else if(userEntity.getCheckinfoEntity().getIsCheckCompany()==2) {
                    auditPass.add(userEntity);//添加通过审核
                }
                else if(userEntity.getCheckinfoEntity().getIsCheckCompany()==3) {
                    refuse.add(userEntity);//添加已拒绝
                }
            }
        }
        TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        if(null==status) {//待审核集合数
            int length = checkPending.size();
            int pageSize=tableResult.getPageSize();
            tableResult.setTotal(Long.valueOf(length));
            int begin=tableResult.getPageNumber()*10;
            int end=begin+10;
            if(end>length) {
                end=length;
            }
            tableResult.setRows(checkPending.subList(begin,end));
            if(length%pageSize==0) tableResult.setPageCount(Long.valueOf(length/pageSize));
            else tableResult.setPageCount(Long.valueOf(length/pageSize+1));
        }
        else if(2==Integer.valueOf(status)) {//通过审核集合数
            int length = auditPass.size();
            int pageSize=tableResult.getPageSize();
            tableResult.setTotal(Long.valueOf(length));
            int begin=tableResult.getPageNumber()*10;
            int end=begin+10;
            if(end>length) {
                end=length;
            }
            tableResult.setRows(auditPass.subList(begin,end));
            if(length%pageSize==0) tableResult.setPageCount(Long.valueOf(length/pageSize));
            else tableResult.setPageCount(Long.valueOf(length/pageSize+1));
        }
        else if (3==Integer.valueOf(status)){
            int length = refuse.size();
            int pageSize=tableResult.getPageSize();
            tableResult.setTotal(Long.valueOf(length));
            int begin=tableResult.getPageNumber()*10;
            int end=begin+10;
            if(end>length) {
                end=length;
            }
            tableResult.setRows(refuse.subList(begin,end));
            if(length%pageSize==0) tableResult.setPageCount(Long.valueOf(length/pageSize));
            else tableResult.setPageCount(Long.valueOf(length/pageSize+1));
        }
        return tableResult;
    }

    public UserVo StatusDetail(String id) {
        YskUserEntity userEntity = userRepository.findById(Integer.valueOf(id)).get();
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(userEntity,userVo);
        return userVo;
    }
    @Transactional
    public Result updateStatus(Integer userid,String content,Byte agree) {
        Result result=new Result();
        YskUserCheckinfoEntity checkinfoEntity = checkInfoRepository.findById(userid).get();
        if(checkinfoEntity.getUserType()==0) checkinfoEntity.setIsCheckUser(agree);
        if(checkinfoEntity.getUserType()==1) checkinfoEntity.setIsCheckCompany(agree);
        YskMessageEntity message = new YskMessageEntity();
        Date date = new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String time = DateUtils.getTime(format.format(date), format);
        message.setCreateTime(Integer.valueOf(time));
        message.setStatus((byte) 0);
        message.setSend(1);
        message.setUid(userid);
        message.setContent(content);
        message.setType(1);
        message.setTitle("用户认证审核");
        try {
            checkInfoRepository.saveAndFlush(checkinfoEntity);
            messageRepository.saveAndFlush(message);
            result.setStatus(200);
            result.setMessage("审核完成");
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setStatus(500);
            result.setMessage("审核失败");
            return result;
        }
    }

    public TableResult orderList(TableResult result, String status) {
        if(status==null) status="0";
        StringBuilder sql=new StringBuilder();
        sql.append("select ui,u.mobile,u.userid,u.username from YskUpdateUserinfoEntity ui left join YskUserEntity u on ui.uid=u.userid where ui.status="+status);
        if (null != result.getLevel()) {//等级查询
            if(managerUtils.checkWhere(sql.toString())) {
                sql.append(" and ui.updateType="+result.getLevel());

            }
            else {
                sql.append("where ui.updateType="+result.getLevel());
            }
        }
        if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
            String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
            if(managerUtils.checkWhere(sql.toString())) {
                sql.append(" and ui.createTime>"+date);
            }
            else {
                sql.append("where c.createTime>"+date);
            }
        }
        if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
            String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
            if(managerUtils.checkWhere(sql.toString())) {
                sql.append(" and ui.createTime<"+date);
            }
            else {
                sql.append("where ui.createTime<"+date);
            }
        }
        if (null != result.getTypeText() && !("".equals(result.getTypeText()))) {//按类型条件查询
            if ("username".equals(result.getType())) {//按用户名模糊查
                if(managerUtils.checkWhere(sql.toString())) {
                    sql.append(" and u.username like '"+"%"+result.getTypeText()+"%'");
                }
                else {
                    sql.append("where u.username like '"+"%"+result.getTypeText()+"%'");
                }
            }
            if ("mobile".equals(result.getType())) {//按手机模糊查
                if(managerUtils.checkWhere(sql.toString())) {
                    sql.append(" and u.mobile like '"+"%"+result.getTypeText()+"%'");
                }
                else {
                    sql.append("where u.monile like '"+"%"+result.getTypeText()+"%'");
                }
            }
        }
        List<Object[]> usersList = managerUtils.indexTable(sql);
        List<YskUpdateUserinfoEntity> infoList=new ArrayList<>();
        for (Object[] objects : usersList) {
            YskUpdateUserinfoEntity yskUpdateUserinfoEntity = new YskUpdateUserinfoEntity();
            yskUpdateUserinfoEntity=(YskUpdateUserinfoEntity) objects[0];
            YskUserEntity userEntity = new YskUserEntity();
            userEntity.setMobile((String) objects[1]);
            userEntity.setUserid((Integer) objects[2]);
            userEntity.setUsername((String) objects[3]);
            yskUpdateUserinfoEntity.setUser(userEntity);
            infoList.add(yskUpdateUserinfoEntity);
        }
        Collections.reverse(infoList);
        TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        int length = infoList.size();
        int pageSize=tableResult.getPageSize();
        tableResult.setTotal(Long.valueOf(length));
        int begin=tableResult.getPageNumber()*10;
        int end=begin+10;
        if(end>length) {
            end=length;
        }
        tableResult.setRows(infoList.subList(begin,end));
        if(length%pageSize==0) tableResult.setPageCount(Long.valueOf(length/pageSize));
        else tableResult.setPageCount(Long.valueOf(length/pageSize+1));
        return tableResult;
    }
    public WorkOrderVo orderDetail(Integer id) {
        Object[] objects = updateUserInfoRepository.selectDetail(id);
        Object object[] = (Object[]) objects[0];
        WorkOrderVo orderVo = new WorkOrderVo();
        YskUpdateUserinfoEntity userinfoEntity = new YskUpdateUserinfoEntity();
        userinfoEntity= (YskUpdateUserinfoEntity) object[0];
        BeanUtils.copyProperties(userinfoEntity,orderVo);
        orderVo.setUserType((Byte) object[1]);
        orderVo.setUsername((String) object[2]);
        orderVo.setMobile((String) object[3]);
        return orderVo;
    }
    @Transactional
    public Result updateWork(Integer id, Integer agree, String reply,Integer uid) {
        Result result = new Result();
        YskUpdateUserinfoEntity info = updateUserInfoRepository.findById(id).get();//工单审核信息
        YskUserEntity userEntity = repository.findById(uid).get();//用户信息
        try {
            if(agree==1) {//工单通过
                if(info.getUpdateType()==1) {//修改手机
                    Long count = repository.countByMobileEquals(info.getNewInfo());
                    if(count>0) {
                        result.setStatus(500);
                        result.setMessage("手机号已被注册");
                        return result;
                    }
                    userEntity.setMobile(info.getNewInfo());//设置新手机号
                    repository.saveAndFlush(userEntity);
                }
                else if(info.getUpdateType()==2) {//修改姓名
                    userEntity.setUsername(info.getNewInfo());
                    repository.saveAndFlush(userEntity);
                }
                else if(info.getUpdateType()==3) {//修改企业名称
                    userEntity.setUsername(info.getNewInfo());
                    YskUserCheckinfoEntity check = checkInfoRepository.findById(uid).get();
                    check.setCompanyName(info.getNewInfo());
                    checkInfoRepository.saveAndFlush(check);
                    repository.saveAndFlush(userEntity);
                }
                else if(info.getUpdateType()==4) {//修改发放积分额度
                    userEntity.setMaxDistributionIntegral(info.getMaxDistributionIntegral());
                    userEntity.setCountDistributionIntegral(info.getCountDistributionIntegral());
                    repository.saveAndFlush(userEntity);
                }
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = DateUtils.getTime(format.format(date), format);
            info.setStatus(agree);
            info.setReply(reply);
            info.setUpdateTime(Integer.valueOf(time));
            info.setAdminId(uid);
            updateUserInfoRepository.saveAndFlush(info);
            result.setMessage("工单审核成功");
            result.setStatus(200);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.setStatus(500);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("系统错误，审核失败");
        }
        return result;
    }

    /**
     * 推荐结构-----树tree
     * @return
     */
    public StringBuffer treeList() {
    	StringBuffer pidsb = treeByPid(0,0);
    	//全部树
    	StringBuffer allsb = new StringBuffer();
    	allsb.append("<ul>");
    	allsb.append(pidsb);
    	allsb.append("</ul>");
		return allsb;
    }
    /**
     * 推荐结构-----树tree   keyword
     * @return
     */
    public StringBuffer treeListkey(String keyword) {
    	StringBuffer allsb = new StringBuffer();
    	YskUserEntity entity = null;
    	boolean result=keyword.matches("[0-9]+");
    	if(result==true) {
    		if(keyword.length()<11) {
    			entity = userRepository.findByUserid(Integer.valueOf(keyword));
    			if(entity==null) {
    				List<YskUserEntity> aclist = userRepository.findByAccount(keyword);
    				if(aclist!=null&&(!aclist.isEmpty())) {
    					entity = userRepository.findByAccount(keyword).get(0);
    				}
        		}
    		}else {
    			List<YskUserEntity> molist = userRepository.findByMobile(keyword);
    			if(molist!=null&&(!molist.isEmpty())) {
    				entity = userRepository.findByMobile(keyword).get(0);
    			}
    			if(entity==null) {
    				List<YskUserEntity> acList = userRepository.findByAccount(keyword);
    				if(acList!=null&&(!acList.isEmpty())) {
    					entity = userRepository.findByAccount(keyword).get(0);
    				}
        		}
			}
    	}else {
    		List<YskUserEntity> acLista = userRepository.findByAccount(keyword);
			if(acLista!=null&&(!acLista.isEmpty())) {
				entity = userRepository.findByAccount(keyword).get(0);
			}
		}
    	if(entity==null) {
    		allsb.append("");
    	}else {
    		allsb.append("<ul>");
    		allsb.append("<li style=\"display:none\"><span><i class=\"icon-plus-sign fa-plus blue \">");
    		allsb.append("</i>"+entity.getUsername()+"</span>");
    		allsb.append("<a href=\"/Admin/User/edit/id/"+entity.getUserid()+"\">"+entity.getAccount()+"</a>");
    		StringBuffer treeByPid = treeByPid(entity.getUserid(), 1);
    		if(treeByPid.length()!=0) {
    			allsb.append("<ul>");
    			allsb.append(treeByPid);
    			allsb.append("</ul>");
    		}
    		allsb.append("</li>");
        	allsb.append("</ul>");
		}
		return allsb;
    }
    /**
     * 推荐结构-----树tree-----通过pid查
     * @param pid
     * @return
     */
    public StringBuffer treeByPid(int pid,int a) {
    	a++;
    	StringBuffer sb = new StringBuffer();//当前一级
    	StringBuffer sb2 = new StringBuffer();//下一级
    	List<YskUserEntity> list1 = userRepository.findByPid(pid);
    	for(int i = 0;i<list1.size();i++) {
    		if(a>6) {
    			break;
    		}
    		sb.append("<li style=\"display:none\"><span><i class=\"icon-plus-sign fa-plus blue \">");
    		sb.append("</i>"+list1.get(i).getUsername()+"</span>");
    		sb.append("<a href=\"/Admin/User/edit/id/"+list1.get(i).getUserid()+"\">"+list1.get(i).getAccount()+"</a>");
    		sb2 = treeByPid(list1.get(i).getUserid(),a);
    		if(sb2.length()!=0) {
        		sb.append("<ul>");
        		sb.append(sb2);
        		sb.append("</ul>");
        	}
    		sb.append("</li>");
    	}
		return sb;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
	 * 查询用户积分明细表
	 * @param tableResult
	 * @return
	 */
	public TableResult integraldetail(Integer id,TableResult result) {
	    //按订单创建时间降序
        Sort sort = Sort.by(new Sort.Order(Direction.DESC, "createTime"));
        PageRequest pageRequest = PageRequest.of(result.getPageNumber(), result.getPageSize(),sort);
        Page<YskIntegralDetailEntity> all = integralDetailRepository.findAll(new Specification<YskIntegralDetailEntity>() {
			private static final long serialVersionUID = -4613601662825505994L;

			@Override
            public Predicate toPredicate(Root<YskIntegralDetailEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
               List<Predicate> predicates=new ArrayList<>();
               predicates.add(criteriaBuilder.equal(root.get("uid"),id));
                if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
                    String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.greaterThan(root.get("createTime"), Long.valueOf(date)));
                }
                if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
                    String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
                    predicates.add(criteriaBuilder.lessThan(root.get("createTime"), Long.valueOf(date) + 60 * 60 * 24));//将时间加到查询时间的后一天0点
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageRequest);
        TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result,tableResult);
        tableResult.setRows(all.getContent());
        tableResult.setTotal(all.getTotalElements());
        tableResult.setPageCount((long) all.getTotalPages());
        return tableResult;
    }
	
	 /**
		 * 查询用户余额明细表
		 * @param tableResult
		 * @return
		 */
		public TableResult moneydetail(Integer id,TableResult result) {
		    //按订单创建时间降序
	        Sort sort = Sort.by(new Sort.Order(Direction.DESC, "createTime"));
	        PageRequest pageRequest = PageRequest.of(result.getPageNumber(), result.getPageSize(),sort);
	        Page<YskMoneyDetailEntity> all = detailRepository.findAll(new Specification<YskMoneyDetailEntity>() {
				private static final long serialVersionUID = 7934985324496081271L;

				@Override
	            public Predicate toPredicate(Root<YskMoneyDetailEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	               List<Predicate> predicates=new ArrayList<>();
	               predicates.add(criteriaBuilder.equal(root.get("uid"),id));
	                if (null != result.getBeginDate() && !("".equals(result.getBeginDate()))) {//开始时间查询
	                    String date = DateUtils.getTime(result.getBeginDate(), new SimpleDateFormat("yyyy-MM-dd"));
	                    predicates.add(criteriaBuilder.greaterThan(root.get("createTime"), Long.valueOf(date)));
	                }
	                if (null != result.getEndDate() && !("".equals(result.getEndDate()))) {//结束时间查询
	                    String date = DateUtils.getTime(result.getEndDate(), new SimpleDateFormat("yyyy-MM-dd"));
	                    predicates.add(criteriaBuilder.lessThan(root.get("createTime"), Long.valueOf(date) + 60 * 60 * 24));//将时间加到查询时间的后一天0点
	                }
	                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	            }
	        }, pageRequest);
	        TableResult tableResult = new TableResult();
	        BeanUtils.copyProperties(result,tableResult);
	        tableResult.setRows(all.getContent());
	        tableResult.setTotal(all.getTotalElements());
	        tableResult.setPageCount((long) all.getTotalPages());
	        return tableResult;
	    }
}
