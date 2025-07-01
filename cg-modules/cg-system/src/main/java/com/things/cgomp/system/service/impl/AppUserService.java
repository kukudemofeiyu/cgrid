package com.things.cgomp.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.constants.AppUserRedisConstants;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.common.core.enums.EnableEnum;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.utils.uuid.IdUtils;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.common.record.enums.*;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.AppUserTrendDateData;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.domain.SysUserAccount;
import com.things.cgomp.system.api.dto.AppRechargeDTO;
import com.things.cgomp.system.domain.dto.AppUserRechargeReq;
import com.things.cgomp.system.domain.dto.AppUserRefundReq;
import com.things.cgomp.system.domain.dto.RegisterTransferDTO;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.AppUserMapper;
import com.things.cgomp.system.service.IAppRechargeOrderService;
import com.things.cgomp.system.service.IAppUserService;
import com.things.cgomp.system.service.ISysAmountRecordService;
import com.things.cgomp.system.service.ISysUserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;
import static com.things.cgomp.common.core.utils.StatisticsUtils.buildQueryParam;

/**
 * 注册用户实现类
 */
@Slf4j
@Service
public class AppUserService extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

    @Resource
    private ISysUserAccountService userAccountService;
    @Resource
    private ISysAmountRecordService amountRecordService;
    @Resource
    private IAppRechargeOrderService rechargeOrderService;
    @Resource
    private RedisService redisService;

    @Override
    public PageInfo<AppUser> selectPage(AppUser appUser) {
        startPage();
        List<AppUser> userList = baseMapper.selectUserList(appUser);
        return new PageInfo<>(userList);
    }

    @Override
    public void updateFirstChargeStatus(Long userId) {
        if(userId == null){
            return;
        }

        Integer firstChargeStatus = selectFirstChargeStatus(userId);
        if (EnableEnum.DISABLE.getCode().equals(firstChargeStatus)) {
            return;
        }

        AppUser appUser = new AppUser()
                .setUserId(userId)
                .setFirstCharge(EnableEnum.DISABLE.getCode());
        baseMapper.updateById(appUser);

        deleteFirstChargeStatusFromCache(userId);
    }

    private void deleteFirstChargeStatusFromCache(Long userId) {
        String firstChargeKey = getFirstChargeKey(userId);
        redisService.deleteObject(firstChargeKey);
    }

    private String getFirstChargeKey(Long userId) {
        return AppUserRedisConstants.FIRST_CHARGE + userId;
    }

    @Override
    public Integer selectFirstChargeStatus(Long userId) {
        Integer firstChargeStatus = selectFirstChargeStatusFromCache(userId);
        if(firstChargeStatus != null){
            return firstChargeStatus;
        }

        firstChargeStatus = selectFirstChargeStatusFromDb(userId);
        if (firstChargeStatus != null) {
            saveFirstChargeStatusToCache(
                    userId,
                    firstChargeStatus
            );
        }

        return firstChargeStatus;
    }

    private void saveFirstChargeStatusToCache(
            Long userId,
            Integer firstChargeStatus
    ) {
        String firstChargeKey = getFirstChargeKey(userId);
        redisService.setCacheObject(firstChargeKey, firstChargeStatus);
    }

    private Integer selectFirstChargeStatusFromCache(Long userId) {
        String firstChargeKey = getFirstChargeKey(userId);
        return redisService.getCacheObject(firstChargeKey);
    }

    private Integer selectFirstChargeStatusFromDb(Long userId) {
        AppUser appUser = baseMapper.selectById(userId);
        if(appUser == null){
            return null;
        }

        return appUser.getFirstCharge();
    }

    @Override
    public List<AppUser> selectList(AppUser appUser) {
        LambdaQueryWrapperX<AppUser> wrapper = new LambdaQueryWrapperX<AppUser>()
                .eqIfPresent(AppUser::getStatus, appUser.getStatus())
                .likeIfPresent(AppUser::getMobile, appUser.getMobile())
                .likeIfPresent(AppUser::getNickName, appUser.getNickName());
        return baseMapper.selectList(wrapper);
    }

    @Override
    public AppUser selectUserByMobile(String mobile) {
        LambdaQueryWrapperX<AppUser> wrapper = new LambdaQueryWrapperX<AppUser>()
                .eq(AppUser::getMobile, mobile);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferFromCard(RegisterTransferDTO req) {
        // TODO、1.验证卡和运营商

        // TODO、2.验证金额

        // TODO、3.IC卡扣除余额

        // TODO、账户余额表添加金额

        // TODO、添加余额明细

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int systemRecharge(AppUserRechargeReq req) {
        try {
            //添加充值订单
            String orderNo = rechargeOrderService.addRechargeOrder(req.getUserId(), req.getAmount(), req.getAmount(),BigDecimal.ZERO);
            // 添加账户余额
            SysUserAccount account = userAccountService.addUserBalance(req.getUserId(), UserAccountType.APP.getCode(), req.getAmount());
            // 添加账户明细
            SysAmountRecord rechargeRecord = buildRechargeRecord(orderNo,account, req);
            return amountRecordService.insertAmountRecord(rechargeRecord);
        } catch (Exception e) {
            log.error("systemRecharge 后台充值失败, userId={}, amount={}", req.getUserId(), req.getAmount(), e);
            throw exception(ErrorCodeConstants.APP_USER_SYSTEM_RECHARGE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int refund(AppUserRefundReq req) {
        try {
            SysAmountRecord record = amountRecordService.getById(req.getId());
            if (record == null) {
                throw exception(ErrorCodeConstants.RECORD_AMOUNT_IS_NULL);
            }
            if (!AmountRecordType.isRefundType(record.getType())) {
                // 只允许充值和系统充值类型退款
                throw exception(ErrorCodeConstants.APP_USER_REFUND_TYPE_NOT_SUPPORT);
            }
            BigDecimal refundAvailable = record.getAmount().subtract(record.getRefundAmount());
            if (req.getAmount().compareTo(refundAvailable) > 0) {
                // 退款金额不允许大于可退款金额
                throw exception(ErrorCodeConstants.APP_USER_REFUND_AMOUNT_NOT_ALLOW, stripZeros(refundAvailable));
            }
            // 更新账户余额
            SysUserAccount account = userAccountService.reduceUserBalance(record.getBindUserId(), record.getUserType(), req.getAmount());
            // 修改原记录
            record.setRefundAmount(record.getRefundAmount().add(req.getAmount()));
            record.setUpdateTime(account.getUpdateTime());
            String remark = StringUtils.isEmpty(record.getRemark())
                    ? StrUtil.format("主动退款[{}]元", stripZeros(req.getAmount()))
                    : StrUtil.format("{} => 主动退款[{}]元", record.getRemark(), stripZeros(req.getAmount()));
            record.setRemark(remark);
            // 更新原记录
            amountRecordService.updateById(record);
            // 新增退款记录
            SysAmountRecord refundRecord = buildRefundRecord(record, account, req);
            return amountRecordService.insertAmountRecord(refundRecord);
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                throw e;
            }
            log.error("refund 退款失败, recordId={}, amount={}", req.getId(), req.getAmount(), e);
            throw exception(ErrorCodeConstants.APP_USER_REFUND_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer appRecharge(AppRechargeDTO req) {
        try {
            // 添加账户余额
            SysUserAccount account = userAccountService.addUserBalance(req.getUserId(), UserAccountType.APP.getCode(), req.getAmount());
            // 添加账户明细
            SysAmountRecord rechargeRecord = buildAppRechargeRecord(account, req);
            return amountRecordService.insertAmountRecord(rechargeRecord);
        } catch (Exception e) {
            log.error("systemRecharge 前台充值失败, userId={}, amount={}", req.getUserId(), req.getAmount(), e);
            throw exception(ErrorCodeConstants.APP_RECHARGE_FAIL);
        }
    }

    @Override
    public Map<Long, AppUser> selectUserMap(
            List<Long> userIds
    ) {
        if(CollectionUtils.isEmpty(userIds)){
            return new HashMap<>();
        }
        List<AppUser> appUsers = baseMapper.selectBatchIds(userIds);
        return appUsers.stream()
                .collect(Collectors.toMap(
                        AppUser::getUserId,
                        a->a,
                        (a,b)->a
                ));
    }

    @Override
    public List<AppUserTrendDateData> selectTrendDateData(String beginDate, String endDate) {
        TrendQueryDTO queryDTO = buildQueryParam(beginDate, endDate);
        return baseMapper.selectTrendDateData(queryDTO);
    }

    private SysAmountRecord buildRechargeRecord(String orderNo,SysUserAccount account, AppUserRechargeReq req) {
        return SysAmountRecord.builder()
                .serialNumber(orderNo)
                .operateUserId(SecurityUtils.getUserId())
                .bindUserId(req.getUserId())
                .amount(req.getAmount())
                .changeBefore(account.getBalance())
                .changeAfter(account.getNewBalance())
                .channel(RecordChannel.ACCOUNT_BALANCE.getChannel())
                .module(RecordModule.APP_USER.getModule())
                .type(AmountRecordType.SYSTEM_RECHARGE.getType())
                .recordType(IncomeExpenseType.INCOME.getType())
                .status(RecordStatus.SUCCESS.getStatus())
                .remark(StrUtil.format("系统充值[{}]元", stripZeros(req.getAmount())))
                .eventTime(account.getUpdateTime())
                .updateTime(account.getUpdateTime())
                .build();
    }
    private SysAmountRecord buildAppRechargeRecord(SysUserAccount account, AppRechargeDTO req) {
        return SysAmountRecord.builder()
                .serialNumber(req.getOrderNo())
                .operateUserId(SecurityUtils.getUserId())
                .bindUserId(req.getUserId())
                .amount(req.getAmount())
                .changeBefore(account.getBalance())
                .changeAfter(account.getNewBalance())
                .channel(RecordChannel.ACCOUNT_BALANCE.getChannel())
                .module(RecordModule.APP_USER.getModule())
                .type(AmountRecordType.USER_RECHARGE.getType())
                .recordType(IncomeExpenseType.INCOME.getType())
                .status(RecordStatus.SUCCESS.getStatus())
                .remark(StrUtil.format("APP充值[{}]元", stripZeros(req.getAmount())))
                .eventTime(account.getUpdateTime())
                .updateTime(account.getUpdateTime())
                .build();
    }


    private SysAmountRecord buildRefundRecord(SysAmountRecord existRecord, SysUserAccount account, AppUserRefundReq req) {
        return SysAmountRecord.builder()
                .serialNumber("_" + IdUtils.fastSimpleUUID())
                .operateUserId(SecurityUtils.getUserId())
                .bindUserId(existRecord.getBindUserId())
                .amount(req.getAmount())
                .changeBefore(account.getBalance())
                .changeAfter(account.getNewBalance())
                .channel(RecordChannel.ACCOUNT_BALANCE.getChannel())
                .module(RecordModule.APP_USER.getModule())
                .type(AmountRecordType.REFUND.getType())
                .recordType(IncomeExpenseType.EXPENSE.getType())
                .status(RecordStatus.SUCCESS.getStatus())
                .remark(StrUtil.format("主动退款[{}]元", stripZeros(req.getAmount())))
                .eventTime(account.getUpdateTime())
                .updateTime(account.getUpdateTime())
                .build();
    }

    private String stripZeros(BigDecimal value){
        return value.stripTrailingZeros().toPlainString();
    }
}
