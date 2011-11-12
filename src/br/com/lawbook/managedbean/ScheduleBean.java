/*
 * Copyright 2010 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.lawbook.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.hibernate.HibernateException;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;

import br.com.lawbook.business.EventService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 12NOV2011-07
 * 
 */
@ManagedBean
@SessionScoped
public class ScheduleBean implements Serializable {

	private Profile authProfile;
	private Event event;
	private LazyScheduleModel lazyEventModel;
	private HashMap<String, Long> idEventMapping;
	private Boolean disabled;
	private List<Event> upcomingEvents;
	private static final long serialVersionUID = 3659583666295112931L;

	public ScheduleBean() {
		this.event = new Event();
		this.upcomingEvents = new ArrayList<Event>();
		try {
			this.authProfile = ProfileService.getInstance().getAuthorizedUserProfile();
			this.idEventMapping = new HashMap<String, Long>();
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		} catch (Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	@PostConstruct
	public void loadLazilyEvents() {
		
		this.upcomingEvents = EventService.getInstance().getUpcomingEvents(authProfile);
		
		if(this.lazyEventModel == null) {
			this.lazyEventModel = new LazyScheduleModel() {

				private static final long serialVersionUID = -1716309540036635519L;

				@Override
				public void loadEvents(Date startDate, Date endDate) {
					clear();
					EventService eventService = EventService.getInstance();
					try {
						List<Event> events = eventService.getProfileEvents(authProfile, startDate, endDate);
						for (Event e : events) {
							addEvent(e);
							idEventMapping.put(e.getId(), e.getEventId());
						}
					} catch (IllegalArgumentException e) {
						FacesUtil.warnMessage("=|", e.getMessage());
					} catch (HibernateException e) {
						FacesUtil.errorMessage("=(", e.getMessage());
					}
				}
			};
		}
	}
	
	public ScheduleEvent getEvent() {
		return this.event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = (Event) event;
	}

	public Profile getAuthProfile() {
		return authProfile;
	}

	public LazyScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}
	
	public List<Event> getUpcomingEvents() {
		return this.upcomingEvents;
	}

	public Boolean getDeletable() {
		return disabled;
	}

	public void addEvent(ActionEvent actionEvent) {
		this.event.setCreator(this.authProfile);
		
		if(this.event.isAllDay()) {
			Calendar c = Calendar.getInstance();
			c.setTime(this.event.getStartDate());
			c.set(Calendar.HOUR_OF_DAY, 12);
			this.event.setStartDate(c.getTime());
			c.set(Calendar.HOUR_OF_DAY, 14);
			this.event.setEndDate(c.getTime());
		}
		
		try {
			if (this.event.getEventId() == null) 
				createEvent();
			else
				updateEvent();
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public void deleteEvent(ActionEvent actionEvent) {
		try {
			EventService.getInstance().delete(this.event);
			this.lazyEventModel.deleteEvent(this.getEvent()); 
			FacesUtil.infoMessage("=)", "Event deleted");
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		try {
			this.disabled = false;
			Long eventId = this.idEventMapping.get(selectEvent.getScheduleEvent().getId());
			this.event = EventService.getInstance().getEventById(eventId);
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public void onDateSelect(DateSelectEvent selectEvent) {
		this.disabled = true;
		this.event = new Event("", selectEvent.getDate(), selectEvent.getDate());
	}
	
	public String getDisabled() {
		if(this.disabled == null) this.disabled = true;
		return this.disabled.toString();
	}
	
	public void onEventMove(ScheduleEntryMoveEvent event) {
		try {
			Long eventId = this.idEventMapping.get(event.getScheduleEvent().getId());
			this.event = EventService.getInstance().getEventById(eventId);
			
			Calendar c = Calendar.getInstance();
			c.setTime(this.event.getStartDate());
			c.add(Calendar.DAY_OF_MONTH, event.getDayDelta());
			c.add(Calendar.MINUTE, event.getMinuteDelta());
			this.event.setStartDate(c.getTime());
			
			c = Calendar.getInstance();
			c.setTime(this.event.getEndDate());
			c.add(Calendar.DAY_OF_MONTH, event.getDayDelta());
			c.add(Calendar.MINUTE, event.getMinuteDelta());
			this.event.setEndDate(c.getTime());
			
			this.event.setAllDay(event.getScheduleEvent().isAllDay());
			
			updateEvent();
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public void onEventResize(ScheduleEntryResizeEvent event) {
		try {
			Long eventId = this.idEventMapping.get(event.getScheduleEvent().getId());
			this.event = EventService.getInstance().getEventById(eventId);
			
			Calendar c = Calendar.getInstance();
			c.setTime(this.event.getEndDate());
			c.add(Calendar.DAY_OF_MONTH, event.getDayDelta());
			c.add(Calendar.MINUTE, event.getMinuteDelta());
			this.event.setEndDate(c.getTime());
			
			this.event.setAllDay(event.getScheduleEvent().isAllDay());
			
			updateEvent();
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	private void updateEvent() throws IllegalArgumentException, HibernateException {
		EventService.getInstance().update(this.event);
		this.lazyEventModel.updateEvent(this.event);
		this.idEventMapping.put(this.event.getId(), this.event.getEventId());
		this.event = new Event();
		FacesUtil.infoMessage("=)", "Event updated successfully");
	}
	
	private void createEvent() throws IllegalArgumentException, HibernateException {
		EventService.getInstance().create(this.event);
		this.lazyEventModel.addEvent(this.event);
		this.idEventMapping.put(this.event.getId(), this.event.getEventId());
		this.event = new Event();
		FacesUtil.infoMessage("=)", "Event created successfully");
	}
	
}