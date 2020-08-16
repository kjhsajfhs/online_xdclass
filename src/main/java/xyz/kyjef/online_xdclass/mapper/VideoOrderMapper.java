package xyz.kyjef.online_xdclass.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.kyjef.online_xdclass.domain.VideoOrder;

import java.util.List;

public interface VideoOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoOrder record);

    int insertSelective(VideoOrder record);

    VideoOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoOrder record);

    int updateByPrimaryKey(VideoOrder record);

    /**
     * 查询用户是否购买过此商品
     * @param userId
     * @param vidoeId
     * @param state
     * @return
     */
    VideoOrder findByUserIdAndVideoIdAndState(@Param("user_id") int userId, @Param("video_id") int videoId, @Param("state") int state);


    /**
     * 下单
     * @return
     */
    int saveOrder(VideoOrder videoOrder);


    /**
     * 视频列表
     * @param userId
     * @return
     */
    List<VideoOrder> listOrderByUserId(@Param("user_id") Integer userId);
}