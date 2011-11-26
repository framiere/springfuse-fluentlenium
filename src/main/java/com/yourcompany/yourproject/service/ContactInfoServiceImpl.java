/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend:src/main/java/project/manager/ManagerImpl.e.vm.java
 */
package com.yourcompany.yourproject.service;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourcompany.yourproject.dao.support.GenericDao;
import com.yourcompany.yourproject.service.support.GenericEntityServiceImpl;
import com.yourcompany.yourproject.domain.ContactInfo;
import com.yourcompany.yourproject.dao.ContactInfoDao;

/**
 *
 * Default implementation of the {@link ContactInfoService} interface
 * @see ContactInfoService
 */
@Service("contactInfoService")
public class ContactInfoServiceImpl extends GenericEntityServiceImpl<ContactInfo, Integer> implements
        ContactInfoService {

    private static final Logger logger = Logger.getLogger(ContactInfoServiceImpl.class);

    protected ContactInfoDao contactInfoDao;

    @Autowired
    public void setContactInfoDao(ContactInfoDao contactInfoDao) {
        this.contactInfoDao = contactInfoDao;
    }

    /**
     * Dao getter used by the {@link GenericEntityServiceImpl}.
     */
    @Override
    public GenericDao<ContactInfo, Integer> getDao() {
        return contactInfoDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContactInfo getNew() {
        return new ContactInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContactInfo getNewWithDefaults() {
        ContactInfo result = getNew();
        result.initDefaultValues();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(ContactInfo contactInfo) {
        if (contactInfo == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping deletion for null model!");
            }

            return;
        }

        // remove the reference from the associated parent
        if (contactInfo.getParent() != null) {
            contactInfo.getParent().setContactInformation(null);
        }

        super.delete(contactInfo);
    }
}