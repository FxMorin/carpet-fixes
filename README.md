# Carpet-Fixes
[Fabric Carpet](https://github.com/gnembon/fabric-carpet) extension mod which attempts to fix as many vanilla bugs as possible!

Feel free to contribute by adding as many fixes as you want


# Features
## chunkRegenFix
Fixes Chunk Regen due to StringTag writeUTF() not respecting readUTF() Limits
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`

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
* Categories: `CARPETFIXES`,`BACKPORT`
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
* Categories: `CARPETFIXES`,`BUGFIX`
* Fixes: [MC-88959](https://bugs.mojang.com/browse/MC-88959)
* By: *DeadlyMC* from *Carpet-Extra*

## repeaterPriorityFix
Quick pulses won't get lost in repeater setups
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
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
* Categories: `CARPETFIXES`,`BUGFIX`,`EXPERIMENTAL`
* By: *Fallen-Breath* from *Carpet-TIS-Addition*




