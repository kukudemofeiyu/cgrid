package com.things.cgomp.system.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.RemoteOperatorService;
import com.things.cgomp.system.api.domain.SysOperator;
import com.things.cgomp.system.api.dto.SysOperatorAccountUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 运营商服务降级处理
 * 
 * @author things
 */
@Slf4j
@Component
public class RemoteOperatorFallbackFactory implements FallbackFactory<RemoteOperatorService>
{

    @Override
    public RemoteOperatorService create(Throwable throwable)
    {
        log.error("运营商服务调用失败:{}", throwable.getMessage());
        return new RemoteOperatorService() {

            @Override
            public R<SysOperator> getOperatorInfoByUserId(Long userId, String source) {
                return R.fail("获取运营商失败:" + throwable.getMessage());
            }

            @Override
            public R<SysOperator> getOperatorInfoById(Long operatorId, String source) {
                return R.fail("获取运营商失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> updateOperatorAccount(SysOperatorAccountUpdateDTO updateDTO, String source) {
                return R.fail("修改运营商账户失败:" + throwable.getMessage());
            }
        };
    }
}
