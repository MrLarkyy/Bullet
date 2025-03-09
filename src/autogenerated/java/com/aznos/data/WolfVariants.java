package com.aznos.data;

import java.lang.String;
import org.jetbrains.annotations.NotNull;

public enum WolfVariants {
  ASHEN("minecraft:entity/wolf/wolf_ashen","minecraft:entity/wolf/wolf_ashen_tame","minecraft:entity/wolf/wolf_ashen_angry"),

  WOODS("minecraft:entity/wolf/wolf_woods","minecraft:entity/wolf/wolf_woods_tame","minecraft:entity/wolf/wolf_woods_angry"),

  BLACK("minecraft:entity/wolf/wolf_black","minecraft:entity/wolf/wolf_black_tame","minecraft:entity/wolf/wolf_black_angry"),

  RUSTY("minecraft:entity/wolf/wolf_rusty","minecraft:entity/wolf/wolf_rusty_tame","minecraft:entity/wolf/wolf_rusty_angry"),

  STRIPED("minecraft:entity/wolf/wolf_striped","minecraft:entity/wolf/wolf_striped_tame","minecraft:entity/wolf/wolf_striped_angry"),

  CHESTNUT("minecraft:entity/wolf/wolf_chestnut","minecraft:entity/wolf/wolf_chestnut_tame","minecraft:entity/wolf/wolf_chestnut_angry"),

  SPOTTED("minecraft:entity/wolf/wolf_spotted","minecraft:entity/wolf/wolf_spotted_tame","minecraft:entity/wolf/wolf_spotted_angry"),

  SNOWY("minecraft:entity/wolf/wolf_snowy","minecraft:entity/wolf/wolf_snowy_tame","minecraft:entity/wolf/wolf_snowy_angry"),

  PALE("minecraft:entity/wolf/wolf","minecraft:entity/wolf/wolf_tame","minecraft:entity/wolf/wolf_angry");

  public final String wildTexture;

  public final String tameTexture;

  public final String angryTexture;

  WolfVariants(@NotNull String wildTexture, @NotNull String tameTexture,
      @NotNull String angryTexture) {
    this.wildTexture = wildTexture;
    this.tameTexture = tameTexture;
    this.angryTexture = angryTexture;
  }
}
