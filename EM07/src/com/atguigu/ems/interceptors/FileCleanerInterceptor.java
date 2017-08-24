package com.atguigu.ems.interceptors;



import java.io.File;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


public class FileCleanerInterceptor implements Interceptor {

	@Override
	public void destroy() {}

	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        String result = invocation.invoke();
		//删除临时文件
        //1. 把文件名从 request中取出来
        Map<String, Object> request = (Map<String, Object>) invocation.getInvocationContext().get("request");
        String tempFileName = (String) request.get("tempFileName");
        if(tempFileName != null){
        	//System.out.println("---- >" + tempFileName);
        	Thread.sleep(5000);
        	File file = new File(tempFileName);
        	if(file.exists()){
        		file.delete();
        	}
        }
        return result;
	}
}
