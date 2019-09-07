package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.ScsConstants;
import com.sean.aconex.scs.service.SiteMapService;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.BlockType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SiteMapServiceImpl implements SiteMapService {

    @Override
    public List<List<Block>> createSiteMap(String fileName) {
        List<List<Block>> siteMap = new ArrayList<>();

        try(Stream<String> stream = Files.lines(Paths.get(fileName))){

            stream.forEach(l-> siteMap.add(l.chars()
                    .mapToObj(s -> new Block(BlockType.getBlockType(Character.toString((char) s))))
                    .collect(Collectors.toList())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return siteMap;
    }

    @Override
    public void printSiteMap(List<List<Block>> siteMap) {
        System.out.println(ScsConstants.WELCOME + ScsConstants.TITLE_SITEMAP);

        siteMap.forEach(row->System.out.println(
                row.stream().map(s->s.getBlockType().getShortName())
                        .collect(Collectors.joining(" "))));

    }
}
