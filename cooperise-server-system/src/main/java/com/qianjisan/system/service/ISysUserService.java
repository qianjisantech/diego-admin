package com.qianjisan.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.system.request.SysUserQueryRequest;
import com.qianjisan.system.request.SysUserRequest;
import com.qianjisan.core.PageVO;
import com.qianjisan.system.vo.SysUserVO;
import com.qianjisan.system.entity.SysUser;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 分页查询用户列表
     *
     * @param request 查询请求
     * @return 分页结果
     */
    PageVO<SysUserVO> page(SysUserQueryRequest request);

    /**
     * 获取用户详情（包含角色信息）
     *
     * @param userId 用户ID
     * @return 用户VO
     */
    SysUserVO getUserDetail(Long userId);

    /**
     * 更新用户信息
     *
     * @param userId  用户ID
     * @param request 用户请求
     */
    void updateUser(Long userId, SysUserRequest request);

    /**
     * 删除用户（逻辑删除）
     *
     * @param userId 用户ID
     */
    void deleteUser(Long userId);

    /**
     * 分配角色给用户
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void assignRoles(Long userId, List<Long> roleIds);

    /**
     * 重置用户密码
     *
     * @param userId 用户ID
     */
    void resetPassword(Long userId);

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态：1-正常，0-禁用
     */
    void updateStatus(Long userId, Integer status);

    /**
     * 更新最后登录时间
     *
     * @param userId 用户ID
     */
    void updateLastLoginTime(Long userId);


    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    SysUser getUserByEmail(String email);

    /**
     * 根据用户编码查询用户
     *
     * @param userCode 用户编码
     * @return 用户信息
     */
    SysUser getUserByUserCode(String userCode);

    /**
     * 获取简单用户列表（仅包含 id, username, userCode, email）
     * 只查询未被逻辑删除的用户
     *
     * @return 用户列表
     */
    List<SysUser> getSimpleUserList();
}
