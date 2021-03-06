package com.ylz.ai.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.ai.admin.entity.Role;
import com.ylz.ai.admin.service.IRoleService;
import com.ylz.ai.auth.client.annotation.CheckClientToken;
import com.ylz.ai.auth.user.annotation.CheckUserToken;
import com.ylz.ai.common.vo.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
@Slf4j
@Api(tags="角色")
@RestController
@RequestMapping("/role")
@CheckClientToken
@CheckUserToken
public class RoleController {
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 分页列表查询
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @param sortProp
	 * @param sortType
	 * @return
	 */
	@ApiOperation(value="角色-分页列表查询", notes="角色-分页列表查询")
	@GetMapping(value = "/getRolePageList")
	public Result<IPage<Role>> getRolePageList(Role role,
											   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											   @RequestParam(name="sortProp", required = false) String sortProp,
											   @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<Role>> result = new Result<>();
		IPage<Role> pageList = roleService.findRolePageList(role, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	@ApiOperation(value="所有角色-查询", notes="所有角色-查询")
	@GetMapping(value = "/getAllRoles")
	public Result<List<Role>> getAllRoles() {
		Result<List<Role>> result = new Result<>();
		List<Role> roles = roleService.list();
		result.setSuccess(true);
		result.setResult(roles);
		return result;
	}
	
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	@ApiOperation(value="角色-添加", notes="角色-添加")
	@PostMapping(value = "/generateRole")
	public Result<Role> generateRole(@RequestBody Role role) {
		Result<Role> result = new Result<>();
		roleService.createRole(role);
		result.success("添加成功！");
		return result;
	}
	
	/**
	 * 编辑
	 * @param role
	 * @return
	 */
	@ApiOperation(value="角色-编辑", notes="角色-编辑")
	@PutMapping(value = "/changeRoleById")
	public Result<Role> changeRoleById(@RequestBody Role role) {
		Result<Role> result = new Result<>();
		roleService.alterRoleById(role);
		result.success("编辑成功！");
		return result;
	}
	
	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="角色-通过id删除", notes="角色-通过id删除")
	@DeleteMapping(value = "/expurgateRoleById")
	public Result<?> expurgateRoleById(@RequestParam(name="id",required=true) String id) {
		roleService.dropRoleById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="角色-批量删除", notes="角色-批量删除")
	@DeleteMapping(value = "/expurgateRoleBatch")
	public Result<?> expurgateRoleBatch(@RequestParam(name="ids",required=true) String ids) {
		roleService.dropRoleBatch(ids);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="角色-通过id查询", notes="角色-通过id查询")
	@GetMapping(value = "/getRoleById")
	public Result<Role> getRoleById(@RequestParam(name="id",required=true) String id) {
		Result<Role> result = new Result<>();
		Role role = roleService.findRoleById(id);
        result.setResult(role);
        result.setSuccess(true);
		return result;
	}

}
