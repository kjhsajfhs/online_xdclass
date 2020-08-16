package xyz.kyjef.online_xdclass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import xyz.kyjef.online_xdclass.config.CacheKeyManager;
import xyz.kyjef.online_xdclass.domain.VideoBanner;
import xyz.kyjef.online_xdclass.mapper.VideoBannerMapper;
import xyz.kyjef.online_xdclass.service.VideoBannerService;
import xyz.kyjef.online_xdclass.utils.BaseCache;

import java.util.List;

@Service
public class VideoBannerServiceImpl implements VideoBannerService {

    @Autowired
    private VideoBannerMapper videoBannerMapper;

    @Autowired
    private BaseCache baseCache;


    @Override
    public List<VideoBanner> listBanner() {

        try{

            Object cacheObj =  baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY, ()->{

                List<VideoBanner> bannerList =  videoBannerMapper.listBanner();

                System.out.println("从数据库里面找轮播图列表");

                return bannerList;

            });

            if(cacheObj instanceof List){
                List<VideoBanner> bannerList = (List<VideoBanner>)cacheObj;
                return bannerList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
