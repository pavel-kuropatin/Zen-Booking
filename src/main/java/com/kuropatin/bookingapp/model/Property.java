package com.kuropatin.bookingapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PropertyType type = PropertyType.OTHER;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "building")
    private String building;

    @Column(name = "apartment")
    private String apartment = "";

    @Column(name = "price")
    private int price;

    @Column(name = "guests")
    private short guests;

    @Column(name = "rooms")
    private short rooms;

    @Column(name = "beds")
    private short beds;

    @Column(name = "kitchen")
    private boolean hasKitchen;

    @Column(name = "washer")
    private boolean hasWasher;

    @Column(name = "tv")
    private boolean hasTv;

    @Column(name = "internet")
    private boolean hasInternet;

    @Column(name = "pets_allowed")
    private boolean isPetsAllowed;

    @Column(name = "available")
    private boolean isAvailable;

    @Column(name = "approved")
    private boolean isApproved;

    @Column(name = "banned")
    private boolean isBanned;

    @Column(name = "deleted")
    private boolean isDeleted;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<PropertyImage> propertyImages;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Order order;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public boolean hasKitchen() {
        return hasKitchen;
    }

    public boolean hasWasher() {
        return hasWasher;
    }

    public boolean hasTv() {
        return hasTv;
    }

    public boolean hasInternet() {
        return hasInternet;
    }
}