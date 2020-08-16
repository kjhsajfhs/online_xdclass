package xyz.kyjef.online_xdclass.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.kyjef.online_xdclass.domain.Episode;

public interface EpisodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Episode record);

    int insertSelective(Episode record);

    Episode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Episode record);

    int updateByPrimaryKey(Episode record);

    Episode findFirstEpisodeByVideoId(@Param("video_id") int videoId);
}