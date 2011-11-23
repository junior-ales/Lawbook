package br.com.lawbook.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

/**
 * @author Edilson Luiz Ales Junior
 * @version 22NOV2011-01
 *
 */
@Entity(name = "lwb_process")
public class Process implements Serializable {

	@Id
	@SequenceGenerator(name="lwb_process_seq_id", sequenceName="lwb_process_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_process_seq_id", strategy= GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 50)
	private String situation;
	@Column(length = 50)
	private String topic;
	@Column(length = 50, name = "legal_area")
	private String legalArea;
	@Column(length = 255)
	private String description;
	@Column(name = "opening_date")
	private Calendar openingDate;
	@ManyToOne @ForeignKey(name="FK_PROCESS_LOCATION")
	private Location location;
	@ManyToOne @ForeignKey(name="FK_PROCESS_RESPONSIBLE")
	private Profile responsible;
	@ManyToOne @ForeignKey(name="FK_PROCESS_PETITIONER")
	private Profile petitioner;
	@ManyToOne @ForeignKey(name="FK_PROCESS_DEFENDANT")
	private Profile defendant;
	@Transient
	private static final long serialVersionUID = 4845117648639736388L;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Calendar getOpeningDate() {
		return this.openingDate;
	}

	public void setOpeningDate(final Calendar openingDate) {
		if (Calendar.getInstance().compareTo(openingDate) < 0) {
			throw new IllegalArgumentException("A data de abertura deve ser menor que a data de hoje");
		}
		this.openingDate = openingDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public Profile getResponsible() {
		return this.responsible;
	}

	public void setResponsible(final Profile responsible) {
		this.responsible = responsible;
	}

	public String getSituation() {
		return this.situation;
	}

	public void setSituation(final String situation) {
		this.situation = situation;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(final String topic) {
		this.topic = topic;
	}

	public String getLegalArea() {
		return this.legalArea;
	}

	public void setLegalArea(final String legalArea) {
		this.legalArea = legalArea;
	}

	public Profile getPetitioner() {
		return this.petitioner;
	}

	public void setPetitioner(final Profile petitioner) {
		this.petitioner = petitioner;
	}

	public Profile getDefendant() {
		return this.defendant;
	}

	public void setDefendant(final Profile defendant) {
		this.defendant = defendant;
	}
}
