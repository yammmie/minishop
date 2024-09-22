package user;

import java.sql.*;

public class OrderDTO {
	private int ordno; // 주문번호
	private int pro_no; // 상품 일련번호
	private int quantity; // 주문 수량
	private Timestamp orddate; // 주문 날짜
	private String state; // 상태
	private String id; // 구매자 id
	private String color; // 색상
	private String size; // 사이즈
	private String payment; // 결제 수단
	
	private int cnt; // 주문번호 중복 개수 체크
	
	// 디폴트 생성자
	public OrderDTO() {}

	public int getOrdno() {
		return ordno;
	}

	public void setOrdno(int ordno) {
		this.ordno = ordno;
	}

	public int getPro_no() {
		return pro_no;
	}

	public void setPro_no(int pro_no) {
		this.pro_no = pro_no;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Timestamp getOrddate() {
		return orddate;
	}

	public void setOrddate(Timestamp orddate) {
		this.orddate = orddate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
