package com.things.cgomp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.enums.OrgTypeEnum;
import com.things.cgomp.common.core.enums.SysUserType;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.utils.bean.BeanValidators;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.service.TokenService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.*;
import com.things.cgomp.system.constant.SecurityConstant;
import com.things.cgomp.system.domain.SysPost;
import com.things.cgomp.system.domain.SysUserPost;
import com.things.cgomp.system.domain.dto.UpdatePwdDTO;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.*;
import com.things.cgomp.system.service.ISysConfigService;
import com.things.cgomp.system.service.ISysOrgService;
import com.things.cgomp.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;
import static com.things.cgomp.common.core.utils.SpringUtils.getAopProxy;

/**
 * 用户 业务层处理
 *
 * @author things
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysPostMapper postMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private SysUserPostMapper userPostMapper;
    @Resource
    private SysUserAccountMapper userAccountMapper;
    @Resource
    private SysUserSiteMapper userSiteMapper;
    @Resource
    private SysUserOperatorMapper userOperatorMapper;
    @Resource
    private ISysConfigService configService;
    @Resource
    private ISysOrgService orgService;
    @Resource
    protected Validator validator;
    @Resource
    private TokenService tokenService;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(orgAlias = "o", userOperatorAlias = "uop")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    @Override
    @DataScope(orgAlias = "o", userOperatorAlias = "uop")
    public PageInfo<SysUser> selectUserPage(SysUser user) {
        startPage();
        List<SysUser> users = userMapper.selectUserList(user);
        return new PageInfo<>(users);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(orgAlias = "o")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(orgAlias = "o")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        SysUser user = userMapper.selectUserById(userId);
        if(user == null){
            return null;
        }
        SysOrg org = user.getOrg();
        return user.setOrgName(org.getOrgName())
                .setOrgType(org.getType());
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean checkUserNameUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkUserNameUnique(user.getUsername());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public boolean checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getMobile());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public boolean checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw exception(ErrorCodeConstants.USER_SUPER_ADMIN_CANT_NOT_OPERATOR);
        }
        SysUser dbUser = userMapper.selectUserById(user.getUserId());
        if (dbUser != null) {
            if (SysUserType.OPERATOR_ADMIN.getType().equals(dbUser.getUserType())) {
                throw exception(ErrorCodeConstants.USER_OPERATOR_CANT_NOT_OPERATOR);
            }
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw exception(ErrorCodeConstants.USER_NO_PERMISSION);
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUser user) {
        // 保存用户
        int rows = saveUser(user);
        // 新增用户岗位关联
        //insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        // 新增用户与运营商关联
        insertUserOperator(user);
        // 新增用户与站点关联
        insertUserSite(user);
        // 新增用户账号
        insertUserAccount(user);
        return rows;
    }

    private int saveUser(SysUser user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            // 设置默认密码
            user.setPassword(SecurityUtils.encryptPassword(SecurityConstant.USER_DEFAULT_PASS));
        }
        // 新增用户信息
        if (user.getUserType() != null) {
            user.setUserType(OrgTypeEnum.PLATFORM.getType().equals(user.getOrgType())
                    ? SysUserType.PLATFORM_USER.getType()
                    : SysUserType.OPERATOR_USER.getType());
        }
        if (user.getOrgId() == null) {
            user.setOrgId(SecurityUtils.getOrgId());
        }
        user.setUserAccountType(UserAccountType.WEB);
        user.setCreateBy(SecurityUtils.getUserId());
        return userMapper.insertUser(user);
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user)
    {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
        SysUser dbUser = userMapper.selectById(userId);
        // 更新用户与角色关联
        updateUserRole(user, userId);
        // 删除用户与岗位关联
        //userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        //insertUserPost(user);
        // 更新用户与运营商关联
        updateUserOperator(user, dbUser);
        // 更新用户与站点关联
        updateUserSite(user, dbUser);
        int rows = userMapper.updateUser(user);
        tokenService.updateUserSession(user);
        return rows;
    }

    /**
     * 更新用户与角色关联
     */
    private void updateUserRole(SysUser user, Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
    }

    /**
     * 更新用户与站点关联
     */
    private void updateUserSite(SysUser user,  SysUser dbUser) {
        if (!SysUserType.OPERATOR_USER.getType().equals(dbUser.getUserType())) {
            return;
        }
        user.setUserType(dbUser.getUserType());
        // 删除用户与站点关联
        userSiteMapper.deleteUserSite(new Long[]{dbUser.getUserId()});
        // 新增用户与站点关联
        insertUserSite(user);
    }

    /**
     * 更新用户与运营商关联
     */
    private void updateUserOperator(SysUser user, SysUser dbUser) {
        if (!SysUserType.PLATFORM_USER.getType().equals(dbUser.getUserType())) {
            return;
        }
        user.setUserType(dbUser.getUserType());
        // 删除用户与运营商关联
        userOperatorMapper.deleteUserOperator(new Long[]{dbUser.getUserId()});
        // 新增用户与运营商关联
        insertUserOperator(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean updateUserProfile(SysUser user)
    {
        return userMapper.updateUser(user) > 0;
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    @Override
    public int updatePwd(UpdatePwdDTO user)
    {
        if (StringUtils.isEmpty(user.getPassword())) {
            throw exception(ErrorCodeConstants.USER_PASSWORD_NOT_NULL, "新");
        }
        if (StringUtils.isEmpty(user.getOldPassword())) {
            throw exception(ErrorCodeConstants.USER_PASSWORD_NOT_NULL, "原");

        }
        SysUser sysUser = userMapper.selectById(user.getUserId());
        if (!SecurityUtils.matchesPassword(user.getOldPassword(), sysUser.getPassword())) {
            throw exception(ErrorCodeConstants.USER_PASSWORD_NOT_MATCH);
        }
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUserId());;
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String username, String password)
    {
        return userMapper.resetUserPwd(username, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }

    private void insertUserSite(SysUser user) {
        if (!SysUserType.OPERATOR_USER.getType().equals(user.getUserType())) {
            return;
        }
        List<Long> siteIds = user.getSiteIds();
        if (CollectionUtils.isEmpty(siteIds)) {
            return;
        }
        List<SysUserSite> userSites = siteIds.stream().map(s -> new SysUserSite().setUserId(user.getUserId()).setSiteId(s)).collect(Collectors.toList());
        userSiteMapper.insertBatch(userSites);
    }

    private void insertUserOperator(SysUser user) {
        if (!SysUserType.PLATFORM_USER.getType().equals(user.getUserType())) {
            return;
        }
        List<Long> operatorIds = user.getOperatorIds();
        if (CollectionUtils.isEmpty(operatorIds)) {
            return;
        }
        List<SysUserOperator> userOperators = operatorIds.stream().map(s -> new SysUserOperator().setUserId(user.getUserId()).setOperatorId(s)).collect(Collectors.toList());
        userOperatorMapper.insertBatch(userOperators);
    }

    private void insertUserAccount(SysUser user) {
        SysUserAccount userAccount = new SysUserAccount();
        userAccount.setUserId(user.getUserId());
        userAccount.setType(user.getUserAccountType().getCode());
        userAccountMapper.insert(userAccount);
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotEmpty(posts))
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotEmpty(roleIds))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        // 停用账号
        userAccountMapper.disableUserAccountByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(Long[] userIds)
    {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        // 删除用户与站点关联
        userSiteMapper.deleteUserSite(userIds);
        // 删除用户与运营商关联
        userOperatorMapper.deleteUserOperator(userIds);
        // 停用账号
        userAccountMapper.disableUserAccount(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(userList) || userList.isEmpty())
        {
            throw exception(ErrorCodeConstants.USER_IMPORT_NOT_EMPTY);
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUsername());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, user);
                    orgService.checkOrgDataScope(user.getOrgId());
                    String password = configService.selectConfigByKey("sys.user.initPassword");
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(SecurityUtils.getUserId());
                    userMapper.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUsername()).append(" 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, user);
                    checkUserAllowed(u);
                    checkUserDataScope(u.getUserId());
                    orgService.checkOrgDataScope(user.getOrgId());
                    user.setUserId(u.getUserId());
                    user.setUpdateBy(SecurityUtils.getUserId());
                    userMapper.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUsername()).append(" 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getUsername()).append(" 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUsername() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "用户导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "用户数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public SysUser getUserInfo(Long userId) {
        if (userId == null) {
            userId = SecurityUtils.getUserId();
        }
        checkUserDataScope(userId);
        SysUser user = selectUserById(userId);
        user.hidePwd();
        user.setRoleIds(user.getRoles().stream().map(SysRole::getRoleId).toArray(Long[]::new));
        user.setSiteIds(userSiteMapper.selectSiteIds(userId));
        return user;
    }

}
