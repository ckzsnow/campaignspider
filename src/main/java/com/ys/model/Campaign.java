package com.ys.model;

import java.sql.Timestamp;

public class Campaign {
    private Long id;

    private String actName;

    private String actTime;
    //目的地
    private String actDestination;
    //收藏人数
    private Integer actEnrollSum;
    //报名人数
    private Integer actInterestSum;
    //活动快照
    private String actSnapshot;
    //发起人
    private String actOriginator;
    
    private String actOriginatorImage;
    //报名链接
    private String actEnroll;

    private Timestamp createTime;

    private String actDetails;

    public Campaign() {
    	this.createTime = new Timestamp(System.currentTimeMillis());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName == null ? null : actName.trim();
    }

    public String getActTime() {
        return actTime;
    }

    public void setActTime(String actTime) {
        this.actTime = actTime == null ? null : actTime.trim();
    }

    public String getActDestination() {
        return actDestination;
    }

    public void setActDestination(String actDestination) {
        this.actDestination = actDestination == null ? null : actDestination.trim();
    }

    public Integer getActEnrollSum() {
        return actEnrollSum;
    }

    public void setActEnrollSum(Integer actEnrollSum) {
        this.actEnrollSum = actEnrollSum;
    }

    public Integer getActInterestSum() {
        return actInterestSum;
    }

    public void setActInterestSum(Integer actInterestSum) {
        this.actInterestSum = actInterestSum;
    }

    public String getActSnapshot() {
        return actSnapshot;
    }

    public void setActSnapshot(String actSnapshot) {
        this.actSnapshot = actSnapshot == null ? null : actSnapshot.trim();
    }

    public String getActOriginator() {
        return actOriginator;
    }

    public void setActOriginator(String actOriginator) {
        this.actOriginator = actOriginator == null ? null : actOriginator.trim();
    }

    public String getActOriginatorImage() {
        return actOriginatorImage;
    }

    public void setActOriginatorImage(String actOriginatorImage) {
        this.actOriginatorImage = actOriginatorImage == null ? null : actOriginatorImage.trim();
    }

    public String getActEnroll() {
        return actEnroll;
    }

    public void setActEnroll(String actEnroll) {
        this.actEnroll = actEnroll == null ? null : actEnroll.trim();
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getActDetails() {
        return actDetails;
    }

    public void setActDetails(String actDetails) {
        this.actDetails = actDetails == null ? null : actDetails.trim();
    }
}