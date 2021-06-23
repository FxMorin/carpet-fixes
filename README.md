# Carpet-Fixes
[Fabric Carpet](https://github.com/gnembon/fabric-carpet) extension mod which attempts to fix as many vanilla bugs as possible!

Feel free to contribute by adding as many fixes as you want

Join my discord for more information, updates, and discussion on the mod [FX's Discord](https://discord.gg/vurv5pdFpa)


# Features
## carpetFixesPreset
Allows you to choose different pre-defined groups of rules to use
* Type: `PresetSettings`
* Default value: `Custom`
* Required options: `Custom`,`Vanilla`,`Backport`,`CrashFix`,`Stability`,`NotBackports`,`All`
* Categories: `CARPETFIXES`
* Additional notes:
    * Information about what each preset does can be found in-game by doing `/carpet carpetFixesPreset`

## chunkRegenFix
Fixes Chunk Regen due to StringTag writeUTF() not respecting readUTF() Limits
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`CRASHFIX`
* Additional notes:
  * This fixes using books to dupe inventories, regen chunks, and vanilla tick freeze chunks.

## blockCollisionCheckFix
Fixes incorrect block collision checks
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-123364](https://bugs.mojang.com/browse/MC-123364)

## fallingBlockDuplicationFix
Fixes falling blocks duping using the end portal
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`

## blockUpdateOrderFix
Fixes the issue where block updates are inconsistent due to directionality
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-161402](https://bugs.mojang.com/browse/MC-161402)

## comparatorUpdateFix
Fixes the issue where comparators don't always get updated correctly
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-120986](https://bugs.mojang.com/browse/MC-120986)

## crystalExplodeOnExplodedFix
End Crystals now explode when damaged from explosions. End Crystal chaining
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-118429](https://bugs.mojang.com/browse/MC-118429)

## spongeUpdateFix
Makes it so that sponges give block updates when absorbing water
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-220636](https://bugs.mojang.com/browse/MC-220636)

## hopperUpdateFix
Makes it so that hoppers give block updates when placed while powered
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [2No2Name's video](https://www.youtube.com/watch?v=QVOONJ1OY44)

## incorrectBounceLogicFix
Fixes some entities not bouncing on slime blocks and getting stuck
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-216985](https://bugs.mojang.com/browse/MC-216985)

## incorrectBubbleColumnLogicFix
Fixes some entities getting stuck in bubble columns
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-207866](https://bugs.mojang.com/browse/MC-207866)

## directionalBlockSlowdownFix
Fixes movement slowdown being calculated based on last block in search. Uses the slowest value instead
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-202654](https://bugs.mojang.com/browse/MC-202654)

## catsBreakLeadsDuringGiftFix
Fixes cats sometimes breaking there leads after giving a gift
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-202607](https://bugs.mojang.com/browse/MC-202607)

## petsBreakLeadsDuringReloadFix
Fixes Leashed pets teleporting to the player when reloaded
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-173303](https://bugs.mojang.com/browse/MC-173303)

## endermanDontUpdateOnPlaceFix
Fixes enderman not updating the block they place correctly
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-183054](https://bugs.mojang.com/browse/MC-183054)

## railInvalidUpdateOnPushFix
Fixes rails updating other rails before checking if they are in a valid location
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-174864](https://bugs.mojang.com/browse/MC-174864)

## railMissingUpdateOnPushFix
Fixes rails not updating other rails on being moved, allowing for invalid states
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-123311](https://bugs.mojang.com/browse/MC-123311)
* Additional notes:
  * Prevents redstone budding from working

## railMissingUpdateAfterPushFix
Fixes rails not updating other rails after being moved
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-96224](https://bugs.mojang.com/browse/MC-96224)

## endVoidRingsFix
Fixes the bug which causes there to be void rings (empty chunks) in the end
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-159283](https://bugs.mojang.com/browse/MC-159283)

## mountingFlyingTooLongFix
Fixes getting kicked for flying too long when jumping and riding an entity
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-98727](https://bugs.mojang.com/browse/MC-98727)

## sleepingDelaysFallDamageFix
Fixes fall damage being delayed by sleeping, fall damage will be removed instead
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-19830](https://bugs.mojang.com/browse/MC-19830)

## tntCantUseNetherPortalsFix
Fixes TNT Entity not being able to go through nether portals
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-8983](https://bugs.mojang.com/browse/MC-8983)

## fallingBlocksCantUseNetherPortalsFix
Fixes Falling Blocks not being able to go through nether portals
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-9644](https://bugs.mojang.com/browse/MC-9644)

## spawnChunkEntitiesUnloadingFix
Fixes Spawn Chunks not ticking entities and block entities if no player online
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-59134](https://bugs.mojang.com/browse/MC-59134)

## repairCostItemNotStackingFix
Fixes Named Blocks not stacking due to useless RepairCost tag
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-197473](https://bugs.mojang.com/browse/MC-197473)

## playerBlockCollisionUsingCenterFix
Fixes multiple bugs related to effects happening only when player center in block instead of hitbox
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-1133](https://bugs.mojang.com/browse/MC-1133)

## witchHutsSpawnIncorrectCatFix
Fixes incorrect cat types spawning inside swamp huts
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-147659](https://bugs.mojang.com/browse/MC-147659)

## hardcodedSeaLevelFix
Fixes incorrect sea level height being used when datapacks change the sea height
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-226687](https://bugs.mojang.com/browse/MC-226687)

## fishingOutsideWaterFix
Fixes being able to fish outside of water
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-175544](https://bugs.mojang.com/browse/MC-175544)

## giveCommandDupeFix
Fixes being able to dupe items using the /give command
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-120507](https://bugs.mojang.com/browse/MC-120507)

## drownedEnchantedTridentsFix
Makes enchantments work on tridents thrown by drowned
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* By: *Skyrising*

# Backports
## targetBlockPermanentlyPoweredFix
Fixes target blocks being permanently powered when moved by pistons
* Type: `boolean`
* Default value: `true`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BACKPORT`
* Fixes: [MC-173244](https://bugs.mojang.com/browse/MC-173244)

## lightningRodPermanentlyPoweredFix
Fixes lightning rods being permanently powered when moved by pistons
* Type: `boolean`
* Default value: `true`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BACKPORT`
* Fixes: [MC-203718](https://bugs.mojang.com/browse/MC-203718)

## oldFlintAndSteelBehavior
Backport 1.12 flint and steel behavior. Flint and steel can be used for updating observers / buds
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`BACKPORT`
* Fixes: [MC-4923](https://bugs.mojang.com/browse/MC-4923) from 18w05a
* By: *whoImT* from *carpet-addons*

## donkeyRidingDupeFix
Re-introduces multiplayer donkey/llama dupe bug based on disconnecting while riding donkey/llama
* Type: `boolean`
* Default value: `true`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`BACKPORT`,`EXPERIMENTAL`
* Fixes: Fixed [MC-181241](https://bugs.mojang.com/browse/MC-181241) from 18w05a
* By: *whoImT* from *carpet-addons*
* Additional notes:
  * This has not been tested in 1.17 and may not work!

# From Carpet & Other Extensions
## lightningKillsDropsFix
Lightning kills the items that drop when lightning kills an entity
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-206922](https://bugs.mojang.com/browse/MC-206922)

## doubleRetraction
Re-adds 1.8 double retraction to pistons
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-88959](https://bugs.mojang.com/browse/MC-88959)
* By: *DeadlyMC* from *Carpet-Extra*

## repeaterPriorityFix
Quick pulses won't get lost in repeater setups
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-54711](https://bugs.mojang.com/browse/MC-54711)
* By: *DeadlyMC* from *Carpet-Extra*

## railDuplicationFix
Fixes rails duplicating
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* By: *Fallen-Breath* from *Carpet-TIS-Addition*

## pistonDupingFix
Disable TNT, carpet and part of rail dupers
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`
* By: *Fallen-Breath* from *Carpet-TIS-Addition*




