package com.things.cgomp.common.security.service;

import com.things.cgomp.common.core.constant.CacheConstants;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.system.api.RemoteSiteEfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author things
 */
@Slf4j
@Component
public class SecurityUserSiteService {

    @Resource
    private RedisService redisService;
    @Resource
    private RemoteSiteEfService remoteSiteEfService;

    public List<Long> getSiteIds(Long userId) {
        try {
            if (userId == null) {
                return new ArrayList<>();
            }
            List<Long> siteIds = getSiteIdsFromRedis(userId);
            if (siteIds != null) {
                return siteIds;
            }
            R<List<Long>> siteIdsR = remoteSiteEfService.getAllSiteIdsByUserId(userId, SecurityConstants.INNER);
            siteIds = siteIdsR.getData();
            if (siteIds == null) {
                return new ArrayList<>();
            }
            saveSiteIdsToRedis(userId, siteIds);
            return siteIds;
        } catch (Exception e) {
            log.error("UserSiteService getSiteIds error, userId={}", userId, e);
            return new ArrayList<>();
        }
    }

    private List<Long> getSiteIdsFromRedis(Long userId) {
        String key = getSiteIdKey(userId);
        List<Integer> siteIds = redisService.getCacheObject(key);
        if (siteIds == null) {
            return null;
        }
        return siteIds.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    private void saveSiteIdsToRedis(Long userId, List<Long> siteIds) {
        String key = getSiteIdKey(userId);
        redisService.setCacheObject(key, siteIds, 2L, TimeUnit.HOURS);
    }

    private String getSiteIdKey(Long userId) {
        return CacheConstants.SITE_ID_KEY + userId;
    }
}
