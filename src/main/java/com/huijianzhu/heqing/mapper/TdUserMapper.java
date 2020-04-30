package com.huijianzhu.heqing.mapper;

import com.huijianzhu.heqing.entity.TdUser;

import java.util.List;

public interface TdUserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(TdUser record);

    int insertSelective(TdUser record);

    TdUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(TdUser record);

    int updateByPrimaryKey(TdUser record);

    List<TdUser> selectUserAll();
}