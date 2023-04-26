package com.ctmill.service;

import com.ctmill.pojo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  菜单服务类
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getAllMenu();

    List<Menu> getMenuListByUserId(Integer userId);
}
