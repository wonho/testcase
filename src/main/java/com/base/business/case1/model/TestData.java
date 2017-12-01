package com.base.business.case1.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TestData extends ResourceSupport{
	private String header;
	private String data;
	private String tail;

	@JsonCreator
	public TestData(String header,String data,String tail) {
		this.header = header;
		this.data = data;
		this.tail = tail;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getTail() {
		return tail;
	}
	public void setTail(String tail) {
		this.tail = tail;
	}
	
	
}
