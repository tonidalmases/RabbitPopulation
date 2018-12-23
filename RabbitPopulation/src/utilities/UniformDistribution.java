package utilities;

public class UniformDistribution {

	private MT19937 mt;

	public UniformDistribution(MT19937 mt) {
		this.mt = mt;
	}

	public double nextDouble() {
		return mt.nextDouble();
	}

	public double nextDouble(double min, double max) {
		return min + (max - min) * nextDouble();
	}

	public int nextInt(int min, int max) {
		return min + mt.nextInt(max - min + 1);
	}

}
