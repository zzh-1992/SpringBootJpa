/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.repository;

import com.grapefruit.springbootjpa.entity.SubClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 9:26 上午
 */
public interface SubClassRepo extends JpaRepository<SubClass, Integer> {

    // 依据父类id获取所有子类
    List<SubClass> findByParentId(int parentId);

    @Transactional
    @Modifying
    @Query("delete from SubClass s where s.id=:id")
    int deleteSubClassById(@Param(value = "id") int id);
}
