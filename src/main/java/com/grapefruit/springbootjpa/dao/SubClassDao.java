package com.grapefruit.springbootjpa.dao;

import com.grapefruit.springbootjpa.entity.SubClass;

public interface SubClassDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SubClass record);

    int insertSelective(SubClass record);

    SubClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SubClass record);

    int updateByPrimaryKey(SubClass record);
}