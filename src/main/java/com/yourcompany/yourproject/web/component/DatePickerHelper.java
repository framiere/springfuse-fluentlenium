/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-primefaces:src/main/java/component/DatePickerHelper.p.vm.java
 */
package com.yourcompany.yourproject.web.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.stereotype.Component;

import com.yourcompany.yourproject.context.AccountContext;

/**
 * Helper used from the datePicker composite component.
 */
@Component
public class DatePickerHelper {

    public List<SelectItem> getYears(int min, int max) {
        List<SelectItem> result = new ArrayList<SelectItem>();

        for (int i = min; i <= max; i++) {
            String year = "" + i;
            result.add(new SelectItem(year, year));
        }

        return result;
    }

    public List<SelectItem> getMonths() {
        List<SelectItem> result = new ArrayList<SelectItem>();

        Calendar c = Calendar.getInstance();
        for (int i = 1; i <= 12; i++) {
            c.set(Calendar.MONTH, i - 1);
            String label = c.getDisplayName(Calendar.MONTH, Calendar.LONG, AccountContext.getAccountContext()
                    .getLocale());

            String month = normalize(i);
            result.add(new SelectItem(month, label));
        }

        return result;
    }

    public List<SelectItem> getDays(String ccClientId, boolean appendDayOfWeek) {
        UIInput ccDatepicker = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(ccClientId);
        UIInput year = (UIInput) ccDatepicker.findComponent("year");
        UIInput month = (UIInput) ccDatepicker.findComponent("month");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt((String) year.getLocalValue()));
        c.set(Calendar.MONTH, Integer.parseInt((String) month.getLocalValue()) - 1);

        List<SelectItem> result = new ArrayList<SelectItem>();

        int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= max; i++) {
            String day = normalize(i);
            c.set(Calendar.DAY_OF_MONTH, i);
            StringBuilder sb = new StringBuilder();
            sb.append(day);
            if (appendDayOfWeek) {
                sb.append(" ").append(
                        c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, AccountContext.getAccountContext()
                                .getLocale()));
            }
            result.add(new SelectItem(day, sb.toString()));
        }

        return result;
    }

    public List<SelectItem> getHours() {
        List<SelectItem> result = new ArrayList<SelectItem>();
        for (int i = 0; i < 24; i++) {
            String hour = normalize(i);
            result.add(new SelectItem(hour, hour));
        }
        return result;
    }

    public List<SelectItem> getMinutes() {
        List<SelectItem> result = new ArrayList<SelectItem>();
        result.add(new SelectItem("00", "00"));
        result.add(new SelectItem("15", "15"));
        result.add(new SelectItem("30", "30"));
        result.add(new SelectItem("45", "45"));
        return result;
    }

    public static final String normalize(int i) {
        return i < 10 ? "0" + i : "" + i;
    }
}