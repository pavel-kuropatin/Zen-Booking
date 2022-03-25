package com.kuropatin.zenbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = "property")
@Entity
@Table(name = "property_image")
public class PropertyImage extends BasicEntity {

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference
    private Property property;
}