package com.airgear.model;

import com.airgear.model.goods.Goods;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"goods", "favoriteGoods", "userReviews", "complaints"})
@ToString(exclude = {"goods", "favoriteGoods","userReviews", "complaints"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String email;

    @Column
    //@Pattern(regexp = "^\\+380\\d{9}$", message = "the phones must be in the format +380XXXXXXXXX")
    private String phone;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Goods> goods;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deleteAt;

    @Column(name = "last_activity")
    private OffsetDateTime lastActivity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_status_id")
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "reviewedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserReview> userReviews;

    @OneToMany(mappedBy = "user")
    private List<Complaint> complaints;

    @Column
    private Float rating;

    @Column(name = "is_potentially_scam", nullable = false)
    @JsonIgnore
    private boolean isPotentiallyScam = false;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_FAVORITE_GOODS",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "GOODS_ID")})
    private Set<Goods> favoriteGoods;

}
