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

    @Test
    void contextLoads() {
        Student s1 = Student.builder().name("aaa").age(2).classId(101).build();

        Class c1 = Class.builder().cName("class A7").build();
        Class c2 = Class.builder().cName("class B8").build();
        Class c3 = Class.builder().cName("class C9").build();
        List<Class> list = Arrays.asList(c1, c2, c3);

        //stuRepo.save(s1);
        classRepo.saveAll(list);
    }

    @Transactional
    @Test
    void contextLoadsByEntityManager() {
        Class c1 = Class.builder().cName("class G1=======").build();
        Class c2 = Class.builder().cName("class G2").build();
        Class c3 = Class.builder().cName("class G3").build();
        Class c4 = Class.builder().cName("class G4").build();
        Class c5 = Class.builder().cName("class G5").build();
        Class c6 = Class.builder().cName("class G6").build();
        Class c7 = Class.builder().cName("class G7").build();
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
        Class c1 = Class.builder().cName("class G1=======").build();
        Class c2 = Class.builder().cName("class G2").build();
        Class c3 = Class.builder().cName("class G3").build();
        Class c4 = Class.builder().cName("class G4").build();
        Class c5 = Class.builder().cName("class G5").build();
        Class c6 = Class.builder().cName("class G6").build();
        Class c7 = Class.builder().cName("class G7").build();
        List<Class> list = Arrays.asList(c1, c2, c3, c4, c5, c6, c7);

        StringBuilder insert = new StringBuilder("INSERT INTO `t_class` (`c_name`) VALUES ");

        for (int i = 0; i < list.size(); i++) {
            insert.append("(")
                    .append("'")
                    .append(list.get(i).getCName())
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
