package com.ctmill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctmill.mapper.RoleMenuMapper;
import com.ctmill.pojo.entity.Role;
import com.ctmill.mapper.RoleMapper;
import com.ctmill.pojo.entity.RoleMenu;
import com.ctmill.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMenuMapper roleMenuMapper;
    /**
     * 分页查询角色信息
     * @param roleName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getPageUserList(String roleName, Long pageNum, Long pageSize) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(roleName),Role::getRoleName,roleName);
        Page<Role> pageRole = new Page<>(pageNum,pageSize);
        page(pageRole,wrapper);
        Map<String,Object> data = new HashMap<>();
        data.put("total",pageRole.getTotal());
        data.put("rows",pageRole.getRecords());
        if(pageRole.getTotal() != 0){
            return data;
        }
        return null;
    }

    @Override
    @Transactional
    public void addAndRole(Role role) {
        //写入角色表
        this.baseMapper.insert(role);
        //写入角色菜单关系表
        if (null != role.getMenuIdList()){
            for (Integer menuId:role.getMenuIdList()) {
                roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
            }
        }
    }

    @Override
    public Role getRoleById(Integer id) {
        //根据角色id查询角色表
        Role role = this.baseMapper.selectById(id);
        //根据角色id查询关系表对应菜单集合id
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        //存入角色对象
        role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    public void updateRole(Role role) {
        //修改角色表
        this.baseMapper.updateById(role);
        //删除原有权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,role.getRoleId());
        roleMenuMapper.delete(wrapper);
        //新增权限
        if (null != role.getMenuIdList()){
            for (Integer menuId:role.getMenuIdList()) {
                roleMenuMapper.insert(new RoleMenu(null,role.getRoleId(),menuId));
            }
        }
    }

    @Override
    public void deleteRoleById(Integer id) {
        this.baseMapper.deleteById(id);
        //删除权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        roleMenuMapper.delete(wrapper);
    }
}
