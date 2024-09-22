package user;

import java.sql.*; // Connection, Statememt, PreparedStatement, ResultSet
import java.util.*;
import javax.sql.*; // DataSource
import javax.naming.*; // lookup

// 주문 처리
public class OrderMgr {
	public OrderMgr() {}
	private static OrderMgr dao = new OrderMgr();
	
	// 커넥션 풀 사용
	public Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		
		return ds.getConnection();
	}
	
	public static OrderMgr getDao() {
		return dao;
	}
	
	// 새 주문을 추가할 때 넣기 위해 이전까지 작성된 ordno(주문번호) 받아오기
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
			System.out.println("getOrdno() 예외 : " + e);
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
	
	// 주문 추가 - 1개 주문
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
			System.out.println("insertOrder() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// 주문 추가 - 여러개 주문 (장바구니)
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
			System.out.println("insertOrders() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// 클라이언트의 모든 주문 목록 얻기
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
			
			// 날짜 : 최신순
			// 상태별로
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
			System.out.println("getUserOrder() 예외 : " + e);
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
	
	// 모든 주문 정보 (관리자)
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
			System.out.println("getOrderList() 예외 : " + e);
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
	
	// 주문번호마다 주문한 상품 체크 (관리자)
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
			System.out.println("getOrdnoCount() 예외 : " + e);
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
	
	// 주문처리상태 수정 (관리자)
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
			System.out.println("updateState() 예외 : " + e);
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
	
	// 주문번호마다 주문한 상품 체크
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
			System.out.println("getOrdnoCount() 예외 : " + e);
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
	
	// 해당하는 ordno row 가져오기
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
			System.out.println("getOrderDetail() 예외 : " + e);
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
