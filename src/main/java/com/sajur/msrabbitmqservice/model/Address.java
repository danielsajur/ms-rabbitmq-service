package com.sajur.msrabbitmqservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "addresses")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @EqualsAndHashCode.Include
    private String city;

    private String street_name;

    private String street_address;

    @EqualsAndHashCode.Include
    private String zip_code;

    @EqualsAndHashCode.Include
    private String state;

    @EqualsAndHashCode.Include
    private String country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates;

}
