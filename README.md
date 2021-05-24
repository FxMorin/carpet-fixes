# Carpet-Fixes
[Fabric Carpet](https://github.com/gnembon/fabric-carpet) extension mod which attempts to fix as many vanilla bugs as possible!

Feel free to contribute by adding as many fixes as you want


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

## worldgenIncorrectOrderFix
Fixes World Modifying tasks to be before decorations
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* Fixes: [MC-610](https://bugs.mojang.com/browse/MC-610)

## updateSuppressionFix
Prevents update suppression from working! Original concept by: *Carpet-TCTC-Addition*
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`CRASHFIX`

## zombiePiglinTracingFix
Tracing the target to another dimension does not stop checking for visibility, so that many unnecessary chunks are loaded
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`CRASHFIX`
* By: *Hendrix-Shen*

# Backports
## targetBlockPermanentlyPoweredFix
Fixes target blocks being permanently powered when moved by pistons
* Type: `boolean`
* Default value: `true`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BACKPORT`
* Fixes: [MC-173244](https://bugs.mojang.com/browse/MC-173244)

## shulkerTeleportFix
Shulkers do not teleport correctly when going through a portal
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`BACKPORT`
* Fixes: [MC-139265](https://bugs.mojang.com/browse/MC-139265) in 21w03a
* By: *Hendrix-Shen*

## drownedMemoryLeakFix
Fixes Drowned navigation causing memory leak/performance degradation
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BACKPORT`,`BUGFIX`
* Fixes: [MC-202246](https://bugs.mojang.com/browse/MC-202246) from 20w45a
* By: *Fallen-Breath*

## shulkerBoxItemsDropContents
Backport dropping the contents of a Shulker Box item when its item entity is destroyed
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BACKPORT`
* Fixes: [MC-176615](https://bugs.mojang.com/browse/MC-176615) from 20w51a
* By: *Copetan* from *lunaar-carpet-addons*

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
* Categories: `CARPETFIXES`,`BUGFIX`,`BACKPORT`
* Fixes: Fixed [MC-181241](https://bugs.mojang.com/browse/MC-181241) from 18w05a
* By: *whoImT* from *carpet-addons*

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




