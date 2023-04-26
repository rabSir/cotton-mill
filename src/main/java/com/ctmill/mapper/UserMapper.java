package com.ctmill.mapper;

import com.ctmill.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户id查询对应权限名称
     * @param userId
     * @return
     */
    List<String> getRoleNameByUserId(Integer userId);
}
