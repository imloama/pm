'use strict';

app.directive('tree',['origanizationManager',function(origanizationManager){
	return {
		require:'?ngModel',
		restrict: 'A',
		link: function ($scope, element, attrs, ngModel) {
			
			origanizationManager.loadAll().then(function(msg){
				if(msg.code!=1){
					alert("获取数据失败,请稍后重试!");
					return;
				}
				
				$scope.origs = msg.data;
				$scope.initTree(msg.data);
			},function(err){
				//alert(err);
			});
			
		}
	}
}]);