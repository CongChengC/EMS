<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="${ctp}/css/content.css">
<link rel="stylesheet" type="text/css" href="${ctp}/css/list.css">
<link rel="stylesheet" type="text/css" href="${ctp}/script/thickbox/thickbox.css">

<script type="text/javascript" src="${ctp}/script/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctp}/script/thickbox/thickbox.js"></script>

<!-- <script type="text/javascript">
	$(function(){
		//若当前页面是第一页, 则隐藏 prevPageLinks
		if("${page.pageNo}" == "1"){
			$("#prevPageLinks").hide();
		}
		//若当前页面是最后一页, 则隐藏 nextPageLinks
		//自己完成
		if("${page.pageNo}" == "${page.totalPages}"){
			$("#nextPageLinks").hide();
		}
		
		//建立一个删除函数
		function deleteTr(aNode){
			var loginName = $(aNode).next(":hidden").val();
			var flag = confirm("确定要删除" + loginName + "的信息吗?");
			if(!flag){
				return false;
			}
			
			var url = aNode.href;
			//还需要携带一个参数: pageNo. 因为需要 Ajax 更新当前的页面
			//获取当前页的页码:
			var pageNo = $("#pageNo").val();
			
			//准备 Ajax 请求:
			var args = {"date":new Date(),"pageNo":pageNo}
			$.post(url, args, function(data){
				//如何来判断删除是否成功呢 ? 若成功则返回 page 对应的 JSON, 若失败, 则什么都没有返回
				//表示你返回的结果里面有pageNo属性
			   if(data.pageNo){
					alert("删除成功!");
				}else{
					alert("是 Manager! 不能被删除!");
				}
			});
		}
		
		//点击删除时候，调用删除函数
		$(".delete").click(function(){
			deleteTr(this);				
			return false;
		});
		
		
		function turnPage(url,args){
			$.post(url, args, function(data){
				
				//更新 #pageNo
				$("#pageNo").val(data.pageNo);
				
				//3. 使用 jQuery 和 javascript 更新页面的内容
				//3.1 更新: .pagebanner
				$(".pagebanner").html("共" + data.totalElements + "条记录"
						+ "&nbsp;&nbsp;"
						+ "共" + data.totalPages + "页"
						+ "&nbsp;&nbsp;"
						+ "当前第" + data.pageNo + "页");	
				//3.2 更新: .pagelinks
				if(data.pageNo == 1){
					$("#prevPageLinks").hide();
				}else{
					$("#prevPageLinks").show();
				}
				//关于 nextPageLinks 待实现。 
				if(data.pageNo == data.totalPages){
					$("#nextPageLinks").hide();
				}else{
					$("#nextPageLinks").show();
				}
				
				//3.3 更新隐藏域中的 value 值
				$("#prevPageNoVal").val(data.prev);
				$("#nextPageNoVal").val(data.next);
				
				//3.4 更新 table 中 tbody 的内容. 
				var $tbody = $("table tbody");
				$tbody.empty();
				
				for( i=0; i<data.content.length;i++){
					var item = data.content[i];
					
					var $tr = $("<tr></tr?");
					$tr.append("<td><a>"+item.loginName +"</a></td>");
					$tr.append("<td>" + item.employeeName + "</td>");
					
					$tr.append("<td>" + (item.enabled == 1 ? '允许':'禁止') + "</td>");
					$tr.append("<td>" + item.departmentName + "</td>");
					
					$tr.append("<td>" + item.displayBirth + "</td>");
					$tr.append("<td>" + (item.gender == 1 ? '男':'女') + "</td>");
					
					$tr.append("<td>" + item.email + "</td>");
					$tr.append("<td>" + (item.mobilePhone == null ? "" : item.mobilePhone) + "</td>");
					
					$tr.append("<td>" + item.visitedTimes + "</td>");
					$tr.append("<td>" + (item.isDeleted == 1 ? '删除':'正常') + "</td>");
					
					$tr.append("<td>" + item.roleNames + "</td>");
					
					<security:authorize ifAnyGranted="ROLE_EMP_DELETE,ROLE_EMP_UPDATE">
					var $td = $("<td></td>");
					$td.append("<a href='emp-input?id=" + item.employeeId + "'>修改</a>");
					if(item.isDeleted == 1){
						$td.append("&nbsp;&nbsp;删除")
					}else{
						$td.append("&nbsp;&nbsp;");
						var $deleteANode = $("<a href='emp-delete?id=" + item.employeeId + "'>删除</a>");
						$deleteANode.click(function(){
							deleteTr(this);				
							return false;
						});
						$td.append($deleteANode);
						$td.append("<input type='hidden' value='" + item.loginName + "'/>");
					}
					$tr.append($td);
					</security:authorize>
					
					$tbody.append($tr);
				}
				
			},"json");
		}
		
		
		//通过 js 实现输入页码之后的直接分页.
		$(".logintxt").change(function(){
			//1.得到输入页码的value值
			var val = this.value;
			val = $.trim(val);
			
			//2. 对 value 值进行校验: 输入的是否为一个数字, 页码是否在合法的范围内
			var reg = /^\d+$/;
			if(!reg.test(val)){
				alert("输入的页码不合法!");
				this.value = "";
				return;
			}
			//parseInt 是 js 中自带的一个函数: 可以把字符串转为一个数值
			var pageNo = parseInt(val);
			if(pageNo < 1 || pageNo > parseInt("${page.totalPages}")){
				alert("输入的页码不合法!");
				this.value = "";
				return;
			}
			
			//3.Ajax分页
			var url = "${ctp}/emp-list2";
			var args = {"pageNo":pageNo,"time":new Date()};
			turnPage(url,args);
			
		});
		
		$(".pagelinks a").click(function(){
			//1. 准备 Ajax 请求
			var url = "${ctp}/emp-list2";
			var pageNo = $(this).next(":hidden").val();
			var args = {"pageNo":pageNo,"time":new Date()};
			
			//2. 发送 Ajax 请求
			turnPage(url,args);
			
			return false;
		});
	})
</script>  -->

</head>
<body>
    <input type="hidden" id="pageNo" value="${page.pageNo }"/>
    <br><br>
	<center>
		<br><br>
		
		<a id="criteria" href="${ctp}/emp-criteriaInput?height=300&width=320&time=new Date()&${queryString}"  class="thickbox"> 
	   		增加(显示当前)查询条件	   		
		</a> 
		
		<a href="" id="delete-query-condition">
		   	删除查询条件
		</a>
		
		<span class="pagebanner">
			共  ${page.totalElements}条记录
			&nbsp;&nbsp;
			共  ${page.totalPages} 页
			&nbsp;&nbsp;
			当前第  ${page.pageNo} 页
		</span>
		
		<span class="pagelinks">
			
			<span id="prevPageLinks">
				[
				<a href="emp-list3?pageNo=1&${queryString}">首页</a>
				<input type="hidden" value="1"/>
				/
				<a href="emp-list3?pageNo=${page.prev }&${queryString}">上一页</a>
				<input type="hidden" value="${page.prev }" id="prevPageNoVal"/>
				] 
			</span>
			
			<span id="pagelist">
				转到 <input type="text" name="pageNo" size="1" height="1" class="logintxt"/> 页
			</span>
			
			<span id="nextPageLinks">
				[
				<a href="emp-list3?pageNo=${page.next }&${queryString}">下一页</a>
				<input type="hidden" value="${page.next }" id="nextPageNoVal"/><!-- 存放 pageNo 的隐藏域 -->
				/
				<a href="emp-list3?pageNo=${page.totalPages }&${queryString}">末页</a>
				<input type="hidden" value="${page.totalPages }"/>
				] 
			</span>
		</span>
		
		<table>
			<thead>
				<tr>
					<td><a id="loginname" href="">登录名</a></td> 
					<td>姓名</td>
					
					<td>登录许可</td>
					<td>部门</td>
					
					<td>生日</td>
					<td>性别</td>
					
					<td><a id="email" href="">E-Mail</a></td>
					<td>手机</td>
					
					<td>登录次数</td>
					<td>删除</td>
					<td>角色</td>
					
					<security:authorize ifAnyGranted="ROLE_EMP_DELETE,ROLE_EMP_UPDATE">
					<td>操作</td>
					</security:authorize>
					
				</tr>
			</thead>
			
			<tbody>
				 <s:iterator value="page.content">
					<tr>
						<td><a id="loginname" href="">${loginName}</a></td> 
						<td>${employeeName}</td>
						<td>${enabled == 1 ? '允许':'禁止'}</td>
						<td>${department.departmentName }</td>
						<td>
						<s:date name="birth" format="yyyy-MM-dd"/>
						</td>
						<td>${gender == 1 ? '男':'女'}</td>
						<td><a id="email" href="">${email }</a></td>
						<td></td>
						<td>${visitedTimes }</td>
						<td>${isDeleted == 1 ? '删除':'正常'}</td>
						<td>${roleNames }</td>
						<security:authorize ifAnyGranted="ROLE_EMP_DELETE,ROLE_EMP_UPDATE">
						<td>
							<security:authorize ifAnyGranted="ROLE_EMP_UPDATE">
							<a href="emp-input?id=${employeeId }">修改</a>
							&nbsp;
							</security:authorize>
							<security:authorize ifAnyGranted="ROLE_EMP_DELETE">
							<s:if test="isDeleted == 1">
								删除
							</s:if>
							<s:else>
								<a class="delete" href="emp-delete?id=${employeeId }">删除</a>
								<input type="hidden" value="${loginName }"/>
							</s:else>
							</security:authorize>
						</td>
						</security:authorize>
					</tr>
				</s:iterator>
					
					
			</tbody>
		</table>
		
		<a href="${ctp}/emp-download">下载到 Excel 中</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
	</center>
      
</body>
</html>