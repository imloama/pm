<#--配置好界面模板,配置左侧菜单,顶部工具条,只保留右侧中心区域用于显示信息-->
<#assign ctx=request.contextPath>
<#include "layout/header.ftl">
<#import "func/ui.ftl" as ui>

<body class="skin-blue fixed" ng-app="lite" ng-controller="IndexController">
		
	<div class="header">
			<a href="javascript:void(0)" class="logo" >项目管理系统</a>
			<div class="navbar navbar-static-top" role="navigation">
			 	<@ui.togglebtn id="tglbtn"></@ui.togglebtn>
			 	<div class="navbar-right">
			 		<ul class="nav navbar-nav">
						<@ui.usermenu user></@ui.usermenu>
			 		</ul>
			 	</div>
			</div><#--navbar 结束-->
		</div>
	
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<div  class="left-side sidebar-offcanvas">
			<#--菜单-->
			<@ui.menus menus></@ui.menus>
		</div>
		<div class="right-side">
			<div ng-view></div>
		</div>
	</div>
	

<#include "layout/app-js.ftl">

<script type="text/javascript">

app.config(['$routeProvider','$httpProvider','$locationProvider', 
	function($routeProvider,$httpProvider,$locationProvider) {
	
  $routeProvider.when('/', {
	  templateUrl: '${ctx}/template/dashboard.html', 
	  controller: 'DashboardCtrl'
	});
	
	
	<@ui.aggrouter menus></@ui.aggrouter>
	
	//router
	
	$routeProvider.otherwise({redirectTo: '/'});


}]);

app.value("menus",${menus_json});
	
</script>

<#include "layout/footer.ftl">