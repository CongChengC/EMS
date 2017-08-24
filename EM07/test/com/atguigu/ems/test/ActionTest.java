package com.atguigu.ems.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope("prototype")
@Controller
public class ActionTest {

	//输入流
	private InputStream inputStream;
	
	//文件下载的类型
	private String contentType;
	//下载的文件的长度
	private long contentLength;
	//下载的文件的文件名
	private String fileName;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public String getContentType() {
		return contentType;
	}
	public long getContentLength() {
		return contentLength;
	}
	public String getContentDisposition () {
		return "attachment;filename=" + fileName;
	}
	
	public String testFileDownload() throws IOException{
		//确定文件下载相关的成员变量的值
		inputStream = ServletActionContext.getServletContext().getResourceAsStream("/test/helloworld.txt");
		contentType = "text/html";
		contentLength = inputStream.available();
		fileName = "helloworld.txt";
		
		return "download-success";
	}
}
