package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.service.SiteMapService;
import com.sean.aconex.scs.model.Site;
import static org.junit.Assert.*;

import com.sean.aconex.scs.model.SiteType;
import static com.sean.aconex.scs.model.SiteType.*;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SiteMapServiceImplTest {

    private SiteMapService siteMapService = new SiteMapServiceImpl();

    @Test
    public void testCreateSiteMap() {

        String fileName = "/siteMapMock.txt";
        URL file = getClass().getResource(fileName);
        System.out.println(file.getFile());
        File f = new File(file.getFile());
        System.out.println(f.getAbsolutePath());

        List<List<Site>> siteMap = siteMapService.createSiteMap(f.getAbsolutePath());

        SiteType[] row1 = new SiteType[]{
                PLAIN_LAND,PLAIN_LAND,TREE_REMOVABLE,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row1, siteMap.get(0).stream().map(Site::getSiteType).toArray());

        SiteType[] row2 = new SiteType[]{
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PRESERVED_TREE,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row2, siteMap.get(1).stream().map(Site::getSiteType).toArray());

        SiteType[] row3 = new SiteType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PRESERVED_TREE,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row3, siteMap.get(2).stream().map(Site::getSiteType).toArray());

        SiteType[] row4 = new SiteType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row4, siteMap.get(3).stream().map(Site::getSiteType).toArray());

        SiteType[] row5 = new SiteType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,
                TREE_REMOVABLE,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row5, siteMap.get(4).stream().map(Site::getSiteType).toArray());
    }

    @Test
    public void testPrintSiteMap() {
        List<List<Site>> siteMap = new ArrayList<>();

        List<Site> row1 = new ArrayList<>();
        row1.add(new Site(PLAIN_LAND));
        row1.add(new Site(ROCKY_LAND));
        row1.add(new Site(TREE_REMOVABLE));
        row1.add(new Site(PRESERVED_TREE));
        siteMap.add(row1);

        List<Site> row2 = new ArrayList<>();
        row2.add(new Site(ROCKY_LAND));
        row2.add(new Site(ROCKY_LAND));
        row2.add(new Site(TREE_REMOVABLE));
        row2.add(new Site(PRESERVED_TREE));
        siteMap.add(row2);

        siteMapService.printSiteMap(siteMap);
    }
}
