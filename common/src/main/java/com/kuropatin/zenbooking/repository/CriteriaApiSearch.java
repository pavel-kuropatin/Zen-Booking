package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.model.BasicEntity_;
import com.kuropatin.zenbooking.model.Order;
import com.kuropatin.zenbooking.model.Order_;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.model.Property_;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @deprecated This repository is no longer supported. Use {@link JdbcSearch} or {@link JdbcTemplateSearch} instead
 *
 * <p/>  - All {@link PropertySearchCriteria} fields must be not null.
 * <br/> - There are 2 queries to database instead one so {@link SearchRepository#searchByCriteria(Long, PropertySearchCriteria)} method must be refactored.
 * <br/> - Takes longer time than {@link JdbcSearch} and {@link JdbcTemplateSearch}.
 */
@Deprecated(forRemoval = true)
@Repository
public class CriteriaApiSearch implements SearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CriteriaApiSearch(final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Property> searchByCriteria(final Long userId, final PropertySearchCriteria searchCriteria) {
        final CriteriaQuery<Long> criteriaSubquery = criteriaBuilder.createQuery(Long.class);
        final Root<Order> subqueryRoot = criteriaSubquery.from(Order.class);

        final Predicate subqueryPredicate = getSubqueryPredicate(searchCriteria, subqueryRoot);
        criteriaSubquery.select(subqueryRoot.get(Order_.property).get(BasicEntity_.id));
        criteriaSubquery.where(subqueryPredicate);
        criteriaSubquery.distinct(true);
        final List<Long> subquery = entityManager.createQuery(criteriaSubquery).getResultList();

        final CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        Root<Property> root = criteriaQuery.from(Property.class);

        final Predicate predicate = getQueryPredicate(userId, searchCriteria, root, subquery);
        criteriaQuery.select(root);
        criteriaQuery.where(predicate);
        final TypedQuery<Property> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    private Predicate getQueryPredicate(final Long userId, final PropertySearchCriteria searchCriteria, final Root<Property> root, final List<Long> subquery) {
        final Expression<Boolean> rootIsDeleted = root.get(Property_.isDeleted);
        final Expression<Long> rootUserId = root.get(Property_.user).get(BasicEntity_.id);
        final Expression<Boolean> rootIsAvailable = root.get(Property_.isAvailable);
        final Expression<PropertyType> rootType = root.get(Property_.type);
        final Expression<String> rootAddress = root.get(Property_.address);
        final Expression<Integer> rootPrice = root.get(Property_.price);
        final Expression<Short> rootGuests = root.get(Property_.guests);
        final Expression<Short> rootRooms = root.get(Property_.rooms);
        final Expression<Short> rootBeds = root.get(Property_.beds);
        final Expression<Boolean> rootHasKitchen = root.get(Property_.hasKitchen);
        final Expression<Boolean> rootHasWasher = root.get(Property_.hasWasher);
        final Expression<Boolean> rootHasTv = root.get(Property_.hasTv);
        final Expression<Boolean> rootHasInternet = root.get(Property_.hasInternet);
        final Expression<Boolean> rootIsPetsAllowed = root.get(Property_.isPetsAllowed);
        final Expression<Long> orderId = root.get(BasicEntity_.id);

        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(rootIsDeleted, false));
        predicates.add(criteriaBuilder.notEqual(rootUserId, userId));
        predicates.add(criteriaBuilder.equal(rootIsAvailable, true));
        if (Objects.nonNull(searchCriteria.getType()) && !PropertyType.valueOf(searchCriteria.getType()).equals(PropertyType.NOT_SPECIFIED)) {
            predicates.add(criteriaBuilder.equal(rootType, PropertyType.valueOf(searchCriteria.getType())));
        }
        if (Objects.nonNull(searchCriteria.getAddress())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(rootAddress), StringUtils.join("%", searchCriteria.getAddress().toLowerCase(), "%")));
        }
        predicates.add(criteriaBuilder.between(rootPrice, searchCriteria.getPriceMin(), searchCriteria.getPriceMax()));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootGuests, searchCriteria.getGuests()));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootRooms, searchCriteria.getRooms()));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootBeds, searchCriteria.getBeds()));
        if (Boolean.TRUE.equals(searchCriteria.getHasKitchen())) {
            predicates.add(criteriaBuilder.equal(rootHasKitchen, searchCriteria.getHasKitchen()));
        }
        if (Boolean.TRUE.equals(searchCriteria.getHasWasher())) {
            predicates.add(criteriaBuilder.equal(rootHasWasher, searchCriteria.getHasWasher()));
        }
        if (Boolean.TRUE.equals(searchCriteria.getHasTv())) {
            predicates.add(criteriaBuilder.equal(rootHasTv, searchCriteria.getHasTv()));
        }
        if (Boolean.TRUE.equals(searchCriteria.getHasInternet())) {
            predicates.add(criteriaBuilder.equal(rootHasInternet, searchCriteria.getHasInternet()));
        }
        if (Boolean.TRUE.equals(searchCriteria.getIsPetsAllowed())) {
            predicates.add(criteriaBuilder.equal(rootIsPetsAllowed, searchCriteria.getIsPetsAllowed()));
        }
        if (!subquery.isEmpty()) {
            predicates.add(criteriaBuilder.not(orderId.in(subquery)));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getSubqueryPredicate(final PropertySearchCriteria searchCriteria, final Root<Order> root) {
        final Expression<Boolean> orderIsFinished = root.get(Order_.isFinished);
        final Expression<LocalDate> orderStartDate = root.get(Order_.startDate);
        final Expression<LocalDate> orderEndDate = root.get(Order_.endDate);
        final LocalDate startDate = LocalDate.parse(searchCriteria.getStartDate());
        final LocalDate endDate = LocalDate.parse(searchCriteria.getEndDate());

        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(orderIsFinished, false));

        final Predicate exp1 = criteriaBuilder.greaterThanOrEqualTo(orderStartDate, startDate);
        final Predicate exp2 = criteriaBuilder.lessThanOrEqualTo(orderStartDate, endDate);
        final Predicate and1 = criteriaBuilder.and(exp1, exp2);

        final Predicate exp3 = criteriaBuilder.greaterThanOrEqualTo(orderEndDate, startDate);
        final Predicate exp4 = criteriaBuilder.lessThanOrEqualTo(orderEndDate, endDate);
        final Predicate and2 = criteriaBuilder.and(exp3, exp4);

        final Predicate exp5 = criteriaBuilder.lessThanOrEqualTo(orderStartDate, startDate);
        final Predicate exp6 = criteriaBuilder.greaterThanOrEqualTo(orderEndDate, startDate);
        final Predicate and3 = criteriaBuilder.and(exp5, exp6);

        final Predicate exp7 = criteriaBuilder.lessThanOrEqualTo(orderStartDate, endDate);
        final Predicate exp8 = criteriaBuilder.greaterThanOrEqualTo(orderEndDate, endDate);
        final Predicate and4 = criteriaBuilder.and(exp7, exp8);

        predicates.add(criteriaBuilder.and(criteriaBuilder.or(and1, and2, and3, and4)));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}