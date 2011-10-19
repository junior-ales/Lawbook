package br.com.lawbook.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 19OCT2011-06 
 * 
 */

@Entity(name="lwb_comment")
public class Comment implements Serializable {
	
	@Id
	@SequenceGenerator(name="lwb_comment_seq_id", sequenceName="lwb_comment_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_comment_seq_id", strategy= GenerationType.SEQUENCE)
	public Long id;
	@ManyToOne
	@JoinColumn(name="sender_id")
	public Profile sender;
	@Column(length = 255)
	public String content;
	public Calendar dateTime;
	@Transient
	private static final long serialVersionUID = 4292348627560767987L;
	
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
			throw new IllegalArgumentException("Datetime parameter cannot be a future datetime");
		}
		this.dateTime = dateTime;
	}
}
