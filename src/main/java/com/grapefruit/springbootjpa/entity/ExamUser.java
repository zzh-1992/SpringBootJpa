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

/**
 * t_exam_user
 *
 * @author
 */
@Data
@Entity
@Table(name = "t_exam_user")
public class ExamUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String uid;
    /**
     * 考试分数
     */
    private Integer score;
    /**
     * 考试状态
     */
    @Column(name = "status")
    private String status;

    @Column(name = "exam_id")
    private Integer examId;
}