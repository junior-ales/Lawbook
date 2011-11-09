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
 * @version 08NOV2011-04
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
		workaround(this.event, 1);
		try {
			if (this.event.getEventId() == null) {
				this.event.setCreator(this.authProfile);
				this.event.setContent("CREATED");
				EventService.getInstance().create(this.event);
				this.eventModel.addEvent(this.event);
			} else {
				this.event.setCreator(this.authProfile);
				this.event.setContent("UPDATED");
				EventService.getInstance().update(this.event);
				this.eventModel.updateEvent(this.event);
			}
			this.idEventMapping.put(this.event.getId(), this.event.getEventId());
			this.event = new Event();
		} catch (Exception e) {
			FacesUtil.infoMessage("=(", e.getMessage());
		}
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		Long eventId = this.idEventMapping.get(selectEvent.getScheduleEvent().getId());
		this.event = EventService.getInstance().getEventById(eventId);
		workaround(this.event, -1);
	}
	
	public void onDateSelect(DateSelectEvent selectEvent) {
		this.event = new Event("", selectEvent.getDate(), selectEvent.getDate());
	}
	
	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesUtil.infoMessage("=)", "Event has changed");
	}
	
	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesUtil.infoMessage("=)", "Event has changed");
	}
	
	private void loadEvents() throws Exception {
		List<Event> events = EventService.getInstance().getProfileEvents(this.authProfile);
		for (Event e : events) {
			this.eventModel.addEvent(e);
			idEventMapping.put(e.getId(), e.getEventId());
		}
	}
	
	private void workaround(Event event, int day) {
		Calendar c = Calendar.getInstance();
		
		c.setTime(event.getStartDate());
		c.add(Calendar.DAY_OF_MONTH, day);
		event.setStartDate(c.getTime());
		
		c.setTime(event.getEndDate());
		c.add(Calendar.DAY_OF_MONTH, day);
		event.setEndDate(c.getTime());
	}
	
}