package lite.erp.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * js文件处理类,用于将js文件合并
 * @author mazhaoyong
 */
@Controller
@RequestMapping("/api")
public class JSController {

	/**
	 * 合并js,输出文件
	 * @return
	 */
	@RequestMapping("/js")
	public String index(HttpServletResponse rep,HttpServletRequest req,HttpSession session){
		
		return null;
	}
	
}
