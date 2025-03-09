package com.aznos.data;

import java.lang.String;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum PaintingVariants {
  FINDING("minecraft:finding",2,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.finding.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.finding.author\"}"),

  POND("minecraft:pond",4,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.pond.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.pond.author\"}"),

  COURBET("minecraft:courbet",1,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.courbet.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.courbet.author\"}"),

  COTAN("minecraft:cotan",3,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.cotan.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.cotan.author\"}"),

  WATER("minecraft:water",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.water.title\"}",null),

  POOL("minecraft:pool",1,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.pool.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.pool.author\"}"),

  UNPACKED("minecraft:unpacked",4,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.unpacked.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.unpacked.author\"}"),

  ORB("minecraft:orb",4,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.orb.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.orb.author\"}"),

  WIND("minecraft:wind",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.wind.title\"}",null),

  BOUQUET("minecraft:bouquet",3,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.bouquet.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.bouquet.author\"}"),

  CREEBET("minecraft:creebet",1,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.creebet.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.creebet.author\"}"),

  STAGE("minecraft:stage",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.stage.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.stage.author\"}"),

  BURNING_SKULL("minecraft:burning_skull",4,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.burning_skull.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.burning_skull.author\"}"),

  BOMB("minecraft:bomb",1,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.bomb.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.bomb.author\"}"),

  WASTELAND("minecraft:wasteland",1,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.wasteland.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.wasteland.author\"}"),

  POINTER("minecraft:pointer",4,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.pointer.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.pointer.author\"}"),

  TIDES("minecraft:tides",3,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.tides.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.tides.author\"}"),

  SUNFLOWERS("minecraft:sunflowers",3,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.sunflowers.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.sunflowers.author\"}"),

  PRAIRIE_RIDE("minecraft:prairie_ride",2,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.prairie_ride.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.prairie_ride.author\"}"),

  SKULL_AND_ROSES("minecraft:skull_and_roses",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.skull_and_roses.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.skull_and_roses.author\"}"),

  EARTH("minecraft:earth",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.earth.title\"}",null),

  PLANT("minecraft:plant",1,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.plant.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.plant.author\"}"),

  LOWMIST("minecraft:lowmist",2,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.lowmist.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.lowmist.author\"}"),

  KEBAB("minecraft:kebab",1,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.kebab.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.kebab.author\"}"),

  CHANGING("minecraft:changing",2,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.changing.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.changing.author\"}"),

  BUST("minecraft:bust",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.bust.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.bust.author\"}"),

  PASSAGE("minecraft:passage",2,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.passage.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.passage.author\"}"),

  HUMBLE("minecraft:humble",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.humble.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.humble.author\"}"),

  WITHER("minecraft:wither",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.wither.title\"}",null),

  BACKYARD("minecraft:backyard",4,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.backyard.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.backyard.author\"}"),

  VOID("minecraft:void",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.void.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.void.author\"}"),

  AZTEC("minecraft:aztec",1,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.aztec.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.aztec.author\"}"),

  FIRE("minecraft:fire",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.fire.title\"}",null),

  AZTEC2("minecraft:aztec2",1,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.aztec2.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.aztec2.author\"}"),

  MEDITATIVE("minecraft:meditative",1,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.meditative.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.meditative.author\"}"),

  SKELETON("minecraft:skeleton",3,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.skeleton.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.skeleton.author\"}"),

  PIGSCENE("minecraft:pigscene",4,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.pigscene.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.pigscene.author\"}"),

  MATCH("minecraft:match",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.match.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.match.author\"}"),

  ALBAN("minecraft:alban",1,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.alban.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.alban.author\"}"),

  CAVEBIRD("minecraft:cavebird",3,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.cavebird.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.cavebird.author\"}"),

  BAROQUE("minecraft:baroque",2,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.baroque.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.baroque.author\"}"),

  ENDBOSS("minecraft:endboss",3,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.endboss.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.endboss.author\"}"),

  WANDERER("minecraft:wanderer",2,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.wanderer.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.wanderer.author\"}"),

  FERN("minecraft:fern",3,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.fern.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.fern.author\"}"),

  SEA("minecraft:sea",1,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.sea.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.sea.author\"}"),

  FIGHTERS("minecraft:fighters",2,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.fighters.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.fighters.author\"}"),

  SUNSET("minecraft:sunset",1,2,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.sunset.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.sunset.author\"}"),

  OWLEMONS("minecraft:owlemons",3,3,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.owlemons.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.owlemons.author\"}"),

  GRAHAM("minecraft:graham",2,1,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.graham.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.graham.author\"}"),

  DONKEY_KONG("minecraft:donkey_kong",3,4,"{\"color\":\"yellow\",\"translate\":\"painting.minecraft.donkey_kong.title\"}","{\"color\":\"gray\",\"translate\":\"painting.minecraft.donkey_kong.author\"}");

  @NotNull
  public final String assetId;

  @NotNull
  public final int height;

  @NotNull
  public final int width;

  @Nullable
  public final String title;

  @Nullable
  public final String author;

  PaintingVariants(@NotNull String assetId, @NotNull int height, @NotNull int width,
      @Nullable String title, @Nullable String author) {
    this.assetId = assetId;
    this.height = height;
    this.width = width;
    this.title = title;
    this.author = author;
  }
}
