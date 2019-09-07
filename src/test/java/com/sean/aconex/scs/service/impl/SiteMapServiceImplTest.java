package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.service.SiteMapService;
import com.sean.aconex.scs.model.Block;
import static org.junit.Assert.*;

import com.sean.aconex.scs.constant.BlockType;
import static com.sean.aconex.scs.constant.BlockType.*;

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SiteMapServiceImplTest {

    private SiteMapService siteMapService = new SiteMapServiceImpl();

    /**
     * create a site map from file
     * file contains below lines:
     * ootooooooo
     * oooooooToo
     * rrrooooToo
     * rrrroooooo
     * rrrrrtoooo
     */
    @Test
    public void testCreateSiteMap() {

        String fileName = "/siteMapMock.txt";
        URL file = getClass().getResource(fileName);
        System.out.println(file.getFile());
        File f = new File(file.getFile());
        System.out.println(f.getAbsolutePath());

        List<List<Block>> siteMap = siteMapService.createSiteMap(f.getAbsolutePath());

        BlockType[] row1 = new BlockType[]{
                PLAIN_LAND,PLAIN_LAND,TREE_REMOVABLE,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row1, siteMap.get(0).stream().map(Block::getBlockType).toArray());

        BlockType[] row2 = new BlockType[]{
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PRESERVED_TREE,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row2, siteMap.get(1).stream().map(Block::getBlockType).toArray());

        BlockType[] row3 = new BlockType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,PLAIN_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PRESERVED_TREE,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row3, siteMap.get(2).stream().map(Block::getBlockType).toArray());

        BlockType[] row4 = new BlockType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,PLAIN_LAND,
                PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row4, siteMap.get(3).stream().map(Block::getBlockType).toArray());

        BlockType[] row5 = new BlockType[]{
                ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,ROCKY_LAND,
                TREE_REMOVABLE,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND,PLAIN_LAND};
        assertArrayEquals(row5, siteMap.get(4).stream().map(Block::getBlockType).toArray());
    }

    /**
     * test print a site map
     * expected result:
     * o r t T
     * r r t T
     *
     */
    @Test
    public void testPrintSiteMap() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(PLAIN_LAND),new Block(ROCKY_LAND),new Block(TREE_REMOVABLE),new Block(PRESERVED_TREE)));
        siteMap.add(Arrays.asList(new Block(ROCKY_LAND),new Block(ROCKY_LAND),new Block(TREE_REMOVABLE),new Block(PRESERVED_TREE)));

        siteMapService.printSiteMap(siteMap);
    }
}
