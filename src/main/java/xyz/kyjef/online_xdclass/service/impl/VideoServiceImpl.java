package xyz.kyjef.online_xdclass.service.impl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.kyjef.online_xdclass.config.CacheKeyManager;
import xyz.kyjef.online_xdclass.domain.Video;
import xyz.kyjef.online_xdclass.mapper.VideoMapper;
import xyz.kyjef.online_xdclass.service.VideoService;
import xyz.kyjef.online_xdclass.utils.BaseCache;
import xyz.kyjef.online_xdclass.utils.StringUtit;
import xyz.kyjef.online_xdclass.utils.excel.ExcelUtil;

import java.io.InputStream;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return videoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Video record) {
        return videoMapper.insert(record);
    }

    @Override
    public int insertSelective(Video record) {
        return videoMapper.insertSelective(record);
    }

    @Override
    public Video selectByPrimaryKey(Integer id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Video record) {
        return videoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Video record) {
        return videoMapper.updateByPrimaryKey(record);
    }



    @Override
    public List<Video> listVideo() {

        try{
            Object cacheObj =  baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEL_LIST,()->{
                List<Video> videoList = videoMapper.listVideo();
                return videoList;
            });

            if(cacheObj instanceof List){
                List<Video> videoList = (List<Video>)cacheObj;
                return videoList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //可以返回兜底数据，业务系统降级-》SpringCloud专题课程
        return null;
    }

    @Override
    public Video findDetailById(int videoId) {

        //单独构建一个缓存key，每个视频的key是不一样的
        String videoCacheKey = String.format(CacheKeyManager.VIDEO_DETAIL,videoId);

        try{

            Object cacheObject = baseCache.getOneHourCache().get( videoCacheKey, ()->{

                // 需要使用mybaits关联复杂查询
                Video video = videoMapper.findDetailById(videoId);

                return video;

            });

            if(cacheObject instanceof Video){

                Video video = (Video)cacheObject;
                return video;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String exportExcelForVideoList(InputStream is, String fileName) {
        try{
            int addNum = 0;// 统计新增条数
            int subNum = 0;// 成功数
            int errNum = 0;// 失败数
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            // 循环工作表Sheet
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);//获取第一张表
            if (hssfSheet == null) {
                return "导入失败，模板内容不能为空！";
            }
            addNum = hssfSheet.getLastRowNum();//获取行数
            if (addNum == 0) {
                return "导入失败，模板内容不能为空！";
            }

            if (addNum > 300) {
                int realNum = 0;
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {

                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    HSSFCell stuCode = hssfRow.getCell(0);
                    if (StringUtit.isNotEmptyString(stuCode)) {
                        realNum++;
                    }
                }
                if (realNum > 300) {
                    return "导入失败，上传条数不得超过300条！";
                }
            }
            int realNum = 0;
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                Video video = new Video();
                String titile = ExcelUtil.getCellValue(hssfRow.getCell(0));// 标题--必填
                String summary = ExcelUtil.getCellValue(hssfRow.getCell(1));// 地址--必填
                String price = ExcelUtil.getCellValue(hssfRow.getCell(2));// 价格--必填
                String point = ExcelUtil.getCellValue(hssfRow.getCell(3));// 分数--必填
                video.setTitle(titile);
                video.setSummary(summary);
                video.setPrice(Integer.parseInt(price));
                video.setPoint(Double.parseDouble(point));
                int i = videoMapper.insertSelective(video);


            }

        }catch (Exception e){
            e.printStackTrace();
        }



        return "插入成功";
    }
}
