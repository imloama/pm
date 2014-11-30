'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
var services = angular.module('lite.services', []);
services.value('version', '0.1');

//1 menu service
services.factory("menuService",function($rootScope){
	var menuService = {};
	//初始化,配置可编辑性
	menuService.initMenus = function(menus){
		if(menus==null)return;
		$rootScope.menu0=true;
		for(var i=0;i<menus.length;i++){
			this.initMenu(menus[i]);
		}
	};
	menuService.initMenu =function(menu){
		if(menu.id){
			var idstr = "menu"+menu.id;
			$rootScope.idstr = false;
		}
		if(menu.children){
			for(var i=0;i<menu.children.length;i++){
				this.initMenu(menu.children[i]);
			}
		}
	};
	//根据控制器名称查询对应的菜单id
	menuService.activeByCtrl = function(menus,ctrl){
		var m = this.findMenuByCtrl(menus,ctrl);
		if(m==null||typeof(m)=="undefined"){
			return;
		}
		this.initMenus(menus);
		$rootScope.menu0=false;
		var idstr = "menu"+m.id;
		$rootScope.idstr = true;
		this.activeMenu(m);
		this.activeParent(menus,m);
	}
	
	//点击某个菜单时 
	menuService.active =function(menus,menuid){
		var m = this.findMenu(menuid);
		if(m==null||typeof(m)=="undefined"){
			return;
		}
		this.initMenus(menus);
		$rootScope.menu0=false;
		$rootScope.menuid = true;
		//找到父节点，并配置父节点为可用
		if(m){
			this.activeMenu(menu);
			this.activeParent(menus,menu);
		}
		
		
	};
	//配置某个菜单
	menuService.activeMenu = function(menu){
		var idstr = "menu"+menu.id;
		$rootScope.idstr = true;
	};
	menuService.activeParent = function(menus,menu){
		var m = this.findParent(menu);
		if(m){
			var idstr = "menu"+m.id;
			$rootScope.idstr = true;
			this.activeParent(menus,m);
		}else{
			return;
		}
	};
	
	menuService.findMenuByCtrl = function(menus,ctrl){
		for(var i=0;i<menus.length;i++){
			if(menus[i].jsController==ctrl){
				return menus[i];
			}else{
				if(menus[i].children){
					var m = this.findMenuByCtrl(menus[i].children,ctrl);
					if(m){
						return m;
					}
				}
			}
		}
		return null;
	}
	
	//根据idstr格式（menu+id)查找对应的menu节点
	menuService.findMenu = function(menus,idstr){
		for(var i=0;i<menus.length;i++){
			var id = "menu"+menus[i].id;
			if(id==idstr){
				return menus[i];
			}else{
				if(menus[i].children){
					var m = this.findMenu(menus[i].chidren,idstr);
					if(m!=null){
						return m;
					}
				}
			}
		}
		return null;
	};
	//找到节点的父节点
	menuService.findParent = function(menus,menu){
		//第1层，没有父节点
		for(var i=0;i<menus.length;i++){
			if(menus[i].id==menu.id){
				return null;
			}else{
				var m = this.findParentLoop(menus[i],menu);
				if(m){
					return m;
				}
			}
		}
		return null;
	}
	menuService.findParentLoop = function(target,menu){
		if(target.children){
			for(var i=0;i<target.children.length;i++){
				if(taget.children[i].id==menu.id){
					return target;
				}else{
					var m = this.findParentLoop(target.children[i],menu);
					if(m){
						return m;
					}
				}
			}
		}
		return null;
	}
	
	
	return menuService;
});


//2。 提示
//  alertService.add('warning', '这是一个警告消息！');
//alertService.add('error', "这是一个出错消息！");
services.factory('alertService', function($rootScope) {
    var alertService = {};
 
    // 创建一个全局的 alert 数组
    $rootScope.alerts = [];
 
    alertService.add = function(type, msg) {
      $rootScope.alerts.push({'type': type, 'msg': msg, 'close': function(){ alertService.closeAlert(this); }});
    };
 
    alertService.closeAlert = function(alert) {
      alertService.closeAlertIdx($rootScope.alerts.indexOf(alert));
    };
 
    alertService.closeAlertIdx = function(index) {
      $rootScope.alerts.splice(index, 1);
    };
 
    return alertService;
});


//加载service,目前是调用的layer
services.factory('loadingService',function(){
	var loadingService = {};
	loadingService.loading = function(txt){
		if(!txt){
			txt  = "加载中…";
		}
		if(this.loadi){
			this.stop();
		}
		this.loadi = layer.load(txt);
	};
	loadingService.stop = function(){
		if(this.loadi){
			layer.close(this.loadi);
		}
	}
	return loadingService;
});
