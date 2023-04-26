package com.ctmill.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctmill.common.utils.JwtUtil;
import com.ctmill.mapper.UserRoleMapper;
import com.ctmill.pojo.entity.Menu;
import com.ctmill.pojo.entity.User;
import com.ctmill.mapper.UserMapper;
import com.ctmill.pojo.entity.UserRole;
import com.ctmill.service.IMenuService;
import com.ctmill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private IMenuService menuService;

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> login(User user) {
        //根据用户名查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getStatus,1);
        User loginUser = this.baseMapper.selectOne(wrapper);
        //结果不为空,并且密码和传入的密码匹配,则生成token,并将用户信息存入redis
        if(loginUser != null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
            //不为空，生成token
            //创建jwt
            loginUser.setPassword(null);
            String key = jwtUtil.createToken(loginUser);
            //存入redis
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
            //返回数据
            Map<String,Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }

    /**
     * 根据登录token获取用户信息
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String token) {
        User loginUser = null;
        //根据token从redis中获取用户信息
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (loginUser != null){
            Map<String,Object> data = new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());
            //角色信息
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);
            //权限菜单
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList",menuList);
            return data;
        }
        return null;
    }

    /**
     * 添加用户
     * //TODO 未做返回类型
     * @param user
     */
    @Override
    public void saveUser(User user) {
        //加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Boolean flag = save(user);
    }

    /**
     * 分页查询用户数据
     * @param username
     * @param phone
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getPageUserList(String username, String phone, Long pageNum, Long pageSize) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(phone),User::getPhone,phone);
        Page<User> pageUser = new Page<>(pageNum,pageSize);
        page(pageUser,wrapper);
        Map<String,Object> data = new HashMap<>();
        data.put("total",pageUser.getTotal());
        data.put("rows",pageUser.getRecords());
        if(pageUser.getTotal() != 0){
            return data;
        }
        return null;
    }

    /**
     * 注销登录
     * @param token
     */
    @Override
    public void logout(String token) {
        //redis清除token
        redisTemplate.delete(token);
    }

    /**
     * 新增用户
     * @param user
     */
    @Override
    @Transactional
    public void addUserAndRole(User user) {
        //写入用户表
        this.baseMapper.insert(user);
        //写入用户角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if (null != roleIdList){
            for (Integer roleId: roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(),roleId));
            }
        }
    }

    @Override
    @Transactional
    public User getUserById(Integer id) {
        //根据id查询用户
        User user = this.baseMapper.selectById(id);
        user.setPassword(null);
        //根据用户id查询对应角色关系表
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);
        List<Integer> roleIdList = userRoleList.stream()
                .map(userRole -> {
                    return userRole.getRoleId();
                })
                .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);
        return user;
    }

    @Override
    @Transactional
    public void updateUserById(User user) {
        //更新用户表
        this.baseMapper.updateById(user);
        //清除用户原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        userRoleMapper.delete(wrapper);
        //设置新的角色
        List<Integer> roleIdList = user.getRoleIdList();
        if (null != roleIdList){
            for (Integer roleId: roleIdList) {
                userRoleMapper.insert(new UserRole(null, user.getId(),roleId));
            }
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        //删除用户
        this.baseMapper.deleteById(id);
        //清除用户对应角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        userRoleMapper.delete(wrapper);
    }
}
