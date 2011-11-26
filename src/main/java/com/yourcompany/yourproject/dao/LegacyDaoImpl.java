/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend:src/main/java/project/hibernate/DAOHibernate.e.vm.java
 */
package com.yourcompany.yourproject.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;
import com.yourcompany.yourproject.dao.LegacyDao;
import com.yourcompany.yourproject.dao.hibernate.HibernateUtil;
import com.yourcompany.yourproject.dao.support.SearchTemplate;
import com.yourcompany.yourproject.domain.LegacyPk;

import com.yourcompany.yourproject.domain.Legacy;
import com.yourcompany.yourproject.dao.hibernate.HibernateGenericDao;

/**
 * Hibernate implementation of the Legacy DAO interface.
 */
@Repository
public class LegacyDaoImpl extends HibernateGenericDao<Legacy, LegacyPk> implements LegacyDao {
    public LegacyDaoImpl() {
        super(Legacy.class);
    }

    /**
     * Add criterion so the id fields part of the composite primary key are
     * included in a search by example.
     */
    @Override
    protected List<Criterion> getByExampleExtraCriterions(Legacy model, SearchTemplate searchTemplate) {
        List<Criterion> criterions = new ArrayList<Criterion>();

        // handle pk fields
        if (model.getPrimaryKey().isNameSet()) {
            criterions.add(HibernateUtil.constructPatternCriterion("legacyPk.name", model.getPrimaryKey().getName(),
                    searchTemplate));
        }

        if (model.getPrimaryKey().isCodeSet()) {
            criterions.add(HibernateUtil.constructPatternCriterion("legacyPk.code", model.getPrimaryKey().getCode(),
                    searchTemplate));
        }

        return criterions;
    }
}