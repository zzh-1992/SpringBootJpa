/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.repository;

import com.grapefruit.springbootjpa.entity.Student;
import com.grapefruit.springbootjpa.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-05 10:38 上午
 */

public interface StuRepo extends JpaRepository<Student, Integer> {

    @Query(value = "select s.age from Student s",
            countQuery = "SELECT count(*) FROM Student")
    Page<Student> findByName(String lastname, Pageable pageable);

    @Query(value = "select new Student(s.name,s.age) from Student s",
            countQuery = "SELECT count(*) FROM Student")
    List<Student> findAllNameAndAge();


    @Query(value = "select s.age from t_student s ",nativeQuery = true)
    Page<StudentDto> findByName(Pageable pageable);

    // 返回部分字段
    @Query(value = "select s.name, s.age from t_student s where s.name = ?1",nativeQuery = true)
    List<StudentDto> findAgetAndNameByName(String name);

    //@Query("select s.name,s.classId from Student s where s.name = ?1")
    //List<Student> findByName(String name);

    //@Query("select s.name,s.classId from Student s where s.name = ?1")
    //List<Student> findByName(String name);

    @Query("select s.name,s.classId from Student s where s.name like %?1")
    List<Map<String,Object>> findByNameEndsWith(String name);

    @Query("select s from Student s where s.name like ?1%")
    List<Student> findByNameBeginWith(String name);


    @Query("select s from Student s where s.name like %?1%")
    List<Student> findByNameIsLike(String name);
}
