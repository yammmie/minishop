package admin;

public class CategoryDTO {
	// topcate 테이블
	private int topcate_idx; 
	private String topcate_title;
	
	// subcate 테이블
	private int subcate_idx;
	private String subcate_title;

	public CategoryDTO() {}

	public int getTopcate_idx() {
		return topcate_idx;
	}

	public void setTopcate_idx(int topcate_idx) {
		this.topcate_idx = topcate_idx;
	}

	public String getTopcate_title() {
		return topcate_title;
	}

	public void setTopcate_title(String topcate_title) {
		this.topcate_title = topcate_title;
	}

	public int getSubcate_idx() {
		return subcate_idx;
	}

	public void setSubcate_idx(int subcate_idx) {
		this.subcate_idx = subcate_idx;
	}

	public String getSubcate_title() {
		return subcate_title;
	}

	public void setSubcate_title(String subcate_title) {
		this.subcate_title = subcate_title;
	}
}
