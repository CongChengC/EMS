<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
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

</head>
<body>
      <br><br>
	<center>
		<br><br>
		
		<a id="criteria" href="${ctp}/emp-criteriaInput?height=300&width=320&time=new Date()"  class="thickbox"> 
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
			
			<s:if test="page.hasPrev">
			    [
				<a href="emp-list?pageNo=1">首页</a>
				/
				<a href="emp-list?pageNo=${page.prev }">上一页</a>
				] 
			</s:if>
			
			
			<span id="pagelist">
				转到 <input type="text" name="pageNo" size="1" height="1" class="logintxt"/> 页
			</span>
			
			<s:if test="page.hasNext">
			    [
				<a href="emp-list?pageNo=${page.next }">下一页</a>
				/
				<a href="emp-list?pageNo=${page.totalPages }">末页</a>
				] 
			</s:if>
			
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
					
					<td>操作</td>
					
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
						<td>
							<a href="emp-input?id=${employeeId }">修改</a>
							&nbsp;
							<s:if test="isDeleted == 1">
								删除
							</s:if>
							<s:else>
								<a href="emp-delete?id=${employeeId }">删除</a>
							</s:else>
						</td>
					</tr>
				</s:iterator>
					
					
			</tbody>
		</table>
		
		<a href="${ctp}/emp-downToExcel.action?time=Thu Mar 24 18:40:20 GMT+08:00 2016">下载到 Excel 中</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
	</center>
      
</body>
</html>