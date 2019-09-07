package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.service.SiteMapService;
import com.sean.aconex.scs.service.com.sean.aconex.scs.model.Site;
import static org.junit.Assert.*;

import com.sean.aconex.scs.service.com.sean.aconex.scs.model.SiteType;
import static com.sean.aconex.scs.service.com.sean.aconex.scs.model.SiteType.*;
import org.junit.Test;

import java.util.List;


class SiteMapServiceImplTest {

    private SiteMapService siteMapService = new SiteMapServiceImpl();

    @Test
    void testCreateSiteMap() {

        String fileName = "/siteMapMock.txt";

        List<List<Site>> siteMap = siteMapService.createSiteMap(fileName);

        SiteType[] row1 = new SiteType[]{
                PLAIN_LAND,PLAIN_LAND,TREE_REMOVABLE,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row1, siteMap.get(0));

        SiteType[] row2 = new SiteType[]{
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PRESERVED_TREE,PLAIN_LAND,PLAIN_LAND};

        SiteType[] row3 = new SiteType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PRESERVED_TREE,PLAIN_LAND,PLAIN_LAND};

        SiteType[] row4 = new SiteType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};

        SiteType[] row5 = new SiteType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,
                TREE_REMOVABLE,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};
    }

    @Test
    void testPrintSiteMap() {
    }
}
