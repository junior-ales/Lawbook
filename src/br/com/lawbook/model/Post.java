package br.com.lawbook.model;

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

/**
 * @author Edilson Luiz Ales Junior
 * @version 12SEP2011-06 
 *
 */

@Entity(name="lwb_post")
public class Post {
	
	@Id
	@GeneratedValue
	public Long id;
	@ManyToOne
	@JoinColumn(name="sender_id")
	public Profile sender;
	@OneToMany
	@JoinTable(name="lwb_post_receivers")
	public List<Profile> receivers;
	@Column(length = 255)
	public String content;
	public Calendar dateTime;
	@OneToMany
	@JoinTable(name="lwb_post_comments")
	public List<Comment> comments;
	
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
	public List<Profile> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<Profile> receivers) {
		this.receivers = receivers;
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
		for (Comment c : this.comments) {
			comments += c.getContent();
		}
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return "Post [id=" + id + ", senderId=" + sender.getId() + ", receivers="
				+ receivers + ", content=" + content + ", dateTime="
				+ df.format(dateTime.getTime()) + ", comments=" + comments + "]";
	}
}
