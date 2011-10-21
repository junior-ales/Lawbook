package br.com.lawbook.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-10 
 *
 */

@Entity(name="lwb_post")
public class Post implements Serializable {
	
	@Id
	@SequenceGenerator(name="lwb_post_seq_id", sequenceName="lwb_post_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_post_seq_id", strategy= GenerationType.SEQUENCE)
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
	private static final long serialVersionUID = 497092916483761201L;

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
			throw new IllegalArgumentException("Datetime parameter cannot be a future datetime");
		}
		this.dateTime = dateTime;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
