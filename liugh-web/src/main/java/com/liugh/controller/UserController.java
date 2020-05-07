package com.liugh.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liugh.annotation.AccessLimit;
import com.liugh.annotation.CurrentUser;
import com.liugh.annotation.Log;
import com.liugh.annotation.ValidationParam;
import com.liugh.base.BusinessException;
import com.liugh.config.ResponseHelper;
import com.liugh.config.ResponseModel;
import com.liugh.entity.User;
import com.liugh.service.IUserService;
import com.liugh.util.ComUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liugh
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;


    /**
     * 获取当前登录用户信息
     * @param user
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/currentUser")
    public ResponseModel getUser(@CurrentUser User user) throws Exception{
        return ResponseHelper.succeed(user);
    }

    /**
     * 获取当前登录用户信息
     * @param user
     * @return
     * @throws Exception
     */
    @GetMapping("/logout")
    public ResponseModel logout(@CurrentUser User user) throws Exception{
        return ResponseHelper.succeed(null);
    }

    @ApiOperation(value = "根据ID获取用户信息")
    @GetMapping(value = "/users/{id}")
    public ResponseEntity getUser(@PathVariable String userNo){
        return new ResponseEntity(userService.selectById(userNo), HttpStatus.OK);
    }

    @ApiOperation(value = "查询用户")
    @Log(description = "查询用户")
    @GetMapping(value = "/users")
    public ResponseEntity getUsers(@RequestParam(name = "pageIndex", defaultValue = "0", required = false) Integer pageIndex,
                                   @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                   @RequestParam(value = "username", defaultValue = "",required = false) String username,
                                   @RequestParam(value = "mobile", defaultValue = "",required = false) String mobile,
                                   @RequestParam(value = "status", defaultValue = "",required = false) Integer status){
        PageListIO<User> body = new PageListIO<>();
        body.setPageIndex(pageIndex+1);
        body.setPageSize(pageSize);
        User user = new User();
        if(StringUtils.isNotBlank(username)){
            user.setUsername(username);
        }
        if(StringUtils.isNotBlank(mobile)){
            user.setMobile(mobile);
        }
        if(status != null){
            user.setStatus(status);
        }
        body.setFormData(user);
        return new ResponseEntity(userService.queryAll(body), HttpStatus.OK);
    }

    @Log(description = "新增用户")
    @PostMapping(value = "/users")
    public ResponseEntity create(@Validated @RequestBody User resources){
        userService.saveUser(resources);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Log(description = "修改用户")
    @PutMapping(value = "/users")
    public ResponseEntity update(@Validated @RequestBody User resources){
        userService.updateUser(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log(description = "删除用户")
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity delete(@PathVariable Integer id) throws Exception{
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 验证密码
     * @param pass
     * @return
     */
    @GetMapping(value = "/users/validPass/{pass}")
    public ResponseModel validPass(@CurrentUser User user, @PathVariable String pass){
        Map map = new HashMap();
        map.put("status",200);
        if (ComUtil.isEmpty(user) || !BCrypt.checkpw(pass, user.getPassword())) {
            map.put("status",400);
        }
        return ResponseHelper.succeed(map);
    }

    /**
     * 修改密码
     * @param pass
     * @return
     */
    @GetMapping(value = "/users/updatePass/{pass}")
    public ResponseEntity updatePass(@CurrentUser User user, @PathVariable String pass) throws Exception {
        if(BCrypt.checkpw(pass, user.getPassword())){
            throw new BusinessException("新密码不能与旧密码相同");
        }
        user.setPassword(pass);
        userService.updatePass(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}

