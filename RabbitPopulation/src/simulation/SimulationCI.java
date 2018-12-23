package simulation;

import java.io.IOException;

import population.Population;
import utilities.CSVEditor;
import utilities.Constants;
import utilities.Initializer;
import utilities.MT19937;
import utilities.NormalDistribution;
import utilities.UniformDistribution;

public class SimulationCI {

	public static void main(String[] args) throws IOException {
		MT19937 mt = new MT19937(0);
		NormalDistribution nd = new NormalDistribution(mt);
		UniformDistribution ud = new UniformDistribution(mt);

		CSVEditor editor = new CSVEditor("C:/Programes/Eclipse/files/03_cr.csv");

		double[] exp = new double[Constants.EXPERIMENTS];
		double[] ci_exp = new double[Constants.CI_EXPERIMENTS];

		Population population;
		for (int k = 0; k < Constants.CI_EXPERIMENTS; k++) {
			for (int i = 0; i < Constants.EXPERIMENTS; i++) {
				population = new Population(Initializer.generateInitialPopulation(ud, nd), nd, ud);
				for (int j = 0; j < Constants.YEARS; j++) {
					population.iterateOneYear();
				}
				exp[i] = population.getNumberRabbits();
			}
			ci_exp[k] = computeMean(exp, Constants.EXPERIMENTS);
			System.out.println("CI Experiment " + k + ". Mean: " + ci_exp[k]);
			editor.writeLine(new String[] { String.valueOf(k), String.valueOf(ci_exp[k]) });
		}

		double mean = computeMean(ci_exp, Constants.CI_EXPERIMENTS);
		System.out.println("Mean: " + mean);

		double r = computeConfidenceRadius(ci_exp, mean);
		System.out.println("Confidence radius: " + r);

		double out = computeOutSimulation(ci_exp, mean, r);
		System.out.println("Out: " + out);

		editor.closeEditor();

		System.out.println("Finished");
	}

	private static double computeMean(double[] exp, int n_experiments) {
		double sum = 0;
		for (int i = 0; i < n_experiments; i++) {
			sum += exp[i];
		}
		return sum / (double) n_experiments;
	}

	private static double computeConfidenceRadius(double[] exp, double mean) {
		double sum = 0;
		for (int i = 0; i < Constants.CI_EXPERIMENTS; i++) {
			sum += (exp[i] - mean) * (exp[i] - mean);
		}
		double s = sum / (Constants.CI_EXPERIMENTS - 1);
		return Constants.CI_T * Math.sqrt(s / Constants.CI_EXPERIMENTS);
	}

	private static double computeOutSimulation(double[] exp, double mean, double r) {
		int out = 0;
		for (int i = 0; i < Constants.CI_EXPERIMENTS; i++) {
			if (exp[i] < (mean - r) || exp[i] > (mean + r))
				out++;
		}
		return (double) out / (double) Constants.CI_EXPERIMENTS;
	}

}
