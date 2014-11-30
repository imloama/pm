'use strict';

/* Directives */


var liteDirectives = angular.module('lite.directives', []);
liteDirectives.directive('appVersion', ['version', function(version) {
    return function(scope, elm, attrs) {
      elm.text(version);
    };
  }]);

liteDirectives.directive("uiBox",function(){
	var boxDirective = {
			restrict:'EA',//E严格按照<directive></directive>来执行
			replace: true,
			transclude: true,//编译元素的内容，使它能够被directive所用
			template:'<div class="box"><span ng-transclude></span></div>',
			link:function(scope,lEle,lAttr){
			}//end {func:link}
			
	};//end {boxDirective}
	
	return boxDirective;
});
///====================================ui box============================================
//uibox中的头部
liteDirectives.directive("uiBoxHeader",function(){
	var boxHeaderDirective = {
			restrict:'EA',//E严格按照<directive></directive>来执行
			replace: true,
			transclude: true,//编译元素的内容，使它能够被directive所用
			template:'<div class="box-header"><i class="{{icocls}}"></i><h3 class="box-title">{{title}}</h3><span ng-transclude></span></div>',
			link:function(scope,lEle,lAttr){//配置图标\标题
				scope.icocls = lAttr.icocls;
				scope.title = lAttr.title;
			}//end {func:link}
			
	};//end {boxDirective}
	return boxHeaderDirective;
});
//uibox body
liteDirectives.directive("uiBoxBody",function(){
	var boxBodyDirective = {
			restrict:'EA',//E严格按照<directive></directive>来执行
			replace: true,
			transclude: true,//编译元素的内容，使它能够被directive所用
			template:'<div class="box-body"><span ng-transclude></span></div>',
			link:function(scope,lEle,lAttr){//配置图标\标题
			
			}//end {func:link}
			
	};//end {boxBodyDirective}
	return boxBodyDirective;
});
//uibox footer
liteDirectives.directive("uiBoxFooter",function(){
	var boxBodyDirective = {
			restrict:'EA',//E严格按照<directive></directive>来执行
			replace: true,
			transclude: true,//编译元素的内容，使它能够被directive所用
			template:'<div class="box-footer"><span ng-transclude></span></div>',
	};//end {boxBodyDirective}
	return boxBodyDirective;
});
//======================ui box 结束=======================================

//sidebar toggle button
liteDirectives.directive("toggleBtn",function(){
	var toggleBtnDirective = {
			restrict:'EA',
			replace: true,
			transclude: true,
			template:'<a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">'
					+ '<span class="sr-only">导航</span><span class="icon-bar"></span>'
					+ '<span class="icon-bar"></span><span class="icon-bar"></span></a>',
	};//end {toggleBtnDirective}
	return toggleBtnDirective;
});//=====end toggle button

//=================================顶部用户信息区=====================


