package user;

import java.sql.*; // Connection, Statememt, PreparedStatement, ResultSet
import java.util.*;
import javax.sql.*; // DataSource
import javax.naming.*; // lookup

// �ֹ� ó��
public class OrderMgr {
	public OrderMgr() {}
	private static OrderMgr dao = new OrderMgr();
	
	// Ŀ�ؼ� Ǯ ���
	public Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		
		return ds.getConnection();
	}
	
	public static OrderMgr getDao() {
		return dao;
	}
	
	// �� �ֹ��� �߰��� �� �ֱ� ���� �������� �ۼ��� ordno(�ֹ���ȣ) �޾ƿ���
	public int getOrdno() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int num = 0;
		
		try {
			con = getCon();
			stmt = con.createStatement();
			sql = "select distinct ordno from orderlist";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				num = rs.getInt("ordno");
			}
		} catch(Exception e) {
			System.out.println("getOrdno() ���� : " + e);
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return num;
	}
	
	// �ֹ� �߰� - 1�� �ֹ�
	public void insertOrder(OrderDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			con = getCon();
			sql = "insert into orderlist(ordno, pro_no, quantity, orddate, state, id, color, size, payment) values("
					+ "?, ?, ?, now(), 1, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getOrdno());
			pstmt.setInt(2, dto.getPro_no());
			pstmt.setInt(3, dto.getQuantity());
			pstmt.setString(4, dto.getId());
			pstmt.setString(5, dto.getColor());
			pstmt.setString(6, dto.getSize());
			pstmt.setString(7, dto.getPayment());
				  		
	  		pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("insertOrder() ���� : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// �ֹ� �߰� - ������ �ֹ� (��ٱ���)
	public void insertOrders(OrderDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			con = getCon();
			sql = "insert into orderlist(ordno, pro_no, quantity, orddate, state, id, color, size, payment) values("
					+ "?, ?, ?, now(), 1, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getOrdno()); 
			pstmt.setInt(2, dto.getPro_no());
			pstmt.setInt(3, dto.getQuantity());
			pstmt.setString(4, dto.getId());
			pstmt.setString(5, dto.getColor());
			pstmt.setString(6, dto.getSize());
			pstmt.setString(7, dto.getPayment());
				  		
	  		pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("insertOrders() ���� : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// Ŭ���̾�Ʈ�� ��� �ֹ� ��� ���
	public Vector<OrderDTO> getUserOrder(String id, String state, String sdate, String edate) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Vector<OrderDTO> vec = new Vector<OrderDTO>();
		OrderDTO dto = null;
		
		try {
			con = getCon();
			stmt = con.createStatement();
			
			// ��¥ : �ֽż�
			// ���º���
			if(state.equals("0"))
				sql = "select * from orderlist where id='"+id+"' and orddate between '"+sdate+"' and date('"+edate+"'+interval 1 day) order by ordno desc, orddate desc";
			else
				sql = "select * from orderlist where id='"+id+"' and state='"+state+"' and orddate between '"+sdate+"' and date('"+edate+"'+interval 1 day) order by ordno desc, orddate desc";
			
			rs = stmt.executeQuery(sql);
			
			
			while(rs.next()) {
				dto = new OrderDTO();
				
				dto.setOrdno(rs.getInt("ordno"));
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setQuantity(rs.getInt("quantity"));
				dto.setOrddate(rs.getTimestamp("orddate"));
				dto.setState(rs.getString("state"));
				dto.setId(rs.getString("id"));
				dto.setColor(rs.getString("color"));
				dto.setSize(rs.getString("size"));
				dto.setPayment(rs.getString("payment"));
				
				vec.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getUserOrder() ���� : " + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				
				if(stmt != null)
					stmt.close();
			
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return vec;
	}
	
	// ��� �ֹ� ���� (������)
	public Vector<OrderDTO> getOrderList() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Vector<OrderDTO> vec = new Vector<OrderDTO>();
		OrderDTO dto = null;
		
		try {
			con = getCon();
			sql = "select * from orderlist order by ordno desc, orddate desc";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new OrderDTO();
				
				dto.setOrdno(rs.getInt("ordno"));
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setQuantity(rs.getInt("quantity"));
				dto.setOrddate(rs.getTimestamp("orddate"));
				dto.setState(rs.getString("state"));
				dto.setId(rs.getString("id"));
				dto.setColor(rs.getString("color"));
				dto.setSize(rs.getString("size"));
				dto.setPayment(rs.getString("payment"));
	  			
	  			vec.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getOrderList() ���� : " + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return vec;
	}
	
	// �ֹ���ȣ���� �ֹ��� ��ǰ üũ (������)
	public Vector<OrderDTO> getAllOrdnoCount() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Vector<OrderDTO> vec = new Vector<OrderDTO>();
		OrderDTO dto = null;
		
		try {
			con = getCon();
			sql = "select ordno, orddate, count(*) from orderlist group by ordno order by ordno desc";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new OrderDTO();
				
				dto.setOrdno(rs.getInt("ordno"));
				dto.setOrddate(rs.getTimestamp("orddate"));
				dto.setCnt(rs.getInt("count(*)"));
	  			
	  			vec.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getOrdnoCount() ���� : " + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return vec;
	}
	
	// �ֹ�ó������ ���� (������)
	public void updateState(int ordno, String state) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			con = getCon();
			sql = "update orderlist set state=? where ordno=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, state);
			pstmt.setInt(2, ordno);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("updateState() ���� : " + e);
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
	
	// �ֹ���ȣ���� �ֹ��� ��ǰ üũ
	public Vector<OrderDTO> getOrdnoCount(String id) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Vector<OrderDTO> vec = new Vector<OrderDTO>();
		OrderDTO dto = null;
		
		try {
			con = getCon();
			sql = "select ordno, orddate, count(*) from orderlist where id='"+id+"' group by ordno order by ordno desc";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new OrderDTO();
				
				dto.setOrdno(rs.getInt("ordno"));
				dto.setOrddate(rs.getTimestamp("orddate"));
				dto.setCnt(rs.getInt("count(*)"));
	  			
	  			vec.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getOrdnoCount() ���� : " + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return vec;
	}
	
	// �ش��ϴ� ordno row ��������
	public Vector<OrderDTO> getOrderDetail(int ordno) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Vector<OrderDTO> vec = new Vector<OrderDTO>();
		OrderDTO dto = null;
		
		try {
			con = getCon();
			sql = "select * from orderlist where ordno="+ordno+" order by orddate desc";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new OrderDTO();
				
				dto.setOrdno(rs.getInt("ordno"));
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setQuantity(rs.getInt("quantity"));
				dto.setOrddate(rs.getTimestamp("orddate"));
				dto.setState(rs.getString("state"));
				dto.setId(rs.getString("id"));
				dto.setColor(rs.getString("color"));
				dto.setSize(rs.getString("size"));
				dto.setPayment(rs.getString("payment"));
	  			
	  			vec.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getOrderDetail() ���� : " + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return vec;
	}	
}
