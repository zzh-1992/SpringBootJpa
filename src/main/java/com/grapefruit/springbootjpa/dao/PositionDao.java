package com.grapefruit.springbootjpa.dao;

import com.grapefruit.springbootjpa.entity.Position;

public interface PositionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);
}