package com.mycompany.quanlyquancf;

import java.io.EOFException;
import java.util.Scanner;

public abstract class MonAn {
	public static Scanner scanner = new Scanner(System.in);

	protected String ten;

	protected double giaBan;

	protected int tinhTrang;

	protected String thoiDiemBan;

	public  String toString() {
		return "tên: " + this.getTen()+"Gia bán: "+this.giaBan+"Tình trang: "+this.tinhTrang+"thời điểm: "+this.thoiDiemBan;
	};
	public abstract void nhapMonAn(Scanner scanner) throws EOFException;
	public int getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(int tinhTrang) {
		if (tinhTrang ==0 || tinhTrang==1) {
			this.tinhTrang= tinhTrang;
		  }
		  else {
			  System.out.println("sai tình trạng món ăn");
			throw new ArithmeticException("sai tình trạng cmón ăn");
		  }
	}

	public String getThoiDiemBan() {
		return thoiDiemBan;
	}

	public void setThoiDiemBan(String thoidiemban) throws EOFException {
		this.thoiDiemBan = thoidiemban;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

}
