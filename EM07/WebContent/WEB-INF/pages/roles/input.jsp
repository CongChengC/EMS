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
<script type="text/javascript" src="${ctp }/script/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctp }/script/jquery.validate.js"></script>
<script type="text/javascript" src="${ctp }/script/messages_cn.js" ></script>
<script type="text/javascript" src="${ctp }/script/jquery.blockUI.js" ></script>

<script type="text/javascript">
	$(function(){
		//当选取父权限时, 显示对应的子权限
		$("select[name='parentAuthroities']").change(function(){
			//以authority-开头的就隐藏
			$("p[class^='authority-']").hide();
			
			var val = this.value;
			$(".authority-" + val).show();
		});
		
		$(":checkbox").click(function(){
			//如果当前被选中
			var flag = $(this).is(":checked");
			
			//若被选中, 则关联的也被选中. 
			if(flag){
				var relatedAuthorites = $(this).attr("class"); //,3,4,5,
				var ras = relatedAuthorites.split(",");
				for(var i = 0; i < ras.length; i++){
					var ra = ras[i];
					ra = $.trim(ra);
					
					if(ra != ""){
						//使 value 值等于关联值的那些 checkbox 也被选中
						$(":checkbox[value='" + ra + "']").attr("checked",true);
					}
				}
			}else{
				//若没选中
				//使 class 值中包含当前 value 值的 checkbox 取消选择.
				//若当前的 val 值为 2, 则 class 属性值包含 2 的或许有如下情况:
				//class=',2,3,4', class='1,12,31' 类似于 12 的不该被取消. 所以光包含 2 不行. 
				//应该不包含歧义的部分: class 包含 ,val,  就不会有这样的歧义了. 
				//变成了包含 ,2,
				var val = this.value; 
				//使 class 值中包含当前 value 值的 checkbox 取消选择.
				$(":checkbox[class*='," + val + ",']").attr("checked",false);
			}
		});
	});
</script>

</head>
<body>
 

	<br>
	<s:form name="employeeForm" id="employeeForm" action="/role-save" method="POST" cssClass="employeeForm">
		<fieldset>
			<p>
				<label for="name">角色名(必填*)</label>
				<s:textfield name="roleName" cssClass="required"></s:textfield>
			</p>
			
			<p>
				<label for="authority">授予权限(必选)</label>
			</p>
			
			<p>
				<label>权限名称(必填)</label>
				<!-- 父权限 -->
				<s:select list="#request.parentAuthorities" 
					listKey="id" listValue="displayName"
					headerKey="" headerValue="请选择..."
					name="parentAuthroities"></s:select>
			</p>
			
			
			<s:checkboxlist list="#request.subAuthorities"
    			listKey="id" listValue="displayName"
    			name="authorities2" templateDir="ems/template"
    			cssStyle="border:none" listCssClass="relatedAuthorites"></s:checkboxlist>
			
			
			<!-- 子权限以隐藏的 checkbox 的形式显示出来 -->
<%-- 			<s:iterator value="#request.subAuthorities">
				<p style="display:none" class="authority-${parentAuthority.id }">    
					<label>&nbsp;</label>    
					<input type="checkbox" name="authorities2" value="${id }"
						style="border:none" style="border:none"
						class="${relatedAuthorites }" />
					${displayName }
				</p>
			</s:iterator> --%>
			
			
			
			
			<p>
				<input class="submit" type="submit" value="Submit"/>
			</p>
			
		</fieldset>
	</s:form>	
	
</body>
</html>