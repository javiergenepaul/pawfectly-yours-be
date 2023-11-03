package com.childsplay.pawfectly.common.model;

import com.childsplay.pawfectly.common.enums.LoginProviderEnum;
import com.childsplay.pawfectly.common.model.User;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LoginProvider")
public class LoginProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private LoginProviderEnum providerName;

    @Column(nullable = false)
    private String providerId;

    @Column(columnDefinition = "TINYINT(1)")
    @JsonProperty("isActive")
    private Boolean isActive;

    @Column(columnDefinition = "TINYINT(1)")
    @JsonProperty("isDeleted")
    private Boolean isDeleted = false;

}
