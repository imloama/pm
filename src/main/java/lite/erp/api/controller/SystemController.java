package lite.erp.api.controller;

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

import com.google.common.collect.Lists;

/**
 * 系统相关功能api服务
 * @author mazhaoyong
 *
 */
@Controller
@RequestMapping("/api")
public class SystemController {

	@Autowired
	private IUserService userService;
	
	/**
	 * 查询当前登陆用户的所有菜单
	 * @return
	 */
	@RequestMapping("/user/menu")
	@ResponseBody
	public List<Menu> findUserMenu(HttpSession session){
		User user = (User)session.getAttribute(SystemConsts.ORIGIN_CURRENT_USER_NAME);
		if(null==user){
			return Lists.newArrayList();
		}
		return userService.findMenu(user);
	}
	
}
