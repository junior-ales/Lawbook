package br.com.lawbook.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 20OCT2011-03 
 * 
 */
@Entity(name="lwb_authority")
public class Authority implements Serializable {

	@Id
	@SequenceGenerator(name="lwb_authority_seq_id", sequenceName="lwb_authority_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_authority_seq_id", strategy= GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 100, unique=true)
	private String name;
	@Transient
	private static final long serialVersionUID = 2493487525173846223L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
