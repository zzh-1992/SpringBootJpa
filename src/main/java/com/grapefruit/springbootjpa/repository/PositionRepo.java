/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.repository;

import com.grapefruit.springbootjpa.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 9:28 上午
 */
public interface PositionRepo extends JpaRepository<Position,Integer> {

    Position findByPositionClassAndLevel(String positionClass,Short level);
}
