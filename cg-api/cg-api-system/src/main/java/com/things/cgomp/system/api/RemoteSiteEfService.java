package com.things.cgomp.system.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.domain.SysSiteEf;
import com.things.cgomp.system.api.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 站点关联服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteSiteEfService",
        value = ServiceNameConstants.SYSTEM_SERVICE,
        //url = "http://localhost:9012",
        fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteSiteEfService {

    /**
     * 根据用户ID查询所有站点ID
     *
     * @param userId 用户ID
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/siteEf/getSiteIdsByUserId")
    R<List<Long>> getAllSiteIdsByUserId(@RequestParam("userId") Long userId,
                                        @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 保存用户、组织站点关联
     *
     * @param sysSiteEf 关联对象
     * @return Integer
     */
    @PostMapping("/siteEf")
    R<Integer> addSiteEf(@RequestBody SysSiteEf sysSiteEf,
                         @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 修改用户、组织站点关联
     *
     * @param sysSiteEf 关联对象
     * @return Integer
     */
    @PutMapping("/siteEf")
    R<Integer> updateSiteEf(@RequestBody SysSiteEf sysSiteEf,
                            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     *
     * @param siteId 站点ID
     * @return Integer
     */
    @DeleteMapping("/siteEf/deleteBySiteId/{siteId}")
    R<Integer> deleteSiteEf(@PathVariable("siteId") Long siteId,
                            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
