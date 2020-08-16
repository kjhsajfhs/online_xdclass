package xyz.kyjef.online_xdclass.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.boot.Banner;
import xyz.kyjef.online_xdclass.domain.Video;

import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> listVideo();

    Video findDetailById(@Param("video_id") Integer videoId);

    /**
     * 简单查询视频信息
     * @param videoId
     * @return
     */
    Video findById(@Param("video_id") int videoId);
}