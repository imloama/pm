<#include "layout/header.ftl">
<#assign ctx=request.contextPath>
<#import "func/ui.ftl" as ui>
 <body class="bg-black skin-blue">
	<@ui.div id="login-box" cls="form-box">
		<@ui.div cls="header">登陆</@ui.div>

 		<form id="loginForm" action="${ctx}/login" method="post">
	        <div class="body bg-gray">
		        <div class="error">
					<#if errmsg??>${errmsg}</#if>
				</div>
	            <div class="form-group">
	                <input type="text" name="username" id="username" class="form-control required" placeholder="请输入用户名" <#if username??> value="${username}"</#if>/>
	            </div>
	            <div class="form-group">
	                <input type="password" name="password" id="password" class="form-control required" placeholder="请输入密码"/>
	            </div>     
	             
	        </div>
	        <div class="footer">                                                               
	            <input type="submit" id="btn-login" class="btn bg-olive btn-block" value="登陆"/>  
	        </div>
		 </form>  
	    
	</@ui.div>
	
	<script>
		var empoent = null;
		var module = null;
	
	     
		$(function(){
			//获取加密密钥
			rsa();
		
			//1 校验功能绑定			
			$("#loginForm").validate({
				//debug:true,//true表示只验证,不提交表单1
				focusInvalid:false,//当为false时，验证无效时，没有焦点响应  
				onkeyup:false,
				submitHandler: function(form){
					//禁用btn
					$("#btn-login").attr("disabled","disabled");
					SubmitForm(form);
					return false;
				},    
                rules:{
                	username:{
                        required:true
                    }
                },//end of rules
                messages:{
                	username:{
                        required:"用户名不能为空"
                    },
                    password:{
                    	required:"密码不能为空"
                    }
                }//end of messages
			});
			
			
		
		});
		
		//表单提交句柄,为一回调函数，带一个参数：form  
		function SubmitForm(form){
		 	setMaxDigits(130);
		 	var key = new RSAKeyPair(empoent,"",module); 
			var result = encryptedString(key, encodeURIComponent($("#password").val()));
			$("#password").val(result);
			form.submit(); //提交表单   
			$("#btn-login").removeAttr("disabled");
		 	return false;
		}
		
		
		//获取当前加密密钥
		function rsa(){
			$.ajax({
	    		url : "${ctx}/api/keypair",
	    		dataType : 'json',
	    		success: function(data){
	    			console.log(data);
	    			empoent = data.empoent;
	    			module = data.module;
	    		},
	    		error:function(err){
	    			alert("连接服务器失败!");
	    		}
	    	});//end of ajax
		}
		
	</script>

<#include "layout/footer.ftl">
