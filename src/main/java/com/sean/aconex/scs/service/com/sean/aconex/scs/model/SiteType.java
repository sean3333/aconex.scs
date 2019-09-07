package com.sean.aconex.scs.service.com.sean.aconex.scs.model;

public enum SiteType {
    PLAIN_LAND("o"),
    ROCKY_LAND("r"),
    TREE_REMOVABLE("t"),
    PRESERVED_TREE("T");

    private String shortName;

    SiteType(String shortName){
        this.shortName = shortName;
    }

    public static SiteType getSiteType(String shortname){
        for (SiteType siteType : SiteType.values()) {
            if(siteType.shortName.equals(shortname))
                return siteType;
        }
        return null;
    }
}
