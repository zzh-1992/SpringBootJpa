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

/**
 * t_child_class
 *
 * @author
 */
@Table(name = "t_parent_class")
@Data
@Entity
public class ParentClass {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "TINYINT(4)")
    private Integer id;
    /**
     * 子类名称
     */
    @NotEmpty
    private String name;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 修改者
     */
    private String modifier;
    /**
     * 创建时间
     */
    @Column(name = "creat_time")
    private String creatTime;
    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private String modifyTime;

    /**
     * 分类说明的跳转链接
     */
    private String link;
}