package com.sajur.msrabbitmqservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employments")
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String title;

    private String key_skill;
}
