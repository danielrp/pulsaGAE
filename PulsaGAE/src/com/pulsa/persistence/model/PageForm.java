package com.pulsa.persistence.model;

public class PageForm {
	private int current;
	private int total;
	private int view;
	private int begin;
	private int end;
	private int size;
	private String search;
	
	public PageForm(){
		
	}
	
	public PageForm(int size,int view){
		setSize(size);
		this.view=view;
		total=size/view;
		if(size%view>0){
			total++;
		}
		current=total;
	}
	
	
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}



	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		if(current<=total){
			this.current = current;
		}else{
			this.current = total;
		}
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public int getBegin() {
		begin=view*(current-1);
		if(begin<0) begin=0;
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		end=(view*current)-1;
		if(end>size) end=size-1;
		if(end<0) end=0;
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	
	
	
}
