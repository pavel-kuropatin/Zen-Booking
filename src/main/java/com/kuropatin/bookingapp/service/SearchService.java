package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.request.PropertySearchCriteria;
import com.kuropatin.bookingapp.repository.SearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepositoryImpl searchRepository;

    public List<Property> searchProperty(long userId, PropertySearchCriteria searchCriteria) {
        return searchRepository.searchByCriteria(userId, searchCriteria);
    }
}