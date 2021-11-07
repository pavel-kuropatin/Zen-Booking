package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.exception.QueryBuilderException;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.repository.mapper.PropertyMapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

@Repository
@RequiredArgsConstructor
public class JdbcSearch implements SearchRepository {

    private final HikariDataSource hikariDataSource;

    @AllArgsConstructor
    private final class QueryAndParams {
        private final String sql;
        private final Map<Integer, Object> params;
    }

    @Override
    public List<Property> searchByCriteria(final Long userId, final PropertySearchCriteria searchCriteria) {
        final List<Property> propertyList = new ArrayList<>();
        final QueryAndParams queryAndParams = buildQuery(userId, searchCriteria);
        final String sql = queryAndParams.sql;
        final Map<Integer, Object> params = queryAndParams.params;

        try(final Connection connection = hikariDataSource.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            for (int i = 1; i <= params.size(); i++) {
                preparedStatement.setObject(i, params.get(i));
            }
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    propertyList.add(new PropertyMapper().mapRow(resultSet, resultSet.getRow()));
                }
            }
            return propertyList;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    private QueryAndParams buildQuery(final Long userId, final PropertySearchCriteria criteria) {
        try {
            final Map<Integer, Object> params = new HashMap<>();
            int index = 0;
            final StringBuilder queryBuilder = new StringBuilder(
                    "SELECT p.* FROM property p " +
                    "WHERE p.is_deleted = false " +
                    "AND p.user_id <> ? " +
                    "AND p.is_available = true "
            );
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
                params.put(++index, Integer.parseInt(criteria.getPriceMin()));
            }
            if (!criteria.getPriceMax().isEmpty()) {
                queryBuilder.append("AND p.price <= ? ");
                params.put(++index, Integer.parseInt(criteria.getPriceMax()));
            }
            if (!criteria.getGuests().isEmpty()) {
                queryBuilder.append("AND p.guests >= ? ");
                params.put(++index, Short.parseShort(criteria.getGuests()));
            }
            if (!criteria.getRooms().isEmpty()) {
                queryBuilder.append("AND p.rooms >= ? ");
                params.put(++index, Short.parseShort(criteria.getRooms()));
            }
            if (!criteria.getBeds().isEmpty()) {
                queryBuilder.append("AND p.beds >= ? ");
                params.put(++index, Short.parseShort(criteria.getBeds()));
            }
            if (!criteria.getHasKitchen().isEmpty() && Boolean.parseBoolean(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_kitchen = ? ");
                params.put(++index, Boolean.parseBoolean(criteria.getHasKitchen()));
            }
            if (!criteria.getHasWasher().isEmpty() && Boolean.parseBoolean(criteria.getHasWasher())) {
                queryBuilder.append("AND p.has_washer = ? ");
                params.put(++index, Boolean.parseBoolean(criteria.getHasWasher()));
            }
            if (!criteria.getHasTv().isEmpty() && Boolean.parseBoolean(criteria.getHasTv())) {
                queryBuilder.append("AND p.has_tv = ? ");
                params.put(++index, Boolean.parseBoolean(criteria.getHasTv()));
            }
            if (!criteria.getHasInternet().isEmpty() && Boolean.parseBoolean(criteria.getHasInternet())) {
                queryBuilder.append("AND p.has_internet = ? ");
                params.put(++index, Boolean.parseBoolean(criteria.getHasInternet()));
            }
            if (!criteria.getIsPetsAllowed().isEmpty() && Boolean.parseBoolean(criteria.getIsPetsAllowed())) {
                queryBuilder.append("AND p.is_pets_allowed = ? ");
                params.put(++index, Boolean.parseBoolean(criteria.getIsPetsAllowed()));
            }
            queryBuilder.append(
                    "AND p.id NOT IN ( " +
                    "SELECT DISTINCT o.property_id FROM orders o " +
                    "WHERE o.is_accepted = false " +
                    "AND o.is_finished = false " +
                    "AND (to_date('yyyy-mm-dd', ?) BETWEEN o.start_date AND o.end_date " +
                    "OR to_date('yyyy-mm-dd', ?) BETWEEN o.start_date AND o.end_date " +
                    "OR o.start_date BETWEEN to_date('yyyy-mm-dd', ?) AND to_date('yyyy-mm-dd', ?) " +
                    "OR o.end_date BETWEEN to_date('yyyy-mm-dd', ?) AND to_date('yyyy-mm-dd', ?))) " +
                    "ORDER BY p.price ASC " +
                    "LIMIT 25"
            );
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            return new QueryAndParams(queryBuilder.toString(), params);
        } catch (RuntimeException e) {
            throw new QueryBuilderException(e);
        }
    }
}