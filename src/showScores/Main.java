package showScores;
import java.io.BufferedWriter;
import java.io.*; 
import java.nio.file.Files; 
import java.nio.file.*; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import neuralNetwork.neuralNet;
import show.Show;
public class Main {
	public static String Path = "Data";
	static File animeNames;
	static ArrayList<String> animeTitles;
	static Scanner filereading;
	static PrintWriter input;
	public static String list;
	public static String currentPath;
	public static int bestGeneration;
	public static int currentGeneration;
	public static int iteration;
	public static String[][] animeList;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Boolean cont = true;
		list = "Watching";
		Scanner userInput;
		String animeName = null;
		Show anime = null;
		animeTitles = new ArrayList<String>();

		while(cont)
		{
			currentPath = Path + "\\" + list;
			new File(currentPath).mkdirs();
			animeNames = new File(currentPath + "//" + "animenames" + ".txt");
			try {
				if(animeNames.createNewFile())
				{
					try {
						input = new PrintWriter(new FileWriter(animeNames, false));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
			compileTitleList();
			boolean exists;
			boolean inList = true;
			int maxpage = 1;
			int page = 1;
			int pageS = 1;
			while(inList)
			{
				maxpage = (int)(animeTitles.size()/10 + 1.5);
				userInput = new Scanner(System.in);
				System.out.println("Page " + page + "/" + maxpage);
				for(int y = 1; y <= 10; y++)
				{
					if((y <= animeTitles.size() - (page - 1)*10))
					{
						try {
							System.out.println(y + ". " + animeTitles.get(y-1 + (page-1)*10));
						}
						catch(ArrayIndexOutOfBoundsException e){
							page = pageS;
							System.out.println(y + ". " + animeTitles.get(y-1 + (page-1)*10));
						}
						
					}
				}
				System.out.println("N. Manual/New");
				System.out.println("Q. Back");
				System.out.println("E. Next");
				System.out.println("O. Options");
				System.out.println("X. Exit Program");
				String ans = makeCompareable(userInput.nextLine());
				switch(ans)
				{
					case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "10":
						animeName = animeTitles.get(Integer.parseInt(ans) - 1 + (page-1)*10);
						if(list.equals(""))
						{
							boolean listContains = false;
							list = "Watching";
							compileTitleList();
							for(int x = 0; x < animeTitles.size(); x++)
							{
								if(makeCompareable(animeTitles.get(x)).equals((makeCompareable(animeName))))
								{
									listContains = true;
								}
							}
							if(listContains == false)
							{
								list = "Completed";
								compileTitleList();
							}
						}
						anime = new Show(animeName, list);
						exists = true;
						for(int x = 0; x < animeTitles.size(); x++)
						{
							if((makeCompareable(animeTitles.get(x)).equals(makeCompareable(animeName))))
							{
								exists = true;
							}
							System.out.println("exists");
						}
						if(animeTitles.size() == 0 || !exists)
						{
							animeTitles.add(animeName);
						}
						if(!anime.getStatus().equals(list) && !list.equals(""))
						{
							for(int x = 0; x < animeTitles.size(); x++)
							{
								if((makeCompareable(animeTitles.get(x)).equals(makeCompareable(animeName))))
								{
									animeTitles.remove(x);
								}
							}
							File animePath = new File(currentPath + "//" + makeSafe(animeName) + ".txt");
							File newPath = new File(Path + "//" + anime.getStatus() + "//" + makeSafe(animeName) + ".txt");
							new File(Path + anime.getStatus()).mkdirs();
							newPath.createNewFile();
							try {
								Files.move(animePath.toPath(), newPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
							}
							catch(IOException e)
							{
								try {
									System.out.println("this happened");
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							WritetoFile();
							list = "Completed";
							compileTitleList();
							animeTitles.add(animeName);
						}
						
						else {
							WritetoFile();
						}
						
						break;
					case "n":
						WritetoFile();
						list = "Watching";
						compileTitleList();
						System.out.println("anime name?");
						animeName = userInput.nextLine();
						anime = new Show(makeSafe(animeName), list);
						exists = true;
							for(int x = 0; x < animeTitles.size(); x++)
							{
								if(!(makeCompareable(animeTitles.get(x)).equals(makeCompareable(animeName))))
								{
									exists = false;
								}	
							}
							
							if(animeTitles.size() == 0 || !exists)
							{
								animeTitles.add(animeName);
							}
						if(!anime.getStatus().equals(list))
						{
							for(int x = 0; x < animeTitles.size(); x++)
							{
								if(!(makeCompareable(animeTitles.get(x)).equals(makeCompareable(animeName))))
								{
									exists = false;
								}
								if(!exists)
								{
									animeTitles.add(animeName);
								}
							}
							File animePath = new File(currentPath + "//" + makeSafe(animeName) + ".txt");
							currentPath = Path + "//" + anime.getStatus();
							File newPath = new File(currentPath + "//" + makeSafe(animeName) + ".txt");
							
							new File(currentPath).mkdirs();
							newPath.createNewFile();
							try {
								Files.move(animePath.toPath(), newPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
							}
							catch(IOException e)
							{
								try {
									System.out.println("this happened");
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							WritetoFile();
							list = anime.getStatus();
							animeTitles.clear();
							compileTitleList();
							animeTitles.add(animeName);
						}
						WritetoFile();
						break;
					case "q":
						pageS = page;
						page--;
						break;
					case "e":
						pageS = page;
						page++;
						break;
					case "o":
						for(int x = 0; x < 5; x++)
						{
							System.out.println("");
						}
						System.out.println("1. View Watching Only");
						System.out.println("2. View Completed Only");
						System.out.println("3. View All");
						System.out.println("4. Run all in list once");
						System.out.println("5. Neural Network Training");
						ans = makeCompareable(userInput.nextLine());
						switch(ans)
						{
							case "1":
								WritetoFile();
								list = "Watching";
							break;
							
							case "2":
								WritetoFile();
								list = "Completed";
							break;
							
							case "3":
								WritetoFile();
								list = "";
							break;
							
							case "4":
								openAll(list);
							case "5":
								neuralNetworkTraining();
								
						}
						
						
						
						compileTitleList();
						break;
					case "x":
						inList = false;
						cont = false;
						WritetoFile();
				}
				}				
			}
		}
	private static void neuralNetworkTraining() throws IOException
	{
		list = "Completed";
		compileTitleList();
		compileAnimeList();
		String animename;
		Show anime;
		double weights[];
		boolean firstGen;
		double nnScore;
		neuralNet nn = new neuralNet();
		currentGeneration = 1;
		boolean cont = true;
		iteration = 1;
		while(cont)
		{
			for(int animeInList = 0; animeInList < animeTitles.size(); animeInList++)
			{
				nn.addDataPoint(Double.valueOf(animeList[animeInList][0]), Double.valueOf(animeList[animeInList][1]), Double.valueOf(animeList[animeInList][2]), Double.valueOf(animeList[animeInList][3]), Double.valueOf(animeList[animeInList][4]), Double.valueOf(animeList[animeInList][5]));
				nn.changeWeights();
			}
			nn.finishIteration2();
			if(iteration % 400 == 0 || iteration < 100)
			{
				System.out.println("Best Avg Accuracy: " + nn.getBestAvgAccuracy())/*((1 - nn.getBestAvgAccuracy()) * 100)) */;
				System.out.println("     Iteration " + iteration);
			}
			iteration++;
			if(nn.getBestAvgAccuracy() < 0.05 || iteration > 20 * 400)
			{
				cont = false;
			}
		}
		double avg = 0;
		double pointavg = 0;
		for(int animeInList = 0; animeInList < animeTitles.size(); animeInList++)
		{
			nnScore = nn.getNNScore(Double.valueOf(animeList[animeInList][0]), Double.valueOf(animeList[animeInList][1]), Double.valueOf(animeList[animeInList][2]), Double.valueOf(animeList[animeInList][3]), Double.valueOf(animeList[animeInList][4]), Double.valueOf(animeList[animeInList][5]));
			System.out.println(animeList[animeInList][6] + " NN Score: " + nnScore + "                               Real Score: " + animeList[animeInList][5] + "                            % off: " + ((nnScore - Double.valueOf(animeList[animeInList][5]))/Double.valueOf(animeList[animeInList][5])));
		}
		for(int animeInList = 0; animeInList < animeTitles.size(); animeInList++)
		{
			animename = animeTitles.get(animeInList);
			anime = new Show(animename, list, true);
			nnScore = nn.getNNScore(anime.getAverageScore(), anime.getAverageEpisodeDeviation(), anime.getAverageSpeedDeviation(), anime.getEpisodeCount(), anime.getImpactScore(), anime.getRealScore());
			avg += Math.abs((nnScore-anime.getRealScore())/anime.getRealScore());
			pointavg += Math.abs(nnScore - anime.getRealScore());
			System.out.println(Math.abs((nnScore-anime.getRealScore())/anime.getRealScore()));
		}
		System.out.println("avg = " + (avg/animeTitles.size()));
		System.out.println("average points off: " + (pointavg/animeTitles.size()));

		/*do
		{
			System.out.println("current generation: " + currentGeneration);
			System.out.println("best generation: " + bestGeneration);
			for(int iteration = 1; iteration <= 30; iteration++)
			{
				if(currentGeneration % 30 == 0)
				{
					nn.randomizeHalf();
				}
				else
				{
					nn.mutation();
				}
				for(int animeInList = 0; animeInList < animetitles.size(); animeInList++)
				{
					animename = animetitles.get(animeInList);
					anime = new anime(animename, list, true);
					nn.addDataPoint(anime.getAverageScore(), anime.getAverageEpisodeDeviation(), anime.getAverageSpeedDeviation(), anime.getEpisodeCount(), anime.getImpactScore(), anime.getRealScore());
					nn.RunItThrough();
				}
				
				nn.finishIteration();
			}

			nn.finishGeneration();
			currentGeneration++;
			System.out.println("Best Avg Accuracy: " + nn.getBestAvgAccuracy());
		}
		while(nn.getBestAvgAccuracy() > 0.05); */
		nn.returnData();

	}
	private static void compileAnimeList() throws IOException
	{
		Show anime;
		animeList = new String[animeTitles.size()][7];
		for(int x = 0; x < animeTitles.size(); x++)
		{
			anime = new Show(animeTitles.get(x), list, true);
			for(int y = 0; y < 7; y++)
			{
				switch(y)
				{
					case 0:
						animeList[x][y] = Double.toString(anime.getAverageScore());
						break;
					case 1:
						animeList[x][y] = Double.toString(anime.getAverageEpisodeDeviation());
						break;
					case 2:
						animeList[x][y] = Double.toString(anime.getAverageSpeedDeviation());
						break;
					case 3:
						animeList[x][y] = Double.toString(anime.getEpisodeCount());
						break;
					case 4:
						animeList[x][y] = Double.toString(anime.getImpactScore());
						break;
					case 5:
						animeList[x][y] = Double.toString(anime.getRealScore());
						break;
					case 6:
						animeList[x][y] = animeTitles.get(x);
						break;
				}
				System.out.println(animeList[x][y]);
			}
		}
	}
	private static void openAll (String List) throws IOException
	{
		compileTitleList();
		String animename;
		Show anime;
		for(int x = 0; x < animeTitles.size(); x++)
		{
			animename = animeTitles.get(x);
			anime = new Show(animename, List, true);
		}
	}
	private static void compileTitleList() throws IOException {
		// TODO Auto-generated method stub
		currentPath = Path + "//" + list;
		animeNames = new File(currentPath + "//" + "animenames" + ".txt");
		try {
			filereading = new Scanner(animeNames);
		}
		catch (FileNotFoundException e){
			new File(currentPath + "//").mkdirs();
			animeNames.createNewFile();
			filereading = new Scanner(animeNames);
		}
		int x = 0;
		boolean loop = true;
		animeTitles.clear();
		if(list.equals(""))
		{
			String tempPath =Path + "//" + "Watching";
			animeNames = new File(Path + "//" + tempPath + "//" + "animenames" + ".txt");
			filereading = new Scanner(animeNames);
			String next;
			while(filereading.hasNextLine())
			{
				next = filereading.nextLine();
				animeTitles.add(next);
			}
			tempPath =Path + "//" + "Completed";
			animeNames = new File(tempPath + "//" + "animenames" + ".txt");
			filereading = new Scanner(animeNames);
			while(filereading.hasNextLine())
			{
				next = filereading.nextLine();
				animeTitles.add(next);
			}
			animeNames = new File(currentPath + "//" + "animenames" + ".txt");
		}
		else
		{
			boolean lineExists;
			while(loop)
			{
				x++;
				lineExists = filereading.hasNextLine();
				if(lineExists)
				{
					animeTitles.add(filereading.nextLine());
				}
				else if(!lineExists)
				{
					loop = false;
				}
			}
		}
		Collections.sort(animeTitles, new Comparator<String>() {
			@Override
		    public int compare(String s1, String s2) {
		        return s1.compareToIgnoreCase(s2);
		    }
		});
		filereading.close();
		
	}
	public static void WritetoFile()
	{
		try {
			new File(currentPath).mkdirs();
			animeNames = new File(currentPath + "//" + "animenames" + ".txt");
			input = new PrintWriter(new FileWriter(animeNames, false));
			for(int x = 0; x < animeTitles.size(); x++)
			{
				input.println(animeTitles.get(x));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.close();

	}
	public static String makeCompareable(String str)
	{
		str = str.toLowerCase().trim();
		str = str.replace(" ", "");
		str = str.replace("?", "");
		str = str.replace("!", "");
		str = str.replace("'", "");
		str = str.replace(".","");
		return str;
	}
	
	public static String makeCompareable(String str, boolean period)
	{
		str = str.toLowerCase().trim();
		str = str.replace(" ", "");
		str = str.replace("?", "");
		str = str.replace("!", "");
		str = str.replace("'", "");
		return str;
	}

	public static String makeSafe(String str)
	{
		str = str.replace("?", " ");
		str = str.replace("!", " ");
		str = str.replace("<", " ");
		str = str.replace(">", " ");
		str = str.replace("\"", " ");
		str = str.replace("/", " ");
		str = str.replace("\\", " ");
		str = str.replace("|", " ");
		str = str.replace("*", " ");
		str = str.replace(":", " ");
		return str;
	}
	public static double round(double value)
	{
		value = (int)((value + 0.005) * 100 );
		value = value/100;
		return value;
	}

}
