package com.kuropatin.bookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role = Roles.ROLE_MODER;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "displayed_name")
    private String displayedName;

    @Column(name = "suspended")
    private boolean isSuspended = false;

    @Column(name = "deleted")
    private boolean isDeleted = false;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}