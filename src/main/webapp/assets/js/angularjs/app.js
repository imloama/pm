'use strict';

var app = angular.module('lite', [
  'ngRoute',
  'lite.filters',
  'lite.services',
  'lite.directives',
  'lite.controllers',
  'ngGrid',
  'ui.bootstrap',
  'treeGrid'
]);

//$routeParams
//菜单的控制器
app.controller("menuCtrl",['$scope','$location','menus','menuService',
                           function($scope,$location,menus,menuService){
	menuService.initMenus(menus);
	//点击菜单时  url指的是要打开的链接   id是指的点击的菜单id
	$scope.menuClick = function(url,id){
		//1 配置class
		menuService.active(menus,id);
		//2 内容跳转
		if(url!="#"){
			$location.path(url);
		}
	};
}]);


//提示信息
var msginfo = {};
msginfo.error = function(info){
	$.layer({
		title:false,
		type:1,
		shift:'right-bottom',
		closeBtn: 1,
		time:10,
		border: [0],
		shade: [0],
		fix:true,
		//dialog:{type:-1,msg:info}
		page:{
			html:'<div class="alert alert-danger msginfo" role="alert">'+info+'</div>'
		}
	});
}

msginfo.success = function(info){
	$.layer({
		title:false,
		type:1,
		shift:'right-bottom',
		closeBtn: 1,
		time:10,
		border: [0],
		shade: [0],
		fix:true,
		page:{
			html:'<div class="alert alert-success msginfo" role="alert">'+info+'</div>'
		}
	});
}

/**
 * 判断后台返回的msg对象是否正确
 * @param msg
 */
function msgSuccess(msg){
	var result = false;
	if(typeof(msg.code)!="undefined"&&msg.code==1){
		result = true;
	}
	return result;
}
//前台page空对象，与后台的page对象内容相同
function newPage(){
	var page = {};
	page.content = [];
	page.totalElements = 0;
	page.totalPages = 1;
	page.firstPage = true;
	page.lastPage = true;
	page.number = 0;
	page.size = 100;
	return page;
}

/**
 * 封装spring-data-jpa返回的page对象，生成相应的页码对象,按5个页码来显示
 */
function pageinfo(page){
	var data = {};
	data.content = page.content;
	data.totalElements = page.totalElements;//总记录数
	data.totalPages = page.totalPages;//总页码
	data.firstPage = page.firstPage;
	data.lastPage = page.lastPage;
	data.number = page.number+1;//当前页码
	data.size = page.size;//每页显示多少条记录
	
	//计算页码位置
	data.firstIndex = (data.number<=5/2+1?1:(data.number-5/2));;//页码起始
	data.lastIndex = (data.firstIndex+5-1>=data.totalPages?data.totalPages:firstIndex+5-1);//页码结束
	
	return data;
}


