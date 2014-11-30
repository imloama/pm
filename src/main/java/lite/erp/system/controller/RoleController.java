package lite.erp.system.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lite.erp.system.consts.MessageCode;
import lite.erp.system.entity.Menu;
import lite.erp.system.entity.Role;
import lite.erp.system.service.IRoleService;
import lite.erp.system.vo.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

/**
 * 角色控制器
 * @author mazhaoyong
 *
 */

@RestController
public class RoleController {

	
	@Autowired
	private IRoleService roleService;
	
	public IRoleService getRoleService() {
		return roleService;
	}
	
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	
	@RequestMapping("/role/page/{id}")
	public Message page(@RequestParam(value="id")Optional<Integer> id){
		Message msg = new Message();
		int index = 1;
		if(id.isPresent()){
			index = id.get();
		}
		msg.setCode(MessageCode.SUCCESS).setData(getRoleService().page(index));
		System.out.println("-------msg:"+JSON.toJSONString(msg));
		return msg;
	}
	
	/**
	 * 查询,并且按分页来查询
	 * @return
	 */
	@RequestMapping(value="/role/query/{id}",method={RequestMethod.POST})
	public Message query(@PathVariable("id") Integer id,@RequestBody Map<String,Object> map){
		Message msg = new Message();
		int index = 1;
		if(id!=null){
			index = id;
		}
		msg.setCode(MessageCode.SUCCESS).setData(this.getRoleService().page(index, map));
		System.out.println(JSON.toJSONString(msg));
		return msg;
	}
	
	/**
	 * 查询某个角色的当前菜单
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/role/menus/{id}",method={RequestMethod.GET})
	public Message findMenus(@PathVariable("id") Long id){
		Message msg = new Message();
		if(id==null){
			return msg.setCode(MessageCode.ERROR).setMsg("连接错误!请重新登陆!");
		}
		Role role = this.getRoleService().get(id);
		if(role!=null){
			List<Menu> menus = role.getMenus();
			msg.setCode(MessageCode.ERROR).setMsg("连接错误!请重新登陆!").setData(menus);
		}else{
			msg.setCode(MessageCode.ERROR).setMsg("没有找到角色信息!");
		}
		return msg;
	}
	
	
	
}
