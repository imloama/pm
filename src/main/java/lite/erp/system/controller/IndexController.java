package lite.erp.system.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lite.erp.system.consts.MessageCode;
import lite.erp.system.consts.SystemConsts;
import lite.erp.system.service.IUserService;
import lite.erp.system.vo.Message;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private IUserService userService;

	
	public IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	

	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public String page(HttpSession session){
		Subject user = SecurityUtils.getSubject();
		if(user.isAuthenticated()){
			return "redirect:index";
		}
		return "login";
	}
	
	/**
	 * 走到这里都是验证失败的，登陆已经在shiro filter中进行验证过了！
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public ModelAndView login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,HttpSession session){
		logger.debug("request into method[login].");
		session.removeAttribute("errmsg");
		String errmsg = null;
		this.clearSession(session);
		Map<String,String> userAttrs = (Map<String,String>)session.getAttribute(SystemConsts.USER_LOGIN_ATTRS);
		if(null==userAttrs||userAttrs.size()==0){
			errmsg = "登陆失败!用户名或密码错误！";
		}
		ModelAndView view = new ModelAndView("login");
		view.getModel().put("username", userName);
		view.getModel().put("errmsg", errmsg);
		return view;
	}
	
	
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session){
		logger.debug("request success into /index for method[index].");
		ModelAndView view = new ModelAndView("index");
		return view;
	}

	/**
	 * 退出登陆
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpSession session){
		logger.debug("用户请求退出");
		Subject user = SecurityUtils.getSubject();
		user.logout();
		clearSession(session);
		ModelAndView view = new ModelAndView("login");
		view.getModel().put("code", 0);
		return view;
	}
	
	
	@ExceptionHandler(Exception.class)  
	@ResponseBody
	public Message sqlExceptionHandler(Exception ex){
		logger.error("错误！", ex.getCause());
		logger.error(ex.getMessage());
		return new Message().setCode(MessageCode.ERROR).setMsg(ex.getMessage());
	}
	
	
	/**
	 * 清理session数据
	 */
	private void clearSession(HttpSession session){
		session.removeAttribute(SystemConsts.USER_LOGIN_ATTRS);
		session.removeAttribute(SystemConsts.CURRENT_USER_SESSION_NAME);
		session.removeAttribute(SystemConsts.ORIGIN_CURRENT_USER_NAME);
		session.removeAttribute(SystemConsts.SESSION_ATTR_MENUS);
	}
	
	/**
	 * 未授权用户
	 * @return
	 */
	@RequestMapping(value = "/unauth")
	public ModelAndView unAuth(){
		ModelAndView view = new ModelAndView("/unauth");
		view.getModel().put(MessageCode.MESSAGE_CODE, MessageCode.UNAUTH);
		return view;
	}
	
	
}
