package xyz.kyjef.online_xdclass.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.kyjef.online_xdclass.domain.Video;
import xyz.kyjef.online_xdclass.domain.VideoBanner;
import xyz.kyjef.online_xdclass.service.VideoBannerService;
import xyz.kyjef.online_xdclass.service.VideoService;
import xyz.kyjef.online_xdclass.utils.BaseCache;
import xyz.kyjef.online_xdclass.utils.JsonData;

import java.util.List;

@RestController
@RequestMapping("api/v1/pub/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoBannerService videoBannerService;


    /**
     * 轮播图列表
     * @return
     */
    @GetMapping("indexBanner")
    public  JsonData indexBanner(){

      List<VideoBanner> bannerList= videoBannerService.listBanner();
      return JsonData.buildSuccess(bannerList,"查询成功");
    }

    /**
     * 视频列表
     * @return
     */
    @RequestMapping("listVideo")
    public Object listVideo(){
        List<Video> videoList = videoService.listVideo();
        return JsonData.buildSuccess(videoList,"查询成功");
    }


    /**
     * 查询视频详情 包裹章、集
     * @param videoId
     * @return
     */
    @RequestMapping("findDetailById")
    public  JsonData findDetailById(@RequestParam(value = "video_id",required = true) int videoId){
     Video video=videoService.findDetailById(videoId);
     return  JsonData.buildSuccess(video,"查询成功");
    }


}
