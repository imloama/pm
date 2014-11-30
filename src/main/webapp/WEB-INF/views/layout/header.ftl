<#--配置页面头部内容-->
<#assign ctx=request.contextPath>
<!DOCTYPE html>
<html class="bg-black">
    <head>
        <meta charset="UTF-8">
 		<title>PM项目管理系统</title>
 		<base href="${ctx}">
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <link rel="shortcut icon" href="${ctx}/assets/img/favicon.ico" />
        
        <#include "commons-css.ftl">
      
        <#include "commons-js.ftl">

	<script>
		var BASE_URL = "${ctx}";//默认项目根路径
	</script>
	
    </head>
   