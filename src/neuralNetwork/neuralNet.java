package neuralNetwork;

import java.util.ArrayList;

import showScores.Main;

public class neuralNet {


	double weights[];
	double derWeights[];
	double changeWeights[];
	double bestWeights[];

	double bias[];
	double derBias[];
	double changeBias[];
	double bestBias[];

	double inputs[];
	double avgAccuracy;
	double bestAvgAccuracy;
	double baseAccuracy;

	ArrayList<Double> accuracyList = new ArrayList<Double>();




	public neuralNet()
	{
		weights = new double[51];
		bestWeights = new double[51];
		changeWeights = new double [51];
		derWeights = new double[51];
		for(int x = 0; x < 51; x++)
		{
			weights[x] = Math.random();
		}
		
		bias = new double[11];
		derBias = new double[11];
		changeBias = new double[11];
		bestBias = new double[11];
		for(int x = 0; x < 11; x++)
		{
			bias[x] = Math.random();
		}
	/*	weights[0] = 0.28220627518182345;
        weights[1] = -0.6388493577617642;
        weights[2] = -0.5567956955682409;
        weights[3] = -0.003786491229766035;
        weights[4] = 0.22449168910577025;
        bias[0] = -2.563357434836538; */
		
		weights[0] = 0.9933876118340546;
		weights[1] = 0.5140150256803155;
		weights[2] = 0.4716424634153016;
		weights[3] = 0.8375376125316227;
		weights[4] = 0.936482374835705;
		weights[5] = 0.15569475010233969;
		weights[6] = 0.02332377197884998;
		weights[7] = 0.9670028276100154;
		weights[8] = 0.5187846941838676;
		weights[9] = 0.5227786070605185;
		weights[10] = 0.10570648666223993;
		weights[11] = 0.634275901087352;
		weights[12] = 0.021368899319620074;
		weights[13] = 0.019667871778283736;
		weights[14] = 0.44298637643968686;
		weights[15] = 0.34036513941064467;
		weights[16] = 0.38787863900985853;
		weights[17] = 0.2858403352884815;
		weights[18] = 0.3004641687586788;
		weights[19] = 0.7695008870048777;
		weights[20] = 0.25741438280847384;
		weights[21] = 0.8144325476537806;
		weights[22] = 0.27955401093396437;
		weights[23] = 0.11719046222553033;
		weights[24] = 0.4104367544299733;
		weights[25] = 0.19251070404201945;
		weights[26] = 0.5133086663721383;
		weights[27] = 0.8613631081970303;
		weights[28] = 0.16224323655508788;
		weights[29] = 0.17166996046088456;
		weights[30] = 0.5242727713483268;
		weights[31] = 0.3037311413574869;
		weights[32] = 0.03148046141296146;
		weights[33] = 0.8599766315518451;
		weights[34] = 0.19442759168079474;
		weights[35] = 0.7486124283179323;
		weights[36] = 0.8083152640778664;
		weights[37] = 0.4601905245027037;
		weights[38] = 0.9995299988155745;
		weights[39] = 0.8623419736346781;
		weights[40] = 1.1674898131130922;
		weights[41] = 2.5959125791821216;
		weights[42] = 5.700678546004439;
		weights[43] = 6.485777780904867;
		weights[44] = 6.068445254532578;
		weights[45] = 5.91011823227844;
		weights[46] = 5.7468225163111955;
		weights[47] = 6.339117727827465;
		weights[48] = 5.815809464639331;
		weights[49] = 13.42806531396655;
		weights[50] = 38.52635196477415;
		
		bias[0] = -0.20485914566879174;
		bias[1] = 0.1712351194404911;
		bias[2] = 0.1486951210426833;
		bias[3] = -0.392838691090854;
		bias[4] = -0.3197295510515654;
		bias[5] = -0.553546701481008;
		bias[6] = -0.29638940777301237;
		bias[7] = -0.4110024456152197;
		bias[8] = -0.3594700786597949;
		bias[9] = -0.27124171616780013;
		bias[10] = -3.839520787079571;
		
		inputs = new double[8];

		avgAccuracy = 1;
		bestAvgAccuracy = 1;
		baseAccuracy = 1;

	}
	public void addDataPoint(double avgScore,double epDeviation, double speedDeviation,double epCount,double impactScore, double realScore)
	{
		inputs[0] = avgScore;
		inputs[1] = epDeviation;
		inputs[2] = speedDeviation;
		inputs[3] = epCount;
		inputs[4] = impactScore;
		inputs[5] = realScore;
		
	}
	public void addDataPoint(double avgScore,double epDeviation, double speedDeviation,double epCount,double impactScore)
	{
		inputs[0] = avgScore;
		inputs[1] = epDeviation;
		inputs[2] = speedDeviation;
		inputs[3] = epCount;
		inputs[4] = impactScore;
		double output = runItThroughDet();
		double accuracy = (inputs[5] - output)/inputs[5];
		accuracyList.add(accuracy);
	}
	public double runItThroughDet()
	{
		double unfirstLayer1 = weights[0]*inputs[0] + weights[1]*inputs[1] + weights[2]*inputs[2] + weights[3]*inputs[3] + weights[4]*inputs[4] + bias[0];
		double firstLayer1 = sigmundIt(unfirstLayer1);
		//double derFirstLayer1 = sigmoidDer(unfirstLayer1);
		
		double unfirstLayer2 = weights[5]*inputs[0] + weights[6]*inputs[1] * weights[7]*inputs[2] + weights[8]*inputs[3] + weights[9]*inputs[4] + bias[1];
		double firstLayer2 = sigmundIt(unfirstLayer2);
		//double derFirstLayer2 = sigmoidDer(unfirstLayer2);
		
		double unfirstLayer3 = weights[10]*inputs[0] + weights[11]*inputs[1] + weights[12]*inputs[2] + weights[13]*inputs[3] * weights[14]*inputs[4] + bias[2];
		double firstLayer3 = sigmundIt(unfirstLayer3);
		//double derFirstLayer3 = sigmoidDer(unfirstLayer3);

		double unfirstLayer4 = weights[15]*inputs[0] * weights[16]*inputs[1] + weights[17]*inputs[2] * weights[18]*inputs[3] + weights[19]*inputs[4] + bias[3];
		double firstLayer4 = sigmundIt(unfirstLayer4);
		//double derFirstLayer4 = sigmoidDer(unfirstLayer4);
		
		double unfirstLayer5 = weights[20]*inputs[0] * weights[21]*inputs[3] + weights[22]*inputs[2] * weights[23]*inputs[1] + weights[24]*inputs[4] + bias[4];
		double firstLayer5 = sigmundIt(unfirstLayer5);
		
		double unfirstLayer6 = weights[25]*Math.sin(inputs[0]) + weights[26]*Math.sin(inputs[1]) + weights[27]*Math.sin(inputs[2]) + weights[28]*Math.sin(inputs[3]) + weights[29]*Math.sin(inputs[4]) + bias[5];
		double firstLayer6 = sigmundIt(unfirstLayer6);
		
		double unfirstLayer7 = weights[30]*inputs[0]*inputs[0] + weights[31]*inputs[1] + weights[32]*inputs[2] + weights[33]*inputs[3] + weights[34]*inputs[4] + bias[6];
		double firstLayer7 = sigmundIt(unfirstLayer7);
		
		double secondLayer1 = weights[35]*firstLayer1 + weights[36]*firstLayer2 + weights[37]*firstLayer3 + weights[38] * firstLayer4 + weights[39] * firstLayer5 + weights[40] * firstLayer6 + weights[41] * firstLayer7 + bias[7];
		secondLayer1 = sigmundIt(secondLayer1);
		double secondLayer2 = weights[42]*firstLayer1 + weights[43]*firstLayer2 + weights[44]*firstLayer3 + weights[45] * firstLayer4 + weights[46] * firstLayer5 + weights[47] * firstLayer6 + weights[48] * firstLayer7 + bias[8];
		secondLayer2 = sigmundIt(secondLayer2);

		double output = weights[49]*secondLayer1 + weights[50]*secondLayer2 + bias[9];
		output = sigmundIt(output);
		
		output = (output) * 10 + bias[10];
		
		return output; 
	}
	public void RunItThrough()
	{
		double unfirstLayer1 = weights[0]*inputs[0] + weights[1]*inputs[1] + weights[2]*inputs[2] + weights[3]*inputs[3] + weights[4]*inputs[4] + bias[0];
		double firstLayer1 = sigmundIt(unfirstLayer1);
		double derFirstLayer1 = sigmoidDer(unfirstLayer1);
		
		double unfirstLayer2 = weights[5]*inputs[0] + weights[6]*inputs[1] + weights[7]*inputs[2] + weights[8]*inputs[3] + weights[9]*inputs[4] + bias[1];
		double firstLayer2 = sigmundIt(unfirstLayer2);
		double derFirstLayer2 = sigmoidDer(unfirstLayer2);
		
		double unfirstLayer3 = weights[10]*inputs[0] + weights[11]*inputs[1] + weights[12]*inputs[2] + weights[13]*inputs[3] + weights[14]*inputs[4] + bias[2];
		double firstLayer3 = sigmundIt(unfirstLayer3);
		double derFirstLayer3 = sigmoidDer(unfirstLayer3);

		double unfirstLayer4 = weights[15]*inputs[0] + weights[16]*inputs[1] + weights[17]*inputs[2] + weights[18]*inputs[3] + weights[19]*inputs[4] + bias[3];
		double firstLayer4 = sigmundIt(unfirstLayer4);
		double derFirstLayer4 = sigmoidDer(unfirstLayer4);
		
		double secondLayer1 = weights[20]*firstLayer1 + weights[21]*firstLayer2 + weights[22]*firstLayer3 + weights[23] * firstLayer4 + bias[3];
		secondLayer1 = sigmundIt(secondLayer1);
		double secondLayer2 = weights[24]*firstLayer1 + weights[25]*firstLayer2 + weights[26]*firstLayer3 + weights[27] * firstLayer4 + bias[4];
		secondLayer2 = sigmundIt(secondLayer2);

		double output = weights[28]*secondLayer1 + weights[29]*secondLayer2 + bias[5];
		output = sigmundIt(output);
		
		output = (output) * 10;
		

		
		
		
	}
	public void changeWeights()
	{
		double output = runItThroughDet();
		double cost = Math.pow((output - inputs[5]),2);
		double derCostdOutput = 2 * (output - inputs[5]);
		double learningRate = 0.001;
		for(int x = 0; x < weights.length; x++)
		{
			weights[x] += 0.00001;
			derWeights[x] = (Math.pow(runItThroughDet() - inputs[5], 2) - cost)/0.00001;
			weights[x] -= 0.00001;
			if(weights[x] < -30)
			{
				weights[x] = -30;
			}
			if(weights[x] > 30)
			{
				weights[x] = 30;
			}
		}
		
		for(int x = 0; x < weights.length; x++)
		{
			weights[x] -= derWeights[x] * learningRate;
		}
		
		for(int x = 0; x < bias.length; x++)
		{
			bias[x] += 0.01;
			derBias[x] = (Math.pow(runItThroughDet() - inputs[5], 2) - cost)/0.01;
			bias[x] -= 0.01;
			
		}
		
		for(int x = 0; x < bias.length; x++)
		{
			bias[x] -= derBias[x] * learningRate;
		}
		
		output = runItThroughDet();
		double accuracy = ((output - inputs[5])/inputs[5]);
		accuracyList.add(accuracy);
		
	}
	public void finishIteration2()
	{
		avgAccuracy = 0;
		for(int x = 0; x < accuracyList.size(); x++)
		{
			avgAccuracy += Math.abs(accuracyList.get(x));
		}
		avgAccuracy = avgAccuracy / accuracyList.size();
		bestAvgAccuracy = avgAccuracy;
		
		accuracyList.clear();
	}
	public double sigmundIt(double value)
	{
		return (1/(1 + Math.exp(-value)));
	}
	public double sigmoidDer(double value)
	{
		return sigmundIt(value) * (1 - sigmundIt(value));
	}
	public void finishIteration()
	{
		avgAccuracy = 0;
		for(int x = 0; x < accuracyList.size(); x++)
		{
			avgAccuracy += Math.abs(accuracyList.get(x));
		}
		avgAccuracy /= accuracyList.size();
		if(avgAccuracy < bestAvgAccuracy)
		{
			saveBestWeights();
			bestAvgAccuracy = avgAccuracy;
		}
		accuracyList.clear();
	}
	public void saveBestWeights()
	{
		for(int x = 0; x < bestWeights.length; x++)
		{
			bestWeights[x] = weights[x];
		}
		for(int x = 0; x < bestBias.length; x++)
		{
			bestBias[x] = bias[x];
		}
		Main.bestGeneration = Main.currentGeneration;
	}
	public void finishGeneration()
	{
		for(int x = 0; x < bestWeights.length; x++)
		{
			weights[x] = bestWeights[x];
		}
		for(int x = 0; x < bestBias.length; x++)
		{
			bias[x] = bestBias[x];
		}
		baseAccuracy = bestAvgAccuracy;
	}
	public void randomizeWeights()
	{
		for(int x = 0; x < weights.length; x++)
		{
			if(Math.random() > 0.5)
			{
				weights[x] = Math.random();
			}
			else
			{
				weights[x] = -Math.random();
			}
		}

		for(int x = 0; x < 6; x++)
		{
			if(Math.random() > 0.5)
			{
				bias[x] = Math.random() * 2;
			}
			else
			{
				bias[x] = -Math.random() * 2;
			}
		}
	}
	public void randomizeHalf()
	{
		mutation();
		for(int x = 0; x < weights.length; x++)
		{
			if(Math.random() > 0.5)
			{

				if(Math.random() > 0.5)
				{
					weights[x] = Math.random();
				}
				else
				{
					weights[x] = -Math.random();
				}
			}
		}

		for(int x = 0; x < bias.length; x++)
		{
			if(Math.random() > 0.5)
			{
				if(Math.random() > 0.5)
				{
					bias[x] = Math.random() * 2;
				}
				else
				{
					bias[x] = -Math.random() * 2;
				}
			}
		}
	}
	public void mutation()
	{
		double mutation = 0.4/(((double)Main.currentGeneration/2) %200 + 1)/(int)(Main.currentGeneration/75 + 1);
		for(int x = 0; x < weights.length; x++)
		{
			weights[x] = bestWeights[x];
			if(Math.random() > 0.5)
			{
				weights[x] += Math.random() * mutation;
			}
			else
			{
				weights[x] -= Math.random() * mutation;
			}
		}
		for(int x = 0; x < bias.length; x++)
		{
			bias[x] = bestBias[x];
			if(Math.random() > 0.5)
			{
				bias[x] += Math.random() * mutation;
			}
			else
			{
				bias[x] -= Math.random() * mutation;
			}
		}

	}
	public void returnData()
	{
		for(int x = 0; x < weights.length; x++)
		{
			System.out.println("weights[" + (x) + "] = " + weights[x] + ";");
		}
		for(int x = 0; x < bias.length; x++)
		{
			System.out.println("bias[" + (x) + "] = " + bias[x] + ";");
		}
		System.out.println("Final Avg Accuracy: " + bestAvgAccuracy);
	}
	public double getBestAvgAccuracy()
	{
		return bestAvgAccuracy;
	}
	public double getNNScore(double avgScore,double epDeviation, double speedDeviation,double epCount, double impactScore, double realScore)
	{
		addDataPoint(avgScore, epDeviation, speedDeviation, epCount, impactScore,realScore);
	/*	double firstLayer1 = weights[0]*inputs[0] + weights[1]*inputs[1] + weights[2]*inputs[2] + weights[3]*inputs[3] + weights[4]*inputs[4] + bias[0];
		double output = sigmundIt(firstLayer1);
		output = (output) * 10; */
		double output = runItThroughDet();
		double accuracy = (output - inputs[5])/inputs[5];
		accuracyList.add(accuracy);
		return output;
	}
	public double getNNScore(double avgScore,double epDeviation, double speedDeviation,double epCount, double impactScore)
	{
		addDataPoint(avgScore, epDeviation, speedDeviation, epCount, impactScore);
		double output = runItThroughDet();
		return (double)(int)(output + 0.5);
	}

}
