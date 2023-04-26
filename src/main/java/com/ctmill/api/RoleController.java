package com.ctmill.api;

import com.ctmill.common.vo.ResponseResult;
import com.ctmill.pojo.entity.Role;
import com.ctmill.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  角色信息模块API
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
@Slf4j
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    @ApiOperation(value = "获取所有角色信息")
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseResult<List<Role>> getAllRoleList(){
        List<Role> roleList = roleService.list();
        return ResponseResult.success("success",roleList);
    }


    /**
     * TODO 传参优化vo类接收
     * 分页查询角色信息
     * @param roleName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseResult<Map<String,Object>> getUserList(@RequestParam(value = "roleName",required = false)String roleName,
                                                          @RequestParam(value = "pageNum")Long pageNum,
                                                          @RequestParam(value = "pageSize")Long pageSize){
        Map<String, Object> data = roleService.getPageUserList(roleName,pageNum, pageSize);
        return ResponseResult.success("success",data);
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseResult<?> addUser(@RequestBody Role role){
        roleService.addAndRole(role);
        return ResponseResult.success("新增角色成功");
    }

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    @PutMapping
    public ResponseResult<?> updateUser(@RequestBody Role role){
        roleService.updateRole(role);
        return ResponseResult.success("修改角色成功");
    }

    /**
     * 删除角色数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult<?> deleteUser(@PathVariable("id") Integer id){
        roleService.deleteRoleById(id);
        return ResponseResult.success("删除角色成功");
    }

    /**
     * 根据用户名获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseResult<Role> getUserById(@PathVariable("id") Integer id){
        Role role = roleService.getRoleById(id);
        return ResponseResult.success("success",role);
    }

}
