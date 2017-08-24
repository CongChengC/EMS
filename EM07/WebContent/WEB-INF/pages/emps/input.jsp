<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="${ctp }/css/content.css">
<link rel="stylesheet" type="text/css" href="${ctp }/css/input.css">
<link rel="stylesheet" type="text/css" href="${ctp }/css/weebox.css">
 
<link rel="stylesheet" type="text/css" href="${ctp}/script/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctp}/script/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctp}/css/default.css">

<script type="text/javascript" src="${ctp}/script/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctp}/script/jquery.validate.js"></script>

<script type="text/javascript" src="${ctp}/script/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctp}/script/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${ctp}/script/messages_cn.js"></script>
<script type="text/javascript" src="${ctp}/script/bgiframe.js"></script>
<script type="text/javascript" src="${ctp}/script/weebox.js"></script>
<script type="text/javascript">

        $(function(){
        	
    		//loginName 的 Ajax 校验
    		$("#loginName").change(function(){
    			var val = this.value;
    			val = $.trim(val);
    			this.value = val;
    			
    			if(val == ""){
    				alert("LoginName 不能为空!");
    				return;
    			}
    			if(val.length < 6){
    				alert("LoginName 不能少于 6 个字符!");
    				return;
    			}
    			var reg = /^[a-zA-Z]\w+\w$/g;
    			if(!reg.test(val)){
    				alert("LoginName 不合法!");
    				return;
    			}
    			
    			var oldLoginName = $("#oldLoginName").val();
    			if(val == oldLoginName){
    				return;
    			}
    			
    			//注意这里 validateLoginName正好对应com.atguigu.ems.actions.EmployeeAction中的
    			//validateLoginName方法，这里用来与数据库对比验证。
    			var url = "${ctp}/emp-validateLoginName";
    			var args = {"loginName":val,"time":new Date()};
    			$.post(url, args, function(data){
    				if(data == "1"){
    					alert("LoginName 可用!");
    				}else if(data == "0"){
    					alert("LoginName 不可用!");
    				}else{
    					alert("请稍后重试!");
    				}
    			});
        	});
        	
        	
    		//完成 jQuery 的 validate 验证
    		$("#employeeForm").validate();
        	
        	$("#role_a_id").click(function(){
    			//实际上窗体可以使用选择器
    			$.weeboxs.open('#rolebox', {
    				title:'分配角色', //title
    				//weebox 窗口打开时需要触发的时间
    				//读取选中的状态
    				//有name属性是保存的
    				//有name属性的$(":checkbox[name ='roles2']")是个数组，后面加[index]是个元素，外面加
    				//$然后{}是一个jQuery对象;"checked"属性是checked
    				onopen:function() {
    					$(":checkbox[name='roles2']").each(function(index){
    						var checked = $(this).is(":checked");
    						$($(":checkbox[name!='roles2']")[index]).attr("checked", checked);
    					})
    				},
    				//点击 OK 按钮时将要触发的事件
    				//保存选择的状态. 有 name 属性的是保存的, 即用于提交表单的. 而没有 name 属性的是用来显示的.
    				//jQuery 的 each 函数中如果加入 index, 则 index 表示索引
    				//没有name属性的是用来显示的
    				//有name属性的$(":checkbox[name ='roles2']")是个数组，后面加[index]是个元素，外面加
    				//$然后{}是一个jQuery对象;"checked"属性是checked
    				onok:function(box){
    					$(":checkbox[name!='roles2']").each(function(index){
    						var checked = $(this).is(":checked");
    						$($(":checkbox[name='roles2']")[index]).attr("checked", checked);
    					})
    					box.close();//增加事件方法后需手动关闭弹窗
    				}
    			});
        		return false;
        	});
        })

		
		function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
		function myparser(s){
			if (!s) return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}

</script>

</head>
<body>
   <%--  <s:debug></s:debug> --%>
	<br>
	<s:form action="/emp-save" id="employeeForm" cssClass="employeeForm">
		<s:if test="employeeId != null">
		<input type="hidden" name="oldLoginName" 
			id="oldLoginName" value="${param.oldLoginName == null ? requestScope.loginName : param.oldLoginName }"/>
		<input type="hidden" name="id" value="${employeeId }"/>	
		</s:if>
		
		<fieldset>
			<p>
				<label for="message">
					<font color="red">添加员工信息</font>
				</label> 
			</p>
			
			<p>
				<label for="loginName">登录名(必填)</label>
				<s:textfield name="loginName" id="loginName" cssClass="required" minlength="6"></s:textfield>
				<label id="loginlabel" class="error" for="loginname" generated="true">
				<!-- 显示服务器端简单验证的信息 -->
					
				</label>
			</p>
			
			<p>
				<label for="employeeName">姓名 (必填)</label>
				<s:textfield name="employeeName"></s:textfield>
			</p>
			
			<p>
				<label for="logingallow">登录许可 (必填)</label>
				<s:radio list="#{'1':'允许','0':'禁止' }" name="enabled" cssStyle="border:none"></s:radio>
			</p>

			<p>
				<label for="gender">性别 (必填)</label>
				<s:radio list="#{'1':'男','0':'女' }" name="gender" cssStyle="border:none"></s:radio>
			</p>
			
			<p>
				<label for="dept">部门 (必填)</label>
				<s:select list="#request.departments" 
					listKey="departmentId" listValue="departmentName"
					name="department.departmentId"></s:select>
				<label class="error" for="dept" generated="true">
				<font color="red">
				<!-- 显示服务器端简单验证的信息 -->
				</font>
				</label>
			</p>
			
			<p>
				<label for="birth">生日 (必填)</label>
				<s:textfield name="birth" 
					cssClass="easyui-datebox" 
					data-options="formatter:myformatter,parser:myparser"></s:textfield>
			</p>
			
			<p>
				<label for="email">Email (必填)</label>
				<s:textfield name="email"></s:textfield>
				<label class="error" for="email" generated="true">
				<!-- 显示服务器端简单验证的信息 -->
					<s:fielderror fieldName="email"></s:fielderror>
				</label>
			</p>
			
			<p>
				<label for="mobilePhone">电话 (必填)</label>
				<s:textfield name="mobilePhone"></s:textfield>
			</p>

			<p>
				<label for="role"><a id="role_a_id" href="">分配角色(必选)</a></label>
			</p>
			
			<div style="display:none">
				<!-- 有 name 属性的. 用来保存选中的状态 -->
				<s:checkboxlist list="#request.roles"
					listKey="roleId" listValue="roleName"
					name="roles2"></s:checkboxlist>					
			</div>
			
			<div style="display:none" id="rolebox"> 
				<!-- 没有 name 属性的. 仅用来显示选择的状态 -->
				<s:iterator value="#request.roles">
					<input type="checkbox" value="${roleId}" style="border:none"/>${roleName }<br>
				</s:iterator>
			</div>
			
			<p>
				<label for="mobilePhone">创建人</label>
				<s:if test="employeeId == null">
				${employee.loginName }
				<input type="hidden" value="${sessionScope.employee.employeeId }" 
					name="editor.employeeId"/>
				</s:if>
				<s:else>
				${editor.loginName }
				<input type="hidden" value="${editor.employeeId }" 
					name="editor.employeeId"/>
				</s:else>
			</p>
			
			<p>
				<input class="submit" type="submit" value="提交"/>
			</p>
				
		</fieldset>
	</s:form>
</body>
</html>