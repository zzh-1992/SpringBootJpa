/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */


package com.grapefruit.springbootjpa.repository;

import com.grapefruit.springbootjpa.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-05 10:38 上午
 */

@Mapper
public interface StuMapper {
    @Update({"<script>",
            "update t_student " +
                    "    <set> " +
                    "      <if test='age != null'> " +
                    "        age = #{age,jdbcType=INTEGER}, " +
                    "      </if> " +
                    "      <if test='classId != null'> " +
                    "        class_id = #{classId,jdbcType=VARCHAR}, " +
                    "      </if> " +
                    "      <if test='name != null'> " +
                    "        `name` = #{name,jdbcType=VARCHAR}, " +
                    "      </if> " +
                    "      <if test='favourite != null'> " +
                    "        favourite = #{favourite,jdbcType=VARCHAR}, " +
                    "      </if> " +
                    "      <if test='upLimit != null'> " +
                    "        up_limit = #{upLimit,jdbcType=VARCHAR}, " +
                    "      </if> " +
                    "    </set> " +
                    "    where id = #{id,jdbcType=INTEGER}",
            "</script>"})
    void update(@Param("student") Student student);
}

