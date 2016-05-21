package com.sanjnan.server.sanjnan.security.provider;

import com.sanjnan.server.sanjnan.service.DateTimeService;
import org.springframework.data.auditing.DateTimeProvider;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by vinay on 1/28/16.
 */
public class SanjnanAuditingDateTimeProvider implements DateTimeProvider {

    private final DateTimeService dateTimeService;

    public SanjnanAuditingDateTimeProvider(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @Override
    public Calendar getNow() {
        return GregorianCalendar.from(dateTimeService.getCurrentDateAndTime());
    }
}