package com.ctmill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ctmill.pojo.entity.Menu;
import com.ctmill.mapper.MenuMapper;
import com.ctmill.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  菜单服务实现类
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getAllMenu() {
        //一级菜单
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,0);
        List<Menu> menuList = this.list(wrapper);
        //填充子菜单
        setMenuChildren(menuList);
        return menuList;
    }

    private void setMenuChildren(List<Menu> menuList){
        if (menuList != null){
            for (Menu menu: menuList) {
                LambdaQueryWrapper<Menu> childWrapper = new LambdaQueryWrapper<>();
                childWrapper.eq(Menu::getParentId,menu.getMenuId());
                List<Menu> childMenuList = this.list(childWrapper);
                menu.setChildren(childMenuList);
                //递归 子菜单
                setMenuChildren(childMenuList);
            }
        }
    }

    @Override
    public List<Menu> getMenuListByUserId(Integer userId) {
        //一级菜单
        List<Menu> menuList = this.baseMapper.getMenuListByUserId(userId, 0);
        //子菜单
        setMenuChildrenByUserId(userId, menuList);
        return menuList;
    }

    private void setMenuChildrenByUserId(Integer userId, List<Menu> menuList) {
        if (null != menuList){
            for (Menu menu: menuList) {
                List<Menu> childMenuList = this.baseMapper.getMenuListByUserId(userId, menu.getMenuId());
                menu.setChildren(childMenuList);
                //递归 子菜单
                setMenuChildren(childMenuList);
            }
        }
    }
}
