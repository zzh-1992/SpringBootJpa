package com.grapefruit.springbootjpa.dao;

import com.grapefruit.springbootjpa.entity.Position;

public interface PositionMapper {
    int insert(Position record);

    int insertSelective(Position record);
}