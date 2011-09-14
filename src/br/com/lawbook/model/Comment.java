package br.com.lawbook.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 14SEP2011-05 
 * 
 */

@Entity(name="lwb_comment")
public class Comment implements Serializable {
	
	@Id
	@GeneratedValue
	public Long id;
	@ManyToOne
	@JoinColumn(name="sender_id")
	public Profile sender;
	@Column(length = 255)
	public String content;
	public Calendar dateTime;
	@Transient
	private static final long serialVersionUID = 1L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Profile getSender() {
		return sender;
	}
	public void setSender(Profile sender) {
		this.sender = sender;
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
		return "Comment [id=" + id + ", senderId=" + sender.getId() + ", content="
				+ content + ", dateTime=" + df.format(dateTime.getTime()) + "]";
	}
}
