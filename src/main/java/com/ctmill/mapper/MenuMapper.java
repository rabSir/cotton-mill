package com.ctmill.mapper;

import com.ctmill.pojo.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);
}
