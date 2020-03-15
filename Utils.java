package elfGorillas;

import simple.hooks.filters.SimplePrayers.Prayers;

/**
 * @author Elf
 *
 */
class Utils {

	public static int FOOD_AMOUNT = 0;
	public static int FOOD = 0;
	public static int ZENYTEAMOUNT = 0;
	public static int[] MAIN_GEAR;
	public static int[] RANGE_SWITCH = new int[4];
	public static int[] OG_INVENTORY;

	public static final int TELEPORT_TAB = 22721;
	public static final int DESERT_AMMY = 13136;
	public static final int DESERT_STATUE = 10389;
	public static final int DESERT_STATUE_ANIM = 645;
	public static final int PORTAL = 35000;// Teleport-previous

	static final int GORILLA[] = { 7144, 7145 };// melee pray, range pray
	static final int BOULDER_PROJECTILE = 856;
	static final int RANGE_PROJECTILE = 1302;
	static final int MAGE_PROJECTILE = 1304;
	static final int BOULDER_ATTACK = 7228;
	static final int RANGE_ATTACK = 7227;
	static final int MELEE_ATTACK = 7226;
	static final int MAGE_ATTACK = 7225;
	static final int DEATH_ANIM = 7229;

	public static boolean USE_VENG = false;
	public static boolean USE_AUGURY = false;
	public static boolean USE_RIGOUR = false;

	public static final int[] FOOD_IDS = { 13441, 385, 7946, 391, 11936, 7060, 397 };
	public static final int[] PRAYER_RESTORES = { 143, 141, 139, 2434, 3030, 3028, 3026, 3024 };
	public static final int[] RANGE_POTS = { 173, 171, 169, 2444 };
	public static final int[] COMBAT_POTS = { 12701, 12699, 12697, 12695 };

	// LOOT ITEMS
	public static final String[] VALUABLES = { "Zenyte shard", "Ballista limbs", "Ballista spring", "Light frame",
			"Heavy frame", "Monkey tail" };
	public static final String[] DROP_TABLE = { "Zenyte shard", "Ballista limbs", "Ballista spring", "Light frame",
			"Heavy frame", "Monkey tail", "Rune platelegs", "Rune plateskirt", "Rune chainbody", "Dragon scimitar",
			"Law rune", "Death rune", "Runite bolts", "Grimy kwuarm", "Grimy cadantine", "Grimy dwarf weed",
			"Grimy lantadyme", "Ranarr seed", "Snapdragon seed", "Torstol seed", "Magic seed", "Spirit seed",
			"Dragondruit tree seed", "Celastrus seed", "Redwood tree seed", "Prayer potion(3)", "Shark", "Coins",
			"Adamantite bar", "Diamond", "Loop half of key", "Tooth half of key", "Runite bar", "Rune 2h sword",
			"Rune battleaxe", "Rune arrow", "Rune sq shield", "Dragon spear", "Shield left half", "Snape grass",
			"Mort myre fungus", "Dragon scale dust", "Red spiders' eggs", "Potato cactus", "White berries",
			"Crystal key", "Dragonstone", "Ruby", "Emerald", "Sapphire", "Limpwurt root", "Ruby bolts", "Diamond bolts",
			"Dragonstone bolts", "Grey chinchompa", "Red chinchompa", "Black chinchompa", "Clue scroll (hard)",
			"Clue scroll (elite)", "Brimstone key", "Crushed nest" };

	public static final int[] SUPPLY_DROPS = { 7061, 386, 398, 13442, 11937, 392, 12696, 12626, 12914, 10926, 21979,
			2435, 3025, 6686 };

	public static Prayers magicPrayer() {
		if (USE_AUGURY) {
			return Prayers.AUGURY;
		} else {
			return Prayers.MYSTIC_MIGHT;
		}
	}

	public static Prayers rangePrayer() {
		if (USE_RIGOUR) {
			return Prayers.RIGOUR;
		} else {
			return Prayers.EAGLE_EYE;
		}
	}

}
