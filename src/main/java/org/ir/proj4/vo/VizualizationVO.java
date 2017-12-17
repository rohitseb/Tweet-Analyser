package org.ir.proj4.vo;

import java.util.List;

public class VizualizationVO {
	String id;
	String name;
	String data;
	List<VizualizationVO> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List<VizualizationVO> getChildren() {
		return children;
	}
	public void setChildren(List<VizualizationVO> children) {
		this.children = children;
	}	
}
