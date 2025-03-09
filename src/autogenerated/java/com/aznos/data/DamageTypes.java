package com.aznos.data;

import java.lang.String;
import org.jetbrains.annotations.NotNull;

public enum DamageTypes {
  THROWN("thrown"),

  GENERIC_KILL("genericKill"),

  UNATTRIBUTED_FIREBALL("onFire"),

  BAD_RESPAWN_POINT("badRespawnPoint"),

  FALLING_ANVIL("anvil"),

  FIREWORKS("fireworks"),

  OUT_OF_WORLD("outOfWorld"),

  SONIC_BOOM("sonic_boom"),

  OUTSIDE_BORDER("outsideBorder"),

  ENDER_PEARL("fall"),

  MACE_SMASH("mace_smash"),

  LAVA("lava"),

  STARVE("starve"),

  CRAMMING("cramming"),

  DRY_OUT("dryout"),

  CACTUS("cactus"),

  WITHER("wither"),

  MOB_ATTACK("mob"),

  FLY_INTO_WALL("flyIntoWall"),

  WITHER_SKULL("witherSkull"),

  PLAYER_ATTACK("player"),

  GENERIC("generic"),

  TRIDENT("trident"),

  DROWN("drown"),

  THORNS("thorns"),

  MOB_PROJECTILE("mob"),

  STALAGMITE("stalagmite"),

  HOT_FLOOR("hotFloor"),

  FALLING_STALACTITE("fallingStalactite"),

  FALLING_BLOCK("fallingBlock"),

  WIND_CHARGE("mob"),

  LIGHTNING_BOLT("lightningBolt"),

  EXPLOSION("explosion"),

  STING("sting"),

  PLAYER_EXPLOSION("explosion.player"),

  DRAGON_BREATH("dragonBreath"),

  FALL("fall"),

  ARROW("arrow"),

  SPIT("mob"),

  IN_WALL("inWall"),

  MOB_ATTACK_NO_AGGRO("mob"),

  SWEET_BERRY_BUSH("sweetBerryBush"),

  FIREBALL("fireball"),

  ON_FIRE("onFire"),

  MAGIC("magic"),

  CAMPFIRE("inFire"),

  IN_FIRE("inFire"),

  FREEZE("freeze"),

  INDIRECT_MAGIC("indirectMagic");

  public final String messageId;

  DamageTypes(@NotNull String messageId) {
    this.messageId = messageId;
  }
}
