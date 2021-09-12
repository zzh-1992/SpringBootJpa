/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.controller;

import com.grapefruit.springbootjpa.entity.ParentClass;
import com.grapefruit.springbootjpa.repository.ParentRepo;
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

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-09-11 9:39 上午
 */
@RestController
@RequestMapping("/")
public class ParentController {

    // add delete update cha

    @Autowired
    ParentRepo parentRepo;

    @GetMapping("/getAllParent")
    public List<ParentClass> getExam() {
        List<ParentClass> all = parentRepo.findAll();
        return all;
    }

    @GetMapping("/deleteParent")
    public String deleteParent(@RequestParam int id) {
        int i = parentRepo.deleteById(id);
        return i == 1 ? "delete successful" : "delete failed";
    }

    // 该接口若是传递id,更新数据 若没有ID,则保存数据
    @PostMapping("/saveParent")
    public String saveParent(@RequestBody ParentClass parentClass) {
        Integer id = parentClass.getId();
        if (id == null) {
            parentClass.setCreatTime(getTime());
            parentClass.setCreator("grape");
        } else {
            parentClass.setModifyTime(getTime());
            parentClass.setModifier("grape");
        }

        ParentClass save = parentRepo.save(parentClass);
        return LocalDateTime.now() + " " + save;
    }


    public String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
