package com.sajur.msrabbitmqservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "credit_cards")
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @EqualsAndHashCode.Include
    private String cc_number;
}
