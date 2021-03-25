      /*
      * To change this license header, choose License Headers in Project Properties.
      * To change this template file, choose Tools | Templates
      * and open the template in the editor.
      */
      package com.mycompany.quanlyquancf;

      import java.io.EOFException;
      import java.sql.Connection;
      import java.sql.DriverManager;
      import java.sql.SQLException;
      import java.text.ParseException;
      //import java.util.Date;
      import java.util.Scanner;

      /**
       *
       * @author ASUS
       */
      public class DeMo {
        
        public static Scanner scanner = new Scanner(System.in,"UTF-8");
        public static Connection connn ;
        public static void menuQuanLyNhanVien(QuanLyNhanVien qlnv,Connection conn) throws Exception {
          boolean cont = true;
          do {
              System.out.println("Quản lý nhân viên");
              System.out.println("1.Xem danh sách nhân viên");
              System.out.println("2.Tra cứu nhân viên");
              System.out.println("3.Thêm nhân vien");
              System.out.println("4.cập nhập nhân viên");
              System.out.println("5.Xóa nhân viên");
              System.out.println("6.Tổ chức sinh nhat: ");
              System.out.println("7.Thoát chức năng quản lý nhân viên");
              System.out.println(" -> Chọn chức năng : [1-6]");
              int chon = scanner.nextInt();
              switch (chon) {
              case 1:
                System.out.println(" Thực hiện chức năng 1:");
                qlnv.hienThiDanhSach(conn);
                break;
              case 2:
                System.out.println("Thực hiện chức năng 2");
                System.out.println("Nhập từ khóa cần tìm: ");
                scanner.nextLine();
                String tuKhoa = scanner.nextLine();
                qlnv.traCuuNhanVien(tuKhoa, conn);
                break;
              case 3:
                System.out.println("Thực hiện chức năng 3");
                NhanVien nv = new NhanVien();
                nv.nhapThongTin(scanner);
                qlnv.themNhanVien(nv, conn);
                break;
              case 4:
                System.out.println("Thực hiện chức năng 4");
                NhanVien nvcp = new NhanVien();
                qlnv.capNhanNhanVien(nvcp, conn);
                break;
              case 5:
                System.out.println("Thực hiện chức năng 5");
                NhanVien nvx = new NhanVien();
                qlnv.xoaNhanVien(nvx, conn);
                break;
              case 6:
                System.out.println("Thực hiện chức năng 6");
                qlnv.toChucSinhNhat(conn);
                break;
              default:
                System.out.println("Đã thoát chức năng");
                cont = false;
                break;
              }
          } while (cont);
      }
        public static void menuQuanLyBan(QuanLyBan qlbBan,Connection conn) throws SQLException, ClassNotFoundException,
        ParseException {
          boolean cont = true;
          do {
            System.out.println("Quản lý Bàn");
            System.out.println("1.Xem danh sách bàn trống");
            System.out.println("2.Thêm bàn");
            System.out.println("3.Xóa thông tin bàn");
            System.out.println("4.Cập nhật thông tin bàn");
            System.out.println("5.Tìm bàn theo sức chứa");
            System.out.println("6.Thoát chức năng quản lý bàn");
            System.out.println(" -> Chọn chức năng : [1-6]");
            int chon = scanner.nextInt();
            switch (chon) {
            case 1:
              System.out.println(" Thực hiện chức năng 1:");
              qlbBan.hienThiBanTrong(conn);
              break;
            case 2:
              System.out.println("Thực hiện chức năng 2");
              System.out.println("Nhập từ khóa cần tìm: ");
              scanner.nextLine();
              Ban b = new Ban();
              b.nhapBan(scanner);
              qlbBan.themBan(b, conn);
              break;
            case 3:
              System.out.println("Thực hiện chức năng 3");
              System.out.println("Nhập mã bàn cần xóa: ");
              scanner.nextLine();
              String maBan = scanner.nextLine();
              qlbBan.xoaBan(conn,maBan);
              break;
            case 4:
              System.out.println("Thực hiện chức năng 4");
              System.out.println("nhập mã bàn cần cập nhật");
              scanner.nextLine();
              String mbCapNhat = scanner.nextLine();
              qlbBan.capNhanBan(conn,mbCapNhat);
              break;
            case 5:
              System.out.println("Thực hiện chức năng 5");
              qlbBan.timKiemTheoSC(conn);
              break;
            default:
              System.out.println("Đã thoát chức năng");
              cont = false;
              break;
            }
          } while (cont);
        }
      public static void menuQuanLyMonAn(QuanLyMonAn qlmAn,Connection conn) throws SQLException, ClassNotFoundException,
      ParseException, EOFException {
              boolean cont = true;
              do {
                System.out.println("Quản lý Món Ăn");
                System.out.println("1.Thêm món ăn (thức ăn/thức uống)");
                System.out.println("2.Xóa món ăn (thức ăn/thức uống)");
                System.out.println("3.Tìm món ăn (thức ăn/thức uống)");
                System.out.println("4.Tìm món ăn (thức ăn/thức uống)");
                System.out.println("5.Sắp xếp thức ăn giảm dần");
                System.out.println("6.Sắn xếp món ăn tăng dần");
                System.out.println("7.Hiện thị tất cả món ăn");
                System.out.println(" -> Chọn chức năng : [1-6]");
                int chon = scanner.nextInt();
                switch (chon) {
                case 1:
                  System.out.println(" Thực hiện chức năng 1:");
                  qlmAn.themThucAn(conn, scanner);
                  break;
                case 2:
                  System.out.println("Thực hiện chức năng 2");
                  qlmAn.xoaThucAn(conn);
                  break;
                case 3:
                  System.out.println("Thực hiện chức năng 3");
                  boolean flag = true;
                  do {
                    System.out.println("Tìm kiếm thức ăn");
                    System.out.println("Tìm kiếm thức uống");
                    System.out.println("bấm phím khác để thoát");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice){
                        case 1: 
                            qlmAn.timKiemTheoTenTa(conn);
                            break;
                        case 2:
                          qlmAn.timKiemTheoTenTu(conn);
                            break; 
                        default:
                            System.out.println("Thoát chức năng 3");
                            flag = false;
                            break;
                        }
                          } while (flag);
                  break;
                  case 4:
                    System.out.println("Thực hiện chức năng 4");
                    System.out.println("Nhập giá từ");
                    double giaTu = scanner.nextDouble();
                    System.out.println("Nhập giá đến");
                    double giaDen = scanner.nextDouble();
                    qlmAn.timKiemTheoGia(giaTu, giaDen, conn);
                  break;
                  case 5:
                    System.out.println("Thực hiện chức năng 5");
                    qlmAn.sapXepGiamDan(conn);
                    break;
                  case 6:
                    System.out.println("Thực hiện chức năng 6");
                    qlmAn.sapXepTangDan(conn);
                    break;
                  case 7:
                    System.out.println("Thực hiện chức năng 6");
                    qlmAn.hienThiDanhSach(conn);
                    break;
                  default:
                    System.out.println("Đã thoát chức năng");
                    cont = false;
                    break;
                  }
              } while (cont);
      } 
        public static void menuDatBan(Connection conn,DatBan db,QuanLyBan qlb,QuanLyMonAn qlma) throws Exception {
            boolean cont = true;
            do {
                System.out.println("1.Dặt bàn");
                System.out.println("2.Cập nhật lại bàn khi khách trả bàn");
                System.out.println("3.Thống kê doanh thu theo tháng");
                System.out.println(" -> Chọn chức năng : [1-3]");
                int chon = scanner.nextInt();
                switch (chon) {
                  case 1:
                  System.out.println(" Thực hiện chức năng 1:");
                  qlb.hienThiBanTrong(conn);
                  db.nhapDatBan(scanner, conn, qlb, qlma);
                  break;
                  case 2:
                  System.out.println("Thực hiện chức năng 2");
                  Ban b = new Ban();
                  System.out.println("Nhập mã bàn cần cập nhật");
                  scanner.nextLine();
                  b.setMaBan(scanner.nextLine());
                  //khách trả bàn cập nhập lại bàn trống
                  db.capNhapTrangThaiDatBan(b, 0, conn);
                  break;
                  case 3:
                  System.out.println("Thực hiện chức năng 3");
                  int thang;
                  do {
                      System.out.println("Nhập tháng muốn thống kê: ");
                      scanner.nextLine();
                      thang = scanner.nextInt();
                      if(thang>13 || thang <0)
                        System.out.println("Nhập sai nhập lại");
                  }while(thang>13 || thang <0);
                  db.thongKeDoanhThuThang(conn, thang);
                  break;
                  default:
                    System.out.println("Đã thoát chức năng");
                    cont = false;
                    break;
                }
            } while (cont);
        }
        public static void main(String[] args)
            throws Exception {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
              Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf","root", "123456789");
            QuanLyNhanVien qlnv = new QuanLyNhanVien();
            QuanLyBan qlBan = new QuanLyBan();
            QuanLyMonAn qlmAn = new QuanLyMonAn();
            DatBan db = new DatBan();
            boolean cont = true;
            do {
                System.out.println("==================Cafe MÈO Ú=====================");
                System.out.println("1.Quản lý nhân viên");
                System.out.println("2.Quản lý bàn");
                System.out.println("3.Quản lý món ăn");
                System.out.println("4.Đặt bàn-quản lý doanh thu");
                System.out.println("===============================================");
                System.out.print("-> Chọn chức năng : [1-4]");
                int chon = scanner.nextInt();
                switch (chon) {
                  case 1:
                  System.out.println(" Thực hiện chức năng 1:");
                  menuQuanLyNhanVien(qlnv, conn);
                  break;
                  case 2:
                  System.out.println("Thực hiện chức năng 2");
                    menuQuanLyBan(qlBan, conn);
                  break;
                  case 3:
                    menuQuanLyMonAn(qlmAn, conn);
                  break;
                  case 4:
                    menuDatBan(conn, db, qlBan, qlmAn);
                  break;
                  default:
                    System.out.println("Đã thoát chức năng");
                    cont = false;
                    break;
                }
            } while (cont);
        }
      }
