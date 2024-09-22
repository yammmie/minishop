package user;

import java.sql.*;

public class OrderDTO {
	private int ordno; // �ֹ���ȣ
	private int pro_no; // ��ǰ �Ϸù�ȣ
	private int quantity; // �ֹ� ����
	private Timestamp orddate; // �ֹ� ��¥
	private String state; // ����
	private String id; // ������ id
	private String color; // ����
	private String size; // ������
	private String payment; // ���� ����
	
	private int cnt; // �ֹ���ȣ �ߺ� ���� üũ
	
	// ����Ʈ ������
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
