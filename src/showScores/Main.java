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
	static File showNames;
	static ArrayList<String> showTitles;
	static Scanner filereading;
	static PrintWriter input;
	public static String list;
	public static String currentPath;
	public static String[][] showList;
	public static ArrayList<String> tempShowTitles;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Boolean cont = true;
		list = "Watching";
		Scanner userInput;
		String showName = null;
		Show show = null;
		showTitles = new ArrayList<String>();

		while(cont)
		{
			currentPath = Path + "\\" + list;
			new File(currentPath).mkdirs();
			showNames = new File(currentPath + "//" + "shownames" + ".txt");
			try {
				if(showNames.createNewFile())
				{
					try {
						input = new PrintWriter(new FileWriter(showNames, false));
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
				maxpage = (int)(showTitles.size()/10 + 1.5);
				userInput = new Scanner(System.in);
				System.out.println("Page " + page + "/" + maxpage);
				for(int y = 1; y <= 10; y++)
				{
					if((y <= showTitles.size() - (page - 1)*10))
					{
						try {
							System.out.println(y + ". " + showTitles.get(y-1 + (page-1)*10));
						}
						catch(ArrayIndexOutOfBoundsException e){
							page = pageS;
							System.out.println(y + ". " + showTitles.get(y-1 + (page-1)*10));
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
						showName = showTitles.get(Integer.parseInt(ans) - 1 + (page-1)*10);
						if(list.equals(""))
						{
							boolean listContains = false;
							list = "Watching";
							compileTitleList();
							for(int x = 0; x < showTitles.size(); x++)
							{
								if(makeCompareable(showTitles.get(x)).equals((makeCompareable(showName))))
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
						show = new Show(showName, list);
						exists = true;
						for(int x = 0; x < showTitles.size(); x++)
						{
							if((makeCompareable(showTitles.get(x)).equals(makeCompareable(showName))))
							{
								exists = true;
							}
							System.out.println("exists");
						}
						if(showTitles.size() == 0 || !exists)
						{
							showTitles.add(showName);
						}
						if(!show.getStatus().equals(list) && !list.equals(""))
						{
							for(int x = 0; x < showTitles.size(); x++)
							{
								if((makeCompareable(showTitles.get(x)).equals(makeCompareable(showName))))
								{
									showTitles.remove(x);
								}
							}
							File showPath = new File(currentPath + "//" + makeSafe(showName) + ".txt");
							File newPath = new File(Path + "//" + show.getStatus() + "//" + makeSafe(showName) + ".txt");
							new File(Path + show.getStatus()).mkdirs();
							newPath.createNewFile();
							try {
								Files.move(showPath.toPath(), newPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
							showTitles.add(showName);
						}
						
						else {
							WritetoFile();
						}
						
						break;
					case "n":
						WritetoFile();
						list = "Watching";
						compileTitleList();
						System.out.println("show name?");
						showName = userInput.nextLine();
						show = new Show(makeSafe(showName), list);
						exists = true;
							for(int x = 0; x < showTitles.size(); x++)
							{
								if(!(makeCompareable(showTitles.get(x)).equals(makeCompareable(showName))))
								{
									exists = false;
								}	
							}
							
							if(showTitles.size() == 0 || !exists)
							{
								showTitles.add(showName);
							}
						if(!show.getStatus().equals(list))
						{
							for(int x = 0; x < showTitles.size(); x++)
							{
								if(!(makeCompareable(showTitles.get(x)).equals(makeCompareable(showName))))
								{
									exists = false;
								}
								if(!exists)
								{
									showTitles.add(showName);
								}
							}
							File showPath = new File(currentPath + "//" + makeSafe(showName) + ".txt");
							currentPath = Path + "//" + show.getStatus();
							File newPath = new File(currentPath + "//" + makeSafe(showName) + ".txt");
							
							new File(currentPath).mkdirs();
							newPath.createNewFile();
							try {
								Files.move(showPath.toPath(), newPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
							list = show.getStatus();
							showTitles.clear();
							compileTitleList();
							showTitles.add(showName);
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
		compileShowList();
		String showname;
		Show show;
		double weights[];
		boolean firstGen;
		double nnScore;
		int iteration = 0;
		neuralNet nn = new neuralNet();
		boolean cont = true;
		while(cont)
		{
			for(int showInList = 0; showInList < tempShowTitles.size(); showInList++)
			{
				nn.addDataPoint(Double.valueOf(showList[showInList][0]), Double.valueOf(showList[showInList][1]), Double.valueOf(showList[showInList][2]), Double.valueOf(showList[showInList][3]), Double.valueOf(showList[showInList][4]), Double.valueOf(showList[showInList][5]));
				nn.changeWeights();
			}
			nn.finishIteration2();
			if(iteration % 400 == 0 || iteration < 100)
			{
				System.out.println("Best Avg Accuracy: " + nn.getBestAvgAccuracy());
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
		for(int showInList = 0; showInList < tempShowTitles.size(); showInList++)
		{
			nnScore = nn.getNNScore(Double.valueOf(showList[showInList][0]), Double.valueOf(showList[showInList][1]), Double.valueOf(showList[showInList][2]), Double.valueOf(showList[showInList][3]), Double.valueOf(showList[showInList][4]), Double.valueOf(showList[showInList][5]));
			System.out.println(showList[showInList][6] + " NN Score: " + nnScore + "                               Real Score: " + showList[showInList][5] + "                            % off: " + ((nnScore - Double.valueOf(showList[showInList][5]))/Double.valueOf(showList[showInList][5])));
		}
		for(int showInList = 0; showInList < tempShowTitles.size(); showInList++)
		{
			showname = tempShowTitles.get(showInList);
			show = new Show(showname, list, true);
			nnScore = nn.getNNScore(show.getAverageScore(), show.getAverageEpisodeDeviation(), show.getAverageSpeedDeviation(), show.getEpisodeCount(), show.getImpactScore(), show.getRealScore());
			avg += Math.abs((nnScore-show.getRealScore())/show.getRealScore());
			pointavg += Math.abs(nnScore - show.getRealScore());
			System.out.println(Math.abs((nnScore-show.getRealScore())/show.getRealScore()));
		}
		System.out.println("avg = " + (avg/tempShowTitles.size()));
		System.out.println("average points off: " + (pointavg/tempShowTitles.size()));
		nn.returnData();

	}
	private static void compileShowList() throws IOException
	{
		Show show;
		boolean skip = false;
		tempShowTitles = (ArrayList<String>) showTitles.clone();
		showList = new String[showTitles.size()][7];
		for(int x = 0; x < tempShowTitles.size(); x++)
		{
			if(skip== true)
			{
				x--;
			}
			show = new Show(tempShowTitles.get(x), list, true);
			for(int y = 0; y < 7; y++)
			{
				switch(y)
				{
					case 0:
						showList[x][y] = Double.toString(show.getAverageScore());
						break;
					case 1:
						showList[x][y] = Double.toString(show.getAverageEpisodeDeviation());
						break;
					case 2:
						showList[x][y] = Double.toString(show.getAverageSpeedDeviation());
						break;
					case 3:
						showList[x][y] = Double.toString(show.getEpisodeCount());
						break;
					case 4:
						showList[x][y] = Double.toString(show.getImpactScore());
						break;
					case 5:
						try
						{
							showList[x][y] = Double.toString(show.getRealScore());
						}
						catch(NullPointerException e)
						{
							tempShowTitles.remove(x);
							x--;
							y = 7;
						}
						break;
					case 6:
						showList[x][y] = tempShowTitles.get(x);
						break;
				}
				System.out.println(tempShowTitles.get(x));
			}
		}
	}
	private static void openAll (String List) throws IOException
	{
		compileTitleList();
		String showname;
		Show show;
		for(int x = 0; x < showTitles.size(); x++)
		{
			showname = showTitles.get(x);
			show = new Show(showname, List, true);
		}
	}
	private static void compileTitleList() throws IOException {
		// TODO Auto-generated method stub
		currentPath = Path + "//" + list;
		showNames = new File(currentPath + "//" + "shownames" + ".txt");
		try {
			filereading = new Scanner(showNames);
		}
		catch (FileNotFoundException e){
			new File(currentPath + "//").mkdirs();
			showNames.createNewFile();
			filereading = new Scanner(showNames);
		}
		int x = 0;
		boolean loop = true;
		showTitles.clear();
		if(list.equals(""))
		{
			String tempPath =Path + "//" + "Watching";
			showNames = new File(Path + "//" + tempPath + "//" + "shownames" + ".txt");
			filereading = new Scanner(showNames);
			String next;
			while(filereading.hasNextLine())
			{
				next = filereading.nextLine();
				showTitles.add(next);
			}
			tempPath =Path + "//" + "Completed";
			showNames = new File(tempPath + "//" + "shownames" + ".txt");
			filereading = new Scanner(showNames);
			while(filereading.hasNextLine())
			{
				next = filereading.nextLine();
				showTitles.add(next);
			}
			showNames = new File(currentPath + "//" + "shownames" + ".txt");
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
					showTitles.add(filereading.nextLine());
				}
				else if(!lineExists)
				{
					loop = false;
				}
			}
		}
		Collections.sort(showTitles, new Comparator<String>() {
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
			showNames = new File(currentPath + "//" + "shownames" + ".txt");
			input = new PrintWriter(new FileWriter(showNames, false));
			for(int x = 0; x < showTitles.size(); x++)
			{
				input.println(showTitles.get(x));
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
