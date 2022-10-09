package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.exception.ApplicationException;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.repository.mapper.PropertyMapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            for (final Map.Entry<Integer, Object> entry : params.entrySet()) {
                preparedStatement.setObject(entry.getKey(), entry.getValue());
            }
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    propertyList.add(new PropertyMapper().mapRow(resultSet, resultSet.getRow()));
                }
            }
            return propertyList;
        } catch (SQLException e) {
            throw new ApplicationException(e);
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
            if (StringUtils.isNotBlank(criteria.getAddress())) {
                queryBuilder.append("AND LOWER(p.address) LIKE LOWER('%' || ? || '%') ");
                params.put(++index, criteria.getAddress());
            }
            if (StringUtils.isNotBlank(criteria.getType()) && !criteria.getType().equals(PropertyType.NOT_SPECIFIED.toString())) {
                queryBuilder.append("AND p.type = ? ");
                params.put(++index, criteria.getType());
            }
            if (criteria.getPriceMin() != null) {
                queryBuilder.append("AND p.price >= ? ");
                params.put(++index, criteria.getPriceMin());
            }
            if (criteria.getPriceMax() != null) {
                queryBuilder.append("AND p.price <= ? ");
                params.put(++index, criteria.getPriceMax());
            }
            if (criteria.getGuests() != null) {
                queryBuilder.append("AND p.guests >= ? ");
                params.put(++index, criteria.getGuests());
            }
            if (criteria.getRooms() != null) {
                queryBuilder.append("AND p.rooms >= ? ");
                params.put(++index, criteria.getRooms());
            }
            if (criteria.getBeds() != null) {
                queryBuilder.append("AND p.beds >= ? ");
                params.put(++index, criteria.getBeds());
            }
            if (Boolean.TRUE.equals(criteria.getHasKitchen())) {
                queryBuilder.append("AND p.has_kitchen = ? ");
                params.put(++index, criteria.getHasKitchen());
            }
            if (Boolean.TRUE.equals(criteria.getHasWasher())) {
                queryBuilder.append("AND p.has_washer = ? ");
                params.put(++index, criteria.getHasWasher());
            }
            if (Boolean.TRUE.equals(criteria.getHasTv())) {
                queryBuilder.append("AND p.has_tv = ? ");
                params.put(++index, criteria.getHasTv());
            }
            if (Boolean.TRUE.equals(criteria.getHasInternet())) {
                queryBuilder.append("AND p.has_internet = ? ");
                params.put(++index, criteria.getHasInternet());
            }
            if (Boolean.TRUE.equals(criteria.getIsPetsAllowed())) {
                queryBuilder.append("AND p.is_pets_allowed = ? ");
                params.put(++index, criteria.getIsPetsAllowed());
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
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            params.put(++index, criteria.getStartDate());
            params.put(++index, criteria.getEndDate());
            final int perPage = criteria.getPerPage() == null ? 10 : criteria.getPerPage();
            final int pageNumber = criteria.getPageNumber() == null ? 1 : criteria.getPageNumber();
            params.put(++index, perPage);
            params.put(++index, (pageNumber - 1) * perPage);
            return new QueryAndParams(queryBuilder.toString(), params);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}