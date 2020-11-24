package xyz.kyjef.online_xdclass.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xyz.kyjef.online_xdclass.domain.Video;
import xyz.kyjef.online_xdclass.domain.VideoBanner;
import xyz.kyjef.online_xdclass.service.VideoBannerService;
import xyz.kyjef.online_xdclass.service.VideoService;
import xyz.kyjef.online_xdclass.utils.JsonData;
import xyz.kyjef.online_xdclass.utils.excel.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
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


    /*导出视频列表
    *
    * */
    @RequestMapping("export")
    public void doExportListVideo(HttpServletRequest request,
                                  HttpServletResponse response){
        try{
            String flag = request.getParameter("flag");
            String headerStr = request.getParameter("columnNames");
            String headerKey = request.getParameter("columnFields");
            List<Video> ls = null;
            if ("all".equals(flag)) {
                ls =  videoService.listVideo();
            } else {
                ls = videoService.listVideo();
            }
            // 定义时间格式
            ExcelUtil.dateFormate = "yyyy-MM-dd";
            ls = ls == null ? new ArrayList<Video>() : ls;
            ExcelUtil.exportExcel("视频信息", headerStr, headerKey, ls,
                    "aaa", response, Video.class, null,null);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*导入视频列表
     *
     * */
    @RequestMapping("excelSaveVideo")
    public void excelSaveVideo(@RequestParam MultipartFile file, HttpServletRequest request,HttpServletResponse response){
        try{
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter =  multiRequest.getFileNames();
            while(iter.hasNext()){
                MultipartFile files = multiRequest.getFile(iter.next());
                if (file!=null){
                    InputStream is = files.getInputStream();
                    String notice = videoService.exportExcelForVideoList(is,file.getOriginalFilename());
                    System.out.println(notice);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }


}
