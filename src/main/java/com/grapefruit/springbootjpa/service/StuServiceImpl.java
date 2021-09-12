/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.service;

import com.grapefruit.springbootjpa.repository.StuRepo;
import com.grapefruit.springbootjpa.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-05 2:13 下午
 */
@Service
public class StuServiceImpl implements StuService {
    @Autowired
    StuRepo stuRepo;

    @Override
    public List<Student> findByName(String name) {
        //return stuDao.findByName(name);
        return null;
    }

    @Override
    public List<Map<String, Object>> findByNameEndsWith(String name) {
        return stuRepo.findByNameEndsWith(name);
    }

    @Override
    public List<Student> findByNameBeginWith(String name) {
        return stuRepo.findByNameBeginWith(name);
    }

    @Override
    public List<Student> findByNameIsLike(String name) {
        return stuRepo.findByNameIsLike(name);
    }
}
