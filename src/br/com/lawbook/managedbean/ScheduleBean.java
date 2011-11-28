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
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.HibernateException;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;

import br.com.lawbook.business.service.EventService;
import br.com.lawbook.business.service.ProfileService;
import br.com.lawbook.model.Event;
import br.com.lawbook.model.Profile;
import br.com.lawbook.util.FacesUtil;

/**
 * @author Edilson Luiz Ales Junior
 * @version 27NOV2011-08
 *
 */
@ManagedBean
@SessionScoped
public class ScheduleBean implements Serializable {

	private Event event;
	private Boolean disabled;
	private Profile authProfile;
	private List<Event> upcomingEvents;
	private LazyScheduleModel lazyEventModel;
	private HashMap<String, Long> idEventMapping;
	private static final long serialVersionUID = 8737854846784246329L;
	private static final Logger LOG = Logger.getLogger("ScheduleBean");
	private static final EventService EVENT_SERVICE = EventService.getInstance();
	private static final ProfileService PROFILE_SERVICE = ProfileService.getInstance();
	private final ResourceBundle rs = ResourceBundle.getBundle("br.com.lawbook.util.messages", 
			   							FacesContext.getCurrentInstance().getViewRoot().getLocale());

	// TODO insert a "p:calendar" tag in schedule.xhtml
	
	public ScheduleBean() {
		this.event = new Event();
		this.upcomingEvents = new ArrayList<Event>();
		try {
			this.authProfile = PROFILE_SERVICE.getAuthorizedUserProfile();
			this.idEventMapping = new HashMap<String, Long>();
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		} catch (final Exception e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	@PostConstruct
	public void loadLazilyEvents() {
		LOG.info("#### loadLazilyEvents()");
		
		if(this.lazyEventModel == null) {
			this.lazyEventModel = new LazyScheduleModel() {

				private static final long serialVersionUID = -1716309540036635519L;

				@Override
				public void loadEvents(final Date startDate, final Date endDate) {
					this.clear();
					try {
						final List<Event> events = EVENT_SERVICE.getProfileEvents(ScheduleBean.this.authProfile, startDate, endDate);
						for (final Event e : events) {
							this.addEvent(e);
							ScheduleBean.this.idEventMapping.put(e.getId(), e.getEventId());
						}
					} catch (final IllegalArgumentException e) {
						FacesUtil.warnMessage("=|", e.getMessage());
					} catch (final HibernateException e) {
						FacesUtil.errorMessage("=(", e.getMessage());
					}
				}
			};
		}
	}

	public ScheduleEvent getEvent() {
		return this.event;
	}

	public void setEvent(final ScheduleEvent event) {
		this.event = (Event) event;
	}

	public Profile getAuthProfile() {
		return this.authProfile;
	}

	public LazyScheduleModel getLazyEventModel() {
		return this.lazyEventModel;
	}

	public List<Event> getUpcomingEvents() {
		return this.upcomingEvents;
	}

	public Boolean getDeletable() {
		return this.disabled;
	}

	public void addEvent(final ActionEvent actionEvent) {
		if (this.event.getTitle() == null || this.event.getTitle().isEmpty()) {
			FacesUtil.warnMessage("=|", this.rs.getString("msg_reqTitle"));
			return;
		}
		this.event.setCreator(this.authProfile);

		if(this.event.isAllDay()) {
			final Calendar c = Calendar.getInstance();
			c.setTime(this.event.getStartDate());
			c.set(Calendar.HOUR_OF_DAY, 12);
			this.event.setStartDate(c.getTime());
			c.set(Calendar.HOUR_OF_DAY, 14);
			this.event.setEndDate(c.getTime());
		}

		try {
			if (this.event.getEventId() == null)
				this.createEvent();
			else
				this.updateEvent();
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void deleteEvent(final ActionEvent actionEvent) {
		try {
			EVENT_SERVICE.delete(this.event);
			this.lazyEventModel.deleteEvent(this.getEvent());
			this.upcomingEvents = EVENT_SERVICE.getUpcomingEvents(this.authProfile);
			FacesUtil.infoMessage("=)", this.rs.getString("msg_eventDeleted"));
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void onEventSelect(final ScheduleEntrySelectEvent selectEvent) {
		try {
			this.disabled = false;
			final Long eventId = this.idEventMapping.get(selectEvent.getScheduleEvent().getId());
			this.event = EVENT_SERVICE.getEventById(eventId);
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void onDateSelect(final DateSelectEvent selectEvent) {
		this.disabled = true;
		this.event = new Event("", selectEvent.getDate(), selectEvent.getDate());
	}

	public String getDisabled() {
		if(this.disabled == null) this.disabled = true;
		return this.disabled.toString();
	}

	public void onEventMove(final ScheduleEntryMoveEvent event) {
		try {
			final Long eventId = this.idEventMapping.get(event.getScheduleEvent().getId());
			this.event = EVENT_SERVICE.getEventById(eventId);

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

			this.updateEvent();
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}

	public void onEventResize(final ScheduleEntryResizeEvent event) {
		try {
			final Long eventId = this.idEventMapping.get(event.getScheduleEvent().getId());
			this.event = EVENT_SERVICE.getEventById(eventId);

			final Calendar c = Calendar.getInstance();
			c.setTime(this.event.getEndDate());
			c.add(Calendar.DAY_OF_MONTH, event.getDayDelta());
			c.add(Calendar.MINUTE, event.getMinuteDelta());
			this.event.setEndDate(c.getTime());

			this.event.setAllDay(event.getScheduleEvent().isAllDay());

			this.updateEvent();
		} catch (final IllegalArgumentException e) {
			FacesUtil.warnMessage("=|", e.getMessage());
		} catch (final HibernateException e) {
			FacesUtil.errorMessage("=(", e.getMessage());
		}
	}
	
	private void updateEvent() throws IllegalArgumentException, HibernateException {
		EVENT_SERVICE.update(this.event);
		this.lazyEventModel.updateEvent(this.event);
		this.idEventMapping.put(this.event.getId(), this.event.getEventId());
		this.event = new Event();
		this.upcomingEvents = EVENT_SERVICE.getUpcomingEvents(this.authProfile);
		FacesUtil.infoMessage("=)", this.rs.getString("msg_eventUpdatedSuccess"));
	}

	private void createEvent() throws IllegalArgumentException, HibernateException {
		EVENT_SERVICE.create(this.event);
		this.lazyEventModel.addEvent(this.event);
		this.idEventMapping.put(this.event.getId(), this.event.getEventId());
		this.event = new Event();
		this.upcomingEvents = EVENT_SERVICE.getUpcomingEvents(this.authProfile);		
		FacesUtil.infoMessage("=)", this.rs.getString("msg_eventCreatedSuccess"));
	}

}