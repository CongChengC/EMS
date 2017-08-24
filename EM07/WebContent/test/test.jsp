<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${ctp }/script/jquery.min.js"></script>
<script type="text/javascript">
     $(function(){
    	 $("#testAjax").click(function(){
    		 var url = "emp-list2";
    		 var args = {"time":new Date(),"pageNo":1};
    		 $.post(url,args,function(data){
    			 
    		 },"json");
    		 return false;
    	 });
     })

</script>
</head>
<body>
     <a href="test-testFileDownload">testFileDownload</a>
     <br><br>
     <button id="testAjax">Test Ajax</button>
</body>
</html>