package admin;

import java.sql.*;
import java.util.*;

import user.*;

import javax.sql.*; // DataSource - Ŀ�ؼ� Ǯ ���
import javax.naming.*; // lookup - Ŀ�ؼ� Ǯ ���

public class CategoryMgr {
	private CategoryMgr() {}
	private static CategoryMgr dao = new CategoryMgr();
	
	public static CategoryMgr getDao() {
		return dao;
	}
	
	private Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		
		return ds.getConnection();
	}
	
	// ���� ī�װ� �б� - topcate	
	public List<CategoryDTO> getTopcateList() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		CategoryDTO dto = null;
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		
		try {
			con = getCon();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from topcate order by topcate_idx");
			
			while(rs.next()) {
				dto = new CategoryDTO();
				
				dto.setTopcate_idx(rs.getInt("topcate_idx"));
				dto.setTopcate_title(rs.getString("title"));
				
				list.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getTopcateList() ���� : " + e);
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
		
		return list;
	}
	
	// ���� ī�װ� idx�� �̸� ��������
	public String getTopcateTitle(int idx) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String title = "";
		
		try {
			con = getCon();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select title from topcate where topcate_idx="+idx);
			
			if(rs.next()) {
				title = rs.getString("title");
			}
		} catch(Exception e) {
			System.out.println("getTopcateTitle() ���� : " + e);
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
		
		return title;
	}
	
	// ���� ī�װ� �߰� - topcate
	public void insertTopcate(String title) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			con = getCon();
			sql = "insert into topcate(topcate_idx, title) values(0, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, title);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("insertTopcate() ���� : " + e);
		} finally {
			try {				
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// ���� ī�װ� �̸� ���� - topcate
	public void updateTopcate(String title, int idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			con = getCon();
			sql = "update topcate set title=? where topcate_idx=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setInt(2, idx);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("updateTopcate() ���� : " + e);
		} finally {
			try {				
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// ���� ī�װ� ���� - topcate
	public void deleteTopcate(int idx) {
		Connection con = null;
		Statement stmt = null;
		String sql = "";
		
		try {
			con = getCon();
			stmt = con.createStatement();
			sql = "delete from topcate where topcate_idx="+idx;
			stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("deleteTopcate() ���� : " + e);
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// ���� ī�װ� �б� - subcate
	public List<CategoryDTO> getSubcateList(int idx) { // idx : topcate_idx
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		CategoryDTO dto = null;
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		
		try {
			con = getCon();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select subcate_idx, title from subcate where topcate_idx="+idx+" order by topcate_idx, subcate_idx");
			
			while(rs.next()) {
				dto = new CategoryDTO();
				
				dto.setTopcate_idx(idx);
				dto.setSubcate_idx(rs.getInt("subcate_idx"));
				dto.setSubcate_title(rs.getString("title"));
				
				list.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getSubcateList() ���� : " + e);
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
		
		return list;
	}
	
	// ���� ī�װ� �߰� - subcate
	public void insertSubcate(CategoryDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			con = getCon();
			sql = "insert into subcate(topcate_idx, subcate_idx, title) values(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getTopcate_idx());
			pstmt.setInt(2, dto.getSubcate_idx());
			pstmt.setString(3, dto.getSubcate_title());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("insertSubcate() ���� : " + e);
		} finally {
			try {				
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// ���� ī�װ� �̸� ���� - subcate
	public void updateSubcate(CategoryDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			con = getCon();
			sql = "update subcate set title=? where subcate_idx=? and topcate_idx=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubcate_title());
			pstmt.setInt(2, dto.getSubcate_idx());
			pstmt.setInt(3, dto.getTopcate_idx());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("updateSubcate() ���� : " + e);
		} finally {
			try {				
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// ���� ī�װ� ���� - subcate
	public void deleteSubcate(int topidx, int subidx) {
		Connection con = null;
		Statement stmt = null;
		String sql = "";
		
		try {
			con = getCon();
			stmt = con.createStatement();
			sql = "delete from subcate where topcate_idx="+topidx+" and subcate_idx="+subidx;
			stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("deleteSubcate() ���� : " + e);
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// �ش� ���� ī�װ��� ������ idx ��� - �߰����� ���
	public int getInsertSubcate_idx(int topidx) {
		int idx = 0;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			con = getCon();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select subcate_idx from subcate where topcate_idx="+topidx+" order by subcate_idx desc limit 1");
			
			if(rs.next()) {
				idx = rs.getInt("subcate_idx");
			}
			
			idx ++;
		} catch(Exception e) {
			System.out.println("getInsertSubcate_idx() ���� : " + e);
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
		
		return idx;
	}
}
