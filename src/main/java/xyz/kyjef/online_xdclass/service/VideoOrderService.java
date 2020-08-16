package xyz.kyjef.online_xdclass.service;

import xyz.kyjef.online_xdclass.domain.VideoOrder;

import java.util.List;

public interface VideoOrderService {


    int save(int userId, int videoId);

    List<VideoOrder> listOrderByUserId(Integer userId);
}
