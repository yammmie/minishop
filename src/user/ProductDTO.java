package user;

import java.util.*;

// �𵨺�
public class ProductDTO {
	// product ���̺�
	private int pro_no; // ��ǰ �Ϸù�ȣ
	private String name; // ��ǰ��
	private int price; // ��ǰ ����
	private int sales; // �Ǹŷ�
	private String image; // ��ǰ �̹���
	private String detail; // ����
	private Date regdate;
	private int topcate_idx; // ����ī�װ�
	private int subcate_idx; // ����ī�װ�

	// product_stock ���̺�
	private String color; // ����
	private String size; // ������
	private String stock; // ����
	
	// ����Ʈ ������
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
