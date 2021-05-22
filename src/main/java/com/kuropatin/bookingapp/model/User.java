package com.kuropatin.bookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
//@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
//@Where(clause = "banned = false AND deleted = false")
public class User {

    public User(String login, String password, String name, String surname, Date birthDate, String email, String phone) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "personal_account")
    private long personalAccount;

    @Column(name = "banned")
    private boolean banned;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

//    @JsonIgnore
//    @OneToMany (mappedBy="user", fetch=FetchType.EAGER)
//    private Set<Property> userProperty;
//
//    public void addProperty(Property property) {
//        userProperty.add(property);
//    }
//
//    public void removeProperty(Property property) {
//        userProperty.remove(property);
//    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}