package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;

import java.util.List;

public interface SearchRepository {

    List<Property> searchByCriteria(Long userId, PropertySearchCriteria searchCriteria);
}