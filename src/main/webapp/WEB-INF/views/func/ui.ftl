<#assign ctx=request.contextPath>

<#--定义1个container  支持嵌套-->
<#macro container cls="" attrs="" id="">
	<div <#if id!="">id="${id}"</#if> class="container ${cls}" ${attrs}> 
		<#nested> 
	</div>
</#macro>


<#--栅格系统中的row-->
<#macro row cls="" attrs="" id="">
	<div <#if id!="">id="${id}"</#if> class="row ${cls}" ${attrs}>
		<#nested>
	</div>
</#macro>

<#--col宽度-->
<#macro col width="1" cls="" attrs="" id="">
	<div <#if id!="">id="${id}"</#if>  class="col-md-${width} ${cls}" ${attrs}>
		<#nested>
	</div>
</#macro>

<#--普通的div-->
<#macro div id="" cls="" attrs="">
	<div <#if id!="">id="${id}"</#if> class="${cls}" ${attrs}>
		<#nested>
	</div>
</#macro>

<#--普通input输入框-->
<#macro textinput id name="" cls="" placeholder="">
	<input type="text" id="${id}" <#if name=="">name="${id}"<#else>name="${name}"</#if> class="${cls}"> 
</#macro>

<#--box功能   指的界面上的一个块区域,主要是在dashboard中用到 -->
<#macro box id="" cls="" attrs="">
	<#assign newcls="box "+cls>
	<@div id newcls attrs>
		<#nested>
	</@div>
</#macro>
<#--box的顶部-->
<#macro boxheader id="" cls="" attrs="" icoCls="" title="">
	<@div id cls attrs>
		<#if icoCls!=""><i class="${icoCls}"></i></#if>
		<#if title!=""><h3 class="box-title">${title}</h3></#if>
		<#nested><#--可能添加toolbox-->
	</@div>
</#macro>

<#--box工具栏-->
<#macro boxtools>
	<div class="pull-right box-tools">
		<#nested>
	</div>
</#macro>




<#--工具栏按  属性分别对应着  id   class  title  attributes iconclass-->
<#macro button id="" cls="" title="" attrs="" icoCls="">
	<button <#if id!="">id="${id}"</#if> class="btn <#if cls!="">${cls}</#if>"  title="${title}">
		<#if icoCls!="">
			<i class="${icoCls}"></i>
		</#if>	
		<#nested>
	</button>
</#macro>

<#--toggle button-->
<#macro togglebtn id="">
	<a href="javascript:void(0)" id="${id}" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
		<span class="sr-only">导航</span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	</a>
</#macro>
<#--顶部用户菜单-->
<#macro usermenu user>
	<li class="dropdown user user-menu">
		 <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
	        <i class="glyphicon glyphicon-user"></i>
	        <span>${user.nickName}<i class="caret"></i></span>
	    </a>
	    <ul class="dropdown-menu" role="menu">
	    	<li><a href="javascript:void(0)"><i class="fa fa-cog"></i>编辑信息</a></li>
	    	<li><a href="javascript:void(0)"><i class="fa fa-edit"></i>修改密码</a></li>
	    	<li class="divider"></li>
	        <li><a href="javascript:void(0)" ng-click="Quite()"><i class="fa fa-sign-out"></i>退出</a></li>
	    </ul>
	</li>
</#macro>


<#--左侧菜单 -->
<#macro menus menus>
	<div  class="sidebar" ng-controller="menuCtrl">
	<!--
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${ctx}/assets/img/avatar3.png" class="img-circle" alt="User Image" />
            </div>
            <div class="pull-left info">
                <p>Hello, Jane</p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="" ng-class="{'active':menu0}"><!--active-->
                <a href="#/">
                    <i class="fa fa-dashboard"></i> <span>主页</span>
                </a>
            </li>
			<#list menus as imenu>
				<@showmenu menu=imenu></@showmenu>
			</#list>
        </ul>
	</div>
</#macro>


<#macro showmenu menu>
	<#if (menu.show)><#--只有为菜单才会显示-->
	<li  <#if (menu.children?size>0)>class="treeview"</#if> ng-class="{'active':menu${menu.id}}" >
		<a href="javascript:void(0)" <#if menu.uri??> ng-click="menuClick('${menu.uri}','menu${menu.id}')"<#else> ng-click="menuClick('#','${menu.id}')"</#if>>
			<#if menu.icoCls??>
				<i class="${menu.icoCls}"></i>
			</#if>
			<span>${menu.name}</span>
		</a>
		<#if (menu.children?size>0)>
			<ul class="treeview-menu" ng-class="{'menutree-show':menu${menu.id}}">
				<#list menu.children as cmenu>
					<@showmenu menu=cmenu></@showmenu>
				</#list>
            </ul>
		</#if>
	</li>
	</#if>
</#macro>

<#--angularjs路由宏-->
<#macro aggrouter menus>
	<#list menus as menu>
		<@aggroutermenu menu></@aggroutermenu>
	</#list>
</#macro>

<#macro aggroutermenu menu>
	<#if menu.templateUrl??>
		$routeProvider.when('${menu.uri}', {
		  templateUrl: '${ctx}${menu.templateUrl}', 
		  controller: '${menu.jsController}'
		});
	</#if>
	<#if (menu.children?size>0)>
		<#list menu.children as m>
			<@aggroutermenu menu=m></@aggroutermenu>
		</#list>
	</#if>
</#macro>