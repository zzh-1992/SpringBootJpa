package com.grapefruit.springbootjpa.dao;

import com.grapefruit.springbootjpa.entity.ParentClass;

public interface ParentClassDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ParentClass record);

    int insertSelective(ParentClass record);

    ParentClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParentClass record);

    int updateByPrimaryKey(ParentClass record);
}