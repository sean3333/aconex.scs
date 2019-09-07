package com.sean.aconex.scs.service;

import com.sean.aconex.scs.service.com.sean.aconex.scs.model.Site;

import java.util.List;

public interface SiteMapService {
    List<List<Site>> createSiteMap(String fileName);

    void printSiteMap(List<List<Site>> siteMap);

}
