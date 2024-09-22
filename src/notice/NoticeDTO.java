package notice;

import java.util.*; // Date

public class NoticeDTO {
	private int num; // 글 번호
	private Date regdate; // 작성일
	private String subject; // 제목
	private String content; // 내용
	
	public NoticeDTO() {} // 기본 생성자

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
