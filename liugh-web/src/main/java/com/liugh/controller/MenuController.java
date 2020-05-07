package com.liugh.controller;


import com.liugh.annotation.CurrentUser;
import com.liugh.annotation.Log;
import com.liugh.config.ResponseHelper;
import com.liugh.config.ResponseModel;
import com.liugh.entity.Menu;
import com.liugh.entity.User;
import com.liugh.entity.UserToRole;
import com.liugh.service.IMenuService;
import com.liugh.service.IUserToRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/system")
//不加入swagger ui里
@ApiIgnore
public class MenuController {

    @Autowired
    private IUserToRoleService userToRoleService;
    @Autowired
    private IMenuService menuService;

    /**
     * 构建前端路由所需要的菜单
     * @return
     */
    @Log(action="buildMenus",modelName= "Menu",description="建立菜单结构")
    @GetMapping(value = "/menus/build")
    public ResponseModel buildMenus(@CurrentUser User currentUser) {
        List<UserToRole> roles = userToRoleService.selectListByUserId(currentUser.getId());
        List<Menu> menuList = menuService.findMenuByRoleId(roles);
        List<Menu> menuDTOTree = (List<Menu>)menuService.buildTree(menuList).get("content");
        return ResponseHelper.succeed(menuService.buildMenus(menuDTOTree));
    }
    @GetMapping(value = "/menus/{id}")
    @Log(action="getMenus",modelName= "Menu",description="获取某个菜单信息")
    public ResponseEntity getMenus(@PathVariable Long id){
        return new ResponseEntity(menuService.selectById(id),HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     * @return
     */
    @GetMapping(value = "/menus/tree")
    public ResponseEntity getMenuTree(){
        return new ResponseEntity(menuService.getMenuTree(menuService.findByPid(0)),HttpStatus.OK);
    }

    @Log(description="查询菜单")
    @GetMapping(value = "/menus")
    public ResponseEntity getMenus(@RequestParam(required = false) String name){
        List<Menu> menuDTOList = menuService.findAll(name);
        return new ResponseEntity(menuService.buildTree(menuDTOList),HttpStatus.OK);
    }

    @Log(description="新增菜单")
    @PostMapping(value = "/menus")
    public ResponseEntity create(@Validated @RequestBody Menu resources){
        menuService.saveMenu(resources);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Log(description="修改菜单")
    @PutMapping(value = "/menus")
    public ResponseEntity update(@Validated @RequestBody Menu resources){
        menuService.updateMenu(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log(description="删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        menuService.deleteMenu(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

