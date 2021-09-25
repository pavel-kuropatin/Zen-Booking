package com.kuropatin.zenbooking.service;

import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    public List<Property> searchProperty(long userId, PropertySearchCriteria searchCriteria) {
        return searchRepository.searchByCriteria(userId, searchCriteria);
    }
}