package com.grapefruit.springbootjpa;

import com.alibaba.fastjson.JSON;
import com.grapefruit.springbootjpa.dto.StudentDto;
import com.grapefruit.springbootjpa.entity.Class;
import com.grapefruit.springbootjpa.entity.Student;
import com.grapefruit.springbootjpa.repository.ClassRepo;
import com.grapefruit.springbootjpa.repository.StuRepo;
import com.grapefruit.springbootjpa.service.StuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootjpaApplicationTests {

    private static final int BATCH_SIZE = 3;
    @Autowired
    StuService stuService;
    @Autowired
    StuRepo stuRepo;
    @Autowired
    ClassRepo classRepo;
    // use EntityManager
    @Autowired
    EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAllNameAndAge() {
        List<Student> List = stuRepo.findAllNameAndAge();
        System.out.println();
    }

    // 多对多查询
    @Test
    void findAllStu() {
        List<Student> List = stuRepo.findAll();
        System.out.println();

        List<Class> classes = classRepo.findAll();
        System.out.println();
    }

    //多对多添加
    @Test
    void saveStu(){
        Student stu = new Student();
        stu.setAge(22);
        stu.setName("ZZH");

        List<Class> classs = new ArrayList<>();
        Class c1 = new Class(1);
        Class c2 = new Class(2);
        Class c3 = new Class(33);
        classs.add(c1);
        classs.add(c2);
        classs.add(c3);

        // 定义偏好学科
        String jsonString = JSON.toJSONString(classRepo.findAll());
        stu.setFavourite(jsonString);

        // 定义上限
        stu.setUpLimit("100");

        stu.setClassList(classs);

        stuRepo.save(stu);
    }

    // 多对多删除
    @Test
    void deleteStu(){
        stuRepo.deleteAllById(38);
    }

    // 多对多删除(删除班级后同时删除对应的学生)
    @Test
    void deleteClass(){
        //classRepo.deleteAllById(Collections.singletonList(1));
        classRepo.deleteAll();
    }



   /* @Test
    void contextLoads() {
        Student s1 = Student.builder().name("aaa").age(2).classId("101").build();

        Class c1 = Class.builder().cName("class A7").build();
        Class c2 = Class.builder().cName("class B8").build();
        Class c3 = Class.builder().cName("class C9").build();
        List<Class> list = Arrays.asList(c1, c2, c3);

        //stuRepo.save(s1);
        classRepo.saveAll(list);
    }*/

    @Transactional
    @Test
    void contextLoadsByEntityManager() {
        Class c1 = Class.builder().name("class G1=======").build();
        Class c2 = Class.builder().name("class G2").build();
        Class c3 = Class.builder().name("class G3").build();
        Class c4 = Class.builder().name("class G4").build();
        Class c5 = Class.builder().name("class G5").build();
        Class c6 = Class.builder().name("class G6").build();
        Class c7 = Class.builder().name("class G7").build();
        List<Class> list = Arrays.asList(c1, c2, c3, c4, c5, c6, c7);

        Iterator<Class> iterator = list.iterator();

        int index = 0;
        while (iterator.hasNext()) {
            entityManager.persist(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (index % BATCH_SIZE != 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Transactional
    @Test
    void batchSaveWithJDBC() {
        Class c1 = Class.builder().name("class G1=======").build();
        Class c2 = Class.builder().name("class G2").build();
        Class c3 = Class.builder().name("class G3").build();
        Class c4 = Class.builder().name("class G4").build();
        Class c5 = Class.builder().name("class G5").build();
        Class c6 = Class.builder().name("class G6").build();
        Class c7 = Class.builder().name("class G7").build();
        List<Class> list = Arrays.asList(c1, c2, c3, c4, c5, c6, c7);

        StringBuilder insert = new StringBuilder("INSERT INTO `t_class` (`c_name`) VALUES ");

        for (int i = 0; i < list.size(); i++) {
            insert.append("(")
                    .append("'")
                    .append(list.get(i).getName())
                    .append("'")
                    .append(")");
            if (i < list.size() - 1) {
                insert.append(",");
            }
        }
        String sql = (String) JSON.toJSON(insert);
        System.out.println("SQL语句:{}" + JSON.toJSON(insert));
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            System.out.println("sql解析错误" + e.getMessage());
        }
    }

   /* @Test
    void batchSave() {
        for (int i = 6; i < 20; i++) {
            Student s1 = Student.builder().name("aaa").age(i).classId("100" + i).build();
            stuRepo.save(s1);
        }
    }*/

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
