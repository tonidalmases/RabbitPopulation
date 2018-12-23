package utilities;

import java.util.ArrayList;
import java.util.List;

import population.Rabbit;

public class Initializer {

	public static List<Rabbit> generateInitialPopulation(UniformDistribution ud, NormalDistribution nd) {
		List<Rabbit> initialPopulation = new ArrayList<Rabbit>();
		for (int i = 0; i < Constants.INITIAL_MALES; i++) {
			initialPopulation.add(new Rabbit(Constants.MALE, 12, newMaturity(ud), false, 0));
		}
		for (int i = 0; i < Constants.INITIAL_FEMALES; i++) {
			initialPopulation.add(new Rabbit(Constants.FEMALE, 12, newMaturity(ud), false, newLitters(nd)));
		}
		return initialPopulation;
	}

	public static int newMaturity(UniformDistribution ud) {
		return ud.nextInt(Constants.MATURITY_MIN, Constants.MATURITY_MAX);
	}

	public static int newLitters(NormalDistribution nd) {
		return nd.nextGaussian(Constants.LITTERS_MIN, Constants.LITTERS_MAX, Constants.LITTERS_NORMAL_MEAN,
				Constants.LITTERS_NORMAL_STDDEV);
	}

}
