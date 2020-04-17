package io.yooksi.trcm;

public class Tick {

	public static boolean process_entities = true;
	private static float rate = 20.0f;
	private static float mspt = 50.0f;

	public static float changeRate(float rate) {

		Tick.rate = rate;
		long mspt = (long) (1000.0 / Tick.rate);
		if (mspt <= 0L)
		{
			mspt = 1L;
			Tick.rate = 1000.0f;
		}
		Tick.mspt = (float) mspt;
		return Tick.rate;
	}

	public static float getRate() {
		return rate;
	}

	public static float getMspt() {
		return mspt;
	}
}
