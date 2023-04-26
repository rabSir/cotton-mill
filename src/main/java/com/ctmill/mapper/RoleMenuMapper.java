package com.ctmill.mapper;

import com.ctmill.pojo.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    public List<Integer> getMenuIdListByRoleId(Integer roleId);
}
