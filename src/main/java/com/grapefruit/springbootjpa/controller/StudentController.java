/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.controller;

import com.grapefruit.springbootjpa.entity.Student;
import com.grapefruit.springbootjpa.repository.StuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 1:00 下午
 */
@RestController
@RequestMapping("/")
public class StudentController {
    @Autowired
    private StuRepo stuRepo;

    @Autowired
    private EntityManager entityManager;

    //@Autowired
    //private StuMapper stuMapper;

    @Version
    @Transactional
    @PostMapping("/updateStudent")
    public String update(@RequestBody Student student) {

        // 查询原有对象
        Student oldStu = stuRepo.findById(student.getId());

        // 拷贝原有属性
        BeanCopyUtil.copyProperties(student,oldStu);

        entityManager.merge(oldStu);
        return LocalDateTime.now() + " success ";
    }

    @PostMapping("/findAllStu")
    public List<Student> findAllStu() {
        return stuRepo.findAll();
    }

    @PostMapping("/saveStu")
    public String saveStu(@RequestBody Student student) {
        stuRepo.save(student);
        return LocalDateTime.now() + " success ";
    }
    
    public String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
