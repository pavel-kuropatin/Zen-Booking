package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Order;
import com.kuropatin.bookingapp.model.*;
import com.kuropatin.bookingapp.model.request.PropertySearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class SearchRepositoryImpl implements SearchRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SearchRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Property> searchByCriteria(Long userId, PropertySearchCriteria searchCriteria) {
        CriteriaQuery<Long> criteriaSubquery = criteriaBuilder.createQuery(Long.class);
        Root<Order> subqueryRoot = criteriaSubquery.from(Order.class);

        Predicate subqueryPredicate = getSubqueryPredicate(searchCriteria, subqueryRoot);
        criteriaSubquery.select(subqueryRoot.get(Order_.property).get(Property_.id));
        criteriaSubquery.where(subqueryPredicate);
        criteriaSubquery.distinct(true);
        TypedQuery<Long> typedSubquery = entityManager.createQuery(criteriaSubquery);
        List<Long> subquery = typedSubquery.getResultList();

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
        Expression<Long> rootUserId = root.get(Property_.user).get(User_.id);
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
        Expression<Long> orderId = root.get(Property_.id);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(rootIsDeleted, false));
        predicates.add(criteriaBuilder.notEqual(rootUserId, userId));
        predicates.add(criteriaBuilder.equal(rootIsAvailable, true));
        if(Objects.nonNull(searchCriteria.getType()) && !searchCriteria.getType().equals(PropertyType.TYPE_NOT_SPECIFIED)) {
            predicates.add(criteriaBuilder.equal(rootType, searchCriteria.getType()));
        }
        if(Objects.nonNull(searchCriteria.getAddress())) {
            predicates.add(criteriaBuilder.like(rootAddress, StringUtils.join("%", searchCriteria.getAddress(), "%")));
        }
        predicates.add(criteriaBuilder.between(rootPrice, searchCriteria.getPriceMin(), searchCriteria.getPriceMax()));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootGuests, searchCriteria.getGuests()));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootRooms, searchCriteria.getRooms()));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootBeds, searchCriteria.getBeds()));
        if(searchCriteria.isHasKitchen()) {
            predicates.add(criteriaBuilder.equal(rootHasKitchen, searchCriteria.isHasKitchen()));
        }
        if(searchCriteria.isHasWasher()) {
            predicates.add(criteriaBuilder.equal(rootHasWasher, searchCriteria.isHasWasher()));
        }
        if(searchCriteria.isHasTv()) {
            predicates.add(criteriaBuilder.equal(rootHasTv, searchCriteria.isHasTv()));
        }
        if(searchCriteria.isHasInternet()) {
            predicates.add(criteriaBuilder.equal(rootHasInternet, searchCriteria.isHasInternet()));
        }
        if(searchCriteria.isPetsAllowed()) {
            predicates.add(criteriaBuilder.equal(rootIsPetsAllowed, searchCriteria.isHasInternet()));
        }
        if(!subquery.isEmpty()) {
            predicates.add(criteriaBuilder.not(orderId.in(subquery)));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getSubqueryPredicate(PropertySearchCriteria searchCriteria, Root<Order> root) {
        Expression<Boolean> orderIsAccepted = root.get(Order_.isAccepted);
        Expression<Boolean> orderIsFinished = root.get(Order_.isFinished);
        LocalDate searchCriteriaStartDate = searchCriteria.getStartDate();
        LocalDate searchCriteriaEndDate = searchCriteria.getEndDate();
        Expression<LocalDate> orderStartDate = root.get(Order_.startDate);
        Expression<LocalDate> orderEndDate = root.get(Order_.endDate);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(orderIsAccepted, false));
        predicates.add(criteriaBuilder.equal(orderIsFinished, false));
        predicates.add(criteriaBuilder.or(criteriaBuilder.between(orderStartDate, searchCriteriaStartDate, searchCriteriaEndDate)));
        predicates.add(criteriaBuilder.or(criteriaBuilder.between(orderEndDate, searchCriteriaStartDate, searchCriteriaEndDate)));
        //TODO: add reverse check
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}