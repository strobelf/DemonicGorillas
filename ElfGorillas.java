package elfGorillas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import simple.hooks.filters.SimpleEquipment.EquipmentSlot;
import simple.hooks.scripts.Category;
import simple.hooks.scripts.ScriptManifest;
import simple.hooks.scripts.task.Task;
import simple.hooks.scripts.task.TaskScript;
import simple.hooks.simplebot.ChatMessage;
import simple.hooks.simplebot.ScriptPaint;
import simple.hooks.wrappers.SimpleItem;
import simple.hooks.wrappers.SimpleNpc;
import simple.robot.api.ClientContext;

/**
 * @author Elf
 *
 */
@ScriptManifest(author = "Elf", category = Category.MONEYMAKING, description = "Kills Demonic Gorillas", discord = "", name = "eGorillas", servers = {
		"Zenyte" }, version = "1.1")
public class ElfGorillas extends TaskScript {
	private List<Task> tasks = new ArrayList<Task>();;
	private GorillasGUI gui;

	@Override
	public void onExecute() {
		try {
			gui = new GorillasGUI(this);
			gui.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.loadWornButton.addActionListener(e -> {
			loadWornEquip(ctx);

		});
		gui.loadInvButton.addActionListener(e -> {
			loadRangeSwitch(ctx);
		});
		gui.startButton.addActionListener(e -> {
			gui.dispose();
			Utils.USE_AUGURY = GorillasGUI.auguryToggle.isSelected();
			Utils.USE_RIGOUR = GorillasGUI.rigourToggle.isSelected();
			Utils.USE_VENG = GorillasGUI.vengToggle.isSelected();
			/*	if (Utils.USE_VENG) {
					VengCheck vC = new VengCheck(ctx);
					Thread vCT = new Thread(vC);
					vCT.start();
			}*/
			ctx.inventory.populate();
			int invSize = ctx.inventory.populate().size();
			Utils.OG_INVENTORY = new int[invSize];
			for (int i = 0; i < invSize; i++) {
				SimpleItem a = ctx.inventory.next();
				Utils.OG_INVENTORY[i] = a.getId();
			}
			System.out.println(Utils.OG_INVENTORY.length);
			countFreq(Utils.OG_INVENTORY, invSize);
			tasks.addAll(Arrays.asList(new Loot(ctx), new Bank(ctx), new Fight(ctx)));
		});

	}

	public static void countFreq(int arr[], int n) {
		boolean checked[] = new boolean[n];

		Arrays.fill(checked, false);

		for (int i = 0; i < n; i++) {
			if (checked[i] == true)
				continue;
			int count = 1;
			for (int j = i + 1; j < n; j++) {
				if (arr[i] == arr[j]) {
					checked[j] = true;
					count++;
				}
			}
			for (int f = 0; f < Utils.FOOD_IDS.length; f++)
				if (arr[i] == Utils.FOOD_IDS[f]) {
					Utils.FOOD = arr[i];
				}
			if (arr[i] == Utils.FOOD) {
				Utils.FOOD_AMOUNT = count;
			}

		}
	}

	public void paint(Graphics gfx) {
		Graphics2D g = (Graphics2D) gfx;
		ScriptPaint g2 = new ScriptPaint(ctx);
		SimpleNpc gorilla = ctx.npcs.populate().filter("Demonic gorilla").filter(new Predicate<SimpleNpc>() {
			public boolean test(SimpleNpc npc) {
				return npc.getInteracting() == ctx.players.getLocal().getPlayer();
			}
		}).nearest().next();
		g.setColor(Color.CYAN);
		g.drawString("Status: " + getScriptStatus(), 7, 270);
		g.drawString("Player in Combat " + ctx.combat.inCombat(), 7, 280); // if something is attacking us
		g.drawString("Player in Combat " + ctx.players.getLocal().inCombat(), 7, 290); // if we're attacking
		if (gorilla != null) {
			g.drawString("Gorilla in Combat " + gorilla.inCombat(), 7, 300);
			g.setColor(Color.RED);
			g2.drawTileMatrix(g, gorilla.getLocation(), Color.RED);
		}

	}

	@Override
	public void onChatMessage(ChatMessage arg0) {

	}

	public void loadWornEquip(ClientContext ctx) {
		ctx.equipment.populate();
		int equipSize = ctx.equipment.population();
		Utils.MAIN_GEAR = new int[equipSize];
		ctx.updateStatus(equipSize + " worn items");
		System.out.println(equipSize + " worn items");
		for (int i = 0; i < equipSize; i++) {
			int p = i;
			if (i == 6) {
				p = 12;
			}
			if (i == 8) {
				p = 13;
			}
			SimpleItem a = ctx.equipment.getEquippedItem(EquipmentSlot.slotByIndex(p));
			if (a != null) {
				Utils.MAIN_GEAR[i] = a.getId();
				GorillasGUI.mainGearIds[i] = a.getId();
				ctx.updateStatus("" + a.getName() + " in index: " + i);
				System.out.println(a.getName() + " in index: " + i);
			} else {
				if (p == 13) {
					a = ctx.equipment.getEquippedItem(EquipmentSlot.slotByIndex(10));
					Utils.MAIN_GEAR[i] = a.getId();
					GorillasGUI.mainGearIds[10] = a.getId();
					ctx.updateStatus("" + a.getName() + " in index: " + i);
					System.out.println(a.getName() + " in index: " + i);
				}
			}
		}
		for (int i = 0; i < GorillasGUI.mainGearIds.length; i++) {
			if (GorillasGUI.mainGearIds[i] == 0) {
				GorillasGUI.mainGearIds[i] = 798;
			}
		}
	}

	public void loadRangeSwitch(ClientContext ctx) {
		ctx.inventory.populate();
		ctx.updateStatus("-- Range switch --");
		System.out.println("-- Range switch --");
		for (int i = 0; i < GorillasGUI.rangeGearIds.length; i++) {
			SimpleItem a = ctx.inventory.next();
			if (a != null) {
				GorillasGUI.rangeGearIds[i] = a.getId();
				Utils.RANGE_SWITCH[i] = a.getId();
				ctx.updateStatus("" + a.getName());
				System.out.println(a.getName());
			} else {
				GorillasGUI.rangeGearIds[i] = 798;
				ctx.updateStatus("" + GorillasGUI.rangeGearIds[i]);
				System.out.println(GorillasGUI.rangeGearIds[i]);
			}

		}
	}

	@Override
	public void onTerminate() {

	}

	@Override
	public boolean prioritizeTasks() {
		return false;
	}

	@Override
	public List<Task> tasks() {
		return tasks;
	}

	@Override
	public void onProcess() {
		super.onProcess();
	}

}
