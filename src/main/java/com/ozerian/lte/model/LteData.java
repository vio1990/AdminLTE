package com.ozerian.lte.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
