pm
==

项目管理系统,作为erp基础使用的项目,一直想写好基础了再放出来,时间实在是不多,先放上来,慢慢再做吧.采用springmvc+freemarker+angularjs为主.界面模板使用的是adminlte.
为方便使用添加了springside4源码,添加了shiro-freemarker-tag源码


技术路线:
1. jdk8
2. springmvc(4.x版本)
3. freemarker做模板,后期再考虑去掉
4. angularjs,除了login.html  index.html是采用freemarker生成,其他都采用静态html文件
5. adminlte,来源于github的后台框架
6. bootstrap3
7. shiro

期望春节前能把权限部分都做完(完成产品发版,时间应该能充足一些了).


完成功能:
1. 脚手架(参考springside)
2. 权限基础,当前只做了组织+角色(未完成)
