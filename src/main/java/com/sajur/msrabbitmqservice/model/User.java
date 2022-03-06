package com.sajur.msrabbitmqservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "users")
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    private String uid;

    private String password;

    private String first_name;

    private String last_name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String email;

    private String avatar;

    private String gender;

    private String phone_number;

    @Column(unique = true)
    private String social_insurance_number;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_of_birth;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_id")
    private CreditCard credit_card;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

}
