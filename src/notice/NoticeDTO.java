package notice;

import java.util.*; // Date

public class NoticeDTO {
	private int num; // �� ��ȣ
	private Date regdate; // �ۼ���
	private String subject; // ����
	private String content; // ����
	
	public NoticeDTO() {} // �⺻ ������

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
