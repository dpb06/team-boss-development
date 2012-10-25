package fitnessFunctions;

import algorithmDataStructures.AlgorithmOutput;

public class FitnessTester  { 
 private final boolean DEBUG = false; 

	public FitnessTester(AlgorithmOutput ap){
		new FirstChoicePercent(ap);
		new LabFullness(ap);
	}
}
