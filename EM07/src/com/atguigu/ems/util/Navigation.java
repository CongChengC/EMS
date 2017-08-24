package com.atguigu.ems.util;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Navigation {
	
	private Integer id;
	private String text;
	
	private String title;
	private String url;
	
	//这里用 LinkedHashSet<>()是因为除了去重复，还得有顺序
	private Collection<Navigation> children = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Collection<Navigation> getChildren() {
		return children;
	}

	public void setChildren(Collection<Navigation> children) {
		this.children = children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	//这个重写可以用右键快速点出来，不用手写了
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Navigation other = (Navigation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
