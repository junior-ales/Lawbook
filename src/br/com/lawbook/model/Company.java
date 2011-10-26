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
@Entity(name="lwb_company")
public class Company extends Customer {
	
	@Id
	@SequenceGenerator(name="lwb_company_seq_id", sequenceName="lwb_company_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_company_seq_id", strategy= GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable=false)
	public Integer cnpj;
	@Transient
	private static final long serialVersionUID = 3369986616854057375L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCnpj() {
		return cnpj;
	}

	public void setCnpj(Integer cnpj) {
		this.cnpj = cnpj;
	}

}
