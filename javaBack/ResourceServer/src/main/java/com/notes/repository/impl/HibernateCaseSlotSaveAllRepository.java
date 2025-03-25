package com.notes.repository.impl;

import com.notes.models.Case;
import com.notes.models.CaseSlot;
import com.notes.models.Item;
import com.notes.repository.CaseSlotNameRepository;
import com.notes.repository.CaseSlotRepository;
import com.notes.repository.CaseSlotSaveAllRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HibernateCaseSlotSaveAllRepository implements CaseSlotSaveAllRepository {
    @PersistenceContext
    EntityManager em;
    private final CaseSlotRepository caseSlotRepository;
    private final CaseSlotNameRepository caseSlotNameRepository;
    private final JdbcPostgreSQLCaseSlot jdbcPostgreSQLCaseSlot;

    @Override
    @Transactional
    public List<CaseSlot<Item>> saveAll(Case caseOwner, Collection<CaseSlot<Item>> slots) {
        for (CaseSlot<Item> slot : slots) {
            slot.setName(caseSlotNameRepository.save(slot.getName()));
        }
        Session session = em.unwrap(Session.class);
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
        Dialect dialect = sessionFactory.getJdbcServices().getDialect();
        final String tempTableName = String.format("TMP_%d_CASE_SLOTS", caseOwner.getId());
        if (dialect instanceof PostgreSQLDialect) {
            try {
                session.doWork(jdbcPostgreSQLCaseSlot.createTempTable(tempTableName));
                session.doWork(jdbcPostgreSQLCaseSlot.insertSlots(tempTableName, caseOwner, slots));
                em.flush();
                session.doWork(jdbcPostgreSQLCaseSlot.merge(tempTableName, caseOwner));
            } finally {
                session.doWork(jdbcPostgreSQLCaseSlot.dropTempTable(tempTableName));
            }
        } else throw new UnsupportedOperationException();
        return caseSlotRepository.findAllByOwnerCaseId(caseOwner.getId());
    }
}
