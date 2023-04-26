package com.ctmill.service;

import com.ctmill.pojo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
public interface IRoleService extends IService<Role> {
    /**
     * 分页查询角色信息
     * @param rolename
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map<String, Object> getPageUserList(String rolename, Long pageNum, Long pageSize);

    void addAndRole(Role role);

    Role getRoleById(Integer id);

    void updateRole(Role role);

    void deleteRoleById(Integer id);
}
