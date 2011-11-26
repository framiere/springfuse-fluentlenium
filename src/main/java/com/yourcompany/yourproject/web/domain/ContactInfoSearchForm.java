/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-primefaces:src/main/java/SearchForm.e.vm.java
 */
package com.yourcompany.yourproject.web.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yourcompany.yourproject.dao.support.SearchTemplate;
import com.yourcompany.yourproject.dao.support.SearchMode;
import com.yourcompany.yourproject.dao.support.DateRange;
import com.yourcompany.yourproject.domain.ContactInfo;
import com.yourcompany.yourproject.service.ContactInfoService;
import com.yourcompany.yourproject.web.domain.support.GenericSearchForm;

/**
 * SearchForm for ContactInfoService
 */
@Component
@Scope("session")
public class ContactInfoSearchForm extends GenericSearchForm<ContactInfo> implements Serializable {
    private static final long serialVersionUID = 1L;
    // make it static to avoid http://jira.springframework.org/browse/SWF-1224
    private static ContactInfoService contactInfoService;

    private ContactInfo contactInfo = new ContactInfo();
    private SearchTemplate searchTemplate = new SearchTemplate().setSearchMode(SearchMode.ANYWHERE);
    private DateRange birthDateRange = new DateRange("birthDate");
    private DateRange otherDateRange = new DateRange("otherDate");

    @Autowired
    public ContactInfoSearchForm(ContactInfoService instance) {
        if (contactInfoService == null) {
            contactInfoService = instance;
        }
    }

    /**
     * The root model for search by example.
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * The default search template.
     */
    public SearchTemplate getSearchTemplate() {
        return searchTemplate;
    }

    public DateRange getBirthDateRange() {
        return birthDateRange;
    }

    public DateRange getOtherDateRange() {
        return otherDateRange;
    }

    /**
     * Prepare the search parameters and call the contactInfoService finder.
     * Automatically called by PrimeFaces component.
     */
    @Override
    public List<ContactInfo> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {
        SearchTemplate st = new SearchTemplate(getSearchTemplate());
        st.add(getBirthDateRange());
        st.add(getOtherDateRange());

        // total count so the paginator may display the total number of pages
        this.setRowCount(contactInfoService.findCount(getContactInfo(), st));

        // load one page of data 
        populateSearchTemplate(st, first, pageSize, sortField, sortOrder, filters);
        return contactInfoService.find(getContactInfo(), st);
    }
}