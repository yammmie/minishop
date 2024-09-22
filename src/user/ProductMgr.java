package user;

import java.sql.*; // Connection, PreparedStatement
import java.util.*; // List, Vector ... 

import javax.sql.*; // DataSource - Ŀ�ؼ� Ǯ ���
import javax.naming.*; // lookup - Ŀ�ؼ� Ǯ ���

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

import javax.servlet.http.*; // ����

import java.io.*; // �׸� ������ �����ϱ� ���ؼ�

// ���� ���ε� - cos.jar
// jdk/jre/lib/ext/cos.jar
// Tomcat8/lib/cos.jar
// ȯ�溯�� - classpath: .;%JAVA_HOME%\lib\tools.jar;C:\cos-20.08\lib\cos.jar

// ���� ������Ʈ WEB-INF/lib/cos.jar ( ���� �� ������ �־����� ���ص� �� )

// �����Ͻ� ���� = DAO
public class ProductMgr {
	// �̱��� ��ü�� ����ϸ� �޸� ���� ȿ��, ���� ��ü�� ����� �� ����
	private static ProductMgr dao = new ProductMgr();

	// jsp���� ȣ���Ͽ� ��ü�� ��� �޼ҵ�
	public static ProductMgr getDao() {
		return dao;
	}
	
	private ProductMgr() {} // ����Ʈ ������
	
	// Ŀ�ؼ� Ǯ ���
	private Connection getCon() throws Exception {
		Context ct = new InitialContext();
		DataSource ds = (DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		
		return ds.getConnection();
	}
	
	// ��ǰ ��� - product (������)
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
			System.out.println("getGoodList() ���� : " + e);
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
			sql = "select * from product"; // ���߿� �ֽż����� ����
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
			System.out.println("getGoodList() ���� : " + e);
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
	
	// ���� ī�װ��� ��ǰ ���
	public List<ProductDTO> getTopcateGoodList(int idx) {
		String sql = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		ProductDTO dto = null;
		List<ProductDTO> topGoodList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			sql = "select * from product where topcate_idx="+idx; // ���߿� �ֽż����� ����
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
			System.out.println("getTopcateGoodList() ���� : " + e);
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
	
	// ���� ī�װ��� ��ǰ ���
	public List<ProductDTO> getSubcateGoodList(int topidx, int subidx) {
		String sql = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ProductDTO dto = null;
		List<ProductDTO> subGoodList = new ArrayList<ProductDTO>();
		
		try {
			con = getCon();
			sql = "select * from product where topcate_idx=? and subcate_idx=?"; // ���߿� �ֽż����� ����
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
			System.out.println("getSubcateGoodList() ���� : " + e);
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

	// product_stock���� color ��������(�ߺ� X)
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
			System.out.println("getProductColor() ���� : " + e);
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
	
	// product_stock���� color�� �ش��ϴ� ������, ���� ��������
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
			System.out.println("getProductStock() ���� : " + e);
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

	// product_stock���� color�� ���� �������� - size, stock �迭 ũ��
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
			System.out.println("getCountStock() ���� : " + e);
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
	
	// ��ǰ ��� - product
	public int insertProduct(HttpServletRequest req) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt=null;
        int pro_no_re=-1;
		String sql = "";
		
		try {
			con = getCon();
			String real_path = req.getServletContext().getRealPath("/"); // ���� ��� ���
			String upload_Dir = real_path + "/imgs/"; // ��ǰ ����ϱ� ���ؼ�
			int s = 5*1024*1024;
			
			MultipartRequest multi = new MultipartRequest(req, upload_Dir, s, "utf-8", new DefaultFileRenamePolicy());
			
			sql="insert into product(pro_no,name,price,"+
					"sales,image,detail,regdate,topcate_idx,subcate_idx) values(0,?,?,0,?,?,NOW(),?,?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, multi.getParameter("name"));
	 		pstmt.setInt(2, Integer.parseInt(multi.getParameter("price")));
	 		
			// image
			if(multi.getFilesystemName("image") != null) { // �׸� ������ ������
				pstmt.setString(3, multi.getFilesystemName("image"));
			} else { // �׸� ������ ������
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
			System.out.println("insertProduct() ���� : " + e);
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
	
	// ��ǰ ��� ��� - product_stock
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
			
			if(rs.next()) { // ��ǰ��ȣ�� ��ǰ�� �����ϸ�
				name = rs.getString("name"); 
			}
			
			sql = "insert into product_stock(pro_no, name, color, size, stock) values(?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			String size = dto.getSize();
			String tmp_size[] = size.split(",");
			
			String stock = dto.getStock();
			String tmp_stock[] = stock.split(",");
			
			if(tmp_size.length == tmp_stock.length) { // size�� stock�� ���̰� ���ƾ� db�� �߰�
				for(int i=0; i<tmp_size.length; i++) {
					pstmt.setInt(1, dto.getPro_no());
					pstmt.setString(2, name);
					pstmt.setString(3, dto.getColor());
					pstmt.setString(4, tmp_size[i]);
					pstmt.setString(5, tmp_stock[i]);
					
					pstmt.executeUpdate();
				}
				
				x = 0; // ����
			} else {
				x = -1; // �߰� X
			}
		} catch(Exception e) {
			System.out.println("insertStock() ���� : " + e);
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
	
	// ��ǰ ��ȣ�� �ش��ϴ� ��ǰ ���� �ҷ����� (+ ��ǰ ���������� ���)
	public ProductDTO getProduct(int pro_no, String size, String color) {
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		ProductDTO dto = new ProductDTO(); // �𵨺�
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
			System.out.println("getProduct() ���� : " + e);
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
		ProductDTO dto = new ProductDTO(); // �𵨺�
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
			System.out.println("getProduct() ���� : " + e);
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
	
	// ��� ����
	public void reduceProduct(int quantity, int pro_no, String color, String size) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con=getCon();
	  		String sql="update product_stock set stock=(stock-?) where pro_no=? and color=? and size=?";
	  		pstmt=con.prepareStatement(sql);
	  		
	  		pstmt.setInt(1, quantity); // ����
	  		pstmt.setInt(2, pro_no); // ��ǰ �Ϸ� ��ȣ 
	  		pstmt.setString(3, color);
	  		pstmt.setString(4, size);
	  		
	  		pstmt.executeUpdate(); // ���� ����
		} catch(Exception e) {
	  		System.out.println("reduceProduct() ���� :"+e);
	  	} finally {
	  		try {
	  			if(pstmt != null)
	  				pstmt.close();
	  			if(con != null)
	  				con.close();
	  		} catch(Exception e2) {}
	  	}
	}
	
	// �ش��ϴ� color, size�� ���� üũ (�����ϱ� ���ؼ�)
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
			System.out.println("getMaxReduce() ���� : " + e);
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
	
	// ��ǰ update (������)
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
			
			// �׸� ���� ���ε�
			MultipartRequest multi = new MultipartRequest(req, upload_Dir, s, "utf-8", new DefaultFileRenamePolicy());
			
			if(multi.getFilesystemName("image") == null) { // �׸� ������ ������
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
			} else { // �׸� ������ ������
				// ���� �׸� ������ �����Ѵ�
				int im = Integer.parseInt(multi.getParameter("pro_no"));
				String sql3 = "select image from product where pro_no="+im;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql3);
				
				if(rs.next()) { // �̹����� �����ϸ�
//					String path = req.getContextPath();
					File f = new File(upload_Dir+rs.getString("image"));
					
					if(f.isFile()) // �׸� ������ �����ϸ�
						f.delete(); // ����
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
			System.out.println("updateProduct() ���� : " + e);
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
	
//	// �� = row = record ���� (������)
//	// ���ε� �� �׸� ���� ����
	public boolean deleteProduct(HttpServletRequest req, int pro_no, String size, String color) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		Statement stmt = null;
		boolean re = false;
		
		try {
			con = getCon();
			
			// �׸� ���� ����
			String sql = "select image from product where pro_no="+pro_no;
			String real_path = req.getServletContext().getRealPath("/");
			String upload_Dir = real_path + "/imgs/";
			
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) { // �̹����� �����ϸ� ����
				File f = new File(upload_Dir+rs.getString("image"));
				
				if(f.isFile()) // �׸� ������ �����ϸ�
					f.delete(); // ����
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
			System.out.println("deleteProduct() ���� : " + e);
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
			System.out.println("getProductName() ����:"+ex);
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
