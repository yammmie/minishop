package user;

import java.util.*;

// 모델빈
public class ProductDTO {
	// product 테이블
	private int pro_no; // 상품 일련번호
	private String name; // 상품명
	private int price; // 상품 가격
	private int sales; // 판매량
	private String image; // 상품 이미지
	private String detail; // 설명
	private Date regdate;
	private int topcate_idx; // 상위카테고리
	private int subcate_idx; // 하위카테고리

	// product_stock 테이블
	private String color; // 색상
	private String size; // 사이즈
	private String stock; // 수량
	
	// 디폴트 생성자
	public ProductDTO() {}

	public int getPro_no() {
		return pro_no;
	}

	public void setPro_no(int pro_no) {
		this.pro_no = pro_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getTopcate_idx() {
		return topcate_idx;
	}

	public void setTopcate_idx(int topcate_idx) {
		this.topcate_idx = topcate_idx;
	}

	public int getSubcate_idx() {
		return subcate_idx;
	}

	public void setSubcate_idx(int subcate_idx) {
		this.subcate_idx = subcate_idx;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}
}
