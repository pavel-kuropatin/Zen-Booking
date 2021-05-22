package com.kuropatin.bookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "property")
@SQLDelete(sql = "UPDATE property SET deleted = true WHERE id=?")
@Where(clause = "banned = false AND deleted = false")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "type")
    private String type;

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
    private String apartment;

    @Column(name = "price")
    private int price;

    @Column(name = "guests")
    private short guests;

    @Column(name = "rooms")
    private short rooms;

    @Column(name = "beds")
    private short beds;

    @Column(name = "kitchen")
    private boolean kitchen;

    @Column(name = "washer")
    private boolean washer;

    @Column(name = "tv")
    private boolean tv;

    @Column(name = "internet")
    private boolean internet;

    @Column(name = "pets_allowed")
    private boolean petsAllowed;

    @Column(name = "available")
    private boolean available;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "banned")
    private boolean banned;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

//    @ManyToOne(optional = false, cascade=CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public void removeUser(User user) {
//        this.user = null;
//    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}