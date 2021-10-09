package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.exception.QueryBuilderException;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.repository.mapper.PropertyMapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Repository
@RequiredArgsConstructor
public class JdbcSearch implements SearchRepository {

    private final HikariDataSource hikariDataSource;

    @AllArgsConstructor
    final class QueryAndParam {
        private String sql;
        private Map<Integer, Object> params;
    }

    @Override
    public List<Property> searchByCriteria(Long userId, PropertySearchCriteria searchCriteria) {
        List<Property> propertyList = new ArrayList<>();
        QueryAndParam queryAndParam = buildQuery(userId, searchCriteria);
        String sql = queryAndParam.sql;
        Map<Integer, Object> params = queryAndParam.params;

        try(Connection connection = hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            for (int i = 1; i <= params.size(); i++) {
                preparedStatement.setObject(i, params.get(i));
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    propertyList.add(new PropertyMapper().mapRow(resultSet, resultSet.getRow()));
                }
            }
            return propertyList;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    private QueryAndParam buildQuery(Long userId, PropertySearchCriteria criteria) {
        try {
            Map<Integer, Object> params = new HashMap<>();
            int index = 0;
            StringBuilder queryBuilder = new StringBuilder()
                    .append("SELECT p.* FROM property p ")
                    .append("WHERE p.is_deleted = false ")
                    .append("AND p.user_id <> ? ")
                    .append("AND p.is_available = true ");
            params.put(++index, userId);
            if (!criteria.getAddress().isEmpty()) {
                queryBuilder.append("AND LOWER(p.address) LIKE LOWER('%' || ? || '%') ");
                params.put(++index, criteria.getAddress());
            }
            if (!criteria.getType().equals(PropertyType.NOT_SPECIFIED.toString())) {
                queryBuilder.append("AND p.type = ? ");
                params.put(++index, criteria.getType());
            }
            if (!criteria.getPriceMin().isEmpty()) {
                queryBuilder.append("AND p.price >= ? ");
                params.put(++index, criteria.getPriceMin());
            }
            if (!criteria.getPriceMax().isEmpty()) {
                queryBuilder.append("AND p.price <= ? ");
                params.put(++index, criteria.getPriceMax());
            }
            if (!criteria.getGuests().isEmpty()) {
                queryBuilder.append("AND p.guests >= ? ");
                params.put(++index, criteria.getGuests());
            }
            if (!criteria.getRooms().isEmpty()) {
                queryBuilder.append("AND p.rooms >= ? ");
                params.put(++index, criteria.getRooms());
            }
            if (!criteria.getBeds().isEmpty()) {
                queryBuilder.append("AND p.beds >= ? ");
                params.put(++index, criteria.getBeds());
            }
            if (!criteria.getHasKitchen().isEmpty() || Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_kitchen = ? ");
                params.put(++index, criteria.getHasKitchen());
            }
            if (!criteria.getHasWasher().isEmpty() || Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_washer = ? ");
                params.put(++index, criteria.getHasWasher());
            }
            if (!criteria.getHasTv().isEmpty() || Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_tv = ? ");
                params.put(++index, criteria.getHasTv());
            }
            if (!criteria.getHasInternet().isEmpty() || Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_internet = ? ");
                params.put(++index, criteria.getHasInternet());
            }
            if (!criteria.getIsPetsAllowed().isEmpty() || Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.is_pets_allowed = ? ");
                params.put(++index, criteria.getIsPetsAllowed());
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
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            return new QueryAndParam(queryBuilder.toString(), params);
        } catch (RuntimeException e) {
            throw new QueryBuilderException(e);
        }
    }
}