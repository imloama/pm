'use strict';

/* Controllers */

angular.module('lite.controllers', []).
  controller('IndexController', ['$scope','$location',function($scope,$location) {
	 //退出
	 $scope.Quite = function(){
		 window.location = BASE_URL+"/logout";
	 };//end [Quite]
	 
	 
  }]);