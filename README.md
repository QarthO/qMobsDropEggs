<p align="center">
<a  href="https://modrinth.com/plugin/qMobsDropEggs" target="_blank">
<img alt="Modrinth Download Link" src="https://img.shields.io/badge/Download-00AF5C?logo=modrinth&logoColor=white&style=for-the-badge" height="32"></a>
 <a href="https://www.quartzdev.gg/discord/" target="_blank">
<img alt="Discord Invite" src="https://img.shields.io/badge/Discord-5865F2?logo=discord&logoColor=white&style=for-the-badge" height="32"></a>
 <a href="https://github.com/QarthO/qMobsDropEggs" target="_blank">
<img alt="GitHub Source Code" src="https://img.shields.io/badge/Source-181717?logo=github&logoColor=white&style=for-the-badge" height="32"></a>
 <a href="https://www.quartzdev.gg/donate/" target="_blank">
<img alt="Paypal Donation Link" src="https://img.shields.io/badge/Donate-00457C?logo=paypal&logoColor=white&style=for-the-badge" height="32"></a>
 <a href="https://modrinth.com/plugin/qMobsDropEggs/versions" target="_blank">
<img alt="Supported Versions: 1.20.2" src="https://img.shields.io/badge/1.20.2-blue?style=for-the-badge&label=Minecraft Versions" height="32"></a>
</p>

---

### Mobs will drop their respective spawn eggs!

---

<h3> Tested Versions </h3>
<p>Minecraft: v1.21.3</p>
<p>Servers: <a href="https://papermc.io" target="_blank">Paper</a>, <a href="https://pufferfish.host/downloads" target="_blank">Pufferfish</a>, <a href="https://purpurmc.org" target="_blank">Purpur</a> or any other <u>stable</u> paper fork. </p>
<blockquote>Spigot or modded hybrids will most <i>*likely*</i> work, but will recieve no support. If you're still using spigot, it is <b>HIGHLY</b> recommend you upgrade to Paper</blockquote>

---

<h3>Configuarble Features</h3>

- Have different drop rates for each mob
- Prevent certain mobs from dropping their spawn egg*
- Increase the drop rates when using looting enchant
- Prevent 
- Disable in certain worlds

<i>*Note: Only supports mobs that have valid spawn eggs. Full list can be found <a href="https://minecraft.wiki/w/Spawn_Egg" target="_blank">here</a></i>

---

### Config

```yaml
################################################################
# +----------------------------------------------------------+ #
# |                   qMobsDropEggs Config                   | #
# |   Source:   https://github.com/QarthO/qMobsDropEggs/     | #
# |   Download: https://modrinth.com/plugin/qMobsDropEggs/   | #
# |   Donate:   https://quartzdev.gg/donate/                 | #
# +----------------------------------------------------------+ #
################################################################

version: 1.0.0
check-updates: true

# Will only drop an egg if killed by a player
requires-player-killer: true
# Will check if the player has the permission: 'qmde.player'
killer-requires-permission: true

# Mobs won't drop eggs if their spawn reason matches anything in this list
# If you want this empty set this to >> blacklist-spawn-reasons: []
# SpawnReason List: https://jd.papermc.io/paper/1.16/org/bukkit/event/entity/CreatureSpawnEvent.SpawnReason.html
blacklisted-spawn-reasons:
  - SPAWNER

# If set to true, this will treat the above spawn-reason list as a whitelist
invert-blacklisted-spawn-reasons: false

# Name of the world where the plugin will be disabled
disabled-worlds:
  - hub
  - creative

# Should the drop chance be increased by looting
# Each level of looting adds 1% to the drop-chance
factor-looting: true
# Changes the drop chance calculation when factoring in looting
# But only if it is below the breakpoint
# Now final drop chance = drop chance * (1 + looting level)
# Example: drop chance of .05% with looting 3
#         0.05% * (1 + 3) = .2%
# complex-looting only makes sense for smaller numbers you want to keep small
# factor-looting must be set to true
complex-looting: true
complex-looting-breakpoint: 1

# The odds of a spawn egg from dropping when a player kills a mob
# 1 would be 1% chance, (supports decimals)
drop-chance:
  default: 1
  mobs:
    SHULKER: .01
    IRON_GOLEM: .1
#    PHANTOM: 10
#    VILLAGER: .1

# Prevent certain mobs from dropping spawn eggs
# Spawn Egg list: https://minecraft.wiki/w/Spawn_Egg
# EntityType format: https://jd.papermc.io/paper/1.20/org/bukkit/entity/EntityType.html
blacklisted-mobs:
  - ENDER_DRAGON
  - WARDEN
  - WITHER
  - WANDERING_TRADER
```

---

This plugin uses <a href="https://bstats.org/" target="_blank">bStats</a>. You can opt-out in the bStats config
<p align="center">
<a href="https://bstats.org/plugin/bukkit/qMobsDropEggs/" target="_blank"><img alt="bStats qSpleef" src="https://bstats.org/signatures/bukkit/qMobsDropEggs.svg"></a></p>
