package notice;

import java.sql.*; // Connection, Statement, ResultSet
import java.util.*; // List, ArrayList (중복 X, 순서 O) 
import javax.sql.*; // DataSource
import javax.naming.*; // lookup

public class NoticeDAO {
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	
	// 싱글톤 객체 생성, 메모리 절약이 된다
	private static NoticeDAO dao = new NoticeDAO();
	
	// jsp에서 dao 객체 얻기
	public static NoticeDAO getDao() {
		return dao;
	}
	
	// 디폴트 생성자, private한 이유 : 외부에서 객체 생성 못하게 하기 위해서
	// <jsp:useBean> 사용 불가능
	private NoticeDAO() {}
	
	// 커넥션 얻기
	private Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		
		return ds.getConnection();
	}
	
	// 원글, 답글 쓰기
	public void insertArticle(NoticeDTO dto) {
		int num = dto.getNum(); // 0이면 원글, 0이 아니면 답글
		
		try {
			con = getCon();
			
			sql = "insert into notice (subject, content, regdate) values(?, ?, curdate())";
			
			// NOW() : 년월일 시분초  mysql
			// curdate() : 년월일  mysql
			// sysdate : oracle
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());

			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("insertArticle() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// 글 개수
	public int getArticleCount() {
		int cnt = 0;
		
		try {
			con = getCon();
			pstmt = con.prepareStatement("select count(*) from notice"); // 글 개수 얻음
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1); // 1: 컬럼 번호, 글 개수 → count(*), 필드가 1개이기 때문
			}
		} catch(Exception e) {
			System.out.println("getArticleCount() 예외 : " + e);
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
		
		return cnt;
	}
	
	// 리스트, 글 목록
	public List getList(int start, int cnt) {
		List<NoticeDTO> list = null;
		
		try {
			con = getCon();
			sql = "select * from notice order by num desc limit ?, ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, start-1);
			pstmt.setInt(2, cnt);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new ArrayList<NoticeDTO>();
				
				do { // if문에서 이미 rs.next()가 됐기 때문에 while 사용 X
					NoticeDTO dto = new NoticeDTO();
					
					dto.setNum(rs.getInt("num"));
					dto.setRegdate(rs.getDate("regdate"));
					dto.setSubject(rs.getString("subject"));
					dto.setContent(rs.getString("content"));
					
					list.add(dto);
					
					//System.out.println("getDate : " + rs.getDate("regdate")); // 2019-05-01
				} while(rs.next());
			}
		} catch(Exception e) {
			System.out.println("getList() 예외 : " + e);
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
		
		return list;
	}
	
	// 글 내용 보기
	public NoticeDTO getArticle(int num) {
		NoticeDTO dto = null;
		
		try {
			con = getCon();
			
			// 글 내용 보기
			pstmt = con.prepareStatement("select * from notice where num=?");
			
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setRegdate(rs.getDate("regdate"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
			}
		} catch(Exception e) {
			System.out.println("getArticle() 예외 : " + e);
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
	
	// 글 수정 폼 → jsp로 보낼 자료 처리
	public NoticeDTO getUpdateNotice(int num) {
		NoticeDTO dto = null;
		
		try {
			con = getCon();
			pstmt = con.prepareStatement("select * from notice where num=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setRegdate(rs.getDate("regdate"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
			}
		} catch(Exception e) {
			System.out.println("getUpdateNotice() 예외 : " + e);
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
	
	// 글 수정 DB에 작업
	public void updateArticle(NoticeDTO dto) {
		try {
			con = getCon();
			
			sql = "update notice set regdate=curdate(), subject=?, content=? where num=?";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNum());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("updateArticle() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// 글 삭제
	public void deleteArticle(int num) {
		try {
			con = getCon();
			
			pstmt = con.prepareStatement("delete from notice where num=?");
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("deleteArticle() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
}
