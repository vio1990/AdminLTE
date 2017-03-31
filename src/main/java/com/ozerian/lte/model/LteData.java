package com.ozerian.lte.model;

import lombok.*;

import javax.persistence.*;

/**
 * Entity class for representation of necessary data.
 */
@Entity
@Table(name = "LteData")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LteData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String renderingEngine;

    private String browser;

    private String platform;

    private String engineVersion;

    private String cssGrade;

}
