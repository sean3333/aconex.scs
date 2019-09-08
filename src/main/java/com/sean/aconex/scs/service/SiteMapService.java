package com.sean.aconex.scs.service;

import com.sean.aconex.scs.model.Block;

import java.util.List;

public interface SiteMapService {
    /**
     * create a site map from a file
     *
     * @param fileName file contains the site map
     * @return site map list
     */
    List<List<Block>> createSiteMap(String fileName);

    /**
     * print site map in console
     *
     * @param siteMap site map to be printed
     */
    void printSiteMap(List<List<Block>> siteMap);

}
