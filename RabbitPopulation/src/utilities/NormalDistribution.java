package utilities;

public class NormalDistribution {

	private MT19937 mt;

	public NormalDistribution(MT19937 mt) {
		this.mt = mt;
	}

	public int nextGaussian(double mean, double stdDev) {
		return (int) Math.round(mean + stdDev * mt.nextGaussian());
	}

	public int nextGaussian(int min, int max, double mean, double stdDev) {
		int next;
		do {
			next = nextGaussian(mean, stdDev);
		} while (next < min || next > max);
		return next;
	}

}
