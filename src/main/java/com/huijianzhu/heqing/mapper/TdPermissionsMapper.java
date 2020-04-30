package com.huijianzhu.heqing.mapper;

import com.huijianzhu.heqing.entity.TdPermissions;

import java.util.List;

public interface TdPermissionsMapper {
    int deleteByPrimaryKey(Integer permissionsId);

    int insert(TdPermissions record);

    int insertSelective(TdPermissions record);

    TdPermissions selectByPrimaryKey(Integer permissionsId);

    int updateByPrimaryKeySelective(TdPermissions record);

    int updateByPrimaryKey(TdPermissions record);

    List<TdPermissions> selectPermissionsAll();
}