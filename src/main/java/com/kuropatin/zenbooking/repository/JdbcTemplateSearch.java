package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.exception.QueryBuilderException;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.repository.mapper.PropertyMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
        private String sql;
        private List<Object> params;
    }

    @Override
    public List<Property> searchByCriteria(Long userId, PropertySearchCriteria searchCriteria) {
        QueryAndParams queryAndParams = buildQuery(userId, searchCriteria);
        String sql = queryAndParams.sql;
        List<Object> params = queryAndParams.params;
        return jdbcTemplate.query(sql, new PropertyMapper(), params.toArray());
    }

    private QueryAndParams buildQuery(Long userId, PropertySearchCriteria criteria) {
        try {
            List<Object> params = new ArrayList<>();
            StringBuilder queryBuilder = new StringBuilder()
                    .append("SELECT p.* FROM property p ")
                    .append("WHERE p.is_deleted = false ")
                    .append("AND p.user_id <> ? ")
                    .append("AND p.is_available = true ");
            params.add(userId);
            if (!criteria.getAddress().isEmpty()) {
                queryBuilder.append("AND LOWER(p.address) LIKE LOWER('%' || ? || '%') ");
                params.add(criteria.getAddress());
            }
            if (!criteria.getType().equals(PropertyType.NOT_SPECIFIED.toString())) {
                queryBuilder.append("AND p.type = ? ");
                params.add(criteria.getType());
            }
            if (!criteria.getPriceMin().isEmpty()) {
                queryBuilder.append("AND p.price >= ? ");
                params.add(Integer.parseInt(criteria.getPriceMin()));
            }
            if (!criteria.getPriceMax().isEmpty()) {
                queryBuilder.append("AND p.price <= ? ");
                params.add(Integer.parseInt(criteria.getPriceMax()));
            }
            if (!criteria.getGuests().isEmpty()) {
                queryBuilder.append("AND p.guests >= ? ");
                params.add(Short.parseShort(criteria.getGuests()));
            }
            if (!criteria.getRooms().isEmpty()) {
                queryBuilder.append("AND p.rooms >= ? ");
                params.add(Short.parseShort(criteria.getRooms()));
            }
            if (!criteria.getBeds().isEmpty()) {
                queryBuilder.append("AND p.beds >= ? ");
                params.add(Short.parseShort(criteria.getBeds()));
            }
            if (!criteria.getHasKitchen().isEmpty() && Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_kitchen = ? ");
                params.add(criteria.getHasKitchen());
            }
            if (!criteria.getHasWasher().isEmpty() && Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_washer = ? ");
                params.add(criteria.getHasWasher());
            }
            if (!criteria.getHasTv().isEmpty() && Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_tv = ? ");
                params.add(criteria.getHasTv());
            }
            if (!criteria.getHasInternet().isEmpty() && Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_internet = ? ");
                params.add(criteria.getHasInternet());
            }
            if (!criteria.getIsPetsAllowed().isEmpty() && Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.is_pets_allowed = ? ");
                params.add(criteria.getIsPetsAllowed());
            }
            queryBuilder.append("AND p.id NOT IN ( ")
                    .append("SELECT DISTINCT o.property_id FROM orders o ")
                    .append("WHERE o.is_accepted = false ")
                    .append("AND o.is_finished = false ")
                    .append("AND (to_date('yyyy-mm-dd', ?) BETWEEN o.start_date AND o.end_date ")
                    .append("OR to_date('yyyy-mm-dd', ?) BETWEEN o.start_date AND o.end_date ")
                    .append("OR o.start_date BETWEEN to_date('yyyy-mm-dd', ?) AND to_date('yyyy-mm-dd', ?) ")
                    .append("OR o.end_date BETWEEN to_date('yyyy-mm-dd', ?) AND to_date('yyyy-mm-dd', ?))) ")
                    .append("ORDER BY p.price ASC ")
                    .append("LIMIT 25");
            params.add(criteria.getStartDate());
            params.add(criteria.getEndDate());
            params.add(criteria.getStartDate());
            params.add(criteria.getEndDate());
            params.add(criteria.getStartDate());
            params.add(criteria.getEndDate());
            return new QueryAndParams(queryBuilder.toString(), params);
        } catch (RuntimeException e) {
            throw new QueryBuilderException(e);
        }
    }
}