package com.mycompany.quanlyquancf;

import java.io.EOFException;
import java.util.Scanner;

public class ThucAn extends MonAn {

	private int isAnChay;

	@Override
	public String toString() {
		return super.toString() + "is ăn chay: " + this.isAnChay;
	}

	@Override
	public void nhapMonAn(Scanner scanner) throws EOFException {
		System.out.println("Nhập tên món ăn");
		this.setTen(scanner.nextLine());
		System.out.println("Nhập tên giá bán");
		this.setGiaBan(scanner.nextDouble());
		System.out.println("Nhập tình trạng: ");
		this.setTinhTrang(scanner.nextInt());
		System.out.println("Nhập thời điểm bán");
		String thoidiemban = scanner.nextLine();
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		do {
			System.out.println("nhập thời điểm bán(sang,trua,toi)");
			thoidiemban = scanner.next();
			if(thoidiemban.equalsIgnoreCase("trua")||thoidiemban.equalsIgnoreCase("sang")||thoidiemban.equalsIgnoreCase("toi")) {
			   if(sb.length()==0){
				sb.append(thoidiemban);
			   }else {
				 if(sb.indexOf(thoidiemban)<0) {
				   sb.append(",").append(thoidiemban);
				 }
					 
			   }
			   System.out.println("Bạn muốn thêm thời điểm bán không? C/K");
			   String them = scanner.next();
			   if(them.equalsIgnoreCase("c")) {
				  flag = true;
			   } else {
					 flag = false;
			   }
			}else {
			  System.out.println("nhập sai thời điểm bán");
			  throw new EOFException("Nhập sai thời điểm bán");
			}
		}while(flag);
		this.setThoiDiemBan(sb.toString());
		System.out.println("Nhập có phải là món chay(0 chay; 1 mặn): ");
		this.setAnChay(scanner.nextInt());
	};
	public int getAnChay() {
		return isAnChay;
	}

	public void setAnChay(int isAnChay) {
		if (isAnChay ==0 || isAnChay==1) {
			this.isAnChay= isAnChay;
			
		  }
		  else {
			  System.out.println("sai tình trạng của bàn");
			throw new ArithmeticException("sai tình trạng của bàn");
		  }
	}

}
