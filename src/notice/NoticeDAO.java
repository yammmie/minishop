package notice;

import java.sql.*; // Connection, Statement, ResultSet
import java.util.*; // List, ArrayList (�ߺ� X, ���� O) 
import javax.sql.*; // DataSource
import javax.naming.*; // lookup

public class NoticeDAO {
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	
	// �̱��� ��ü ����, �޸� ������ �ȴ�
	private static NoticeDAO dao = new NoticeDAO();
	
	// jsp���� dao ��ü ���
	public static NoticeDAO getDao() {
		return dao;
	}
	
	// ����Ʈ ������, private�� ���� : �ܺο��� ��ü ���� ���ϰ� �ϱ� ���ؼ�
	// <jsp:useBean> ��� �Ұ���
	private NoticeDAO() {}
	
	// Ŀ�ؼ� ���
	private Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		
		return ds.getConnection();
	}
	
	// ����, ��� ����
	public void insertArticle(NoticeDTO dto) {
		int num = dto.getNum(); // 0�̸� ����, 0�� �ƴϸ� ���
		
		try {
			con = getCon();
			
			sql = "insert into notice (subject, content, regdate) values(?, ?, curdate())";
			
			// NOW() : ����� �ú���  mysql
			// curdate() : �����  mysql
			// sysdate : oracle
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());

			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("insertArticle() ���� : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// �� ����
	public int getArticleCount() {
		int cnt = 0;
		
		try {
			con = getCon();
			pstmt = con.prepareStatement("select count(*) from notice"); // �� ���� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1); // 1: �÷� ��ȣ, �� ���� �� count(*), �ʵ尡 1���̱� ����
			}
		} catch(Exception e) {
			System.out.println("getArticleCount() ���� : " + e);
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
	
	// ����Ʈ, �� ���
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
				
				do { // if������ �̹� rs.next()�� �Ʊ� ������ while ��� X
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
			System.out.println("getList() ���� : " + e);
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
	
	// �� ���� ����
	public NoticeDTO getArticle(int num) {
		NoticeDTO dto = null;
		
		try {
			con = getCon();
			
			// �� ���� ����
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
			System.out.println("getArticle() ���� : " + e);
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
	
	// �� ���� �� �� jsp�� ���� �ڷ� ó��
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
			System.out.println("getUpdateNotice() ���� : " + e);
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
	
	// �� ���� DB�� �۾�
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
			System.out.println("updateArticle() ���� : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
	}
	
	// �� ����
	public void deleteArticle(int num) {
		try {
			con = getCon();
			
			pstmt = con.prepareStatement("delete from notice where num=?");
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("deleteArticle() ���� : " + e);
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
