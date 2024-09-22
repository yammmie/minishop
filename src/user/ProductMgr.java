package user;

import java.sql.*; // Connection, PreparedStatement
import java.util.*; // List, Vector ... 

import javax.sql.*; // DataSource - 커넥션 풀 사용
import javax.naming.*; // lookup - 커넥션 풀 사용

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

import javax.servlet.http.*; // 서블릿

import java.io.*; // 그림 파일을 삭제하기 위해서

// 파일 업로드 - cos.jar
// jdk/jre/lib/ext/cos.jar
// Tomcat8/lib/cos.jar
// 환경변수 - classpath: .;%JAVA_HOME%\lib\tools.jar;C:\cos-20.08\lib\cos.jar

// 현재 프로젝트 WEB-INF/lib/cos.jar ( 위의 두 폴더에 넣었으면 안해도 됨 )

// 비지니스 로직 = DAO
public class ProductMgr {
	// 싱글톤 객체를 사용하면 메모리 절약 효과, 같은 객체를 사용할 때 유용
	private static ProductMgr dao = new ProductMgr();

	// jsp에서 호출하여 객체를 얻는 메소드
	public static ProductMgr getDao() {
		return dao;
	}
	
	private ProductMgr() {} // 디폴트 생성자
	
	// 커넥션 풀 사용
	private Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		
		return ds.getConnection();
	}
	
	// 상품 목록 - product (관리자)
	public List<ProductDTO> getAdminGoodList() {
		String sql = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		ProductDTO dto = null;
		List<ProductDTO> goodList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			sql="select p.pro_no,p.name,p.price,p.sales,p.image,p.detail,p.regdate,p.topcate_idx,p.subcate_idx,ps.stock,ps.color,ps.size from product as p, product_stock as ps where p.pro_no=ps.pro_no";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new ProductDTO();
				
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setSales(rs.getInt("sales"));
				dto.setImage(rs.getString("image"));
				dto.setDetail(rs.getString("detail"));
				dto.setRegdate(rs.getDate("regdate"));
				dto.setTopcate_idx(rs.getInt("topcate_idx"));
				dto.setSubcate_idx(rs.getInt("subcate_idx"));
				dto.setStock(rs.getString("stock"));
				dto.setColor(rs.getString("color"));
				dto.setSize(rs.getString("size"));
				
				goodList.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getGoodList() 예외 : " + e);
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
		
		return goodList;
	}
	
	public List<ProductDTO> getGoodList() {
		String sql = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		ProductDTO dto = null;
		List<ProductDTO> goodList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			sql = "select * from product"; // 나중에 최신순으로 수정
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new ProductDTO();
				
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setSales(rs.getInt("sales"));
				dto.setImage(rs.getString("image"));
				dto.setDetail(rs.getString("detail"));
				dto.setTopcate_idx(rs.getInt("topcate_idx"));
				dto.setSubcate_idx(rs.getInt("subcate_idx"));
				
				goodList.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getGoodList() 예외 : " + e);
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
		
		return goodList;
	}
	
	// 상위 카테고리별 상품 목록
	public List<ProductDTO> getTopcateGoodList(int idx) {
		String sql = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		ProductDTO dto = null;
		List<ProductDTO> topGoodList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			sql = "select * from product where topcate_idx="+idx; // 나중에 최신순으로 수정
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new ProductDTO();
				
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setSales(rs.getInt("sales"));
				dto.setImage(rs.getString("image"));
				dto.setDetail(rs.getString("detail"));
				dto.setTopcate_idx(rs.getInt("topcate_idx"));
				dto.setSubcate_idx(rs.getInt("subcate_idx"));
				
				topGoodList.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getTopcateGoodList() 예외 : " + e);
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
		
		return topGoodList;
	}
	
	// 상위 카테고리별 상품 목록
	public List<ProductDTO> getSubcateGoodList(int topidx, int subidx) {
		String sql = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ProductDTO dto = null;
		List<ProductDTO> subGoodList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			sql = "select * from product where topcate_idx=? and subcate_idx=?"; // 나중에 최신순으로 수정
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, topidx);
			pstmt.setInt(2, subidx);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new ProductDTO();
				
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setSales(rs.getInt("sales"));
				dto.setImage(rs.getString("image"));
				dto.setDetail(rs.getString("detail"));
				dto.setTopcate_idx(rs.getInt("topcate_idx"));
				dto.setSubcate_idx(rs.getInt("subcate_idx"));
				
				subGoodList.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getSubcateGoodList() 예외 : " + e);
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
		
		return subGoodList;
	}

	// product_stock에서 color 가져오기(중복 X)
	public List<ProductDTO> getProductColor(int pro_no) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ProductDTO dto = null;
		String sql = "";
		List<ProductDTO> stockList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			stmt = con.createStatement();
			sql = "select distinct color from product_stock where pro_no="+pro_no;
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new ProductDTO();
				
				dto.setColor(rs.getString("color"));
				
				stockList.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getProductColor() 예외 : " + e);
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
		
		return stockList;
	}
	
	// product_stock에서 color에 해당하는 사이즈, 수량 가져오기
	public List<ProductDTO> getProductStock(int pro_no, String color) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ProductDTO dto = null;
		String sql = "";
		List<ProductDTO> stockList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			stmt = con.createStatement();
			sql = "select size, stock from product_stock where pro_no="+pro_no+" and color='"+color+"'";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new ProductDTO();
				
				dto.setSize(rs.getString("size"));
				dto.setStock(rs.getString("stock"));
				
				stockList.add(dto);
			}
		} catch(Exception e) {
			System.out.println("getProductStock() 예외 : " + e);
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
		
		return stockList;
	}

	// product_stock에서 color의 개수 가져오기 - size, stock 배열 크기
	public int getCountStock(int pro_no, String color) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		
		try {
			con = getCon();
			stmt = con.createStatement();
			sql = "select count(*) from product_stock where pro_no="+pro_no+" and color='"+color+"'";
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				cnt = rs.getInt("count(*)");
			}
		} catch(Exception e) {
			System.out.println("getCountStock() 예외 : " + e);
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
		
		return cnt;
	}
	
	// 상품 등록 - product
	public int insertProduct(HttpServletRequest req) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt=null;
        int pro_no_re=-1;
		String sql = "";
		
		try {
			con = getCon();
			String real_path = req.getServletContext().getRealPath("/"); // 실제 경로 얻기
			String upload_Dir = real_path + "/imgs/"; // 상품 등록하기 위해서
			int s = 5*1024*1024;
			
			MultipartRequest multi = new MultipartRequest(req, upload_Dir, s, "utf-8", new DefaultFileRenamePolicy());
			
			sql="insert into product(pro_no,name,price,"+
					"sales,image,detail,regdate,topcate_idx,subcate_idx) values(0,?,?,0,?,?,NOW(),?,?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, multi.getParameter("name"));
	 		pstmt.setInt(2, Integer.parseInt(multi.getParameter("price")));
	 		
			// image
			if(multi.getFilesystemName("image") != null) { // 그림 파일이 있으면
				pstmt.setString(3, multi.getFilesystemName("image"));
			} else { // 그림 파일이 없으면
				pstmt.setString(3, "ready.gif");
			}	 		
			
			pstmt.setString(4, multi.getParameter("detail"));
			pstmt.setString(5, multi.getParameter("topcate_idx"));
			pstmt.setString(6, multi.getParameter("subcate_idx"));
			
			stmt=con.createStatement();
			ResultSet rs2=stmt.executeQuery("select max(pro_no) from product");
			
			if(rs2.next())
				pro_no_re = rs2.getInt(1);
		} catch(Exception e) {
			System.out.println("insertProduct() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return pro_no_re;
	}
	
	// 상품 재고 등록 - product_stock
	public int insertStock(ProductDTO dto) {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String name = "";
		int x = -99;
		
		try {
			con = getCon();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select name from product where pro_no="+dto.getPro_no());
			
			if(rs.next()) { // 제품번호의 상품이 존재하면
				name = rs.getString("name"); 
			}
			
			sql = "insert into product_stock(pro_no, name, color, size, stock) values(?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			String size = dto.getSize();
			String tmp_size[] = size.split(",");
			
			String stock = dto.getStock();
			String tmp_stock[] = stock.split(",");
			
			if(tmp_size.length == tmp_stock.length) { // size와 stock의 길이가 같아야 db에 추가
				for(int i=0; i<tmp_size.length; i++) {
					pstmt.setInt(1, dto.getPro_no());
					pstmt.setString(2, name);
					pstmt.setString(3, dto.getColor());
					pstmt.setString(4, tmp_size[i]);
					pstmt.setString(5, tmp_stock[i]);
					
					pstmt.executeUpdate();
				}
				
				x = 0; // 성공
			} else {
				x = -1; // 추가 X
			}
		} catch(Exception e) {
			System.out.println("insertStock() 예외 : " + e);
		} finally {
			try {
				if(rs != null)
					rs.close();
				
				if(stmt != null)
					stmt.close();
				
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return x;
	}
	
	// 제품 번호에 해당하는 제품 정보 불러오기 (+ 상품 수정에서도 사용)
	public ProductDTO getProduct(int pro_no, String size, String color) {
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		ProductDTO dto = new ProductDTO(); // 모델빈
		String sql = "";
		
		try {
			con = getCon();
			sql="select p.pro_no,p.name,p.price,p.sales,p.image,p.detail,p.regdate,ps.stock,ps.color,ps.size from product p, product_stock ps "+
					"where p.pro_no="+pro_no+" and ps.pro_no="+pro_no+" and ps.size='"+size+"' and ps.color='"+color+"'";
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql);
			
			if(rs.next()) {
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setImage(rs.getString("image"));
				dto.setSales(rs.getInt("sales"));
				dto.setDetail(rs.getString("detail"));
				dto.setRegdate(rs.getDate("regdate"));
				dto.setStock(rs.getString("stock"));
				dto.setColor(rs.getString("color"));
				dto.setSize(rs.getString("size"));
			}
		} catch(Exception e) {
			System.out.println("getProduct() 예외 : " + e);
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
		
		return dto;
	}
	
	public ProductDTO getProduct(int pro_no) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ProductDTO dto = new ProductDTO(); // 모델빈
		String sql = "";
		
		try {
			con = getCon();
			stmt = con.createStatement();
			sql = "select * from product where pro_no="+pro_no;
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto.setPro_no(rs.getInt("pro_no"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setImage(rs.getString("image"));
				dto.setSales(rs.getInt("sales"));
				dto.setDetail(rs.getString("detail"));
				dto.setTopcate_idx(rs.getInt("topcate_idx"));
				dto.setSubcate_idx(rs.getInt("subcate_idx"));
			}
		} catch(Exception e) {
			System.out.println("getProduct() 예외 : " + e);
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
		
		return dto;
	}
	
	// 재고 수정
	public void reduceProduct(int quantity, int pro_no, String color, String size) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con=getCon();
	  		String sql="update product_stock set stock=(stock-?) where pro_no=? and color=? and size=?";
	  		pstmt=con.prepareStatement(sql);
	  		
	  		pstmt.setInt(1, quantity); // 수량
	  		pstmt.setInt(2, pro_no); // 상품 일련 번호 
	  		pstmt.setString(3, color);
	  		pstmt.setString(4, size);
	  		
	  		pstmt.executeUpdate(); // 쿼리 수행
		} catch(Exception e) {
	  		System.out.println("reduceProduct() 예외 :"+e);
	  	} finally {
	  		try {
	  			if(pstmt != null)
	  				pstmt.close();
	  			if(con != null)
	  				con.close();
	  		} catch(Exception e2) {}
	  	}
	}
	
	// 해당하는 color, size의 수량 체크 (제한하기 위해서)
	public int getMaxReduce(int pro_no, String color, String size) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int stock = 0;
		
		try {
			con = getCon();
			sql = "select stock from product_stock where pro_no=? and color=? and size=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, pro_no);
			pstmt.setString(2, color);
			pstmt.setString(3, size);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				stock = Integer.parseInt(rs.getString("stock"));
			}
		} catch(Exception e) {
			System.out.println("getMaxReduce() 예외 : " + e);
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
		
		return stock;
	}
	
	// 상품 update (관리자)
	public boolean updateProduct(HttpServletRequest req) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		boolean re = false;
		String sql = "";
		String sql2 = "";
		int count = -1;
		
		try {
			con = getCon();
			String real_path = req.getServletContext().getRealPath("/");
			String upload_Dir = real_path + "/imgs/";
			
			int s = 5*1024*1024;
			
			// 그림 파일 업로드
			MultipartRequest multi = new MultipartRequest(req, upload_Dir, s, "utf-8", new DefaultFileRenamePolicy());
			
			if(multi.getFilesystemName("image") == null) { // 그림 파일이 없으면
				sql = "update product set name=?, price=?, detail=? where pro_no=?";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, multi.getParameter("name"));
		 		pstmt.setInt(2, Integer.parseInt(multi.getParameter("price")));
		 		pstmt.setString(3, multi.getParameter("detail"));
				pstmt.setInt(4, Integer.parseInt(multi.getParameter("pro_no")));
				
				count = pstmt.executeUpdate();
				
				sql2="update product_stock set stock=?"+
						" where pro_no=? and size=? and color=?";
				
				pstmt2=con.prepareStatement(sql2);
				pstmt2.setInt(1, Integer.parseInt(multi.getParameter("stock")));
				pstmt2.setInt(2, Integer.parseInt(multi.getParameter("pro_no")));
				pstmt2.setString(3,multi.getParameter("size"));
				pstmt2.setString(4,multi.getParameter("color"));
			
				count = pstmt2.executeUpdate();
			} else { // 그림 파일이 있으면
				// 먼저 그림 파일을 삭제한다
				int im = Integer.parseInt(multi.getParameter("pro_no"));
				String sql3 = "select image from product where pro_no="+im;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql3);
				
				if(rs.next()) { // 이미지가 존재하면
//					String path = req.getContextPath();
					File f = new File(upload_Dir+rs.getString("image"));
					
					if(f.isFile()) // 그림 파일이 존재하면
						f.delete(); // 삭제
				}
				
				rs.close();
				stmt.close();
				
				sql = "update product set name=?, price=?, detail=?, image=? where pro_no=?";
				
				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, multi.getParameter("name"));
		 		pstmt.setInt(2, Integer.parseInt(multi.getParameter("price")));
		 		pstmt.setString(3, multi.getParameter("detail"));
				pstmt.setString(4, multi.getFilesystemName("image"));
				pstmt.setInt(5, Integer.parseInt(multi.getParameter("pro_no")));
				
				count = pstmt.executeUpdate();
				
				sql2="update product_stock set stock=?"+
						" where pro_no=? and size=? and color=?";
				
				pstmt2=con.prepareStatement(sql2);
				pstmt2.setInt(1, Integer.parseInt(multi.getParameter("stock")));
				pstmt2.setInt(2, Integer.parseInt(multi.getParameter("pro_no")));
				pstmt2.setString(3,multi.getParameter("size"));
				pstmt2.setString(4,multi.getParameter("color"));
				
				count = pstmt2.executeUpdate();
			}
			
			if(count > 0)
				re = true;
		} catch(Exception e) {
			System.out.println("updateProduct() 예외 : " + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return re;
	}
	
//	// 행 = row = record 삭제 (관리자)
//	// 업로드 된 그림 파일 삭제
	public boolean deleteProduct(HttpServletRequest req, int pro_no, String size, String color) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		Statement stmt = null;
		boolean re = false;
		
		try {
			con = getCon();
			
			// 그림 파일 삭제
			String sql = "select image from product where pro_no="+pro_no;
			String real_path = req.getServletContext().getRealPath("/");
			String upload_Dir = real_path + "/imgs/";
			
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) { // 이미지가 존재하면 삭제
				File f = new File(upload_Dir+rs.getString("image"));
				
				if(f.isFile()) // 그림 파일이 존재하면
					f.delete(); // 삭제
			}
			
			String sql2 = "delete from product_stock where pro_no=? and size=? and color=?";
			
			pstmt2=con.prepareStatement(sql2);
			pstmt2.setInt(1, pro_no);
			pstmt2.setString(2, size);
			pstmt2.setString(3, color);
			
			int count2=pstmt2.executeUpdate();
			
			if(count2 > 0)
				re = true;
			
			String sql3 = "select pro_no from product_stock where pro_no="+pro_no;
			ResultSet rs3 = stmt.executeQuery(sql3);
			
			String sql4 = "delete from product where pro_no=?";
			
			if(!rs3.next())
				stmt.execute(sql4);
			
			rs.close();
			rs3.close();
		} catch(Exception e) {
			System.out.println("deleteProduct() 예외 : " + e);
		} finally {
			try {				
				if(pstmt != null)
					pstmt.close();
				
				if(pstmt2 != null)
					pstmt2.close();
				
				if(stmt != null)
					stmt.close();
				
				if(con != null)
					con.close();
			} catch(Exception e2) {}
		}
		
		return re;
	}
	
	public String getProductName(int pro_no) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String name="";
		String sql=null;
		
		try{
			con=getCon();
			sql="select name from product where pro_no="+pro_no;
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				name=rs.getString("name");
			}//while
		}catch(Exception ex){
			System.out.println("getProductName() 예외:"+ex);
		}finally{
			try{
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(con != null){con.close();}
			}catch(Exception exx){}
		}//finally
		
		return name;
	}
}
