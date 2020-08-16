package xyz.kyjef.online_xdclass.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Episode {
    private Integer id;

    private String title;

    private Integer num;

    private Integer ordered;

    @JsonProperty("play_url")
    private String playUrl;

    @JsonProperty("chapter_id")
    private Integer chapterId;

    private Byte free;

    @JsonProperty("video_id")
    private Integer videoId;


    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Episode(Integer id, String title, Integer num, Integer ordered, String playUrl, Integer chapterId, Byte free, Integer videoId, Date createTime) {
        this.id = id;
        this.title = title;
        this.num = num;
        this.ordered = ordered;
        this.playUrl = playUrl;
        this.chapterId = chapterId;
        this.free = free;
        this.videoId = videoId;
        this.createTime = createTime;
    }

    public Episode() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl == null ? null : playUrl.trim();
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Byte getFree() {
        return free;
    }

    public void setFree(Byte free) {
        this.free = free;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", num=").append(num);
        sb.append(", ordered=").append(ordered);
        sb.append(", playUrl=").append(playUrl);
        sb.append(", chapterId=").append(chapterId);
        sb.append(", free=").append(free);
        sb.append(", videoId=").append(videoId);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}