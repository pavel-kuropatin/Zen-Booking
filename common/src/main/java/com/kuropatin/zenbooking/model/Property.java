package com.kuropatin.zenbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {
        "user", "propertyImages", "order"
})
@Entity
@Table(name = "property")
public class Property extends BasicEntity {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PropertyType type = PropertyType.NOT_SPECIFIED;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private int price;

    @Column(name = "guests")
    private short guests;

    @Column(name = "rooms")
    private short rooms;

    @Column(name = "beds")
    private short beds;

    @Column(name = "has_kitchen")
    private boolean hasKitchen;

    @Column(name = "has_washer")
    private boolean hasWasher;

    @Column(name = "has_tv")
    private boolean hasTv;

    @Column(name = "has_internet")
    private boolean hasInternet;

    @Column(name = "is_pets_allowed")
    private boolean isPetsAllowed;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<PropertyImage> propertyImages;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Order order;

    public void addPropertyImage(final PropertyImage propertyImage) {
        this.propertyImages.add(propertyImage);
    }
}