/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.repository;

import com.grapefruit.springbootjpa.entity.ExamConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 5:01 下午
 */
public interface ExamConfigRepo extends JpaRepository<ExamConfig,Integer> {
    // 依据考试id查询对应的考试
    List<ExamConfig> findBySubClassId(Integer subClassId);
}
