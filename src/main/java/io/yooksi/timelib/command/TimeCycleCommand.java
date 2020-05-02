package io.yooksi.timelib.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.yooksi.timelib.TimeCycle;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class TimeCycleCommand {

	private static final String FEEDBACK_CHANGED_SPEED = "Day/night cycle speed changed to %d";
	private static final String FEEDBACK_NOT_CHANGED_SPEED = "Day/night cycle speed is already %d";
	private static final String FEEDBACK_RESET = "Day/night cycle speed was reset to %d";

	public static void register(CommandDispatcher<CommandSource> dispatcher) {

		dispatcher.register(Commands.literal("timecycle")
				.then(Commands.literal("speed").then(Commands.argument("value",
						LongArgumentType.longArg(-100, TimeCycle.MAX_SPEED))
						.executes((c) -> setTimeCycle(c, c.getArgument("value", Long.class))))
				.then(Commands.literal("slow").executes((c) -> setTimeCycle(c, TimeCycle.SLOW_SPEED)))
				.then(Commands.literal("normal").executes((c) -> setTimeCycle(c, TimeCycle.DEFAULT_SPEED)))
				.then(Commands.literal("fast").executes((c) -> setTimeCycle(c, TimeCycle.FAST_SPEED)))
				.then(Commands.literal("reset").executes((c) -> setTimeCycle(c, TimeCycle.DEFAULT_SPEED)))));
	}

	/**
	 * Change time cycle speed to new value
	 */
	private static int setTimeCycle(CommandContext<CommandSource> context, long newSpeed) {

		CommandSource source = context.getSource();
		final long currentSpeed = TimeCycle.getSpeed();

		if (newSpeed != currentSpeed) {
			CmdHelper.sendFeedback(source, String.format(FEEDBACK_CHANGED_SPEED, newSpeed));
			return (int) TimeCycle.setSpeed(newSpeed);
		}
		else {
			CmdHelper.sendFeedback(source, String.format(FEEDBACK_NOT_CHANGED_SPEED, currentSpeed));
			return (int) currentSpeed;
		}
	}

	/**
	 * Reset time cycle speed to default value
	 */
	private static int setTimeCycle(CommandContext<CommandSource> context) {

		final long defaultSpeed = TimeCycle.resetSpeed();
		CmdHelper.sendFeedback(context.getSource(), String.format(FEEDBACK_RESET, defaultSpeed));
		return (int) defaultSpeed;
	}
}