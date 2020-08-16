package xyz.kyjef.online_xdclass.mapper;

import org.springframework.boot.Banner;
import xyz.kyjef.online_xdclass.domain.VideoBanner;

import java.util.List;

public interface VideoBannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoBanner record);

    int insertSelective(VideoBanner record);

    VideoBanner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoBanner record);

    int updateByPrimaryKey(VideoBanner record);

    List<VideoBanner> listBanner();
}