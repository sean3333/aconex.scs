package com.sean.aconex.scs.model;

public class Site {

    public Site(){}

    public Site(SiteType siteType){
        this.siteType = siteType;
    }

    private SiteType siteType;

    private boolean cleaned = false;

    private int visitingTimesAfterCleaned = 0;

    private boolean stoppedWhenCleaning = false;

    public boolean isCleaned() {
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }

    public int getVisitingTimesAfterCleaned() {
        return visitingTimesAfterCleaned;
    }

    public void setVisitingTimesAfterCleaned(int visitingTimesAfterCleaned) {
        this.visitingTimesAfterCleaned = visitingTimesAfterCleaned;
    }

    public boolean isStoppedWhenCleaning() {
        return stoppedWhenCleaning;
    }

    public void setStoppedWhenCleaning(boolean stoppedWhenCleaning) {
        this.stoppedWhenCleaning = stoppedWhenCleaning;
    }

    public SiteType getSiteType() {
        return siteType;
    }

    public void setSiteType(SiteType siteType) {
        this.siteType = siteType;
    }
}
