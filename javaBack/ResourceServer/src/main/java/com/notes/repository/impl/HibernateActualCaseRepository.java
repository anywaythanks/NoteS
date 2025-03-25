package com.notes.repository.impl;

import com.notes.models.TwistMark;
import com.notes.models.dto.acase.CaseLastTwistResponseDto;
import com.notes.repository.ActualCaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HibernateActualCaseRepository implements ActualCaseRepository {
    @PersistenceContext
    private EntityManager em;

    //select t.twistCase.id, t.updatedOn from TwistMark tm where t.generalAccount.id = #generalAccountId and
    //(t.twistCase.id >= #startId and t.twistCase.id <= #endId) and t.consider orderBy #sort
    @Transactional(readOnly = true)
    public List<CaseLastTwistResponseDto> dates(Long generalAccountId, Long startId, Long endId, Sort sort) {
        if (startId < 0 || startId > endId) throw new IllegalArgumentException();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CaseLastTwistResponseDto> queryDate = cb.createQuery(CaseLastTwistResponseDto.class);
        Root<TwistMark> twistMark = queryDate.from(TwistMark.class);
        CriteriaQuery<CaseLastTwistResponseDto> c = queryDate.multiselect(twistMark.get("twistCase").get("id"),
                twistMark.get("updatedOn"));
        Predicate p = cb.and(cb.greaterThanOrEqualTo(twistMark.get("twistCase").get("id"), startId),
                cb.lessThanOrEqualTo(twistMark.get("twistCase").get("id"), endId));
        c.where(cb.and(cb.equal(twistMark.get("generalAccount").get("id"), generalAccountId), p, cb.isTrue(twistMark.get("consider"))));
        if (!sort.isUnsorted()) {
            List<Order> orders = new ArrayList<>();
            for (Sort.Order order : sort) {
                Expression<?> exp = twistMark.get("twistCase").get(order.getProperty());
                orders.add(order.isAscending() ? cb.asc(exp) : cb.desc(exp));
            }
            c.orderBy(orders);
        }
        return em.createQuery(c).getResultList();
    }
}
