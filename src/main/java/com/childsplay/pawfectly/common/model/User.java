package com.childsplay.pawfectly.common.model;

import com.childsplay.pawfectly.common.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 64)
    private String email;

    @JsonIgnore
    @Column(length = 128)
    private String password;

    @Column(length = 64)
    private String displayName;

    @Column(columnDefinition = "TINYINT(1)")
    @JsonProperty("isActive")
    private boolean isActive;

    @Column(length = 64)
    private String firstName;

    @Column(length = 64)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

}
