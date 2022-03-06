package com.sajur.msrabbitmqservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "coordinates")
@ToString
@EqualsAndHashCode
public class Coordinates implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @EqualsAndHashCode.Include
    private double lat;

    @EqualsAndHashCode.Include
    private double lng;
}
