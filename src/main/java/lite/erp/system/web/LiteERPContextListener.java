package lite.erp.system.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 窗口启动事件
 * @author mazhaoyong
 */
public class LiteERPContextListener implements ServletContextListener{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Object obj = sce.getServletContext().getInitParameter("dev");
		logger.debug("运行模式:"+obj);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
