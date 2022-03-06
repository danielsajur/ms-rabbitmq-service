package com.sajur.msrabbitmqservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subscriptions")
@Data
@ToString
@EqualsAndHashCode
public class Subscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String plan;

    private String status;

    private String payment_method;

    private String term;
}
