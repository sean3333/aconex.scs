package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.BlockType;
import com.sean.aconex.scs.constant.CommandType;
import com.sean.aconex.scs.constant.CostType;
import org.junit.Test;

import java.util.stream.Collectors;

public class StringTest {

    @Test
    public void array(){
        String l = "ooto";

        System.out.println(l.chars().mapToObj(s-> BlockType.getBlockType(Character.toString((char) s))).collect(Collectors.toList())
        .stream().map(s->s.name()).collect(Collectors.joining(",")));
    }

    @Test
    public void number(){

        System.out.println(-2%4);
    }

    @Test
    public void forLoop(){
        for(int i = -1; i>=0; i--){
            System.out.println(i);
        }
    }

    @Test
    public void tab(){
        System.out.println("Item\t\t\t\t\t\t\tQuantity\tCost");
        System.out.println(CostType.COMMUNICATION.getDisplayName()+"\t"+123+"\t\t"+"123"+"\t");
        System.out.println(CostType.FUEL.getDisplayName()+"\t"+123+"\t\t"+"123"+"\t");
        System.out.println(CostType.UNCLEARED_BLOCK.getDisplayName()+"\t"+123+"\t\t"+"123"+"\t");
        System.out.println(CostType.DESTRUCTION_PRESERVED_TREE.getDisplayName()+"\t"+123+"\t\t"+"123"+"\t");
        System.out.println(CostType.PAINT_DAMAGE.getDisplayName()+"\t"+123+"\t\t"+"123"+"\t");
        System.out.println("Total\t\t\t\t\t\t\t\t\t\t"+"123");
    }

    @Test
    public void print(){
        System.out.println("aa\n");
        System.out.println("aa");
        System.out.println("bb");
    }
}
