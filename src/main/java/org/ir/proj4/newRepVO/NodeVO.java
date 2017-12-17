package org.ir.proj4.newRepVO;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.ir.proj4.vo.Data;
import org.ir.proj4.vo.Docs;

public class NodeVO {
	String id;
	String name;
	NodeData data;
	List<AdjacenciesVO> adjacencies;
	List<Data> tweets;
	String summary;
	String wikiSummary;
	public String getWikiSummary() {
		return wikiSummary;
	}
	public void setWikiSummary(String wikiSummary) {
		this.wikiSummary = wikiSummary;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	String test;
	
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public List<Data> getTweets() {
		return tweets;
	}
	public void setTweets(List<Data> tweets) {
		this.tweets = tweets;
	}
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
	public NodeData getData() {
		return data;
	}
	public void setData(NodeData data) {
		this.data = data;
	}
	public List<AdjacenciesVO> getAdjacencies() {
		return adjacencies;
	}
	public void setAdjacencies(List<AdjacenciesVO> adjacencies) {
		this.adjacencies = adjacencies;
	}
}
