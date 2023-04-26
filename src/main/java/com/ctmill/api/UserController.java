package com.ctmill.api;

import com.ctmill.common.vo.ResponseResult;
import com.ctmill.pojo.entity.User;
import com.ctmill.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  用户模块API
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 登录用户
     * @param user
     * @return
     */
    @ApiOperation("用户登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseResult<Map<String,Object>> loginUser(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if (data!=null){
            //登录成功
            return ResponseResult.success("登录成功",data);
        }
        return ResponseResult.error("登录失败");
    }

    /**
     * TODO 传参优化vo类接收
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseResult<?> addUser(@RequestBody User user){
        userService.addUserAndRole(user);
        return ResponseResult.success("新增用户成功");
    }

    /**
     * 修改用户信息
     * //TODO User待变成vo类
     * @param user
     * @return
     */
    @PutMapping
    public ResponseResult<?> updateUser(@RequestBody User user){
        user.setPassword(null);
        userService.updateUserById(user);
        return ResponseResult.success("修改成功");
    }

    /**
     * 删除用户数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult<?> deleteUser(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return ResponseResult.success("删除用户成功");
    }

    /**
     * 根据用户名获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseResult<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getUserById(id);
        return ResponseResult.success("success",user);
    }

    /**
     * 注销登录
     * @param token
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResponseResult<?> logout(@RequestHeader("X-Token")String token){
        userService.logout(token);
        return ResponseResult.success("注销成功");
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public ResponseResult<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        //根据token从redis中获取用户信息
        Map<String,Object> data = userService.getUserInfo(token);
        if (data!=null){
            return ResponseResult.success("登录成功",data);
        }
        return ResponseResult.error("登录信息无效,请重新登录");
    }
    /**
     * TODO 传参优化vo类接收
     * 分页查询用户信息
     * @param username
     * @param phone
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseResult<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false)String username,
                                                      @RequestParam(value = "phone",required = false)String phone,
                                                      @RequestParam(value = "pageNum")Long pageNum,
                                                      @RequestParam(value = "pageSize")Long pageSize){

        Map<String, Object> data = userService.getPageUserList(username, phone, pageNum, pageSize);
        return ResponseResult.success("success",data);
    }


}
