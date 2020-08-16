package xyz.kyjef.online_xdclass.service;

import xyz.kyjef.online_xdclass.domain.Video;

import java.util.List;

public interface VideoService {

    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> listVideo();

    Video findDetailById(int videoId);
}
