package elfGorillas;

import java.util.function.Predicate;

import net.runelite.api.HeadIcon;
import net.runelite.api.coords.WorldPoint;
import simple.hooks.filters.SimplePrayers.Prayers;
import simple.hooks.scripts.task.Task;
import simple.hooks.wrappers.SimpleItem;
import simple.hooks.wrappers.SimpleNpc;
import simple.robot.api.ClientContext;

/**
 * @author Elf
 *
 */
class Fight extends Task {

	static SimpleItem food = null;
	static SimpleItem teleport = null;
	static SimpleItem prayPot = null;
	static SimpleItem combatPot = null;
	static SimpleItem rangePot = null;
	static WorldPoint lootLoc = null;

	static boolean needToLoot = false;

	public Fight(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean condition() {
		SimpleNpc gorilla = ctx.npcs.populate().filter("Demonic gorilla").filter(new Predicate<SimpleNpc>() {
			public boolean test(SimpleNpc npc) {
				return npc.getInteracting() == ctx.players.getLocal().getPlayer();
			}
		}).nearest().next();
		return gorilla != null;
	}

	@Override
	public void run() {
		prayPot = ctx.inventory.populate().filter(Utils.PRAYER_RESTORES).next();
		food = ctx.inventory.populate().filter(Utils.FOOD_IDS).next();
		SimpleNpc gorilla = ctx.npcs.populate().filter("Demonic gorilla").filter(new Predicate<SimpleNpc>() {
			public boolean test(SimpleNpc npc) {
				return npc.getInteracting() == ctx.players.getLocal().getPlayer();
			}
		}).nearest().next();
		lootLoc = gorilla.getLocation();
		checkCondition(ctx.combat.healthPercent() < 75, food, Utils.FOOD_IDS);
		checkCondition(ctx.prayers.points() < 10, prayPot, Utils.PRAYER_RESTORES);
		if (!ctx.players.getLocal().inCombat()) {
			attackGorilla(gorilla);
		}
		// Handle Offensive Prayers
		if (gorilla.getOverhead() == HeadIcon.MELEE) {
			if (!ctx.prayers.prayerActive(Utils.rangePrayer())) {
				ctx.updateStatus("Gorilla Melee Pray");
				ctx.prayers.prayer(Utils.rangePrayer(), true);
			}
			if (swapGear(Utils.RANGE_SWITCH)) {
				attackGorilla(gorilla);
			}

		}
		if (gorilla.getOverhead() == HeadIcon.RANGED) {
			if (!ctx.prayers.prayer(Prayers.PIETY)) {
				ctx.updateStatus("Gorilla Range Pray");
				ctx.prayers.prayer(Prayers.PIETY, true);
			}
			if (swapGear(Utils.MAIN_GEAR)) {
				attackGorilla(gorilla);
			}

		}
		// Handle OverHead Praying
		switch (gorilla.getAnimation()) {
		case Utils.BOULDER_ATTACK:
			ctx.updateStatus("Boulder Attack");
			// WorldPoint playerLoc = ctx.players.getLocal().getLocation();
			WorldPoint gorillaLoc = gorilla.getLocation();
			/*WorldPoint dodgeOne = new WorldPoint(playerLoc.getX(), playerLoc.getY() + 1, 0);
			WorldPoint dodgeTwo = new WorldPoint(playerLoc.getX(), playerLoc.getY() - 1, 0);
			WorldPoint dodgeThree = new WorldPoint(playerLoc.getX() + 1, playerLoc.getY(), 0);
			WorldPoint dodgeFour = new WorldPoint(playerLoc.getX() - 1, playerLoc.getY(), 0);*/
			if (ctx.pathing.clickSceneTile(gorillaLoc, true, true)) {
				ctx.updateStatus("Boulder Attack dodged!");
				ctx.sleep(750);
				// attackGorilla(gorilla);
			}
			break;
		case Utils.MAGE_ATTACK:
			if (!ctx.prayers.prayer(Prayers.PROTECT_FROM_MAGIC)) {
				ctx.updateStatus("Mage Gorilla");
				ctx.prayers.prayer(Prayers.PROTECT_FROM_MAGIC, true);
			}
			break;
		case Utils.MELEE_ATTACK:
			if (!ctx.prayers.prayer(Prayers.PROTECT_FROM_MELEE)) {
				ctx.updateStatus("Melee Gorilla");
				ctx.prayers.prayer(Prayers.PROTECT_FROM_MELEE, true);
			}
			break;
		case Utils.RANGE_ATTACK:
			if (!ctx.prayers.prayerActive(Prayers.PROTECT_FROM_MISSILES)) {
				ctx.updateStatus("Range Gorilla");
				ctx.prayers.prayer(Prayers.PROTECT_FROM_MISSILES, true);
			}
			break;
		}

	}

	public static boolean attackGorilla(SimpleNpc gorilla) {
		if (!gorilla.isDead()) {
			if (gorilla.validateInteractable()) {
				gorilla.click(1);
			}
			return true;
		}

		return false;
	}

	boolean swapGear(int[] gearIds) {
		if (!ctx.inventory.populate().filter(gearIds).isEmpty()) {
			for (SimpleItem item : ctx.inventory) {
				item.click(1);
			}
			ctx.sleep(700);
			return true;
		}
		return false;
	}

	boolean checkCondition(boolean condition, SimpleItem item, int[] ids) {
		if (condition) {
			if (!ctx.inventory.populate().filter(ids).isEmpty()) {
				item.click(1);
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public String status() {
		return "Fighting";
	}

}
