package com.grapefruit.springbootjpa;

import com.grapefruit.springbootjpa.repository.ClassRepo;
import com.grapefruit.springbootjpa.repository.StuRepo;
import com.grapefruit.springbootjpa.entity.Class;
import com.grapefruit.springbootjpa.entity.Student;
import com.grapefruit.springbootjpa.dto.StudentDto;
import com.grapefruit.springbootjpa.service.StuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootjpaApplicationTests {

    @Autowired
    StuService stuService;

    @Autowired
    StuRepo stuRepo;

    @Autowired
    ClassRepo classRepo;


    @Test
    void findAllNameAndAge() {
        List<Student> List = stuRepo.findAllNameAndAge();
        System.out.println();
    }

    @Test
    void contextLoads() {
        Student s1 = Student.builder().name("aaa").age(2).classId(101).build();

        Class c1 = Class.builder().cName("class A1").build();
        Class c2 = Class.builder().cName("class B1").build();
        Class c3 = Class.builder().cName("class C1").build();
        List<Class> list = Arrays.asList(c1, c2, c3);

        stuRepo.save(s1);
        classRepo.saveAll(list);
    }

    @Test
    void batchSave() {
        for (int i = 6; i < 20; i++) {
            Student s1 = Student.builder().name("aaa").age(i).classId(100 + i).build();
            stuRepo.save(s1);
        }
    }

    @Test
    void findByName() {
        //Page<StudentInterface> page = stuDao.findByName("aaaaa");
        System.out.println();
    }

    @Test
    void findAgetAndNameByName0() {
        List<StudentDto> list = stuRepo.findAgetAndNameByName("aaaaa");
        System.out.println();
    }

    @Test
    void findByName2() {
        // 分页查询
        PageRequest page = PageRequest.of(2, 6);
        Page<Student> a2 = stuRepo.findByName("a", page);

        System.out.println();
    }

    @Test
    void findAgetAndNameByName() {
        // 分页查询
        PageRequest page = PageRequest.of(2, 6);
        Page<StudentDto> a2 = stuRepo.findByName(page);

        System.out.println();
    }

    @Test
    void findByFirstnameEndsWith() {

        List<Map<String, Object>> list = stuRepo.findByNameEndsWith("bcd");
        //Hibernate: select student0_.s_id as s_id1_1_, student0_.age as age2_1_, student0_.class_id as class_id3_1_,
        // student0_.name as name4_1_ from t_student student0_ where student0_.name like ?
        list.forEach(Object::toString);
    }

    @Test
    void findByNameBeginWith() {

        List<Student> list = stuRepo.findByNameBeginWith("bcd");
        //Hibernate: select student0_.s_id as s_id1_1_, student0_.age as age2_1_,
        // student0_.class_id as class_id3_1_,
        // student0_.name as name4_1_ from t_student student0_ where student0_.name like ?
        list.forEach(System.out::println);
    }

    @Test
    void findByNameIsLike() {
        List<Student> list = stuRepo.findByNameIsLike("bcd");
        //Hibernate: select student0_.s_id as s_id1_1_, student0_.age as age2_1_,
        // student0_.class_id as class_id3_1_, student0_.name as name4_1_ from t_student student0_
        // where student0_.name like ?
        list.forEach(System.out::println);
    }
}
