package org.ir.proj4.vo;

import java.util.List;

public class SubTopic {

	List<Data> tweets;
	String name;
	int size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Data> getTweets() {
		return tweets;
	}

	public void setTweets(List<Data> tweets) {
		this.tweets = tweets;
	}
	
}
