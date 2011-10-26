package br.com.lawbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Edilson Luiz Ales Junior
 * @version 25OCT2011-01 
 * 
 */
@Entity(name="lwb_person")
public class Person extends Customer {
	
	@Id
	@SequenceGenerator(name="lwb_person_seq_id", sequenceName="lwb_person_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_person_seq_id", strategy= GenerationType.SEQUENCE)
	private Long id;
	@Column(length=11,nullable=false, unique=true)
	private Integer cpf;
	private Integer rg;
	@Column(length = 100, name="orgao_expedidor")
	private String orgaoExpedidor;
	private char gender;
	@Column(length = 100, name="marital_status")
	private String maritalStatus;
	@Transient
	private static final long serialVersionUID = -2909020170771573465L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCpf() {
		return cpf;
	}
	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}
	public Integer getRg() {
		return rg;
	}
	public void setRg(Integer rg) {
		this.rg = rg;
	}
	public String getOrgaoExpedidor() {
		return orgaoExpedidor;
	}
	public void setOrgaoExpedidor(String orgaoExpedidor) {
		this.orgaoExpedidor = orgaoExpedidor;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	
}
