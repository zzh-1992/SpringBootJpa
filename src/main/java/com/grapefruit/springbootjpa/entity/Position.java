/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * t_position
 *
 * @author
 */
@Table(name = "t_position")
@Data
@Entity
public class Position {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "TINYINT(4)")
    private Integer id;

    @Column(name = "position_class")
    @NotEmpty
    private String positionClass;

    @NotNull
    private Short level;

    @Column(name = "child_score")
    private Short childScore;

    @Column(name = "parent_size")
    private Short parentSize;

    private String creator;
    private String modifier;

    @Column(name = "creat_time")
    private String creatTime;

    @Column(name = "modify_time")
    private String modifyTime;

    private Short score;
}