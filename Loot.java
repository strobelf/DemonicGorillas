package elfGorillas;

import java.util.function.Predicate;

import simple.hooks.scripts.task.Task;
import simple.hooks.wrappers.SimpleGroundItem;
import simple.hooks.wrappers.SimpleItem;
import simple.robot.api.ClientContext;

/**
 * @author Elf
 *
 */
public class Loot extends Task {

	public Loot(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean condition() {
		SimpleGroundItem loot = ctx.groundItems.populate().filter(Utils.DROP_TABLE)
				.filter(new Predicate<SimpleGroundItem>() {
					@Override
					public boolean test(SimpleGroundItem t) {
						if (Fight.lootLoc != null) {
							return t.getLocation() != Fight.lootLoc;
						} else {
							return false;
						}
					}

				}).next();
		return loot != null;
	}

	@Override
	public void run() {
		SimpleGroundItem loot = ctx.groundItems.populate().filter(Utils.DROP_TABLE)
				.filter(new Predicate<SimpleGroundItem>() {
					@Override
					public boolean test(SimpleGroundItem t) {
						if (Fight.lootLoc != null) {
							return t.getLocation() != Fight.lootLoc;
						} else {
							return false;
						}
					}

				}).next();
		if (loot != null) {
			checkLoot(Utils.DROP_TABLE);
		}

	}

	boolean checkLoot(String[] lootIDs) {
		boolean looted = false;
		if (!ctx.groundItems.populate().filter(lootIDs).isEmpty()) {
			for (SimpleGroundItem item : ctx.groundItems) {
				if (item != null) {
					if (ctx.inventory.inventoryFull()) {
						if (!ctx.inventory.populate().filter(Utils.FOOD_IDS).isEmpty()) {
							SimpleItem food = ctx.inventory.filter(Utils.FOOD).next();
							food.click(1);
						}
					} else {
						if (item.getName().contains("Zenyte shard"))
							Utils.ZENYTEAMOUNT += 1;
						item.click(1);
						ctx.sleep(900);
					}
				}
			}
		} else {
			looted = true;
			Fight.needToLoot = false;
		}
		return looted;
	}

	@Override
	public String status() {
		return "Looting";
	}

}
