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
@EqualsAndHashCode(callSuper = true, exclude = "order")
@Entity
@Table(name = "review")
public class Review extends BasicEntity {

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private byte rating;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
}