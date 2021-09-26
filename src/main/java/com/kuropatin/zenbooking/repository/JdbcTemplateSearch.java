package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.exception.QueryBuilderException;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.repository.mapper.PropertyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
@RequiredArgsConstructor
public class JdbcTemplateSearch implements SearchRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Property> searchByCriteria(Long userId, PropertySearchCriteria searchCriteria) {
        String sql = buildQuery(userId, searchCriteria);
        return jdbcTemplate.query(sql, new PropertyMapper());
    }

    private String buildQuery(Long userId, PropertySearchCriteria criteria) {
        try {
            StringBuilder queryBuilder = new StringBuilder()
                    .append("SELECT p.* FROM property p ")
                    .append("WHERE p.is_deleted = false ")
                    .append("AND p.user_id <> ").append(userId).append(" ")
                    .append("AND p.is_available = true ");
            if (!criteria.getAddress().isEmpty()) {
                queryBuilder.append("AND LOWER(p.address) LIKE LOWER('%").append(criteria.getAddress()).append("%') ");
            }
            if (!criteria.getType().equals(PropertyType.NOT_SPECIFIED.toString())) {
                queryBuilder.append("AND p.type = '").append(criteria.getType()).append("' ");
            }
            if (!criteria.getPriceMin().isEmpty()) {
                queryBuilder.append("AND p.price >= ").append(criteria.getPriceMin()).append(" ");
            }
            if (!criteria.getPriceMax().isEmpty()) {
                queryBuilder.append("AND p.price <= ").append(criteria.getPriceMax()).append(" ");
            }
            if (!criteria.getGuests().isEmpty()) {
                queryBuilder.append("AND p.guests >= ").append(criteria.getGuests()).append(" ");
            }
            if (!criteria.getRooms().isEmpty()) {
                queryBuilder.append("AND p.rooms >= ").append(criteria.getRooms()).append(" ");
            }
            if (!criteria.getBeds().isEmpty()) {
                queryBuilder.append("AND p.beds >= ").append(criteria.getBeds()).append(" ");
            }
            if (!criteria.getHasKitchen().isEmpty()) {
                queryBuilder.append("AND p.has_kitchen = '").append(criteria.getHasKitchen()).append("' ");
            }
            if (!criteria.getHasWasher().isEmpty()) {
                queryBuilder.append("AND p.has_washer = '").append(criteria.getHasWasher()).append("' ");
            }
            if (!criteria.getHasTv().isEmpty()) {
                queryBuilder.append("AND p.has_tv = '").append(criteria.getHasTv()).append("' ");
            }
            if (!criteria.getHasInternet().isEmpty()) {
                queryBuilder.append("AND p.has_internet = '").append(criteria.getHasInternet()).append("' ");
            }
            if (!criteria.getIsPetsAllowed().isEmpty()) {
                queryBuilder.append("AND p.is_pets_allowed = '").append(criteria.getIsPetsAllowed()).append("' ");
            }
            queryBuilder.append("AND p.id NOT IN ( ")
                    .append("SELECT DISTINCT o.property_id FROM orders o ")
                    .append("WHERE o.is_accepted = false ")
                    .append("AND o.is_finished = false ")
                    .append("AND ('").append(criteria.getStartDate()).append("' BETWEEN o.start_date AND o.end_date ")
                    .append("OR '").append(criteria.getEndDate()).append("' BETWEEN o.start_date AND o.end_date ")
                    .append("OR o.start_date BETWEEN '").append(criteria.getStartDate()).append("' AND '").append(criteria.getEndDate()).append("' ")
                    .append("OR o.end_date BETWEEN '").append(criteria.getStartDate()).append("' AND '").append(criteria.getEndDate()).append("')) ")
                    .append("ORDER BY p.price ASC ")
                    .append("LIMIT 25");
            return queryBuilder.toString();
        } catch (RuntimeException e) {
            throw new QueryBuilderException(e);
        }
    }
}