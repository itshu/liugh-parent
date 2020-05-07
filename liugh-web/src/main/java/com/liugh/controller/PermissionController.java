package com.liugh.controller;


import com.liugh.annotation.Log;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  权限控制器
 * </p>
 *
 * @author ustra123
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/system")
@Api(value="权限模块")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    private static final String ENTITY_NAME = "permission";

    @GetMapping(value = "/permissions/{id}")
    @Log(action="getPermission",modelName= "Permission",description="获取某个权限信息")
    public ResponseEntity getPermissions(@PathVariable Long id){
        return new ResponseEntity(permissionService.selectById(id), HttpStatus.OK);
    }

    /**
     * 返回全部的权限，新增角色时下拉选择
     * @return
     */
    @GetMapping(value = "/permissions/tree")
    @Log(action="getRoleTree",modelName= "Permission",description="获取角色下的权限树")
    public ResponseEntity getRoleTree(){
        return new ResponseEntity(permissionService.getPermissionTree(permissionService.findByPid(0)), HttpStatus.OK);
    }

    @Log(description="查询权限")
    @GetMapping(value = "/permissions")
    public ResponseEntity getPermissions(@RequestParam(required = false) String name){
        List<Permission> permissionDTOS = permissionService.queryAll(name);
        return new ResponseEntity(permissionService.buildTree(permissionDTOS), HttpStatus.OK);
    }

    @Log(description="新增权限")
    @PostMapping(value = "/permissions")
    public ResponseEntity create(@Validated @RequestBody Permission resources){
        resources.setCreateTime(new Date());
        return new ResponseEntity(permissionService.insert(resources), HttpStatus.CREATED);
    }

    @Log(description="修改权限")
    @PutMapping(value = "/permissions")
    public ResponseEntity update(@Validated @RequestBody Permission resources){
        permissionService.updateById(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log(description="删除权限")
    @DeleteMapping(value = "/permissions/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        permissionService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

