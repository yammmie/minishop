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
	
	// �� �ۼ�
	public void insertArticle(AskDTO dto){
		int num=dto.getNum();//0�̸� ���۾���, 0�� �ƴϸ� ���
		int ref=dto.getRef();//��� �׷�
		int re_step=dto.getRe_step();//�� ����
		int re_level=dto.getRe_level();//��� ����		
		int number=0;//�� �׷�ó�� �ϱ� ���� ����
		
		try{
			con=getCon();//Connection ���
			
			//�ִ� �۹�ȣ ���	DB �׷��Լ� �� count**, max, min, avg....
			pstmt=con.prepareStatement("select max(num) from ask");
			rs=pstmt.executeQuery();//���� ����
			
			if(rs.next()) {//���� �����ϴ� ���
				number=rs.getInt(1)+1;//�ִ� �۹�ȣ+1
			}else {//���� ���� ��, ù��° ���� ��
				number=1;
			}
			
			if(num != 0) { //����̸�
				sql="update ask set re_step=re_step+1 where ref=? and re_step>?";
				
				pstmt=con.prepareStatement(sql);//PreparedStatement ����
				//?�� ä���
				pstmt.setInt(1, ref);
				pstmt.setInt(2, re_step);
				
				pstmt.executeUpdate();//���� ����
				
				re_step=re_step+1;//�ۼ���
				re_level=re_level+1;//�� ����
			} else{ //�����̸�
				ref=number;//�� �׷��ȣ �Ҵ�, ���� �� ��ȣ�� ref�� �ִ´�
				re_step=0;
				re_level=0;
				
			}//else end
			
			sql="insert into ask (name,subject,"
					+ "passwd,regdate,readcount,ref,re_step,"
					+ "re_level,content)"+
					" values(?,?,?,NOW(),?,?,?,?,?)";
			
			pstmt=con.prepareStatement(sql);//PreparedStatement ����
			
			//?�� ä���
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getPasswd());
			pstmt.setInt(4, dto.getReadcount());
			//��¥
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, dto.getContent());
			
			pstmt.executeUpdate();//���� ����
			
		}catch(Exception ex) {
			System.out.println("insertArticle() ���� :"+ex);
		}finally {
			try {
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			} catch(Exception exx){}
		}//finally end
	}
	
	// �� ����
	public int getArticleCount() {
		int cnt=0;
		
		try{
			con=getCon();//Ŀ�ؼ� ���
			pstmt=con.prepareStatement("select count(*) from ask");//�� ���� ���
			rs=pstmt.executeQuery();//���� ����
			
			if(rs.next()){
				cnt=rs.getInt(1);//Į����ȣ, �� ���� ���
			}
		}catch(Exception ex){
			System.out.println("getArticleCount() ���� :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
		
		return cnt;
	}
	
	// �� ���
	public List<AskDTO> getList(int start, int cnt){
		List<AskDTO> list=null;//����
		
		try{
			con=getCon();
			sql="select * from ask order by ref desc, re_step asc limit ?,?";
			//limit ���� row, �
			
			pstmt=con.prepareStatement(sql);//PrepareStatement ����
			
			pstmt.setInt(1, start-1);
			pstmt.setInt(2, cnt);
			
			rs=pstmt.executeQuery();//��������
			
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
					dto.setRef(rs.getInt("ref"));//�۱׷�
					dto.setRe_step(rs.getInt("re_step"));//�� ����
					dto.setRe_level(rs.getInt("re_level"));//�� ����
					dto.setContent(rs.getString("content"));//�۳���
					
					list.add(dto);//list�� ����
				} while(rs.next());
			}
		}catch(Exception ex){
			System.out.println("getList() ���� :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){rs.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
		
		return list;
	}
	
	// �� ���� ����
	public AskDTO getArticle(int num){
		AskDTO dto=null;
		
		try{
			con=getCon();
			
			//��ȸ�� ����
			sql="update ask set readcount=readcount+1 where num=?";
			
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.executeUpdate();//���� ����
			
			//�� ���� ���⸦ �ϱ� ���� ���� ����
			pstmt=con.prepareStatement("select * from ask where num=?");
			
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();//���� ����
			
			if(rs.next()){
				dto=new AskDTO();
				
				//dto�� setter �۾�
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setRegdate(rs.getDate("regdate"));//�����
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRef(rs.getInt("ref"));
				dto.setRe_step(rs.getInt("re_step"));
				dto.setRe_level(rs.getInt("re_level"));
				dto.setContent(rs.getString("content"));
			}
		}catch(Exception ex){
			System.out.println("getArticle() ����" +ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
		
		return dto;
	}
	
	// �� ���� �� -> jsp�� ���� �ڷ� ó��
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
			System.out.println("getUpdateArticle()���� :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){rs.close();}
				if(con != null){rs.close();}
			}catch(Exception exx){}
		}//finally end
		
		return dto;
	}
	
	// �� ���� DB�� �۾�
	public int updateArticle(AskDTO dto){
		String dbPasswd="";
		int x=-1000;
		
		try{
			con=getCon();//Connection ���
			pstmt=con.prepareStatement("select passwd from ask where num=?");
			pstmt.setInt(1, dto.getNum());
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dbPasswd=rs.getString("passwd");
				if(dbPasswd.equals(dto.getPasswd())){
					//��ȣ�� ��ġ�ϸ� ����
					sql="update ask set name=?, subject=?, content=? where num=?";
					pstmt=con.prepareStatement(sql);//PreparedStatement ����
					
					//?�� ä���
					pstmt.setString(1, dto.getName());
					pstmt.setString(2, dto.getSubject());
					pstmt.setString(3, dto.getContent());
					pstmt.setInt(4, dto.getNum());
					
					pstmt.executeUpdate();//���� ����
					
					x=1;//���� ó��
				}else{
					x=0;//��ȣ�� Ʋ�� ��
				}
			}//if(rs.next()){}
		}catch(Exception ex){
			System.out.println("updateArticle() ���� :"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}
		
		return x;
	}
	
	// �� ����
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
					//��ȣ ������ ����
					pstmt=con.prepareStatement("delete from ask where num=?");
					pstmt.setInt(1, num);
					pstmt.executeUpdate();//���� ����
					
					x=1;//���� ����
				}else{
					x=0;//Ʋ�� ��ȣ
				}//else end
			}
		}catch(Exception ex){
			System.out.println("deleteArticle() ���� :"+ex);
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
