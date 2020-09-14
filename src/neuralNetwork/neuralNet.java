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
			weights[x] = Math.random() * 5;
		}
		
		bias = new double[11];
		derBias = new double[11];
		changeBias = new double[11];
		bestBias = new double[11];
		for(int x = 0; x < 11; x++)
		{
			bias[x] = Math.random();
		}
		/*weights[0] = 0.28220627518182345;
        weights[1] = -0.6388493577617642;
        weights[2] = -0.5567956955682409;
        weights[3] = -0.003786491229766035;
        weights[4] = 0.22449168910577025;
        bias[0] = -2.563357434836538; */
		
		weights[0] = 0.7047978158987594;
		weights[1] = 2.6359782904187328;
		weights[2] = 0.7973855633843541;
		weights[3] = 1.3432884018900104;
		weights[4] = 4.644749519224513;
		weights[5] = 2.483205156620393;
		weights[6] = 3.966621766246536;
		weights[7] = 2.8426753249604424;
		weights[8] = 3.2775088602751987;
		weights[9] = 1.932363597524494;
		weights[10] = 3.765811166696689;
		weights[11] = 3.775760080982718;
		weights[12] = 4.497966776236853;
		weights[13] = 0.3578081471049388;
		weights[14] = 0.4630603102637737;
		weights[15] = 2.878712875358681;
		weights[16] = 3.140955436518182;
		weights[17] = 3.6701856918895306;
		weights[18] = 0.684198366684719;
		weights[19] = 1.7783449880845426;
		weights[20] = 1.4555673794321984;
		weights[21] = 1.8125111330772254;
		weights[22] = 0.39222833823809755;
		weights[23] = 3.668033569914307;
		weights[24] = 1.3851269908481263;
		weights[25] = 3.3230132430010397;
		weights[26] = 0.8077623155748787;
		weights[27] = 4.705401334083072;
		weights[28] = 0.31922864367838144;
		weights[29] = 2.953670556853438;
		weights[30] = 2.747888535479526;
		weights[31] = 0.5293621844626434;
		weights[32] = 4.957885671293521;
		weights[33] = 3.064809192883728;
		weights[34] = 4.162674121505423;
		weights[35] = 4.478716562607558;
		weights[36] = 4.807718728453309;
		weights[37] = 4.4038202333309355;
		weights[38] = 1.3565185252030885;
		weights[39] = 0.9353034215891861;
		weights[40] = 2.616243100007833;
		weights[41] = 1.920007329737218;
		weights[42] = 1.4958255244588752;
		weights[43] = 1.7117779064443341;
		weights[44] = 3.742699328347848;
		weights[45] = 1.4677678902935314;
		weights[46] = 2.5249813267236747;
		weights[47] = 0.5574217999011878;
		weights[48] = 0.06250928275975072;
		weights[49] = 3.6691941920213194;
		weights[50] = 1.6017912796440223;
		bias[0] = 5.284673430116425;
		bias[1] = 5.453148966978525;
		bias[2] = 5.395766649871816;
		bias[3] = 5.951109279607398;
		bias[4] = 5.657080159200418;
		bias[5] = 6.102410237304012;
		bias[6] = 5.820270139183672;
		bias[7] = 5.929831638010509;
		bias[8] = 6.0743624023361225;
		bias[9] = 5.9406457244175;
		bias[10] = -4.9178226571127315;
		
		inputs = new double[8];

		avgAccuracy = 1;
		bestAvgAccuracy = 1;
		baseAccuracy = 1;

	}
	public void addDataPoint(double avgScore,double epDeviation, double speedDeviation,double epCount,double impactScore, double realScore)
	{
		inputs[0] = avgScore;
		inputs[1] = 0;
		inputs[2] = speedDeviation;
		inputs[3] = epCount;
		inputs[4] = impactScore;
		inputs[5] = realScore;
		
	}
	public void addDataPoint(double avgScore,double epDeviation, double speedDeviation,double epCount,double impactScore)
	{
		inputs[0] = avgScore;
		inputs[1] = 0;
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
		double learningRate = 0.1;
		for(int x = 0; x < weights.length; x++)
		{
			weights[x] += 0.0001;
			derWeights[x] = (cost - Math.pow(runItThroughDet() - inputs[5], 2))/0.0001;
			weights[x] -= 0.0001;
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
