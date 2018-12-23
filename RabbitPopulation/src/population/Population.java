package population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.Constants;
import utilities.Initializer;
import utilities.NormalDistribution;
import utilities.UniformDistribution;

public class Population {

	private List<Rabbit> rabbits;
	private NormalDistribution nd;
	private UniformDistribution ud;
	private int month;

	public Population(List<Rabbit> rabbits, NormalDistribution nd, UniformDistribution ud) {
		this.rabbits = rabbits;
		this.nd = nd;
		this.ud = ud;
		this.month = 0;
	}

	/* Iterate simulations */

	public void iterateOneYear() {
		for (int i = 0; i < 12; i++) {
			iterateOneMonth();
		}
	}

	public void iterateOneMonth() {
		matureSurvival();
		gestation();
		pairing();
		updateAge();
	}

	/* Print population */

	public void printPopulation() {
		if (!rabbits.isEmpty()) {
			printTotal();
			printSex();
			printMaturity();
			printAges();
		} else {
			System.out.println("\nNo population");
		}
	}

	public void printTotal() {
		System.out.println("\nTotal rabbits: " + rabbits.size());
	}

	public void printSex() {
		int males = 0;
		int females = 0;
		for (Rabbit r : rabbits) {
			if (Constants.MALE.equals(r.getSex())) {
				males++;
			} else if (Constants.FEMALE.equals(r.getSex())) {
				females++;
			}
		}
		System.out.println("\nSex of the rabbits:");
		System.out.println("Males: " + males + " (" + ((double) males / (double) (males + females)) + ")");
		System.out.println("Females: " + females + " (" + ((double) females / (double) (males + females)) + ")");
	}

	public void printMaturity() {
		int matures = 0;
		int immatures = 0;
		for (Rabbit r : rabbits) {
			if (r.isMature()) {
				matures++;
			} else {
				immatures++;
			}
		}
		System.out.println("\nMaturity of the rabbits:");
		System.out.println("Matures: " + matures + " (" + ((double) matures / (double) (matures + immatures)) + ")");
		System.out.println(
				"Immatures: " + immatures + " (" + ((double) immatures / (double) (matures + immatures)) + ")");
	}

	public void printAges() {
		Map<Integer, Integer> ages = new HashMap<Integer, Integer>();
		for (Rabbit r : rabbits) {
			if (!ages.containsKey(r.getYears())) {
				ages.put(r.getYears(), 0);
			}
			ages.put(r.getYears(), ages.get(r.getYears()) + 1);
		}
		System.out.println("\nAges of the rabbits:");
		for (Map.Entry<Integer, Integer> entry : ages.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue() + " ("
					+ ((double) entry.getValue() / (double) rabbits.size()) + ")");
		}
	}

	/* Getters */
	
	public int getNumberRabbits() {
		return rabbits.size();
	}
	
	public int getNumberBySex(String sex) {
		int count = 0;
		for (Rabbit r : rabbits) {
			if (sex.equals(r.getSex())) {
				count++;
			}
		}
		return count;
	}
	
	public int getNumberMature() {
		int count = 0;
		for (Rabbit r : rabbits) {
			if (r.isMature()) {
				count++;
			}
		}
		return count;
	}
	
	public int getNumberImmature() {
		int count = 0;
		for (Rabbit r : rabbits) {
			if (!r.isMature()) {
				count++;
			}
		}
		return count;
	}
	
	/* Mature survival */

	private void matureSurvival() {
		List<Rabbit> deadRabbits = new ArrayList<Rabbit>();
		for (Rabbit r : rabbits) {
			if (r.isMature()) {
				if (isNewMature(r)) {
					if (!randomUnderThreshold(Constants.SURVIVAL_MATURE)) {
						deadRabbits.add(r);
					}
				} else {
					for (int i = 11; i <= 15; i++) {
						if (hasNewYears(r, i)) {
							double rate = getMatureSurvivalRate(r);
							if (!randomUnderThreshold(rate)) {
								deadRabbits.add(r);
							}
							break;
						}
					}
				}
			}
		}
		rabbits.removeAll(deadRabbits);
	}

	private boolean isNewMature(Rabbit r) {
		return r.getAge() == r.getMaturity();
	}

	private boolean hasNewYears(Rabbit r, int years) {
		return (double) years == ((double) r.getAge() / 12.0);
	}

	private double getMatureSurvivalRate(Rabbit r) {
		double decrease = ((double) (r.getYears() - 10) / 10.0);
		return Constants.SURVIVAL_MATURE - decrease;
	}

	/* Gestation */

	private void gestation() {
		List<Rabbit> newRabbits = new ArrayList<Rabbit>();
		for (Rabbit r : rabbits) {
			if (r.isGestation()) {
				r.setGestation(false);
				int numNewRabbits = ud.nextInt(Constants.GESTATION_MIN, Constants.GESTATION_MAX);
				for (int i = 0; i < numNewRabbits; i++) {
					if (randomUnderThreshold(Constants.SURVIVAL_IMMATURE)) {
						newRabbits.add(newRabbit());
					}
				}
			}
		}
		rabbits.addAll(newRabbits);
	}

	private Rabbit newRabbit() {
		String sex = randomUnderThreshold(Constants.MALE_PROBABILITY) ? Constants.MALE : Constants.FEMALE;
		int maturity = Initializer.newMaturity(ud);
		int litters = 0;
		if (Constants.FEMALE.equals(sex)) {
			litters = Initializer.newLitters(nd);
		}
		return new Rabbit(sex, 0, maturity, false, litters);
	}

	/* Pairing */

	private void pairing() {
		if (existMatureMalesAndFemales()) {
			for (Rabbit r : rabbits) {
				if (Constants.FEMALE.equals(r.getSex()) && r.isMature() && !r.isGestation()) {
					r.setGestation(isPairingMonth(r));
				}
			}
		}
	}
	
	private boolean existMatureMalesAndFemales() {
		boolean m = false;
		boolean f = false;
		for (Rabbit r : rabbits) {
			if (r.isMature()) {
				if (!m && Constants.MALE.equals(r.getSex())) {
					m = true;
				} else if (!f && Constants.FEMALE.equals(r.getSex())) {
					f = true;
				}
				if (m && f) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isPairingMonth(Rabbit r) {
		double pairingFreq = 12.0 / (double) r.getLitters();
		int pairingMonth = 0;
		for (int i = 0; pairingMonth < 12; i++) {
			pairingMonth = (int) Math.round(pairingFreq * i);
			if (month == pairingMonth) {
				return true;
			}
		}
		return false;
	}

	/* Update age */

	private void updateAge() {
		for (Rabbit r : rabbits) {
			r.setAge(r.getAge() + 1);
		}
		month = (month + 1) % 12;
	}

	/* Utils */

	private boolean randomUnderThreshold(double threshold) {
		return ud.nextDouble() < threshold;
	}

}