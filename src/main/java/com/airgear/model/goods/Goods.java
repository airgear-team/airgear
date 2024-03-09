package com.airgear.model.goods;

import com.airgear.model.Complaint;
import com.airgear.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"user", "usersAddedToFavorite"})
@ToString(exclude = {"user", "usersAddedToFavorite"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Goods {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name length must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 1000, message = "Description length must be between 10 and 1000 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    private BigDecimal weekendsPrice;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Embedded
    private Deposit deposit;

    @Embeddable
    class Deposit {
        private BigDecimal amount;
        private Currency currency;

        enum Currency {UAH, EUR, USD}
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "goods_goods_status",
            joinColumns = {
                    @JoinColumn(name = "goods_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "goods_status_id")})
    private GoodsStatus goodsStatus;

    @Size(min = 13, max = 13, message = "The length of the phone number must be at 13")
    @Pattern(regexp = "^\\+380\\d{9}$", message = "The phone number must be in the format +380XXXXXXXXX")
    @JoinColumn(name = "phone")
    private String phoneNumber;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @Column(name = "last_modified")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime lastModified;

    @Column(name = "deleted_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime deletedAt;

    @OneToMany(mappedBy = "goods")
    private List<Complaint> complaints;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_FAVORITE_GOODS",
            joinColumns = {
                    @JoinColumn(name = "GOODS_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "USER_ID")})
    private Set<User> usersAddedToFavorite;
}
