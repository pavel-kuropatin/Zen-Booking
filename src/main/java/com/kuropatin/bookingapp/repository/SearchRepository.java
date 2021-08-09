package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.request.PropertySearchCriteria;

import java.util.List;

public interface SearchRepository {

    List<Property> searchByCriteria(Long userId, PropertySearchCriteria searchCriteria);
}