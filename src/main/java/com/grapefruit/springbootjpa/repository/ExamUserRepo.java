/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.repository;

import com.grapefruit.springbootjpa.entity.ExamUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 5:17 下午
 */
public interface ExamUserRepo extends JpaRepository<ExamUser,Integer> {
    ExamUser findByUidAndExamId(String uid,int examId);
}
