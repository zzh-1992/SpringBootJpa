/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.service;

import com.grapefruit.springbootjpa.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-05 2:13 下午
 */
public interface StuService {

    List<Student> findByName(String name);


    List<Map<String,Object>> findByNameEndsWith(String name);


    List<Student> findByNameBeginWith(String name);


    List<Student> findByNameIsLike(String name);
}
