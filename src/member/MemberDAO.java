package member;

import java.sql.*;

import javax.sql.*; // DataSource
import javax.naming.*; // lookup

public class MemberDAO {
	// 싱글톤 객체 사용, 객체를 하나만 사용하도록 함 → 메모리 절약이 됨
	private static MemberDAO instance = new MemberDAO();
	
	// JSP에서 객체를 얻을 때 : MemberDAO.getInstance() → static 메소드
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private MemberDAO() {}  // 디폴트 생성자 - 외부에서 생성 X
	
	// 커넥션풀 메소드 사용 정의
	private Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
//		con = ds.getConnection();
		
		return ds.getConnection();
	}
	
	// ID 중복 체크
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
			
			if(rs.next()) { // 사용 중인 id
				x = 1;
			} else { // 사용 가능한 id
				x = -1;
			}
		} catch(Exception e) {
			System.out.println("confirmId() 예외 : " + e);
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
	
	// 회원가입
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
			System.out.println("insertMember() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// 로그인(인증)
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
			
			if(rs.next()) { // 아이디 존재
				dbPasswd = rs.getString("passwd");
				
				if(dbPasswd.equals(passwd)) { // 비밀번호 일치
					x = 1; // 인증 성공
				} else { // 비밀번호 틀린 경우
					x = 0;
				}
			} else { // 아이디 존재 X
				x = -1;
			}
		} catch(Exception e) {
			System.out.println("userCheck() 예외 : " + e);
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
	
	// 회원 정보 수정 (웹에 출력)
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
			System.out.println("getMember() 예외 : " + e);
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
	
	// DB 회원정보 수정
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
			System.out.println("updateMember() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// 회원탈퇴
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
				
				if(dbPasswd.equals(passwd)) { // 비밀번호가 일치하면
					pstmt2 = con.prepareStatement("delete from member where id=?");
					pstmt2.setString(1, id);
					
					pstmt2.executeUpdate();
					
					x = 1; // 회원 탈퇴 성공
				} else {
					x = -1; // 비밀번호가 틀리면
				}
			} else {
				x = 0; // 아이디가 존재하지 않는 경우
			}
		} catch(Exception e) {
			System.out.println("deleteMember() 예외 : " + e);
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
