package org.ir.proj4.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.codehaus.jackson.map.ObjectMapper;
import org.ir.proj4.newRepVO.NodeVO;
import org.ir.proj4.vo.Data;
import org.json.simple.JSONObject;
import java.util.*;

import net.sf.classifier4J.summariser.SimpleSummariser;

public class GetTweets {

	public void summarize(NodeVO nodeVo){
		List<Data> tweets = nodeVo.getTweets();
		String tweet_text = "";
		for(Data data: tweets){
			tweet_text += data.getText_en()+".";
		}
		SimpleSummariser summariser = new SimpleSummariser();
		System.out.println("summarization -------  "+tweet_text);
		String s=summariser.summarise(tweet_text, 3);
		nodeVo.setSummary(s);
		System.out.println("summary -- "+s);
		
		//System.out.println(result);
		
	}
	public void wikiSummary(NodeVO nodeVo) throws UnsupportedEncodingException{
		String topic = URLEncoder.encode(nodeVo.getName(), "UTF-8").replace("+", "%20");
		//System.out.println(topic);
		//return topic;
		nodeVo.setWikiSummary(readFromWikiUrl("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="+topic+"&redirects=false&exsentences=2&redirects"));
		
	}
	public String readFromWikiUrl(String url){
		 ObjectMapper mapper = new ObjectMapper();
		 String text = "";
		 try {
			 JSONObject obj = mapper.readValue(new URL(url), JSONObject.class);
	            //System.out.println(obj);
	            String json = obj.toString();
	            if(json.indexOf("extract\":\"") == -1){
	            	return "";
	            }
	            /*System.out.println("length = "+json.length());
	            System.out.println("start - "+json.indexOf("extract\":\""));
	            System.out.println("end - "+json.indexOf("\"}"));*/
	            text = json.substring(json.indexOf("extract\":\"")+10, json.length()-1);
	            text = text.substring(0,  text.indexOf("\"}"));
	            //System.out.println(json);
	            System.out.println("wiki!!! --- "+ text);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		 return text;
	}
	}
