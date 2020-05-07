package com.liugh.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liugh.config.ResponseHelper;
import com.liugh.config.ResponseModel;
import com.liugh.entity.Role;
import com.liugh.model.RoleModel;
import com.liugh.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping(value = "/roles/{id}")
    public ResponseEntity getRoles(@PathVariable String id){
        return new ResponseEntity(roleService.selectById(id), HttpStatus.OK);
    }

    /**
     * 返回全部的角色，新增用户时下拉选择
     * @return
     */
    @GetMapping(value = "/roles/tree")
    public ResponseEntity getRoleTree(){
        return new ResponseEntity(roleService.getRoleTree(),HttpStatus.OK);
    }

    @Log(description="查询角色")
    @GetMapping(value = "/roles")
    public ResponseEntity getRoles(@RequestParam(name = "page", defaultValue = "0", required = false) Integer pageIndex,
                                   @RequestParam(name = "size", defaultValue = "10", required = false) Integer pageSize,
                                   @RequestParam(value = "roleName", defaultValue = "",required = false) String roleName){
        PageListIO<Role> body = new PageListIO<>();
        body.setPageIndex(pageIndex+1);
        body.setPageSize(pageSize);
        Role role = new Role();
        role.setRoleName(roleName);
        body.setFormData(role);
        return new ResponseEntity(roleService.queryAll(body),HttpStatus.OK);
    }

    @Log(description="新增角色")
    @PostMapping(value = "/roles")
    public ResponseEntity create(@Validated @RequestBody Role resources)throws Exception{
        roleService.addRoleAndPermission(resources);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Log(description="修改角色")
    @PutMapping(value = "/roles")
    public ResponseEntity update(@Validated @RequestBody Role resources)throws Exception{
        roleService.updateRoleInfo(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log(description="删除角色")
    @DeleteMapping(value = "/roles/{id}")
    public ResponseEntity delete(@PathVariable Integer id)throws Exception{
        roleService.deleteByRoleId(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

