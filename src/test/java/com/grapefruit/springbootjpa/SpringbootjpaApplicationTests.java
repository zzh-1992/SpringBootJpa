package com.grapefruit.springbootjpa;

import com.grapefruit.springbootjpa.dao.ClassDao;
import com.grapefruit.springbootjpa.dao.StuDao;
import com.grapefruit.springbootjpa.entity.Class;
import com.grapefruit.springbootjpa.entity.Student;
import com.grapefruit.springbootjpa.repository.StudentInterface;
import com.grapefruit.springbootjpa.service.StuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootjpaApplicationTests {

    @Autowired
    StuService stuService;

    @Autowired
    StuDao stuDao;

    @Autowired
    ClassDao classDao;

    @Test
    void contextLoads() {
        Student s1 = Student.builder().name("aaa").age(2).classId(101).build();

        Class c1 = Class.builder().cName("class A1").build();
        Class c2 = Class.builder().cName("class B1").build();
        Class c3 = Class.builder().cName("class C1").build();
        List<Class> list = Arrays.asList(c1, c2, c3);

        stuDao.save(s1);
        classDao.saveAll(list);
    }

    @Test
    void batchSave() {
        for (int i = 6; i < 20; i++) {
            Student s1 = Student.builder().name("aaa").age(i).classId(100 + i).build();
            stuDao.save(s1);
        }
    }

    @Test
    void findByName() {
        List<StudentInterface> iterater = (List<StudentInterface>) stuDao.findByName("aaaaa");
        System.out.println();
    }

    @Test
    void findByName2() {
        Pageable pageable = Pageable.ofSize(5);
        Page<Student> a1 = stuDao.findByName("a", pageable);
        List<Student> content = a1.getContent();
        System.out.println();

        // 分页查询
        PageRequest page = PageRequest.of(2, 6);
        Page<Student> a2 = stuDao.findByName("a", page);

        System.out.println();
    }

    @Test
    void findByName3() {
        // 分页查询
        PageRequest page = PageRequest.of(2, 6);
        Page<StudentInterface> a2 = stuDao.findByName(page);

        System.out.println();
    }

    @Test
    void findByFirstnameEndsWith() {

        List<Map<String, Object>> list = stuDao.findByNameEndsWith("bcd");
        //Hibernate: select student0_.s_id as s_id1_1_, student0_.age as age2_1_, student0_.class_id as class_id3_1_,
        // student0_.name as name4_1_ from t_student student0_ where student0_.name like ?
        list.forEach(Object::toString);
    }

    @Test
    void findByNameBeginWith() {

        List<Student> list = stuDao.findByNameBeginWith("bcd");
        //Hibernate: select student0_.s_id as s_id1_1_, student0_.age as age2_1_,
        // student0_.class_id as class_id3_1_,
        // student0_.name as name4_1_ from t_student student0_ where student0_.name like ?
        list.forEach(System.out::println);
    }

    @Test
    void findByNameIsLike() {
        List<Student> list = stuDao.findByNameIsLike("bcd");
        //Hibernate: select student0_.s_id as s_id1_1_, student0_.age as age2_1_,
        // student0_.class_id as class_id3_1_, student0_.name as name4_1_ from t_student student0_
        // where student0_.name like ?
        list.forEach(System.out::println);
    }
}
