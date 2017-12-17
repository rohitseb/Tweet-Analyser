package org.ir.proj4.newRepVO;

import java.util.List;

public class ReculsterVO {
	List<NodeVO> data;
	public List<NodeVO> getData() {
		return data;
	}
	public void setData(List<NodeVO> data) {
		this.data = data;
	}
	public String getReclusID() {
		return reclusID;
	}
	public void setReclusID(String reclusID) {
		this.reclusID = reclusID;
	}
	String reclusID;
}
