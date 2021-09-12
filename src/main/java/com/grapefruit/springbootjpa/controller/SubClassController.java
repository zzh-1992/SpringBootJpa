/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.controller;

import com.grapefruit.springbootjpa.entity.SubClass;
import com.grapefruit.springbootjpa.repository.SubClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 1:00 下午
 */
@RestController
@RequestMapping("/")
public class SubClassController {
    @Autowired
    private SubClassRepo subClassRepo;

    // parentId需要传 0的时候查询所有,>0的时候查询对应的子类信息
    @GetMapping("/getAllChild")
    public List<SubClass> getExam(@RequestParam int parentId) {
        List<SubClass> all;
        if (parentId > 0) {
            all = subClassRepo.findByParentId(parentId);
        } else {
            all = subClassRepo.findAll();
        }
        return all;
    }

    // 该接口若是传递id,更新数据 若没有ID,则保存数据(批量保存)
    @PostMapping("/saveChildList")
    public String saveChildList(@RequestBody Map<String, List<SubClass>> map) {
        List<SubClass> subClasses = map.get("childClass");
        for (SubClass c : subClasses) {
            Integer id = c.getId();
            if (id == null) {
                c.setCreator("grape");
                c.setCreatTime(getTime());
            } else {
                c.setModifier("grape_modify");
                c.setModifyTime(getTime());
            }

            SubClass save = subClassRepo.save(c);
        }

        return LocalDateTime.now() + " success ";
    }

    @PostMapping("/saveChild")
    public String saveChild(@RequestBody SubClass subClass) {
        Integer id = subClass.getId();
        if (id == null || id == 0) {
            subClass.setCreator("grape");
            subClass.setCreatTime(getTime());
        } else {
            subClass.setModifier("grape_modife");
            subClass.setModifyTime(getTime());
        }

        SubClass save = subClassRepo.save(subClass);

        return LocalDateTime.now() + " success " + save;
    }

    @GetMapping("/deleteSubClass")
    public String deleteSubClass(@RequestParam int id) {
        int i = subClassRepo.deleteSubClassById(id);
        return i == 1 ? "delete successful" : "delete failed";
    }

    public String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
