package simulation;

import java.io.IOException;

import population.Population;
import utilities.CSVEditor;
import utilities.Constants;
import utilities.Initializer;
import utilities.MT19937;
import utilities.NormalDistribution;
import utilities.UniformDistribution;

public class SimulationYear {

	public static void main(String[] args) throws IOException {
		MT19937 mt = new MT19937(0);
		NormalDistribution nd = new NormalDistribution(mt);
		UniformDistribution ud = new UniformDistribution(mt);

		CSVEditor editor = new CSVEditor("C:/Programes/Eclipse/files/01_year.csv");
		editor.writeLine(new String[] { "Year", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" });

		String[] strArray;

		Population population;
		for (int i = 0; i < Constants.EXPERIMENTS; i++) {
			strArray = new String[Constants.YEARS + 1];
			strArray[0] = String.valueOf(i);
			System.out.println("Experiment " + i);
			population = new Population(Initializer.generateInitialPopulation(ud, nd), nd, ud);
			for (int j = 0; j < Constants.YEARS; j++) {
				population.iterateOneYear();
				strArray[j + 1] = String.valueOf(population.getNumberRabbits());
			}
			editor.writeLine(strArray);
		}

		editor.closeEditor();
	}

}