package elfGorillas;

import simple.hooks.scripts.task.Task;
import simple.robot.api.ClientContext;

/**
 * @author Elf
 *
 */
public class Travel extends Task {

	public Travel(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean condition() {
		return false;
	}

	@Override
	public void run() {

	}

	@Override
	public String status() {
		return null;
	}

}
