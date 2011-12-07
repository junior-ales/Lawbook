package br.com.lawbook.model;

import java.io.Serializable;
import java.util.Date;
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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.primefaces.model.DefaultScheduleEvent;

/**
 * @author Edilson Luiz Ales Junior
 * @version 11NOV2011-04
 * 
 */
@Entity(name="lwb_event")
public class Event extends DefaultScheduleEvent implements Serializable {

	@Id
	@SequenceGenerator(name="lwb_event_seq_id", sequenceName="lwb_event_seq_id",allocationSize=1,initialValue=1)
    @GeneratedValue(generator="lwb_event_seq_id", strategy= GenerationType.SEQUENCE)
	@Column(name="event_id")
	private Long eventId;
	private Date endDate;
	private Date startDate;
	private String title;
	private boolean isAllDay;
	@Column(length=255)
	private String content;
	@ManyToOne @ForeignKey(name="FK_EVENT_LOCATION")
	private Location location;
	@ManyToOne @ForeignKey(name="FK_EVENT_USER_PROFILE")
	private Profile creator;
	@OneToMany @ForeignKey(name="FK_EVENT_USER_PROFILE", inverseName="FK_USER_PROFILE_EVENT") @LazyCollection(LazyCollectionOption.FALSE) 
	@JoinTable(name="lwb_event_guests", joinColumns = { @JoinColumn(name = "event_id") }, inverseJoinColumns = { @JoinColumn(name = "user_profile_id") })
	private List<Profile> guests;
	@Transient
	private static final long serialVersionUID = -3129018766121987426L;
	@Transient
	private String id;
	
	public Event() {
	}

	public Event(String title, Date startDate, Date endDate) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public boolean isAllDay() {
		return isAllDay;
	}

	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setAllDay(boolean isAllDay) {
		this.isAllDay = isAllDay;
	}
	
	public Long getEventId() {
		return eventId;
	}
	
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Profile getCreator() {
		return creator;
	}

	public void setCreator(Profile creator) {
		this.creator = creator;
	}

	public List<Profile> getGuests() {
		return guests;
	}

	public void setGuests(List<Profile> guests) {
		this.guests = guests;
	}
	
	public void copyTo(Event event) {
		event.setEventId(this.eventId);
		event.setAllDay(this.isAllDay);
		event.setContent(this.content);
		event.setCreator(this.creator);
		event.setEndDate(this.endDate);
		event.setGuests(this.guests);
		event.setLocation(this.location);
		event.setStartDate(this.startDate);
		event.setTitle(this.title);
	}
	
}
