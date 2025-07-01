package com.things.cgomp.system.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.system.domain.AppUserGroup;
import com.things.cgomp.system.domain.vo.AppUserGroupVo;
import com.things.cgomp.system.domain.vo.appusergroup.AppUserGroupListGroup;
import com.things.cgomp.system.dto.AppGroupPageDTO;
import com.things.cgomp.system.service.IAppUserGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@RestController
@RequestMapping(value = "/appUserGroup",name = "app用户组")
public class AppUserGroupController {

    @Resource
    private IAppUserGroupService appUserGroupService;

    @PostMapping(value = "", name = "新增app用户组")
    public R<Long> saveGroup(
            @RequestBody AppUserGroup group
    ) {
        Long templateId = appUserGroupService.saveGroup(
                group
        );
        return R.ok(templateId);
    }

    @GetMapping(value = "", name = "查询app用户组")
    public R<AppUserGroup> selectGroup(
         @RequestParam Long id
    ) {
        AppUserGroup group = appUserGroupService.selectGroup(
                id
        );
        return R.ok(group);
    }

    @PutMapping(value = "", name = "编辑app用户组")
    public R<?> editGroup(
            @RequestBody AppUserGroup group
    ) {
        appUserGroupService.editGroup(
                group
        );
        return R.ok();
    }

    @DeleteMapping(value = "", name = "删除app用户组")
    public R<?> deleteGroup(
            @RequestBody AppUserGroup group
    ) {
        appUserGroupService.deleteGroup(
                group.getId()
        );
        return R.ok();
    }

    @GetMapping(value = "page", name = "app用户组分页列表")
    public R<PageInfo<AppUserGroupVo>> selectPage(
            AppGroupPageDTO pageDTO
    ) {
        PageInfo<AppUserGroupVo> page = appUserGroupService.selectPage(
                pageDTO
        );
        return R.ok(page);
    }

    @GetMapping(value = "list", name = "app用户组下拉列表")
    public R<List<AppUserGroupListGroup>> selectGroups(
            BaseEntity baseEntity
    ) {
        List<AppUserGroupListGroup> groups = appUserGroupService.selectGroups(
                baseEntity
        );
        return R.ok(groups);
    }

}
