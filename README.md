<p align="center"><a  href="https://modrinth.com/plugin/qMobsDropEggs"><img alt="Modrinth Download Link" src="https://img.shields.io/badge/Download-00AF5C?logo=modrinth&logoColor=white&style=for-the-badge" height="32"></a> <a href="https://www.quartzdev.gg/discord/" target="_blank"><img alt="Discord Invite" src="https://img.shields.io/badge/Discord-5865F2?logo=discord&logoColor=white&style=for-the-badge" height="32"></a> <a href="https://github.com/QarthO/qMobsDropEggs" target="_blank"><img alt="GitHub Source Code" src="https://img.shields.io/badge/Source-181717?logo=github&logoColor=white&style=for-the-badge" height="32"></a> <a href="https://paypal.me/qartho/" target="_blank"><img alt="Paypal Donation Link" src="https://img.shields.io/badge/Donate-00457C?logo=paypal&logoColor=white&style=for-the-badge" height="32"></a> <a href="https://modrinth.com/plugin/qMobsDropEggs/versions"><img alt="Supported Versions: 1.20.2" src="https://img.shields.io/badge/1.20.2-blue?style=for-the-badge&label=Minecraft Versions" height="32"></a></p>

---

### Mobs will drop their respective spawn eggs!

---

<h3> Tested Versions </h3>
<p>Minecraft: v1.20.2</p>
<p>Servers: <a href="https://papermc.io">Paper</a>, <a href="https://pufferfish.host/downloads">Pufferfish</a>, <a href="https://purpurmc.org">Purpur</a> or any other <u>stable</u> paper fork. </p>
<blockquote>Spigot or modded hybrids will most <i>*likely*</i> work, but will recieve no support. If you're still using spigot, it is <b>HIGHLY</b> recommend you upgrade to Paper</blockquote>

---

<h3>Configuarble Features</h3>

- Prevent certain mobs from dropping their spawn egg*
- Have different drop rates for each mob
- Increase the drop rates when using looting enchant
- Disable in certain worlds

**Note: Only supports mobs that have valid spawn eggs. Full list can be found [here](https://minecraft.wiki/w/Spawn_Egg)*

---

### Config

```yaml
################################################################
# +----------------------------------------------------------+ #
# |                   qMobsDropEggs Config                   | #
# |   Source:   https://github.com/QarthO/qMobsDropEggs      | #
# |   Download: https://modrinth.com/plugin/qMobsDropEggs/   | #
# |   Donate:   https://paypal.me/qartho                     | #
# +----------------------------------------------------------+ #
################################################################

version: 1.0
check-updates: true

# Will only drop an egg if killed by a player
requires-player-killer: true
# Will check if the player has the permission: 'qMDE.player'
killer-requires-permission: true

# Name of the world where the plugin will be disabled
disabled-worlds:
  - hub
  - creative

# Should the drop chance be increased by looting
# Each level of looting adds 1% to the drop-chance
factor-looting: true

# The odds of a spawn egg from dropping when a player kills a mob
# 1 would be 1% chance, (supports decimals)
drop-chance:
  default: 1
  mobs:
    SHULKER: .25
    IRON_GOLEM: .05
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

This plugin uses [bStats](https://bstats.org/). You can opt-out in the bStats config
<p align="center">
<a href="https://bstats.org/plugin/bukkit/qMobsDropEggs/"><img alt="bStats qSpleef" src="https://bstats.org/signatures/bukkit/qMobsDropEggs.svg"></a></p>
