package fitnessFunctions;

import algorithmDataStructures.AlgorithmOutput;

public class FitnessTester {
	public FitnessTester(AlgorithmOutput ap){
		new FirstChoicePercent(ap);
		new LabFullness(ap);
	}
}
