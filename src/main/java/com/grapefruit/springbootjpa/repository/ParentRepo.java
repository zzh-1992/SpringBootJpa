/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.repository;

import com.grapefruit.springbootjpa.entity.ParentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 9:27 上午
 */
public interface ParentRepo extends JpaRepository<ParentClass, Integer> {

    @Transactional
    @Modifying
    @Query("delete from ParentClass p where p.id=:id")
    int deleteById(@Param(value = "id") int id);
}
