/**
 * sys控制器
 */

//组织管理 控制器
app.controller('origanizationCtrl', ['$rootScope','$scope','origanizationManager',function($rootScope,$scope,origanizationManager){
	$rootScope.menu0 = false;
	$rootScope.menu1 = true;
	$rootScope.menu2 = true;
	
	
	$scope.selectedOrig = {};
	$scope.selectedOrig.available=true;
	$scope.disable = true;
	
	//保存
	$scope.save = function(){
		//先把按钮等内容不可用
		this.disable=true;
		console.log(this.selectedOrig);
		//修改的
		if(typeof(this.selectedOrig.id)!="undefined"){
			origanizationManager.update(this.selectedOrig).then(function(msg){
				if(msg.exception||msg.code!=1){
					$scope.errmsg = "保存失败!";
					$scope.isError = true;
					return;
				}
				//保存成功,从service中加载所有的组织
				$scope.isSuccess = true;
				$scope.successmsg = "保存成功!";
				$scope.origs = origanizationManager.getOrigs();
				$scope.initTree($scope.origs);
				$scope.selectedOrig = {};
				$scope.selectedOrig.available=true;
			},function(err){
				$scope.errmsg = "保存失败!";
				$scope.isError = true;
				return;
			});
			
		}else{//新增的
			origanizationManager.insert(this.selectedOrig).then(function(msg){
				if(msg.code!=1){
					$scope.errmsg = "保存失败!";
					$scope.isError = true;
					return;
				}
				$scope.isSuccess = true;
				$scope.successmsg = "保存成功!";
				$scope.origs = origanizationManager.getOrigs();
				$scope.disable=false;
				$scope.initTree(this.origs);
				$scope.selectedOrig = {};
				$scope.selectedOrig.available=true;
			},function(err){
				$scope.errmsg = err;
				$scope.isError = true;
				$scope.disable=false;
			});
		}
		
	};
	//取消
	$scope.cancel=function(){
		this.disable=true;
		$scope.selectedOrig = {};
	};
	$scope.isDisable=function(){
		return this.disable;
	}
	//新增按钮
	$scope.add = function(){
		this.disable = false;
		this.selectedOrig = {};
		this.selectedOrig.available=true;
	};
	//修改按钮
	$scope.edit = function(){
		if(this.selectedOrig&&typeof this.selectedOrig.id!="undefined"){
			$scope.disable = false;
		}
	};
	//删除按钮
	$scope.del = function(){
		origanizationManager.del($scope.selectedOrig.id).then(function(msg){
			if(typeof(msg.code)!="undefined"&&msg.code==1){
				//删除成功
				$scope.origs = origanizationManager.getOrigs();
				$scope.initTree($scope.origs);
				$scope.isSuccess = true;
				$scope.successmsg = "删除成功!";
				$scope.selectedOrig = {};
				$scope.selectedOrig.available=true;
			}else{
				$scope.errmsg = "删除失败!";
				$scope.isError = true;
			}
		},function(err){
			
		});
	};
	
	//提示信息的处理
	$scope.isError = false;
	$scope.isSuccess = false;
	$scope.showError = function(){
		return this.isError; 
	};
	$scope.showMsg = function(){
		return this.isSuccess;
	};
	$scope.closeError = function(){
		this.isError = false;
	};
	$scope.closeMsg = function(){
		this.isSuccess = false;
	};
	
	$scope.initTree = function(data){
		//初始化树
		var setting = {
				data: {
					key: {
						title: "组织"
					},
					simpleData: {
						enable: true,
						pIdKey:'parentId'
					}
				},
				callback: {
					onClick: function (event, treeId, treeNode, clickFlag) {
						$scope.selectedNode = treeNode;//当前选中的节点
						$scope.$apply(function () {
							var obj = {};
							obj.id = treeNode.id;
							obj.name = treeNode.name;
							obj.available = treeNode.available;
							obj.parentId = treeNode.parentId;
							$scope.selectedOrig = obj;
						});
					}
				}
		};
		var treeObj = $.fn.zTree.init($("#origTree"), setting,data);
		treeObj.expandAll(true); 
		this.treeObj = treeObj;
	}
	
}]);
//用户管理 
app.controller('usersCtrl', ['$scope',function($scope){
	
}]);

//角色管理
app.controller('rolesCtrl', ['$scope','$rootScope','roleManager','menus','alertService','menuService','loadingService',
                             function($scope,$rootScope,roleManager,menus,alertService,menuService,loadingService){
	//alertService.add('warning', '这是一个警告消息！');
	//配置菜单
	menuService.activeByCtrl(menus,'rolesCtrl');
	$scope.page = {};
	$scope.gridOptions = {
			data: 'roles',
	        selectedItems: $scope.selections,
	    		enableRowSelection: true,
	    		multiSelect: false,
	        enableRowReordering: false,
	        columnDefs: [ { field: 'id', displayName: '#' },
	        { field: 'code', displayName: '编码' },
	        { field: 'name', displayName: '名称' },
	        { field: 'description', displayName: '描述' },
	        { displayName:'操作', cellTemplate: '<div class="btn-group p-t-b-5">'
	        		+'<button type="button" class="btn  btn-xs btn-info" ng-click="edit(row.getProperty(\'id\'))"><i class="fa fa-edit"></i></button>'
	        		+'<button type="button" class="btn btn-xs btn-danger" ng-click="del(row.getProperty(\'id\'))"><i class="fa fa-times"></i></button>'
	        		+'</div>'
	        }]
	};

//	var loadi = layer.load('加载中…');
	loadingService.loading();
	$scope.searchData = {};//{'EQ_code':'1'};
	roleManager.query(1,$scope.searchData).then(function(msg){
		$scope.page = msg.data;
		$scope.roles = msg.data.content;
		$scope.currentPage = msg.data.number + 1;
//		layer.close(loadi);
		loadingService.stop();
	},function(err){
		loadingService.stop();
		console.log(err);
	});
	
	//查询按钮
	$scope.search=function(){
		loadingService.loading();
		if(this.searchForm.code){
			this.searchData.LIKE_code = this.searchForm.code
		}else{
			delete this.searchData.LIKE_code;
		}
		if(this.searchForm.name){
			this.searchData.LIKE_name = this.searchForm.name; 
		}else{
			delete this.searchData.LIKE_name;
		}
		roleManager.query(1,this.searchData).then(function(msg){
			$scope.page = msg.data;
			$scope.roles = msg.data.content;
			$scope.currentPage = msg.data.number + 1;
			loadingService.stop();
		},function(err){
			loadingService.stop();
			console.log(err);
		});
		
	};
	
	
	//翻页
	$scope.pageChanged = function(){
		alert($scope.currentPage);
	};
	
	//树表
	 $scope.tree_data = [
	                     {Name:"USA",Area:9826675,Population:318212000,TimeZone:"UTC -5 to -10",
	                      children:[
	                        {Name:"California", Area:423970,Population:38340000,TimeZone:"Pacific Time",
	                            children:[
	                                {Name:"San Francisco", Area:231,Population:837442,TimeZone:"PST"},
	                                {Name:"Los Angeles", Area:503,Population:3904657,TimeZone:"PST"}
	                            ]
	                        },
	                        {Name:"Illinois", Area:57914,Population:12882135,TimeZone:"Central Time Zone",
	                            children:[
	                                {Name:"Chicago", Area:234,Population:2695598,TimeZone:"CST"}
	                            ]
	                        }
	                    ]
	                  },    
	                  {Name:"Texas",Area:268581,Population:26448193,TimeZone:"Mountain"}
	                  ];
	
	
}]);

//资源管理
app.controller('resourcesCtrl', ['$scope',function($scope){
	
}]);

