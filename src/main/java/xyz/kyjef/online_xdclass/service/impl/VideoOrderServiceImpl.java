package xyz.kyjef.online_xdclass.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kyjef.online_xdclass.domain.Episode;
import xyz.kyjef.online_xdclass.domain.PlayRecord;
import xyz.kyjef.online_xdclass.domain.Video;
import xyz.kyjef.online_xdclass.domain.VideoOrder;
import xyz.kyjef.online_xdclass.exception.XDException;
import xyz.kyjef.online_xdclass.mapper.EpisodeMapper;
import xyz.kyjef.online_xdclass.mapper.PlayRecordMapper;
import xyz.kyjef.online_xdclass.mapper.VideoMapper;
import xyz.kyjef.online_xdclass.mapper.VideoOrderMapper;
import xyz.kyjef.online_xdclass.service.VideoOrderService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

public class VideoOrderServiceImpl implements VideoOrderService {


    @Autowired
    private VideoOrderMapper videoOrderMapper;



    @Autowired
    private VideoMapper videoMapper;


    @Autowired
    private EpisodeMapper episodeMapper;


    @Autowired
    private PlayRecordMapper playRecordMapper;


    /**
     * 下单操作
     * 未来版本：优惠券抵扣，风控用户检查，生成订单基础信息，生成支付信息
     * @param userId
     * @param videoId
     * @return
     */
    @Override
    @Transactional
    public int save(int userId, int videoId) {

        //判断是否已经购买
        VideoOrder videoOrder = videoOrderMapper.findByUserIdAndVideoIdAndState(userId,videoId,1);

        if(videoOrder!=null){return  0;}

        Video video = videoMapper.findById(videoId);

        VideoOrder newVideoOrder = new VideoOrder();
        newVideoOrder.setCreateTime(new Date());
        newVideoOrder.setOutTradeNo(UUID.randomUUID().toString());
        newVideoOrder.setState(1);
        newVideoOrder.setTotalFee(video.getPrice());
        newVideoOrder.setUserId(userId);

        newVideoOrder.setVideoId(videoId);
        newVideoOrder.setVideoImg(video.getCoverImg());
        newVideoOrder.setVideoTitle(video.getTitle());

        int rows = videoOrderMapper.saveOrder(newVideoOrder);

        //生成播放记录
        if(rows == 1){
            Episode episode = episodeMapper.findFirstEpisodeByVideoId(videoId);
            if(episode == null){
                throw  new XDException(-1,"视频没有集信息，请运营人员检查");
            }
            PlayRecord playRecord = new PlayRecord();
            playRecord.setCreateTime(new Date());
            playRecord.setEpisodeId(episode.getId());
            playRecord.setCurrentNum(episode.getNum());
            playRecord.setUserId(userId);
            playRecord.setVideoId(videoId);
            playRecordMapper.saveRecord(playRecord);
        }

        return rows;
    }


    @Override
    public List<VideoOrder> listOrderByUserId(Integer userId) {

        return videoOrderMapper.listOrderByUserId(userId);
    }
}
