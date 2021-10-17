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
 * @deprecated all PropertySearchCriteria fields must be not null,
 *             takes longer than JdbcSearch and JdbcTemplateSearch
 */
@Deprecated(forRemoval = true)
@Repository
public class CriteriaApiSearch implements SearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CriteriaApiSearch(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Property> searchByCriteria(Long userId, PropertySearchCriteria searchCriteria) {
        CriteriaQuery<Long> criteriaSubquery = criteriaBuilder.createQuery(Long.class);
        Root<Order> subqueryRoot = criteriaSubquery.from(Order.class);

        Predicate subqueryPredicate = getSubqueryPredicate(searchCriteria, subqueryRoot);
        criteriaSubquery.select(subqueryRoot.get(Order_.property).get(BasicEntity_.id));
        criteriaSubquery.where(subqueryPredicate);
        criteriaSubquery.distinct(true);
        List<Long> subquery = entityManager.createQuery(criteriaSubquery).getResultList();

        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        Root<Property> root = criteriaQuery.from(Property.class);

        Predicate predicate = getQueryPredicate(userId, searchCriteria, root, subquery);
        criteriaQuery.select(root);
        criteriaQuery.where(predicate);
        TypedQuery<Property> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    private Predicate getQueryPredicate(Long userId, PropertySearchCriteria searchCriteria, Root<Property> root, List<Long> subquery) {
        Expression<Boolean> rootIsDeleted = root.get(Property_.isDeleted);
        Expression<Long> rootUserId = root.get(Property_.user).get(BasicEntity_.id);
        Expression<Boolean> rootIsAvailable = root.get(Property_.isAvailable);
        Expression<PropertyType> rootType = root.get(Property_.type);
        Expression<String> rootAddress = root.get(Property_.address);
        Expression<Integer> rootPrice = root.get(Property_.price);
        Expression<Short> rootGuests = root.get(Property_.guests);
        Expression<Short> rootRooms = root.get(Property_.rooms);
        Expression<Short> rootBeds = root.get(Property_.beds);
        Expression<Boolean> rootHasKitchen = root.get(Property_.hasKitchen);
        Expression<Boolean> rootHasWasher = root.get(Property_.hasWasher);
        Expression<Boolean> rootHasTv = root.get(Property_.hasTv);
        Expression<Boolean> rootHasInternet = root.get(Property_.hasInternet);
        Expression<Boolean> rootIsPetsAllowed = root.get(Property_.isPetsAllowed);
        Expression<Long> orderId = root.get(BasicEntity_.id);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(rootIsDeleted, false));
        predicates.add(criteriaBuilder.notEqual(rootUserId, userId));
        predicates.add(criteriaBuilder.equal(rootIsAvailable, true));
        if(Objects.nonNull(searchCriteria.getType()) && !PropertyType.valueOf(searchCriteria.getType()).equals(PropertyType.NOT_SPECIFIED)) {
            predicates.add(criteriaBuilder.equal(rootType, PropertyType.valueOf(searchCriteria.getType())));
        }
        if(Objects.nonNull(searchCriteria.getAddress())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(rootAddress), StringUtils.join("%", searchCriteria.getAddress().toLowerCase(), "%")));
        }
        predicates.add(criteriaBuilder.between(rootPrice, Integer.valueOf(searchCriteria.getPriceMin()), Integer.valueOf(searchCriteria.getPriceMax())));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootGuests, Short.valueOf(searchCriteria.getGuests())));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootRooms, Short.valueOf(searchCriteria.getRooms())));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootBeds, Short.valueOf(searchCriteria.getBeds())));
        if(Boolean.parseBoolean(searchCriteria.getHasKitchen())) {
            predicates.add(criteriaBuilder.equal(rootHasKitchen, Boolean.parseBoolean(searchCriteria.getHasKitchen())));
        }
        if(Boolean.parseBoolean(searchCriteria.getHasWasher())) {
            predicates.add(criteriaBuilder.equal(rootHasWasher, Boolean.parseBoolean(searchCriteria.getHasWasher())));
        }
        if(Boolean.parseBoolean(searchCriteria.getHasTv())) {
            predicates.add(criteriaBuilder.equal(rootHasTv, Boolean.parseBoolean(searchCriteria.getHasTv())));
        }
        if(Boolean.parseBoolean(searchCriteria.getHasInternet())) {
            predicates.add(criteriaBuilder.equal(rootHasInternet, Boolean.parseBoolean(searchCriteria.getHasInternet())));
        }
        if(Boolean.parseBoolean(searchCriteria.getIsPetsAllowed())) {
            predicates.add(criteriaBuilder.equal(rootIsPetsAllowed, Boolean.parseBoolean(searchCriteria.getIsPetsAllowed())));
        }
        if(!subquery.isEmpty()) {
            predicates.add(criteriaBuilder.not(orderId.in(subquery)));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getSubqueryPredicate(PropertySearchCriteria searchCriteria, Root<Order> root) {
        Expression<Boolean> orderIsFinished = root.get(Order_.isFinished);
        Expression<LocalDate> orderStartDate = root.get(Order_.startDate);
        Expression<LocalDate> orderEndDate = root.get(Order_.endDate);
        LocalDate startDate = LocalDate.parse(searchCriteria.getStartDate());
        LocalDate endDate = LocalDate.parse(searchCriteria.getEndDate());

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(orderIsFinished, false));

        Predicate exp1 = criteriaBuilder.greaterThanOrEqualTo(orderStartDate, startDate);
        Predicate exp2 = criteriaBuilder.lessThanOrEqualTo(orderStartDate, endDate);
        Predicate and1 = criteriaBuilder.and(exp1, exp2);

        Predicate exp3 = criteriaBuilder.greaterThanOrEqualTo(orderEndDate, startDate);
        Predicate exp4 = criteriaBuilder.lessThanOrEqualTo(orderEndDate, endDate);
        Predicate and2 = criteriaBuilder.and(exp3, exp4);

        Predicate exp5 = criteriaBuilder.lessThanOrEqualTo(orderStartDate, startDate);
        Predicate exp6 = criteriaBuilder.greaterThanOrEqualTo(orderEndDate, startDate);
        Predicate and3 = criteriaBuilder.and(exp5, exp6);

        Predicate exp7 = criteriaBuilder.lessThanOrEqualTo(orderStartDate, endDate);
        Predicate exp8 = criteriaBuilder.greaterThanOrEqualTo(orderEndDate, endDate);
        Predicate and4 = criteriaBuilder.and(exp7, exp8);

        predicates.add(criteriaBuilder.and(criteriaBuilder.or(and1, and2, and3, and4)));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}