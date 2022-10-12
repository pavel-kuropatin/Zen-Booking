package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.exception.ApplicationException;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.repository.mapper.PropertyMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
@RequiredArgsConstructor
public class JdbcTemplateSearch implements SearchRepository {

    private final JdbcTemplate jdbcTemplate;

    @AllArgsConstructor
    private final class QueryAndParams {
        private final String sql;
        private final List<Object> params;
    }

    @Override
    public List<Property> searchByCriteria(final Long userId, final PropertySearchCriteria searchCriteria) {
        final QueryAndParams queryAndParams = buildQuery(userId, searchCriteria);
        final String sql = queryAndParams.sql;
        final List<Object> params = queryAndParams.params;
        return jdbcTemplate.query(sql, new PropertyMapper(), params.toArray());
    }

    private QueryAndParams buildQuery(final Long userId, final PropertySearchCriteria criteria) {
        try {
            final List<Object> params = new ArrayList<>();
            final StringBuilder queryBuilder = new StringBuilder(
                    "SELECT p.* FROM property p " +
                    "WHERE p.is_deleted = false " +
                    "AND p.user_id <> ? " +
                    "AND p.is_available = true "
            );
            params.add(userId);
            if (StringUtils.isNotBlank(criteria.getAddress())) {
                queryBuilder.append("AND LOWER(p.address) LIKE LOWER('%' || ? || '%') ");
                params.add(criteria.getAddress());
            }
            if (StringUtils.isNotBlank(criteria.getType()) && !criteria.getType().equals(PropertyType.NOT_SPECIFIED.toString())) {
                queryBuilder.append("AND p.type = ? ");
                params.add(criteria.getType());
            }
            if (criteria.getPriceMin() != null) {
                queryBuilder.append("AND p.price >= ? ");
                params.add(criteria.getPriceMin());
            }
            if (criteria.getPriceMax() != null) {
                queryBuilder.append("AND p.price <= ? ");
                params.add(criteria.getPriceMax());
            }
            if (criteria.getGuests() != null) {
                queryBuilder.append("AND p.guests >= ? ");
                params.add(criteria.getGuests());
            }
            if (criteria.getRooms() != null) {
                queryBuilder.append("AND p.rooms >= ? ");
                params.add(criteria.getRooms());
            }
            if (criteria.getBeds() != null) {
                queryBuilder.append("AND p.beds >= ? ");
                params.add(criteria.getBeds());
            }
            if (Boolean.TRUE.equals(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_kitchen = ? ");
                params.add(criteria.getHasKitchen());
            }
            if (Boolean.TRUE.equals(criteria.getHasWasher())) {
                queryBuilder.append("AND p.has_washer = ? ");
                params.add(criteria.getHasWasher());
            }
            if (Boolean.TRUE.equals(criteria.getHasTv())) {
                queryBuilder.append("AND p.has_tv = ? ");
                params.add(criteria.getHasTv());
            }
            if (Boolean.TRUE.equals(criteria.getHasInternet())) {
                queryBuilder.append("AND p.has_internet = ? ");
                params.add(criteria.getHasInternet());
            }
            if (Boolean.TRUE.equals(criteria.getIsPetsAllowed())) {
                queryBuilder.append("AND p.is_pets_allowed = ? ");
                params.add(criteria.getIsPetsAllowed());
            }
            queryBuilder.append(
                    "AND p.id NOT IN ( " +
                    "SELECT DISTINCT o.property_id FROM orders o " +
                    "WHERE o.status = 'NEW' " +
                    "AND (to_date(?, 'yyyy-mm-dd') BETWEEN o.start_date AND o.end_date " +
                    "OR to_date(?, 'yyyy-mm-dd') BETWEEN o.start_date AND o.end_date " +
                    "OR o.start_date BETWEEN to_date(?, 'yyyy-mm-dd') AND to_date(?, 'yyyy-mm-dd') " +
                    "OR o.end_date BETWEEN to_date(?, 'yyyy-mm-dd') AND to_date(?, 'yyyy-mm-dd'))) " +
                    "ORDER BY p.price ASC " +
                    "LIMIT ? OFFSET ?"
            );
            params.add(criteria.getStartDate());
            params.add(criteria.getEndDate());
            params.add(criteria.getStartDate());
            params.add(criteria.getEndDate());
            params.add(criteria.getStartDate());
            params.add(criteria.getEndDate());
            final int perPage = criteria.getPerPage() == null ? 10 : criteria.getPerPage();
            final int pageNumber = criteria.getPageNumber() == null ? 1 : criteria.getPageNumber();
            params.add(perPage);
            params.add((pageNumber - 1) * perPage);
            return new QueryAndParams(queryBuilder.toString(), params);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}