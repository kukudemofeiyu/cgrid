package com.things.cgomp.system.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.domain.SysOperator;
import com.things.cgomp.system.api.dto.SysOperatorAccountUpdateDTO;
import com.things.cgomp.system.api.factory.RemoteOperatorFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 运营商服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteOperatorService",
        value = ServiceNameConstants.SYSTEM_SERVICE,
        //url = "http://localhost:9012",
        fallbackFactory = RemoteOperatorFallbackFactory.class)
public interface RemoteOperatorService {

    /**
     * 通过用户ID查询运营商信息
     *
     * @param userId 用户ID
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/operator/getByUser/{userId}")
    R<SysOperator> getOperatorInfoByUserId(@PathVariable("userId") Long userId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据运营商ID查询运营商信息
     *
     * @param operatorId 运营商ID
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/operator/getById/{operatorId}")
    R<SysOperator> getOperatorInfoById(@PathVariable("operatorId") Long operatorId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 修改运营商账户
     * @param updateDTO  修改对象
     * @param source     请求来源
     * @return 结果
     */
    @PutMapping("/operator/updateAccount")
    R<Boolean> updateOperatorAccount(@RequestBody SysOperatorAccountUpdateDTO updateDTO, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
