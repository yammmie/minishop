package member;

import java.sql.*;

import javax.sql.*; // DataSource
import javax.naming.*; // lookup

public class MemberDAO {
	// �̱��� ��ü ���, ��ü�� �ϳ��� ����ϵ��� �� �� �޸� ������ ��
	private static MemberDAO instance = new MemberDAO();
	
	// JSP���� ��ü�� ���� �� : MemberDAO.getInstance() �� static �޼ҵ�
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private MemberDAO() {}  // ����Ʈ ������ - �ܺο��� ���� X
	
	// Ŀ�ؼ�Ǯ �޼ҵ� ��� ����
	private Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
//		con = ds.getConnection();
		
		return ds.getConnection();
	}
	
	// ID �ߺ� üũ
	public int confirmId(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;
		
		try {
			con = getCon();
			pstmt = con.prepareStatement("select id from member where id=?");
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // ��� ���� id
				x = 1;
			} else { // ��� ������ id
				x = -1;
			}
		} catch(Exception e) {
			System.out.println("confirmId() ���� : " + e);
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
		
		return x;
	}
	
	// ȸ������
	public void insertMember(MemberDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getCon();
			pstmt = con.prepareStatement("insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPasswd());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getAddr1());
			pstmt.setString(5, dto.getAddr2());
			pstmt.setString(6, dto.getTel());
			pstmt.setString(7, dto.getEmail1());
			pstmt.setString(8, dto.getEmail2());
			pstmt.setString(9, dto.getZipcode());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("insertMember() ���� : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// �α���(����)
	public int userCheck(String id, String passwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbPasswd = "";
		int x = -1;
		
		try {
			con = getCon();
			pstmt = con.prepareStatement("select * from member where id=?");
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // ���̵� ����
				dbPasswd = rs.getString("passwd");
				
				if(dbPasswd.equals(passwd)) { // ��й�ȣ ��ġ
					x = 1; // ���� ����
				} else { // ��й�ȣ Ʋ�� ���
					x = 0;
				}
			} else { // ���̵� ���� X
				x = -1;
			}
		} catch(Exception e) {
			System.out.println("userCheck() ���� : " + e);
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
		
		return x;
	}
	
	// ȸ�� ���� ���� (���� ���)
	public MemberDTO getMember(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDTO dto = null;
		
		try {
			con = getCon();
			pstmt = con.prepareStatement("select * from member where id=?");
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail1(rs.getString("email1"));
				dto.setEmail2(rs.getString("email2"));
				dto.setZipcode(rs.getString("zipcode"));
			}
		} catch(Exception e) {
			System.out.println("getMember() ���� : " + e);
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
		
		return dto;
	}
	
	// DB ȸ������ ����
	public void updateMember(MemberDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getCon();
			String sql="update member set name=?,passwd=?,addr1=?,addr2=?,tel=?,email1=?,email2=?,zipcode=? where id=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getPasswd());
			pstmt.setString(3, dto.getAddr1());
			pstmt.setString(4, dto.getAddr2());
			pstmt.setString(5, dto.getTel());
			pstmt.setString(6, dto.getEmail1());
			pstmt.setString(7, dto.getEmail2());
			pstmt.setString(8, dto.getZipcode());
			pstmt.setString(9, dto.getId());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("updateMember() ���� : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// ȸ��Ż��
	public int deleteMember(String id, String passwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String dbPasswd = "";
		int x = -100;
		
		try {
			con = getCon();
			pstmt = con.prepareStatement("select passwd from member where id=?");
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPasswd = rs.getString("passwd");
				
				if(dbPasswd.equals(passwd)) { // ��й�ȣ�� ��ġ�ϸ�
					pstmt2 = con.prepareStatement("delete from member where id=?");
					pstmt2.setString(1, id);
					
					pstmt2.executeUpdate();
					
					x = 1; // ȸ�� Ż�� ����
				} else {
					x = -1; // ��й�ȣ�� Ʋ����
				}
			} else {
				x = 0; // ���̵� �������� �ʴ� ���
			}
		} catch(Exception e) {
			System.out.println("deleteMember() ���� : " + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				
				if(pstmt != null)
					pstmt.close();
				
				if(pstmt2 != null)
					pstmt2.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return x;
	}
}
