package com.gdxengine.framework.test.towerdefense;

public class GameSpecs {
    //Price for build the Lv.1 weapon
    public static final int canon_cost_first = 50;
    public static final int gun_cost_first = 50;
    public static final int slower_cost_first = 50;
    //Hit point of monster for each wave level
    public static final int[] hitPoint = { 100, 150, 200, 250, 300, 500, 800, 1200, 1700, 2500, 3500, 4500, 6000, 10000, 200000};
    //bonus of monster for each wave level
    public static final int[] bonus = { 50, 100, 150, 200, 250, 300, 400, 500, 600, 800, 1000, 1200, 1300, 1700, 2000};
    //speed of monster for each wave level
    public static final float[] speed = { 1.0f, 1.2f, 1.4f, 2f, 4f, 1.5f, 2f, 3f, 4f, 2f, 1.5f, 2f, 3f, 4f, 5f};
    //time to release a monster in a wave
    public static final float[] timeToRelease = { .9f, .9f, .8f, 2f, 1f, .5f, .5f, .9f, .4f, .6f, 1.1f, 2.5f, 3.5f, 4.5f, 5.5f};
    //number of monster for each wave level
    public static final int[] numberOfMonster = { 10, 13, 16, 20, 20, 15, 20, 22, 25, 20, 15, 10, 8, 6, 2};
    //cost for upgrade gun weapon
    public static final int[] gun_cost = {100,150,200,250,300,350,400,800,900,1000};
    //range of gun weapon
    public static final int[] gun_range = {100,110,120,130,140,150,160,170,180,200};
    //damage of gun weapon
    public static final int[] gun_damage = {10,15,25,40,55,70,90,110,135,160};
    //cost for upgrade canon weapon
    public static final int[] canon_cost = {100,200,300,400,500,600,700,800,900,1000};
    //range of canon weapon
    public static final int[] canon_range = {100,120,140,160,180,200,220,240,260,300};
    //damage of canon weapon
    public static final int[] canon_damage = {30,60,120,160,210,280,350,430,500,600};
    //time period for canon firing 
    public static final float[] canon_timeToAction = {1f, .95f,.9f,.85f,.8f,.75f,.7f,.65f,.55f,.5f};
    //cost for upgrade slower weapon
    public static final int[] slower_cost = {100,200,300,400,500,600,700,800,900,1000};
    //range of slower weapon
    public static final int[] slower_range = {100,120,140,160,180,200,220,240,260,300};
    //damage of slower weapon
    public static int[] slower_damage = {10,15,25,40,55,70,90,110,135,160};
    //The slow amount of slower
    public static final float[] slower_slowerRate = {.2f,.3f,.4f, .45f, .5f, .55f, .6f, .65f , .8f,.9f};
    //time period for slower firing
    public static final float[] slower_timeToAction = {1f, .95f,.9f,.85f,.8f,.75f,.7f,.65f,.55f,.5f};
    //time period for monster restore origin their speed
    public static final float[] slower_timeToRestore = {1f, 1.2f, 1.4f, 1.6f, 1.8f, 2.2f, 2.5f, 3f, 3.5f, 5f}; 
}
