package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.PropertySearchCriteria;
import com.kuropatin.bookingapp.model.response.PropertyResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.SearchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final SearchService searchService;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Search property that available to order")
    @ApiImplicitParam(name = "X-Auth-Token", value = "JWT Authentication Token", dataTypeClass = String.class, paramType = "header")
    @PostMapping("/booking")
    public ResponseEntity<List<PropertyResponse>> search(@RequestBody final PropertySearchCriteria searchCriteria) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToListPropertyResponse(searchService.searchProperty(userId, searchCriteria)), HttpStatus.OK);
    }
}