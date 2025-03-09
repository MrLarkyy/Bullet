package com.aznos.data;

import java.lang.String;
import org.jetbrains.annotations.NotNull;

public enum DimensionTypes {
  THE_NETHER(0.1,false,8.0,"minecraft:the_nether",true,false,false,256,"#minecraft:infiniburn_nether",128,0,false,true,true,true),

  OVERWORLD(0.0,true,1.0,"minecraft:overworld",false,true,true,384,"#minecraft:infiniburn_overworld",384,-64,true,false,false,false),

  THE_END(0.0,false,1.0,"minecraft:the_end",false,true,false,256,"#minecraft:infiniburn_end",256,0,false,false,false,false);

  public final float ambientLight;

  public final boolean bedWorks;

  public final double coordinateScale;

  public final String effects;

  public final boolean hasCeiling;

  public final boolean hasRaids;

  public final boolean hasSkyLight;

  public final int height;

  public final String infiniburn;

  public final int logicalHeight;

  public final int minY;

  public final boolean natural;

  public final boolean piglinSafe;

  public final boolean respawnAnchorWorks;

  public final boolean ultrawarm;

  DimensionTypes(@NotNull double ambientLight, @NotNull boolean bedWorks,
      @NotNull double coordinateScale, @NotNull String effects, @NotNull boolean hasCeiling,
      @NotNull boolean hasRaids, @NotNull boolean hasSkyLight, @NotNull int height,
      @NotNull String infiniburn, @NotNull int logicalHeight, @NotNull int minY,
      @NotNull boolean natural, @NotNull boolean piglinSafe, @NotNull boolean respawnAnchorWorks,
      @NotNull boolean ultrawarm) {
    this.ambientLight = (float) ambientLight;
    this.bedWorks = bedWorks;
    this.coordinateScale = coordinateScale;
    this.effects = effects;
    this.hasCeiling = hasCeiling;
    this.hasRaids = hasRaids;
    this.hasSkyLight = hasSkyLight;
    this.height = height;
    this.infiniburn = infiniburn;
    this.logicalHeight = logicalHeight;
    this.minY = minY;
    this.natural = natural;
    this.piglinSafe = piglinSafe;
    this.respawnAnchorWorks = respawnAnchorWorks;
    this.ultrawarm = ultrawarm;
  }
}
