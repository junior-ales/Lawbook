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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.hibernate.HibernateException;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.lawbook.business.EventService;
import br.com.lawbook.business.ProfileService;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 09NOV2011-05
 * 
 */
@ManagedBean
@SessionScoped
public class ScheduleBean implements Serializable {

	private Profile authProfile;
	private Event event;
	private ScheduleModel eventModel;
	private HashMap<String, Long> idEventMapping;
	private static final long serialVersionUID = 1915515651902440505L;

	public ScheduleBean() {
		this.event = new Event();
		this.eventModel = new DefaultScheduleModel();
		try {
			this.authProfile = ProfileService.getInstance().getAuthorizedUserProfile();
			this.idEventMapping = new HashMap<String, Long>();
			loadEvents();
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		} catch (Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public ScheduleEvent getEvent() {
		return this.event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = (Event) event;
	}
	
	public ScheduleModel getEventModel() {
		return this.eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public void addEvent(ActionEvent actionEvent) {
		try {
			workaround(1);
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

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		try {
			Long eventId = this.idEventMapping.get(selectEvent.getScheduleEvent().getId());
			this.event = EventService.getInstance().getEventById(eventId);
			workaround(-1);
		} catch (IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	public void onDateSelect(DateSelectEvent selectEvent) {
		this.event = new Event("", selectEvent.getDate(), selectEvent.getDate());
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
	
	private void loadEvents() throws IllegalArgumentException, HibernateException {
		List<Event> events = EventService.getInstance().getProfileEvents(this.authProfile);
		for (Event e : events) {
			this.eventModel.addEvent(e);
			idEventMapping.put(e.getId(), e.getEventId());
		}
	}
	
	private void updateEvent() throws IllegalArgumentException, HibernateException {
		this.event.setCreator(this.authProfile);
		this.event.setContent("UPDATED");
		EventService.getInstance().update(this.event);
		this.eventModel.updateEvent(this.event);
		this.idEventMapping.put(this.event.getId(), this.event.getEventId());
		this.event = new Event();
		FacesUtil.infoMessage("=)", "Event updated successfully");
	}
	
	private void createEvent() throws IllegalArgumentException, HibernateException {
		this.event.setCreator(this.authProfile);
		this.event.setContent("CREATED");
		EventService.getInstance().create(this.event);
		this.eventModel.addEvent(this.event);
		this.idEventMapping.put(this.event.getId(), this.event.getEventId());
		this.event = new Event();
		FacesUtil.infoMessage("=)", "Event created successfully");
	}
	
	private void workaround(int day) {
		Calendar c = Calendar.getInstance();
		
		c.setTime(this.event.getStartDate());
		c.add(Calendar.DAY_OF_MONTH, day);
		this.event.setStartDate(c.getTime());
		
		c.setTime(this.event.getEndDate());
		c.add(Calendar.DAY_OF_MONTH, day);
		this.event.setEndDate(c.getTime());
	}
	
}