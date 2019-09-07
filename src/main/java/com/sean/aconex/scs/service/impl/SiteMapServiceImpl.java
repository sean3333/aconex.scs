package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.ScsConstants;
import com.sean.aconex.scs.service.SiteMapService;
import com.sean.aconex.scs.model.Site;
import com.sean.aconex.scs.model.SiteType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SiteMapServiceImpl implements SiteMapService {

    public List<List<Site>> createSiteMap(String fileName) {
        List<List<Site>> siteMap = new ArrayList<>();

        try(Stream<String> stream = Files.lines(Paths.get(fileName))){

            stream.forEach(l-> siteMap.add(l.chars()
                    .mapToObj(s -> new Site(SiteType.getSiteType(Character.toString((char) s))))
                    .collect(Collectors.toList())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return siteMap;
    }

    public void printSiteMap(List<List<Site>> siteMap) {
        System.out.println(ScsConstants.WELCOME + ScsConstants.TITLE_SITEMAP);

        siteMap.forEach(row->System.out.println(
                row.stream().map(s->s.getSiteType().getShortName())
                        .collect(Collectors.joining(" "))));

    }
}
