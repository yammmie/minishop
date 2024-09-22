package ask;

import java.sql.*;//Connection, Statement, ResultSet
import java.util.*;//List,ArrayList
import javax.sql.*;//DataSource
import javax.naming.*;//lookup

public class AskDAO {
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	
	private static AskDAO dao = new AskDAO();
	
	public static AskDAO getDao() {
		return dao;
	}
	
	private AskDAO() {}
	
	private Connection getCon() throws Exception {
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}
	
	// 글 작성
	public void insertArticle(AskDTO dto){
		int num=dto.getNum();//0이면 원글쓰기, 0이 아니면 답글
		int ref=dto.getRef();//답글 그룹
		int re_step=dto.getRe_step();//글 순서
		int re_level=dto.getRe_level();//답글 깊이		
		int number=0;//글 그룸처리 하기 위한 변수
		
		try{
			con=getCon();//Connection 얻기
			
			//최대 글번호 얻기	DB 그룹함수 중 count**, max, min, avg....
			pstmt=con.prepareStatement("select max(num) from ask");
			rs=pstmt.executeQuery();//쿼리 수행
			
			if(rs.next()) {//글이 존재하는 경우
				number=rs.getInt(1)+1;//최대 글번호+1
			}else {//글이 없을 때, 첫번째 글일 때
				number=1;
			}
			
			if(num != 0) { //답글이면
				sql="update ask set re_step=re_step+1 where ref=? and re_step>?";
				
				pstmt=con.prepareStatement(sql);//PreparedStatement 생성
				//?값 채우기
				pstmt.setInt(1, ref);
				pstmt.setInt(2, re_step);
				
				pstmt.executeUpdate();//쿼리 실행
				
				re_step=re_step+1;//글순서
				re_level=re_level+1;//글 깊이
			} else{ //원글이면
				ref=number;//글 그룹번호 할당, 현재 글 번호를 ref에 넣는다
				re_step=0;
				re_level=0;
				
			}//else end
			
			sql="insert into ask (name,subject,"
					+ "passwd,regdate,readcount,ref,re_step,"
					+ "re_level,content)"+
					" values(?,?,?,NOW(),?,?,?,?,?)";
			
			pstmt=con.prepareStatement(sql);//PreparedStatement 생성
			
			//?값 채우기
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getPasswd());
			pstmt.setInt(4, dto.getReadcount());
			//날짜
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, dto.getContent());
			
			pstmt.executeUpdate();//쿼리 실행
			
		}catch(Exception ex) {
			System.out.println("insertArticle() 예외 :"+ex);
		}finally {
			try {
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			} catch(Exception exx){}
		}//finally end
	}
	
	// 글 개수
	public int getArticleCount() {
		int cnt=0;
		
		try{
			con=getCon();//커넥션 얻기
			pstmt=con.prepareStatement("select count(*) from ask");//글 갯수 얻기
			rs=pstmt.executeQuery();//쿼리 수행
			
			if(rs.next()){
				cnt=rs.getInt(1);//칼럼번호, 글 갯수 얻기
			}
		}catch(Exception ex){
			System.out.println("getArticleCount() 예외 :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
		
		return cnt;
	}
	
	// 글 목록
	public List<AskDTO> getList(int start, int cnt){
		List<AskDTO> list=null;//변수
		
		try{
			con=getCon();
			sql="select * from ask order by ref desc, re_step asc limit ?,?";
			//limit 시작 row, 몇개
			
			pstmt=con.prepareStatement(sql);//PrepareStatement 생성
			
			pstmt.setInt(1, start-1);
			pstmt.setInt(2, cnt);
			
			rs=pstmt.executeQuery();//쿼리수행
			
			if(rs.next()){
				list=new ArrayList<AskDTO>();
				
				do{
					AskDTO dto=new AskDTO();
					
					dto.setNum(rs.getInt("num"));
					dto.setName(rs.getString("name"));
					dto.setSubject(rs.getString("subject"));
					dto.setPasswd(rs.getString("passwd"));
					dto.setRegdate(rs.getDate("regdate"));
					dto.setReadcount(rs.getInt("readcount"));
					dto.setRef(rs.getInt("ref"));//글그룹
					dto.setRe_step(rs.getInt("re_step"));//글 순서
					dto.setRe_level(rs.getInt("re_level"));//글 깊이
					dto.setContent(rs.getString("content"));//글내용
					
					list.add(dto);//list에 삽입
				} while(rs.next());
			}
		}catch(Exception ex){
			System.out.println("getList() 예외 :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){rs.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
		
		return list;
	}
	
	// 글 내용 보기
	public AskDTO getArticle(int num){
		AskDTO dto=null;
		
		try{
			con=getCon();
			
			//조회수 증가
			sql="update ask set readcount=readcount+1 where num=?";
			
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.executeUpdate();//쿼리 수행
			
			//글 내용 보기를 하기 위해 쿼리 실행
			pstmt=con.prepareStatement("select * from ask where num=?");
			
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();//쿼리 수행
			
			if(rs.next()){
				dto=new AskDTO();
				
				//dto에 setter 작업
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setRegdate(rs.getDate("regdate"));//년월일
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRef(rs.getInt("ref"));
				dto.setRe_step(rs.getInt("re_step"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setContent(rs.getString("content"));
			}
		}catch(Exception ex){
			System.out.println("getArticle() 예외" +ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
		
		return dto;
	}
	
	// 글 수정 폼 -> jsp로 보낼 자료 처리
	public AskDTO getUpdateArticle(int num){
		AskDTO dto=null;
		
		try{
			
			con=getCon();
				
			pstmt=con.prepareStatement("select * from ask where num=?");
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dto=new AskDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setRegdate(rs.getDate("regdate"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRef(rs.getInt("ref"));
				dto.setRe_step(rs.getInt("re_step"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setContent(rs.getString("content"));
			}	
		}catch(Exception ex){
			System.out.println("getUpdateArticle()예외 :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){rs.close();}
				if(con != null){rs.close();}
			}catch(Exception exx){}
		}//finally end
		
		return dto;
	}
	
	// 글 수정 DB에 작업
	public int updateArticle(AskDTO dto){
		String dbPasswd="";
		int x=-1000;
		
		try{
			con=getCon();//Connection 얻기
			pstmt=con.prepareStatement("select passwd from ask where num=?");
			pstmt.setInt(1, dto.getNum());
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dbPasswd=rs.getString("passwd");
				if(dbPasswd.equals(dto.getPasswd())){
					//암호가 일치하면 수정
					sql="update ask set name=?, subject=?, content=? where num=?";
					pstmt=con.prepareStatement(sql);//PreparedStatement 생성
					
					//?값 채우기
					pstmt.setString(1, dto.getName());
					pstmt.setString(2, dto.getSubject());
					pstmt.setString(3, dto.getContent());
					pstmt.setInt(4, dto.getNum());
					
					pstmt.executeUpdate();//쿼리 수행
					
					x=1;//정상 처리
				}else{
					x=0;//암호가 틀릴 때
				}
			}//if(rs.next()){}
		}catch(Exception ex){
			System.out.println("updateArticle() 예외 :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
		
		return x;
	}
	
	// 글 삭제
	public int deleteArticle(int num, String passwd){
		int x=-100;
		String dbPasswd="";
		
		try{
			con=getCon();
			pstmt=con.prepareStatement("select passwd from ask where num=?");
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dbPasswd=rs.getString("passwd");
				
				if(dbPasswd.equals(passwd)){
					//암호 같으면 삭제
					pstmt=con.prepareStatement("delete from ask where num=?");
					pstmt.setInt(1, num);
					pstmt.executeUpdate();//쿼리 수행
					
					x=1;//정상 삭제
				}else{
					x=0;//틀린 암호
				}//else end
			}
		}catch(Exception ex){
			System.out.println("deleteArticle() 예외 :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}

		return x;
	}
}
