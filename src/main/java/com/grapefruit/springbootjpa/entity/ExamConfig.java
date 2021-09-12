/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 考试配置表
 *
 * @TableName t_exam_config
 */
@Table(name = "t_exam_config")
@Data
@Entity
public class ExamConfig {

    @Id
    // 考试id
    private Integer id;

    // 考试名称
    private String name;

    // 考试链接
    private String link;

    // 子考试考试类型
    @Column(name = "sub_class_id")
    private Integer subClassId;
}