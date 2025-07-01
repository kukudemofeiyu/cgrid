package com.things.cgomp.app.controller;

import com.things.cgomp.app.domain.AppBanners;
import com.things.cgomp.app.domain.dto.AppBannerDTO;
import com.things.cgomp.app.service.AppBannersService;
import com.things.cgomp.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/banner")
@Slf4j
public class AppBannerController {
    @Autowired
    private AppBannersService appBannersService;
    @GetMapping("/appletWeChatLogin")
    public R<List<AppBanners>> getAllBanners() {
        List<AppBanners> appBanners = appBannersService.getAllActiveBanners();
        return R.ok(appBanners);
    }

    @PostMapping("/create")
    public R createBanner(@Valid @RequestBody AppBannerDTO banner) {
         appBannersService.createBanner(banner);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R updateBanner(@PathVariable Long id, @Valid @RequestBody AppBannerDTO banner) {
         appBannersService.updateBanner(id, banner);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R deleteBanner(@PathVariable Long id) {
        appBannersService.deleteBanner(id);
        return R.ok();
    }


}
