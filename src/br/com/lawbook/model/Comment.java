package br.com.lawbook.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Edilson Luiz Ales Junior
 * @version 09SEP2011-03 
 * 
 */

@Entity(name="lwb_comment")
public class Comment {
	
	@Id
	@GeneratedValue
	public Long id;
	@ManyToOne
	@JoinColumn(name="sender_id")
	public User senderId;
	@Column(length = 255)
	public String content;
	public Calendar dateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getSenderId() {
		return senderId;
	}
	public void setSenderId(User senderId) {
		this.senderId = senderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Calendar getDateTime() {
		return dateTime;
	}
	public void setDateTime(Calendar dateTime) {
		if (Calendar.getInstance().compareTo(dateTime) < 0) {
			throw new IllegalArgumentException("A data e hora do comentario deve ser menor que a de agora");
		}
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return "Comment [id=" + id + ", senderId=" + senderId + ", content="
				+ content + ", dateTime=" + df.format(dateTime.getTime()) + "]";
	}
}
