/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.controller;

import com.grapefruit.springbootjpa.entity.SubClass;
import com.grapefruit.springbootjpa.entity.ExamConfig;
import com.grapefruit.springbootjpa.entity.ExamUser;
import com.grapefruit.springbootjpa.entity.ParentClass;
import com.grapefruit.springbootjpa.entity.Position;
import com.grapefruit.springbootjpa.repository.ExamConfigRepo;
import com.grapefruit.springbootjpa.repository.ExamUserRepo;
import com.grapefruit.springbootjpa.repository.ParentRepo;
import com.grapefruit.springbootjpa.repository.PositionRepo;
import com.grapefruit.springbootjpa.repository.SubClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 1:44 下午
 */
@RestController
@RequestMapping("/")
public class ExamController {
    @Autowired
    private PositionRepo positionRepo;

    @Autowired
    private ExamConfigRepo examConfigRepo;

    @Autowired
    private ExamUserRepo examUserRepo;

    @Autowired
    private ParentRepo parentRepo;

    @Autowired
    private SubClassRepo subClassRepo;

    @PostMapping("/getExam")
    public Map<Integer, Integer> getExam(@RequestBody Position position, @RequestParam String uid) {
        // 请求 M 3职业级别
        Short level = position.getLevel();
        String positionClass = position.getPositionClass();

        // 查询这个这个职业对应的position id （position id = 3;）
        Position p = positionRepo.findByPositionClassAndLevel(positionClass, level);

        // 获取该级别对应的分数要求
        Short score = p.getScore();

        // 获取4个大类对应的id
        List<Integer> ids = parentRepo.findAll().stream().map(ParentClass::getId).collect(Collectors.toList());

        // 考试分类存储在map里
        Map<Integer, Integer> map = new HashMap();
        for (int parentId : ids) {
            List<Integer> subClassList =
                    subClassRepo.findByParentId(parentId).stream().map(SubClass::getId).collect(Collectors.toList());
            // TODO 循环一次处理一个大类下的所有考试
            Map<Integer,List<ExamUser>> examUserMap = new HashMap();

            List<ExamUser> examUsers = new ArrayList<>();
            subClassList.forEach(subClassId -> {
                List<ExamUser> examUserList = handleSubClass(uid, subClassId);
                examUsers.addAll(examUserList);
            });
            int subScore = examUsers.stream().mapToInt(ExamUser::getScore).sum();
            map.put(parentId,subScore);
        }

        return map;
    }

    public List<ExamUser> handleSubClass(String uid, Integer subClassId) {
        // 使用 subClassId = 3 到 考试配置表查询 该子类类型的考试
        List<ExamConfig> examConfigs = examConfigRepo.findBySubClassId(subClassId);

        // 获取对应的考试id
        List<Integer> examIdList = examConfigs.stream().map(ExamConfig::getId).collect(Collectors.toList());

        // 该集合存储里考试成绩
        List<ExamUser> list = new ArrayList<>();
        for (int examId : examIdList) {
            // 使用考试id和uid查询用户的这个考试成绩
            ExamUser examUser = examUserRepo.findByUidAndExamId(uid, examId);
            list.add(examUser);
        }
        return list;
    }
}
