package xyz.kyjef.online_xdclass.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class VideoOrder {
    private Integer id;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    private Integer state;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonProperty("total_fee")
    private  Integer totalFee;

    @JsonProperty("video_id")
    private Integer videoId;

    @JsonProperty("video_title")
    private String videoTitle;

    @JsonProperty("video_img")
    private String videoImg;

    @JsonProperty("user_id")
    private Integer userId;

    public VideoOrder(Integer id, String outTradeNo, Integer state, Date createTime, Integer totalFee, Integer videoId, String videoTitle, String videoImg, Integer userId) {
        this.id = id;
        this.outTradeNo = outTradeNo;
        this.state = state;
        this.createTime = createTime;
        this.totalFee = totalFee;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoImg = videoImg;
        this.userId = userId;
    }

    public VideoOrder() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle == null ? null : videoTitle.trim();
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg == null ? null : videoImg.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", outTradeNo=").append(outTradeNo);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", totalFee=").append(totalFee);
        sb.append(", videoId=").append(videoId);
        sb.append(", videoTitle=").append(videoTitle);
        sb.append(", videoImg=").append(videoImg);
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }
}