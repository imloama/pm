package lite.erp.system.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import lite.erp.system.consts.SystemConsts;
import lite.erp.system.entity.Menu;
import lite.erp.system.entity.User;
import lite.erp.system.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 菜单控制器
 * @author mazhaoyong
 *
 */
@Controller
@RequestMapping("/api/v1")
public class MenuAPIController {

	@Autowired
	private IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/menus")
	@ResponseBody	
	public List<Menu> getMenus(HttpSession session){
		User origUser = (User)session.getAttribute(SystemConsts.ORIGIN_CURRENT_USER_NAME);
		return getUserService().findMenu(origUser);
	}
	
}
