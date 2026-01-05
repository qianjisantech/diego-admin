package com.qianjisan.auth.service.impl;


import com.qianjisan.auth.service.IAuthService;
import com.qianjisan.auth.service.IVerificationCodeService;
import com.qianjisan.console.dto.SelfUserCompanyDTO;
import com.qianjisan.console.mapper.UserCompanyMapper;
import com.qianjisan.console.service.ICompanyService;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.core.exception.BusinessException;
import com.qianjisan.core.utils.BeanConverter;
import com.qianjisan.core.utils.JwtUtil;
import com.qianjisan.core.utils.UserCodeGenerator;

import com.qianjisan.enterprise.vo.CompanyVo;
import com.qianjisan.system.entity.SysUser;
import com.qianjisan.system.service.ISysMenuService;
import com.qianjisan.system.service.ISysUserService;
import com.qianjisan.system.vo.SysMenuVO;
import com.qianjisan.system.vo.SysUserProfileVO;
import com.qianjisan.common.service.IAsyncEmailService;
import com.qianjisan.system.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.qianjisan.auth.vo.LoginResponseVO;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final ISysUserService userService;
    private final ISysMenuService menuService;
    private final IAsyncEmailService asyncEmailService;
    private final IVerificationCodeService verificationCodeService;
    private final UserCompanyMapper userCompanyMapper;

    // 普通用户角色ID（对应sys_role表中的USER角色）
    private static final Long DEFAULT_USER_ROLE_ID = 3L;

    @Override
    public LoginResponseVO login(String email, String password) {
        log.info("[AuthService] 用户登录: {}", email);

        // 根据邮箱查询用户
        SysUser sysUser = userService.getUserByEmail(email);

        if (sysUser == null) {
            throw new BusinessException("邮箱或密码错误");
        }

        // 验证密码（使用BCrypt加密算法）
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, sysUser.getPassword())) {
            throw new BusinessException("邮箱或密码错误");
        }

        // 生成JWT token，包含用户ID、用户名和用户编码
        String token = JwtUtil.generateToken(sysUser.getId(), sysUser.getName(), sysUser.getUserCode());

        // 设置用户上下文（用于后续操作的用户信息填充）
        com.qianjisan.core.context.UserContextHolder.setUser(
            sysUser.getId(),
            sysUser.getName(),
            sysUser.getUserCode()
        );

        // 登录成功后更新最后登录时间
        try {
            userService.updateLastLoginTime(sysUser.getId());
            log.debug("[AuthService] 用户最后登录时间已更新: {}", sysUser.getUserCode());
        } catch (Exception e) {
            log.warn("[AuthService] 更新用户最后登录时间失败: {}", e.getMessage());
            // 不影响登录流程
        }

        // 构建返回结果
        LoginResponseVO response = new LoginResponseVO();
        response.setToken(token);

        // 用户信息（不返回密码）
        UserInfoVO userInfoVO = BeanConverter.convert(sysUser, UserInfoVO::new);
        response.setUserInfo(userInfoVO);

        log.info("[AuthService] 用户登录成功，邮箱: {}, 用户编码: {}", email, sysUser.getUserCode());
        return response;
    }

    @Override
    public void sendVerificationCode(String email) {
        log.info("[AuthService] 发送验证码到邮箱: {}", email);

        // 验证邮箱格式
        if (!StringUtils.hasText(email)) {
            throw new BusinessException("邮箱不能为空");
        }

        String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (!email.matches(emailRegex)) {
            throw new BusinessException("邮箱格式不正确");
        }

        // 生成验证码
        String code = verificationCodeService.generateCode(email);

        // 调用独立的异步服务发送邮件（不阻塞主线程）
        asyncEmailService.sendVerificationCodeAsync(email, code);

        log.info("[AuthService] 验证码已生成，邮件正在后台发送");
    }

    @Override
    public void register(String email, String code, String password) {
        log.info("[AuthService] 用户注册: {}", email);

        // 参数校验
        if (!StringUtils.hasText(email)) {
            throw new BusinessException("邮箱不能为空");
        }
        if (!StringUtils.hasText(code)) {
            throw new BusinessException("验证码不能为空");
        }
        if (!StringUtils.hasText(password)) {
            throw new BusinessException("密码不能为空");
        }
        if (password.length() < 6) {
            throw new BusinessException("密码长度不能少于6位");
        }

        // 验证邮箱格式
        String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (!email.matches(emailRegex)) {
            throw new BusinessException("邮箱格式不正确");
        }

        // 验证验证码
        if (!verificationCodeService.verifyCode(email, code)) {
            throw new BusinessException("验证码错误或已过期");
        }

        // 检查邮箱是否已注册
        SysUser existingSysUser = userService.getUserByEmail(email);
        if (existingSysUser != null) {
            throw new BusinessException("该邮箱已被注册");
        }

        // 截取邮箱@前面的部分作为用户名
        String name = email.split("@")[0];

        // 生成8位纯数字的用户编码
        String userCode = UserCodeGenerator.generate();

        // 确保用户编码唯一性（如果重复则重新生成）
        int retryCount = 0;
        while (userService.getUserByUserCode(userCode) != null && retryCount < 10) {
            userCode = UserCodeGenerator.generate();
            retryCount++;
        }

        if (retryCount >= 10) {
            throw new BusinessException("用户编码生成失败，请稍后重试");
        }

        // 创建新用户
        SysUser newSysUser = new SysUser();
        newSysUser.setName(name);
        newSysUser.setUserCode(userCode);
        newSysUser.setEmail(email);

        // 加密密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newSysUser.setPassword(encoder.encode(password));

        // 设置默认状态
        newSysUser.setStatus(1); // 正常状态

        // 保存用户
        userService.save(newSysUser);

        // 自动分配"普通用户"角色
        try {
            List<Long> roleIds = new ArrayList<>();
            roleIds.add(DEFAULT_USER_ROLE_ID);
            userService.assignRoles(newSysUser.getId(), roleIds);
            log.info("[AuthService] 为新用户分配角色成功，用户ID: {}, 角色ID: {}", newSysUser.getId(), DEFAULT_USER_ROLE_ID);
        } catch (Exception e) {
            log.error("[AuthService] 为新用户分配角色失败，用户ID: {}, 错误: {}", newSysUser.getId(), e.getMessage(), e);
            // 角色分配失败不影响注册流程
        }

        // 删除验证码
        verificationCodeService.removeCode(email);

        log.info("[AuthService] 用户注册成功，邮箱: {}, 用户名: {}, 用户编码: {}", email, name, userCode);
    }

    @Override
    public SysUserProfileVO getUserProfile(Long userId) {
        log.info("[AuthService] 获取用户权限信息: {}", userId);

        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        // 获取用户完整信息
        SysUser sysUser = userService.getById(userId);
        if (sysUser == null) {
            throw new BusinessException("用户不存在");
        }

        SysUserProfileVO profile = new SysUserProfileVO();

        // 用户基本信息
        UserInfoVO userInfoVO = BeanConverter.convert(sysUser, UserInfoVO::new);
        profile.setUserInfo(userInfoVO);

        // 判断是否为 admin 用户
        boolean isAdmin = "admin".equalsIgnoreCase(sysUser.getName());

        // 获取用户菜单权限
        List<SysMenuVO> menuTree = menuService.getUserMenuTree(userId);
        List<String> menuPermissions = menuService.getUserMenuPermissions(userId);

        // 检查用户是否有角色（通过菜单权限判断）
        boolean hasRole = menuTree != null && !menuTree.isEmpty();

        if (!hasRole && !isAdmin) {
            // 没有角色的用户：返回空的权限和菜单
            log.warn("[AuthService] 用户没有分配角色，用户ID: {}", userId);
            profile.setMenus(new ArrayList<>());
            profile.setMenuPermissions(new String[0]);
            profile.setRoles(new String[0]);


            log.info("[AuthService] 返回空权限信息（无角色用户）");
            return profile;
        }

        // 设置菜单相关信息
        profile.setMenus(menuTree);
        profile.setMenuPermissions(menuPermissions.toArray(new String[0]));

        if (isAdmin) {
            // admin 用户拥有所有菜单权限（如果菜单为空，添加通配符）
            if (menuPermissions.isEmpty()) {
                profile.setMenuPermissions(new String[]{"*:*:*"});
            }
            profile.setRoles(new String[]{"admin"});

        } else {
            // 普通用户角色
            profile.setRoles(new String[]{"user"});

        }


        try {
            List<SelfUserCompanyDTO> selfUserCompanyDTOS = userCompanyMapper.selectCompaniesByUserId(userId);
            if (selfUserCompanyDTOS != null && !selfUserCompanyDTOS.isEmpty()) {
               
                List<CompanyVo> companyVos = new ArrayList<>();

                for (SelfUserCompanyDTO c : selfUserCompanyDTOS) {
                    CompanyVo cv = new CompanyVo();
                    cv.setId(c.getId());
                    cv.setCompanyCode(c.getCompanyCode());
                    cv.setCompanyName(c.getCompanyName());
                    cv.setIsDefault(c.getIsDefault()==1);
                    companyVos.add(cv);
                }
                log.info("[AuthService] getUserProfile 查询用户企业成功，用户ID: {}, 企业列表为 {}", userId,companyVos);
                profile.setCompanies(companyVos);
            } else {
                profile.setCompanies(new ArrayList<>());
                profile.setCompanyIds(new Long[0]);
            }
        } catch (Exception e) {
            log.error("[AuthService] getUserProfile 查询用户企业失败，用户ID: {}, 错误: {}", userId, e.getMessage());
            profile.setCompanies(new ArrayList<>());
            profile.setCompanyIds(new Long[0]);
        }

        return profile;
    }
}
