package user;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

public class CartMgr {
	private static CartMgr dao = new CartMgr();
	
	private List<CartDTO> list = new ArrayList<CartDTO>();
	
	public static CartMgr getDao() {
		return dao;
	}
	
	public CartMgr() {}
	
	private Connection getCon() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}
	
	// ��ٱ��� ���
	public List<CartDTO> getCartList(String id) {
		String sql="";
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		List<CartDTO> list=new ArrayList<CartDTO>();
		
		try{
			con=getCon();
			sql="select * from cart where id='"+id+"'";
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				CartDTO dto = new CartDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setName(rs.getString("name"));
				dto.setQuantity (rs.getString("quantity"));
				dto.setImage(rs.getString("image"));
				dto.setPrice(rs.getInt("price"));;
				dto.setColor(rs.getString("color"));
				dto.setSize(rs.getString("size"));
				dto.setId(rs.getString("id"));
				
				list.add(dto);
			}
			 
			}catch(Exception ex){
				System.out.println("getCartList()����:"+ex);
			}finally{
				try{
					if(rs != null){rs.close();}
					if(stmt != null){stmt.close();}
					if(con != null){con.close();}
				}catch(Exception exx){}
		}//finally
		
		return list;
	}
	
	// ��ٱ��� �߰�
	public void insertCart(CartDTO cartDto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			con = getCon();
			sql = "insert into cart(num, pro_no, name, quantity, image, price, color, size, id) values(0, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, cartDto.getPro_no());
			pstmt.setString(2, cartDto.getName());
			pstmt.setString(3, cartDto.getQuantity());
			pstmt.setString(4, cartDto.getImage());
			pstmt.setInt(5, cartDto.getPrice());
			pstmt.setString(6, cartDto.getColor());
			pstmt.setString(7, cartDto.getSize());
			pstmt.setString(8, cartDto.getId());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("insertCart() ���� : " + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// ��ٱ��� ������Ʈ
	public void updateCart(int num, String quantity) {
		Connection con=null;
		PreparedStatement pstmt=null;

		try{
			con=getCon();
			String sql="update cart set quantity=? where num=?";
			
			pstmt=con.prepareStatement(sql);
			
			//?�� ä���
			pstmt.setString(1, quantity);
			pstmt.setInt(2, num);
			
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("updateCart() ����"+ex);
		}finally{
			try{
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
	}
	
	// ��ٱ��� ����
	public void deleteCart(int num) {
		Connection con=null;
		PreparedStatement pstmt=null;

		try{
			con=getCon();
			pstmt=con.prepareStatement("delete from cart where num=?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();					
		} catch(Exception ex){
			System.out.println("deleteCart() ����:"+ex);
		} finally{
			try{
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
	}
	
	// ��ٱ��� �ʱ�ȭ
	public void deleteAllCart() {
		Connection con = null;
		Statement stmt = null;

		try{
			con = getCon();
			stmt = con.createStatement();
			stmt.executeUpdate("delete from cart");
		} catch(Exception ex){
			System.out.println("deleteAllCart() ����:"+ex);
		} finally{
			try{
				if(stmt != null){stmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
	}
}
