package com.things.cgomp.pay.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.dto.coupon.CouponActivityPageDTO;
import com.things.cgomp.pay.service.ICouponActivityService;
import com.things.cgomp.pay.vo.coupon.CouponActivityVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券活动表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@RestController
@RequestMapping("/couponActivity")
public class CouponActivityController extends BaseController {

    @Resource
    private ICouponActivityService couponActivityService;

    @GetMapping(value = "nameMap", name = "查询优惠券活动名称Map")
    R<Map<Long,String>> getNameMap(
            @RequestParam("ids") List<Long> ids
    ){
        Map<Long,String> nameMap = couponActivityService.getNameMap(
                ids
        );
        return R.ok(nameMap);
    }

    @PostMapping(value = "grantCoupons", name = "（支付成功后）发放优惠券")
    R<?> grantCoupons(
            @RequestBody OrderInfo orderInfo
    ) {
        couponActivityService.grantCoupons(
                orderInfo
        );
        return R.ok();
    }

    @PostMapping(value = "", name = "新增优惠券活动")
    public R<Long> saveActivity(
            @RequestBody CouponActivity activity
    ) {
        Long templateId = couponActivityService.saveActivity(
                activity
        );
        return R.ok(templateId);
    }

    @GetMapping(value = "", name = "查询优惠券活动")
    public R<CouponActivity> selectActivity(
            @RequestParam Long id
    ) {
        CouponActivity activity = couponActivityService.selectActivity(
                id
        );
        return R.ok(activity);
    }

    @PutMapping(value = "", name = "编辑优惠券活动")
    public R<?> editActivity(
            @RequestBody CouponActivity activity
    ) {
        couponActivityService.editActivity(
                activity
        );
        return R.ok();
    }

    @PutMapping(value = "switch", name = "启用/禁用优惠券活动")
    public R<?> switchActivity(
            @RequestBody CouponActivity activity
    ) {
        couponActivityService.switchActivity(
                activity
        );
        return R.ok();
    }

    @GetMapping(value = "page", name = "优惠券活动分页列表")
    public R<PageInfo<CouponActivityVo>> selectPage(
            CouponActivityPageDTO pageDTO
    ) {
        PageInfo<CouponActivityVo> page = couponActivityService.selectPage(
                pageDTO
        );
        return R.ok(page);
    }

}
