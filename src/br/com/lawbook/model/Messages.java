package br.com.lawbook.model;

import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="lwb_message")
public class Messages {
	
	@Id
	@GeneratedValue
	public Long id;
	@ManyToOne
	@JoinColumn(name="sender_id")
	public User senderId;
	@OneToMany
	@JoinTable(name="lwb_message_receivers")
	public List<User> receiversId;
	@Column(length = 255)
	public String content;
	public Calendar dateTime;
	@OneToMany
	@JoinTable(name="lwb_message_comments")
	public List<Comment> comments;
	
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
	public List<User> getReceiverId() {
		return receiversId;
	}
	public void setReceiverId(List<User> receiverId) {
		this.receiversId = receiverId;
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
		this.dateTime = dateTime;
	}
	
}
