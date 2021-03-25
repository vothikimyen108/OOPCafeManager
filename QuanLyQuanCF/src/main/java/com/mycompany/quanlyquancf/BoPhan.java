package com.mycompany.quanlyquancf;

public class BoPhan {
	private static int dem = 0;
	private int maBoPhan;
	{
     	this.maBoPhan = ++dem;
	}
	private String tenBoPhan;
	public BoPhan(int mabp){
		this.maBoPhan = mabp;
	}
	public BoPhan(String tenbp){
		this.tenBoPhan =tenbp;
	}
	public String toString() {
		return "Mã Bộ Phận: "+this.getMaBoPhan() + " Tên Bộ Phân: " + this.tenBoPhan;
	}

	public String getTenBoPhan() {
		return tenBoPhan;
	}

	public void setTenBoPhan(String tenBoPhan) {
		
		this.tenBoPhan = tenBoPhan;
	}

	public int getMaBoPhan() {
		return maBoPhan;
	}

	public void setMaBoPhan(int maBoPhan) {
		this.maBoPhan = maBoPhan;
	}
}
