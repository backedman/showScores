
package show;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import neuralNetwork.neuralNet;
import showScores.Main;

import java.io.FileWriter;


public class Show {
	private String name;
	private String status;
	private File anime;
	private int episodecount;
	private PrintWriter input;
	private ArrayList<String> information = new ArrayList<String>();
	private Scanner reader;
	boolean read;
	double basespeed;
	private double score;
	private double impactScore;
	private double avgScore;
	private int currentEpisodeCount;
	private Double realScore;
	private String pathcurrent;
	private ArrayList<Double> episodeRatings = new ArrayList<Double>();
	private ArrayList<Double> episodeSpeed = new ArrayList<Double>();
	private boolean realScoreExists = false;
	public Show(String name, String status) throws IOException
	{
		
		pathcurrent = Main.Path + "//" + status;
		this.name = name;
		this.status = status;
		basespeed = 1.15;
		anime = new File(pathcurrent + "//" + Main.makeSafe(name) + ".txt");

		try {
			if(anime.createNewFile())
			{
				try {
					input = new PrintWriter(new FileWriter(anime, true));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				input.println("Name: " + name);
				input.println("Status: " + status);
				input.println("Episodes: " + 0);
				input.println("Base Speed: " + basespeed);
				input.println("Score: " + 0);
				score = 0;
				input.println("    Impact Rating: ");
				impactScore = -1;
				input.println("Average Score: " + 0);
				avgScore = 0;
				input.println("-------------------------");
				input.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			anime = new File(pathcurrent + "//" + Main.makeSafe(name) + ".txt");
			reader = new Scanner(anime);
		} catch (FileNotFoundException e1) {
			anime = new File(pathcurrent + "//" + Main.makeSafe(name) + ".txt");
		}

		read = true;
		String content;
		while(read)
		{
			content = reader.nextLine();
			information.add(content);
			if(content.equals("-------------------------"))
			{
				read = false;
			}
		}
		read = true;
		int episodeScore;
		int speed;
		int counter = 0;
		String[] epInfo;
		while(reader.hasNextLine())
		{
			counter++;
			content = reader.nextLine();
			content = content.replace("Episode " + counter + ":", "");
			content = content.replace("Speed: ", "");
			epInfo = content.split("                   ");
			if(!epInfo[0].isEmpty())
			{
				episodeRatings.add(Double.valueOf(epInfo[0]));
				episodeSpeed.add(Double.valueOf(epInfo[1]));
			}
		}

		for(int x = 0; x < information.size(); x++)
		{
			content = information.get(x);
			if(x == 0)
			{
				content = Main.makeCompareable(content);
				content = content.replaceFirst("name:", "");
			}
			else if(x == 1)
			{
				content = Main.makeCompareable(content);
				content = content.replaceFirst("status:", "");

			}
			else if(x == 2)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("episodes:", "");

			}
			else if(x == 3)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("basespeed:", "");

			}
			else if(x == 4)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("score:","");

			}
			else if(x == 5)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("impactrating:", "");

			}
			else if(x == 6)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("averagescore:", "");

			}
			else if (x == 7 && !content.equals("-------------------------"))
			{
				content = Main.makeCompareable(content, true);
				realScoreExists = true;
				content = content.replaceFirst("realscore:", "");
			}
			information.set(x, content);
		}
		episodecount = Integer.valueOf(information.get(2));
		currentEpisodeCount = episodecount;
		basespeed = Double.valueOf(information.get(3));
		score = Double.valueOf(information.get(4));
		try{
			impactScore = Double.valueOf(information.get(5));
		}
		catch(NumberFormatException e){
			impactScore = -1;
		}
		avgScore = Double.valueOf(information.get(6));
		if(realScoreExists)
		{
			realScore = Double.valueOf(information.get(7));
		}
		reader = new Scanner(System.in);
			boolean accessAnime = true;
			while(accessAnime)
			{
				System.out.println("What do you want to do with the anime?");
				System.out.println("        1. Record watching stats                  ");
				System.out.println("        2. Get watching stats and average ratings ");
				System.out.println("        3. Other settings                         ");
				System.out.println("        4. Exit Program                           ");
				int choice = reader.nextInt();
				if(choice == 1)
				{
					rateEps();
				}
				else if(choice == 2)
				{
					calculateScaledScore();
					calculateAverageScore();
					System.out.println("Scaled Score: "  + score);
					System.out.println("Average Score: " + avgScore);
					System.out.println("NN Score: " + getNNScore());
				}
				else if(choice == 3)
				{
					System.out.println("        1. Set Impact Score                   ");
					System.out.println("        2. Change current episode             ");
					System.out.println("        3. Change base speed");
					System.out.println("        4. Set anime as Completed");
					choice = reader.nextInt();
					if(choice == 1)
					{
						impactScore = reader.nextDouble();
					}
					else if(choice == 2)
					{
						currentEpisodeCount = reader.nextInt();
					}
					else if(choice == 3)
					{
						basespeed = reader.nextDouble();
					}
					else if(choice == 4)
					{
						this.status = "Completed";
					}
				}
				else if(choice == 4)
				{
					updateScores();
					writeToFile();
					accessAnime = false;
				} 	

			}
			System.gc();
		}

		


	private void rateEps()
	{

		reader = new Scanner(System.in);
		read = true;
		double perEpisodeScore;
		double speed;
		double difference;
		String value;
		while(read)
		{
			System.out.println("how do you rate episode " + (currentEpisodeCount + 1) + "?");
			perEpisodeScore = (double)reader.nextInt();
			if(perEpisodeScore == -1)
			{
				read = false;
			}
			else
			{	
				currentEpisodeCount++;
				if(currentEpisodeCount > episodecount)
				{
					episodecount++;
					episodeRatings.add(perEpisodeScore);
					System.out.println("Speed?");
					reader.nextLine();
					value = reader.nextLine();
					if(value.isEmpty())
					{
						speed = basespeed;
					}
					else
					{
						speed = Double.valueOf(value);
					}
					episodeSpeed.add(speed);
					updateEpisodeList(episodecount, perEpisodeScore, speed, false);
				}
				else
				{
					episodeRatings.set(currentEpisodeCount - 1, perEpisodeScore);
					System.out.println("Speed?");
					speed = reader.nextDouble();
					episodeSpeed.set(currentEpisodeCount - 1, speed);
					updateEpisodeList(currentEpisodeCount, perEpisodeScore, speed, true);
				}

			}
		}
	}

	private void updateEpisodeList(int episodecount, double perEpisodeScore, double speed, boolean replace)
	{
		if(!replace)
		{
			try {
				input = new PrintWriter(new FileWriter(anime, true));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			input.println("Episode " + (episodecount) + ": " + perEpisodeScore + "                   Speed: " + speed);
			input.close();
		}
		else if(replace)
		{
			anime = new File(pathcurrent + "//" + name + ".txt");
			ArrayList<String> content = new ArrayList<String>();
			try {
				Scanner reader = new Scanner(anime);
				boolean read = true;
				while(read)
				{
					content.add(reader.nextLine());
					if(!reader.hasNextLine())
					{
						read = false;
					}
				}
				for(int x = 0; x < content.size(); x++)
				{
					if(content.get(x).contains("Episode " + episodecount + ":"))
					{
						content.set(x, "Episode " + (episodecount) + ": " + perEpisodeScore + "                   Speed: " + speed);
					}
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PrintWriter rewrite = null;
			try {
				rewrite = new PrintWriter(anime);
				for(int x = 0; x < content.size(); x++)
				{
					rewrite.println(content.get(x));
				}
				rewrite.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				anime = new File(pathcurrent + "//" + name + ".txt");
			}

		}
	}

	private void calculateScaledScore()
	{
		double total = 0;
		double perEpisodeScore;
		double speed;
		double difference;
		double episodecount = episodeRatings.size();
		for(int x = 0; x < episodecount; x++)
		{
			perEpisodeScore = episodeRatings.get(x);
			speed = episodeSpeed.get(x);
			difference = speed - basespeed;

			//difference = (int)(difference * 4)/(double)4;
			if(difference >= 0 && difference <= 0.25)
			{
				total += perEpisodeScore - 2 * difference;
			}
			else if(difference > 0.25 && difference <= 1)
			{
				total += perEpisodeScore - (Math.log10(difference)/Math.log10(4) + 2);
			}
			else if(difference > 1 && difference <= 2)
			{
				total += perEpisodeScore - (difference + 1);
			}
			else if(difference > 2)
			{
				total += perEpisodeScore - (difference/2 + 2);
			}
			else if(difference < 0 && difference >= -0.25)
			{
				total += (perEpisodeScore - 2 * difference);
			}
			else if(difference < -0.25 && difference >= -1)
			{
				total += perEpisodeScore + (Math.log10(Math.abs(difference))/Math.log10(4) + 2);
			}
		}
		score =  total/episodecount;
		if(impactScore != -1)
		{
			score += (impactScore - 5)/5 * 12/(episodecount);
		}
		score = Main.round(score);
	}

	private void calculateAverageScore()
	{
		double total = 0;
		for(int x = 0; x < episodecount; x++)
		{
			total += episodeRatings.get(x);
		}
		avgScore = total/episodeRatings.size();
		avgScore = Main.round(avgScore);
	}
	private void updateScores()
	{
		calculateScaledScore();
		calculateAverageScore();
	}

	public double getScaledScore()
	{
		score = Main.round(score);
		return score;
	}

	public double getAverageScore()
	{
		avgScore = Main.round(avgScore);
		return avgScore;
	}

	public String getStatus()
	{
		return status;
	}
	
	public double getRealScore()
	{
		return realScore;
	}
	
	public double getBaseSpeed()
	{
		return basespeed;
	}
	
	public double getAverageSpeed()
	{
		double averageSpeed = 0;
		for(int x = 0; x < episodeSpeed.size(); x++)
		{
			averageSpeed += episodeSpeed.get(x);
		}
		return averageSpeed/episodeSpeed.size();
	}
	
	public double getAverageSpeedDeviation()
	{
		double speedDeviation = 0;
		for(int x =0; x < episodeSpeed.size(); x++)
		{
			speedDeviation += episodeSpeed.get(x) - basespeed;
		}
		return speedDeviation/episodeSpeed.size();
	}
	
	public double getAverageEpisodeDeviation()
	{
		double epDeviation = 0;
		calculateAverageScore();
		double max = 0;
		double min = 10;
		for(int x = 0; x < episodeRatings.size(); x++)
		{
			if(episodeRatings.get(x) > max)
			{
				max = episodeRatings.get(x);
			}
			if(episodeRatings.get(x) < min)
			{
				min = episodeRatings.get(x);
			}
		}
		return (max - min);
	}
	
	public double getEpisodeCount()
	{
		return episodeRatings.size();
	}
	
	public double getImpactScore()
	{
		return impactScore;
	}
	
	public double getNNScore()
	{
		neuralNet nn = new neuralNet();
		return nn.getNNScore(avgScore, getAverageEpisodeDeviation(), getAverageSpeedDeviation(), getEpisodeCount(), impactScore);
	}

	private void writeToFile()

	{
		String contentLine;
		anime = new File(pathcurrent + "//" + Main.makeSafe(name) + ".txt");
		ArrayList<String> content = new ArrayList<String>();
		try {
			Scanner reader = new Scanner(anime);
			boolean read = true;
			int adder = 0;
			while(reader.hasNextLine())
			{
				contentLine = reader.nextLine();
				content.add(contentLine);
			}
			contentLine = "";
			reader.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean loop = true;
		int iterate = 0;
		while(loop)
		{
			contentLine = content.get(iterate);
			if(contentLine.contains("Name: "))
			{
				content.set(iterate, "Name: " + name);
			}
			else if(contentLine.contains("Status: "))
			{
				content.set(iterate, "Status: " + status);
			}
			else if(contentLine.contains("Episodes: "))
			{
				content.set(iterate, "Episodes: " + episodecount);
			}
			else if(contentLine.contains("Base Speed:"))
			{
				content.set(iterate, "Base Speed: " + basespeed);

			}
			else if(contentLine.contains("Score: "))
			{
				if(contentLine.contains("Average Score: "))
				{
					content.set(iterate, "Average Score: " + avgScore);
				}
				else if(contentLine.contains("Real Score: "))
				{
					content.set(iterate, "Real Score: " + realScore);
				}
				else
				{
					content.set(iterate, "Score: " + score);
				}
			}
			else if(contentLine.contains("Impact Rating: "))
			{
				content.set(iterate, "    Impact Rating: " + impactScore);
			}
			

			if(contentLine.equals("-------------------------"))
			{
				loop = false;
			}
			iterate++;
		}
		PrintWriter rewrite = null;
		try {
			rewrite = new PrintWriter(anime);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			anime = new File(pathcurrent + "//" + Main.makeSafe(name) + ".txt");
		}
		for(int x = 0; x < content.size(); x++)
		{
			rewrite.println(content.get(x));
		}
		rewrite.close();
	}
	
	
	
	
	
	
	public Show(String name, String status, boolean OpenClose) throws IOException
	{
		pathcurrent = Main.Path + "//" + status;
		this.name = name;
		this.status = status;
		basespeed = 1.15;
		anime = new File(pathcurrent + "//" + Main.makeSafe(name) + ".txt");

		try {
			if(anime.createNewFile())
			{
				try {
					input = new PrintWriter(new FileWriter(anime, true));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				input.println("Name: " + name);
				input.println("Status: " + status);
				input.println("Episodes: " + 0);
				input.println("Base Speed: " + basespeed);
				input.println("Score: " + 0);
				score = 0;
				input.println("    Impact Rating: ");
				impactScore = -1;
				input.println("Average Score: " + 0);
				avgScore = 0;
				input.println("-------------------------");
				input.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			anime = new File(pathcurrent + "//" + Main.makeSafe(name) + ".txt");
			reader = new Scanner(anime);
		} catch (FileNotFoundException e1) {
			anime = new File(pathcurrent + "//" + Main.makeSafe(name) + ".txt");
		}

		read = true;
		String content;
		while(read)
		{
			try {
			content = reader.nextLine();
			information.add(content);
			if(content.equals("-------------------------"))
			{
				read = false;
			}
			}
			catch (NoSuchElementException e)
			{
				System.out.println(name);
			}
			
		}
		read = true;
		int episodeScore;
		int speed;
		int counter = 0;
		String[] epInfo;
		while(reader.hasNextLine())
		{
			counter++;
			content = reader.nextLine();
			content = content.replace("Episode " + counter + ":", "");
			content = content.replace("Speed: ", "");
			epInfo = content.split("                   ");
			if(!epInfo[0].isEmpty())
			{
				episodeRatings.add(Double.valueOf(epInfo[0]));
				episodeSpeed.add(Double.valueOf(epInfo[1]));
			}
		}

		for(int x = 0; x < information.size(); x++)
		{
			content = information.get(x);
			if(x == 0)
			{
				content = Main.makeCompareable(content);
				content = content.replaceFirst("name:", "");
			}
			else if(x == 1)
			{
				content = Main.makeCompareable(content);
				content = content.replaceFirst("status:", "");

			}
			else if(x == 2)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("episodes:", "");

			}
			else if(x == 3)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("basespeed:", "");

			}
			else if(x == 4)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("score:","");

			}
			else if(x == 5)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("impactrating:", "");

			}
			else if(x == 6)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("averagescore:", "");

			}
			else if(x == 7)
			{
				content = Main.makeCompareable(content, true);
				content = content.replaceFirst("realscore:", "");
			}
			information.set(x, content);
		}
		episodecount = Integer.valueOf(information.get(2));
		currentEpisodeCount = episodecount;
		basespeed = Double.valueOf(information.get(3));
		score = Double.valueOf(information.get(4));
		try{
			impactScore = Double.valueOf(information.get(5));
		}
		catch(NumberFormatException e){
			impactScore = -1;
		}
		avgScore = Double.valueOf(information.get(6));
		realScore = Double.valueOf(information.get(7));
		reader = new Scanner(System.in);
		if(OpenClose)
		{
			writeToFile();
		}
	}

}

