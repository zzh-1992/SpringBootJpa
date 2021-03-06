/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * student
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-05 10:35 上午
 */
@Entity
@Table(name = "t_student",schema = "jpa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate()
public class Student {

    @Id()
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "class_id")
    private String classId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_student_class",
            joinColumns = {@JoinColumn(name = "s_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "c_id",referencedColumnName = "id")})
    private List<Class> classList;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "favourite")
    private String favourite;

    @Column(name = "up_limit")
    private String upLimit;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
