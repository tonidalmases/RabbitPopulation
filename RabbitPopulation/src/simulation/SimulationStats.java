package simulation;

import java.io.IOException;

import population.Population;
import utilities.CSVEditor;
import utilities.Constants;
import utilities.Initializer;
import utilities.MT19937;
import utilities.NormalDistribution;
import utilities.UniformDistribution;

public class SimulationStats {

	public static void main(String[] args) throws IOException {
		MT19937 mt = new MT19937(0);
		NormalDistribution nd = new NormalDistribution(mt);
		UniformDistribution ud = new UniformDistribution(mt);

		CSVEditor editor = new CSVEditor("C:/Programes/Eclipse/files/02_stats.csv");
		editor.writeLine(new String[] { "Experiment", "Rabbits", "Males", "Females", "Matures", "Immatures" });

		String[] strArray;

		Population population;
		for (int i = 0; i < Constants.EXPERIMENTS; i++) {
			System.out.println("Experiment " + i);
			population = new Population(Initializer.generateInitialPopulation(ud, nd), nd, ud);
			for (int j = 0; j < Constants.YEARS; j++) {
				population.iterateOneYear();
			}
			
			strArray = new String[Constants.YEARS + 1];
			strArray[0] = String.valueOf(i);
			strArray[1] = String.valueOf(population.getNumberRabbits());
			strArray[2] = String.valueOf(population.getNumberBySex(Constants.MALE));
			strArray[3] = String.valueOf(population.getNumberBySex(Constants.FEMALE));
			strArray[4] = String.valueOf(population.getNumberMature());
			strArray[5] = String.valueOf(population.getNumberImmature());
			
			editor.writeLine(strArray);
		}

		editor.closeEditor();
		
		System.out.println("Finished");
	}

}