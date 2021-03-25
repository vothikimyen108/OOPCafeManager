package com.mycompany.quanlyquancf;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Statement;
/**
 * QuanLyBoPhan
 */
public class QuanLyBoPhan {
	private List<BoPhan> dsBoPhan = new ArrayList<>();
    public List<BoPhan> getDsBoPhan() {
        return dsBoPhan;
    }

    public void setDsBoPhan(List<BoPhan> dsBoPhan) {
        this.dsBoPhan = dsBoPhan;
	}
	public int getDem(Connection conn) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf","root", "123456789");
        Statement  stm = conn.createStatement();
        ResultSet rs1 = stm.executeQuery("Select * from bophan");//trả về resultset
        int dem = 0;
        while(rs1.next()){
           		 dem++;
		}
		return dem;
	}
    public void themBoPhan(BoPhan bp, Connection conn) throws ClassNotFoundException, SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf","root", "123456789");
        Statement  stm = conn.createStatement();
        boolean flag = true;    String ten = bp.getTenBoPhan();
        ResultSet rs1 = stm.executeQuery("Select * from bophan");//trả về resultset
        int dem = 0;
        while(rs1.next()){
           		 dem++;
		}
		if(dem<=0) {
           stm.executeUpdate("INSERT INTO bophan(tenBP)   VALUES('"+ten+"');");
		   String sql = "SELECT * FROM bophan WHERE tenBP like concat(?)";
		   PreparedStatement stm1 = conn.prepareStatement(sql);
		   stm1.setString(1, ten);
		   ResultSet rs2 = stm1.executeQuery();
		   while(rs2.next()){
			   String name = rs2.getString("tenBP");
			   int id = rs2.getInt("mbp");
			   bp.setMaBoPhan(id);
			   bp.setTenBoPhan(name);
		   }
		} else {
			ResultSet rs = stm.executeQuery("Select * from bophan");//trả về resultset
            while(rs.next()){
				String name = rs.getString("tenBP");
				int id = rs.getInt("mbp");
                if(id==bp.getMaBoPhan()) {
					bp.setMaBoPhan(id);
					bp.setTenBoPhan(name);
					flag = false;
                }
            }
            if(flag){
				stm.executeUpdate("INSERT INTO bophan(tenBP)   VALUES(+'"+ten+"');");
				String sql = "SELECT * FROM bophan WHERE mbp like concat(?)";
				PreparedStatement stm1 = conn.prepareStatement(sql);
				stm1.setInt(1, bp.getMaBoPhan());
				ResultSet rs2 = stm1.executeQuery();
				while(rs2.next()){
					String name = rs2.getString("tenBP");
					int id = rs2.getInt("mbp");
					bp.setMaBoPhan(id);
					bp.setTenBoPhan(name);
				}	
            }
        }
	}
	public void show(Connection conn) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf","root", "123456789");
		System.out.println("Danh sách bộ phận");
		Statement  stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("Select * from bophan");//trả về resultset
		while(rs.next()){
			String name = rs.getString("tenBP");
			int id = rs.getInt("mbp");
			System.out.printf("%d: %s \n", id,name);
		}
	}
}