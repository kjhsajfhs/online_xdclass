package xyz.kyjef.online_xdclass.service;

import org.apache.ibatis.annotations.Param;
import xyz.kyjef.online_xdclass.domain.User;

import java.util.Map;

public interface UserService {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int save(Map<String, String> userInfo);

    String findByPhoneAndPwd(String phone, String pwd);

    User findByUserId(Integer userId);
}
