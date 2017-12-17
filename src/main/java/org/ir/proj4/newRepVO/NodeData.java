package org.ir.proj4.newRepVO;

import javax.xml.bind.annotation.XmlElement;

public class NodeData {
	@XmlElement(name = "$dim", required = false)
	String dimension;
	@XmlElement(name = "$type", required = false)
	String type;
	@XmlElement(name = "$color", required = false)
	String color;
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
