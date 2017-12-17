package org.ir.proj4.vo;

public class Data {
	private String tweet_text;
	private String hashtags;
	private String Doc_ID;
	private String text_en;
	private String topic_name;
	private String id;
	
	
	public String getDoc_ID() {
		return Doc_ID;
	}
	public void setDoc_ID(String doc_ID) {
		Doc_ID = doc_ID;
	}
	public String getText_en() {
		return text_en;
	}
	public void setText_en(String text_en) {
		this.text_en = text_en;
	}
	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}	
	
	public String getHashtags() {
		return hashtags;
	}
	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}
	public String getTweet_text() {
		return tweet_text;
	}
	public void setTweet_text(String tweet_text) {
		this.tweet_text = tweet_text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
