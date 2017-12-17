package org.ir.proj4.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
	Docs docs[];
	public Docs[] getDocs() {
		return docs;
	}
	public void setDocs(Docs docs[]) {
		this.docs = docs;
	}
}
