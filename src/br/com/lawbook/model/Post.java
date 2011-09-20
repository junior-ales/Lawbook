package br.com.lawbook.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19SEP2011-08 
 *
 */

@Entity(name="lwb_post")
public class Post implements Serializable {
	
	@Id
	@GeneratedValue
	public Long id;
	@ManyToOne
	@JoinColumn(name="sender_id")
	public Profile sender;
	@ManyToOne
	@JoinColumn(name="receiver_id")
	public Profile receiver;
	@Column(length = 255)
	public String content;
	public Calendar dateTime;
	@OneToMany
	@JoinTable(name="lwb_post_comments")
	public List<Comment> comments;
	@Transient
	private static final long serialVersionUID = 1L;

	public Post(Long id, Profile sender, Profile receiver, String content, Calendar dateTime, List<Comment> comments) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.dateTime = dateTime;
		this.comments = comments;
	}
	
	public Post() {
	}
	
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
	public Profile getReceiver() {
		return receiver;
	}
	public void setReceiver(Profile receiver) {
		this.receiver = receiver;
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
			throw new IllegalArgumentException("A data e hora da mensagem deve ser menor que a de agora");
		}
		this.dateTime = dateTime;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		String comments = "";
		if (this.comments != null) {
			for (Comment c : this.comments) {
				comments += c.getContent();
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return "Post [id=" + id + ", senderId=" + sender.getId() + ", receiver="
				+ receiver.getId() + ", content=" + content + ", dateTime="
				+ df.format(dateTime.getTime()) + ", comments=" + comments + "]";
	}
}
