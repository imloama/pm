"use strict";
/**
 * sys模块的数据服务层
 * 
 * 向后台请求时，使用JSON.stringify(data)，从而在后台使用requestbody接收数据 
 * 
 */

//部门管理服务
app.factory('origanizationManager',['$http','$q','Origanization',function($http,$q,Origanization){
	var origanizationManager = {};
	origanizationManager._pool= {};
	origanizationManager._retrieveInstance = function(id, data) {
        var instance = this._pool[id];

        if (instance) {
            instance.setData(data);
        } else {
            instance = new Origanization(data);
            this._pool[id] = instance;
        }
        return instance;
    };
    origanizationManager._search = function(id) {
        return this._pool[id];
    };
    
    origanizationManager._load = function(id, deferred) {
        var scope = this;
        $http.get(BASE_URL+'/orig/' + id)
            .success(function(data) {
                if(data.code==1){
                	var depart = scope._retrieveInstance(data.data.id, data.data);
                }
                deferred.resolve(data);
            })
            .error(function() {
                deferred.reject();
            });
    };
    origanizationManager._del = function(id){
    	delete this._pool[id];
    };
    
    //公共方法
    origanizationManager.getOrig = function(id){
    	var deferred = $q.defer();
    	var orig = this._search(id);
    	if(orig){
    		deferred.resolve(orig);
    	}else{
    		this._load(id,deferred);
    	}
    	return deferred.promise;
    };
    
    origanizationManager.loadAll = function(){
    	 var deferred = $q.defer();
         var scope = this;
         $http.get(BASE_URL+'/origs')
             .success(function(msg) {
                 if(msg.code==1){
//                	 var origs = [];
                     msg.data.forEach(function(data) {
                         var orig = scope._retrieveInstance(data.id, data);
//                         origs.push(orig);
                     });
                 }
                 deferred.resolve(msg);
             })
             .error(function() {
                 deferred.reject();
             });
         return deferred.promise;
    };
    
    origanizationManager.setOriganization = function(data){
    	 var scope = this;
         var orig = this._search(data.id);
         if (orig) {
        	 orig.setData(data);
         } else {
        	 orig = scope._retrieveInstance(data);
         }
         return orig;
    };
    
    //当前保存的所有内容
    origanizationManager.getOrigs = function(){
    	 var scope = this;
    	 var origs = [];
    	 for(var id in scope._pool){
    		 origs.push(scope._pool[id]);
    	 }
    	 console.log(origs);
    	 return origs;
    };
    //删除
    origanizationManager.del = function(id){
    	 var deferred = $q.defer();
         var scope = this;
    	$http.get(BASE_URL+'/orig/del/' + id)
    	.success(function(msg){
    		if(typeof(msg.code)!="undefined"&&msg.code==1){
    			scope._del(id);
        		deferred.resolve(msg);
    		}
    	})
    	.error(function() {
            deferred.reject();
        });
    	 return deferred.promise;
    };
    //更新
    origanizationManager.update = function(data){
    	 var deferred = $q.defer();
         var scope = this;
    	 $http.post(BASE_URL+'/orig/update',JSON.stringify(data))
    	 .success(function(msg){
    		 if(msg.code==1){
    	     	scope.setOriganization(msg.data);
    		 }
     		deferred.resolve(msg);
     	})
     	.error(function() {
             deferred.reject();
         });
     	 return deferred.promise;
    };
    //插入  
    origanizationManager.insert = function(data){
    	 var deferred = $q.defer();
         var scope = this;
         $http.post(BASE_URL+'/orig', JSON.stringify(data))
    	 .success(function(msg){
     		if(msg.code==1){
     			scope.setOriganization(msg.data);
     		}
     		deferred.resolve(msg);
     	})
     	.error(function() {
             deferred.reject();
         });
     	 return deferred.promise;
    };
    
    
	return origanizationManager;
}]);
app.factory('Origanization',['$http',function($http){
	function Origanization(data) {
        if (data) {
            this.setData(data);
        }
        // Some other initializations related to book
    };
    Origanization.prototype = {
        setData: function(data) {
            angular.extend(this, data);
        }
    };
    return Origanization;
}]);


//========================角色==============================
app.factory('roleManager',['$http','$q','Role',function($http,$q,Role){
	var roleManager = {};
	roleManager.page = {};//从后台获取的page对象
    
    //公共方法,从缓存数据中查找
    roleManager.getRole = function(id){
    	var role = null;
    	var scope = this;
    	for(var i=0;i<scope.page.content.length;i++){
    		if(scope.page.content[i].id==id){
    			role = scope.page.content[i];
    			break;
    		}
    	}
    	return role;
    };
    roleManager.putRole = function(data){
    	var scope = this;
    	for(var i=0;i<scope.page.content.length;i++){
    		if(scope.page.content[i].id==data.id){
    			scope.page.content[i] = data;
    			break;
    		}
    	}
    };
    //添加到临时缓存中
    roleManager.addRole = function(data){
    	if(this.page){
    		this.page = newPage();//创建1个临时的变量
    	}
    	this.page.content.push(data);
    };
    
    //加载某一页
    roleManager.pagefor = function(id){
    	 var deferred = $q.defer();
         var scope = this;
         $http.get(BASE_URL+'/role/page/'+id)
             .success(function(msg) {
                 if(msg.code==1){
                     scope.page = msg.data;
                 }
                 deferred.resolve(msg);
             })
             .error(function() {
                 deferred.reject();
             });
         return deferred.promise;
    };
    //包含查询条件的查询
    roleManager.query = function(id,data){
    	 var deferred = $q.defer();
         var scope = this;
         $http.post(BASE_URL+'/role/query/'+id,data)
         .success(function(msg) {
             if(msg.code==1){
            	 scope.page = msg.data;
             }
             deferred.resolve(msg);
         })
         .error(function() {
             deferred.reject();
         });
         return deferred.promise;
    }
    
    
    //删除，删除成功后，返回当前分页内容
    roleManager.del = function(id){
    	 var deferred = $q.defer();
         var scope = this;
    	$http.get(BASE_URL+'/role/del/' + id+'/'+scope.page.number)
    	.success(function(msg){
    		//从当前缓存中去掉
    		if(msgSuccess(msg)){
    			scope.page = msg.data;
    		}
    		deferred.resolve(msg);
    	})
    	.error(function() {
            deferred.reject();
        });
    	 return deferred.promise;
    };
    //更新,并将数据更新到page对象中
    roleManager.update = function(data){
    	 var deferred = $q.defer();
         var scope = this;
    	 $http.post(BASE_URL+'/role/update',JSON.stringify(data))
    	 .success(function(msg){
    		 if(msgSuccess(msg)){
    			 scope.putRole(msg.data);//将更新后的数据放后当前对象中
    		 }
     		deferred.resolve(msg);
     	})
     	.error(function() {
             deferred.reject();
         });
     	 return deferred.promise;
    };
    //插入  
    roleManager.insert = function(data){
    	 var deferred = $q.defer();
         var scope = this;
         $http.post(BASE_URL+'/role', JSON.stringify(data))
    	 .success(function(msg){
    		 if(msgSuccess(msg)){
    			 scope.addRole(msg.data);//将新增后的数据放后当前临时对象中
    		 }
     		deferred.resolve(msg);
     	})
     	.error(function() {
             deferred.reject();
         });
     	 return deferred.promise;
    };
	return roleManager;
}]);

app.factory('Role',['$http',function($http){
	function Role(data) {
        if (data) {
            this.setData(data);
        }
        // Some other initializations related to book
    };
    Role.prototype = {
        setData: function(data) {
            angular.extend(this, data);
        }
    };
    return Role;
}]);


