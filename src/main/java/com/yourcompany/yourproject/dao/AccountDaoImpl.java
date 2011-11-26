/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend:src/main/java/project/hibernate/DAOHibernate.e.vm.java
 */
package com.yourcompany.yourproject.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.yourcompany.yourproject.dao.AccountDao;
import com.yourcompany.yourproject.dao.hibernate.HibernateUtil;
import com.yourcompany.yourproject.dao.support.SearchTemplate;

import com.yourcompany.yourproject.domain.Account;
import com.yourcompany.yourproject.domain.Address;
import com.yourcompany.yourproject.domain.Book;
import com.yourcompany.yourproject.domain.ContactInfo;
import com.yourcompany.yourproject.domain.Document;
import com.yourcompany.yourproject.domain.Role;
import com.yourcompany.yourproject.dao.hibernate.HibernateGenericDao;

/**
 * Hibernate implementation of the Account DAO interface.
 */
@Repository
public class AccountDaoImpl extends HibernateGenericDao<Account, String> implements AccountDao {
    public AccountDaoImpl() {
        super(Account.class);
    }

    /**
     * Constructs the search by example criteria using not only the passed account but also its associated entities. 
     */
    @Override
    public Criteria getCriteria(Account account, SearchTemplate searchTemplate) {
        // Call super to construct the criteria of the 'root' entity.
        Criteria criteria = super.getCriteria(account, searchTemplate);

        // many-to-one: "homeAddress"
        // Note 1: If 'addressId' != null, no need to add a criteria as the current criteria 
        // already takes this property into account.
        // Note 2: By convention, if the example for "homeAddress" has no property set 
        // and "homeAddress" is not null we assume that you want a not null criteria. 
        if (account.getAddressId() == null && account.getHomeAddress() != null) {
            criteria.add(Restrictions.isNotNull("homeAddress"));
            criteria.createCriteria("homeAddress").add(
                    HibernateUtil.constructExample(account.getHomeAddress(), searchTemplate));
        }

        // one-to-one: "contactInformation"
        // Note 1: If 'accountId' != null, no need to add a criteria as the current criteria 
        // already takes this property into account.
        // Note 2: By convention, if the example for "contactInformation" has no property set 
        // and "contactInformation" is not null we assume that you want a not null criteria. 
        if (account.getAccountId() == null && account.getContactInformation() != null) {
            criteria.add(Restrictions.isNotNull("contactInformation"));
            criteria.createCriteria("contactInformation").add(
                    HibernateUtil.constructExample(account.getContactInformation(), searchTemplate));
        }

        // one-to-many: "books"
        if (account.getBooks() != null && !account.getBooks().isEmpty()) {
            Conjunction conjunction = Restrictions.conjunction();
            for (Book book : account.getBooks()) {
                conjunction.add(HibernateUtil.constructExample(book, searchTemplate));
            }
            criteria.createCriteria("books").add(conjunction);
        }

        // one-to-many: "documents"
        if (account.getDocuments() != null && !account.getDocuments().isEmpty()) {
            Conjunction conjunction = Restrictions.conjunction();
            for (Document document : account.getDocuments()) {
                conjunction.add(HibernateUtil.constructExample(document, searchTemplate));
            }
            criteria.createCriteria("documents").add(conjunction);
        }

        // many-to-many: "roles"
        if (account.getRoles() != null && !account.getRoles().isEmpty()) {
            Conjunction conjunction = Restrictions.conjunction();
            for (Role role : account.getRoles()) {
                conjunction.add(HibernateUtil.constructExample(role, searchTemplate));
            }
            criteria.createCriteria("roles").add(conjunction);
        }

        return criteria;
    }
}