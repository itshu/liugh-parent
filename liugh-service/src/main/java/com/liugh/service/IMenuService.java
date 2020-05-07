package com.liugh.service;

import com.liugh.entity.Menu;
import com.baomidou.mybatisplus.service.IService;
import com.liugh.entity.UserToRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据 Ids 查询
     * @param permissionIds ids
     * @return  权限List
     */
    List<Menu> selectByIds(List<Integer> permissionIds);

    /**
     * 根据角色查询菜单
     * @param roleCode 角色主键
     * @return
     */
    List<Menu> findMenuByRoleCode(String roleCode);

    /**
     * 根据角色查询菜单
     * @param roleId 角色主键
     * @return
     */
    List<Menu> findMenuByRoleId(Integer roleId);

    List<Menu> findAll(String name);
    /**
     * 获取菜单树形结构
     * @param pId
     * @param list
     * @return
     */
    List<Menu> treeMenuList(String pId, List<Menu> list);

    /**
     * 根据角色列表获取菜单列表
     * @param roles
     * @return
     */
    List<Menu> findMenuByRoleId(List<UserToRole> roles);
    /**
     * build Tree
     * @param menus
     * @return
     */
    Map buildTree(List<Menu> menus);

    Object buildMenus(List<Menu> byRoles);

    Object getMenuTree(List<Menu> menus);

    List<Menu> findByPid(Integer pid);

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Integer id);
}
