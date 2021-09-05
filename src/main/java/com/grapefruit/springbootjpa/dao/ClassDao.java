/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.dao;

import com.grapefruit.springbootjpa.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-05 10:51 上午
 */
public interface ClassDao extends JpaRepository<Class,Integer> {


}
