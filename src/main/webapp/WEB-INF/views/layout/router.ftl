<#--用于生成angularjs对应的route内容    从内存中获取当前的menus数据，为list类型-->
<#import "/func/ui.ftl" as ui>
<#assign ctx=request.contextPath>
<script type="text/javascript">
app.config(['$routeProvider','$httpProvider','$locationProvider', function($routeProvider,$httpProvider,$locationProvider) {
	
  $routeProvider.when('/', {
	  templateUrl: '${ctx}/template/dashboard.html', 
	  controller: 'DashboardCtrl'
	});
	
	<@ui.aggrouter menus></@ui.aggrouter>
	$routeProvider.otherwise({redirectTo: '/'});
}]);
	
</script>