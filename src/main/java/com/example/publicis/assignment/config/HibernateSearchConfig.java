package com.example.publicis.assignment.config;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HibernateSearchConfig {

    @Autowired
    private EntityManager entityManager;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeHibernateSearch() {
        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer().startAndWait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error during Hibernate Search indexing", e);
        }
    }
}
