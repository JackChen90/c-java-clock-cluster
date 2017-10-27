package indi.jackie.clock.cluster.dao;

import indi.jackie.clock.cluster.model.UserClockTime;

public interface UserClockTimeMapper {
    int deleteByPrimaryKey(Integer id);

    UserClockTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserClockTime record);

    int updateByPrimaryKey(UserClockTime record);
}