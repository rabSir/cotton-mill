package com.ctmill.api;

import com.ctmill.common.vo.ResponseResult;
import com.ctmill.pojo.entity.Menu;
import com.ctmill.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  菜单模块API
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @ApiOperation("查询所有菜单数据")
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseResult<List<Menu>> getAllMenu(){
        List<Menu> menuList = menuService.getAllMenu();
        return ResponseResult.success("success",menuList);
    }

}
