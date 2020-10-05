package personalOne;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.Writer;
import java.util.List;


public class FoodTrackerClient {
	public static int getDay(String d) {
		switch(d) {
			case "MONDAY":
				return 1;
			case "TUESDAY":
				return 2;
			case "WEDNESDAY":
				return 3;
			case "THURSDAY":
				return 4;
			case "FRIDAY":
				return 5;
			case "SATURDAY":
				return 6;
			case "SUNDAY":
				return 7;
		}
		return -1;

	}
	
	public static FoodTracker[] addLog(Scanner input, FoodTracker[] logs) {
		System.out.println("Log Your Meal\n--------------");
		System.out.print("Name: ");
		String name = input.nextLine(); //assign name of meal to string variable.
		LocalTime time = null; //using Java's built in LocalTime object, set time to null;
		boolean done = false; //flag 
		do {
			System.out.print("Time (e.g 8:45): ");
			String timeString = input.nextLine();
			try {
				time = FoodTracker.parseTime(timeString); //Parse string and get hour and min as integers
				done = true;
				
			} catch (Exception e) {
				System.out.println("'" + timeString + "' is not a valid time. Please try again.");
			}
			
		} while (!done);
		System.out.println("Enter food: \nNote: Add a comma between your ingredients.");
		String foods = input.nextLine();
		System.out.println("If any, enter symptoms:\nNote: Add a comma between your symptoms.");
		System.out.println();
		String symptoms = input.nextLine();
		
		FoodTracker ft = new FoodTracker(name, foods, symptoms, time.getHour() , time.getMinute()); //create an instance
		
		FoodTracker[] temp = new FoodTracker[logs.length + 1]; //create temp array to allocate space on logs[] to be able to add the instance of FoodTracker just created
		for (int i=0; i<logs.length; i++) {
			temp[i] = logs[i];//if any, add all objects to temp[]
		}
		logs = temp; //assign temp to logs. Now logs has more space and original elements as before.
		temp = null; //temp[] points no where.
		
		logs[logs.length - 1] = ft; //add instance to logs[]. Subtract length by 1 and use as index to add to very end
		
		return logs; //return logs[]
	}
	public static FoodTracker[] deleteLog(Scanner input, FoodTracker[] logs) {
		System.out.println("Choose a Log to Delete:");
		for (int i = 0; i < logs.length; i++) {
			System.out.println(i+1 + ".)"+logs[i].getName());
			System.out.println(); 
		}
		System.out.println("\nOr type '-1' to go back.");
		int index = -1 + Integer.parseInt(input.nextLine());
		if (index <0 ) {
			return logs;
		}
		FoodTracker[] temp = new FoodTracker[logs.length - 1];//Resize length by -1. Since we are deleting.
		for (int i = 0; i < index; i++) { //copy the elements only up and before the index the user chose to delete.
			temp[i] = logs[i];
		}
		for (int i = index; i < temp.length; i++) {
			temp[i] = logs[i+1]; //copy the elements only after the index the user chose.
		}
		logs = temp;
		temp = null;
		System.out.println("\nDeleted.");
		return logs;
	}
	
	public static void viewLogs(Scanner input, FoodTracker[] logs) {
		for(int i = 0; i < logs.length; i++) {
			System.out.println(logs[i].toString());
			System.out.println();
		}
	}
	
	public static void saveLogs(FoodTracker[] logs) {
		try {
			FileWriter file = new FileWriter("src/personalOne/foodlogs.csv");
			CSVWriter writer = new CSVWriter(file);
			String[] header  = {"Day", "Time", "Meal","Foods","Symptoms"};
			writer.writeNext(header);
			for (int i=0; i<logs.length; i++) {
				String[] row = {logs[i].getDay().toString(),logs[i].getTime(),logs[i].getName(),logs[i].getFoods(),logs[i].getSymptoms()};
				writer.writeNext(row);
//				file.write("\n");
//				file.flush();
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("An Error Occured While Saving Logs.");
			return;
		}
		
		System.out.println("Logs Saved!");
	}
	public static FoodTracker[] readLogs() {
		FoodTracker[] logs = new FoodTracker[0];
		try {
			File file = new File("src/personalOne/foodlogs.csv");
			FileReader readFile = new FileReader("src/personalOne/foodlogs.csv");
			Scanner input = new Scanner(file);
			CSVReader csvReader = new CSVReader(readFile); 
			List<String[]> values = csvReader.readAll();
			int j = 1;
			while (j < values.size()) {
				try {
					int day = getDay(values.get(j)[0].toString());
					FoodTracker ft = new FoodTracker(DayOfWeek.of(day),values.get(j)[2], values.get(j)[3],values.get(j)[4],FoodTracker.parseTime(values.get(j)[1]).getHour(),FoodTracker.parseTime(values.get(j)[1]).getMinute());
					FoodTracker[] temp = new FoodTracker[logs.length + 1];
					for (int i=0; i<logs.length; i++) {
						temp[i] = logs[i];
					}
					logs = temp;
					temp = null;
					
					logs[logs.length - 1] = ft;
					j++;
				} catch (Exception e) {
					System.out.println("Error Reading Line From File.");
				}
//					
			}
			csvReader.close();
			input.close();
		} catch (Exception e) {
			System.out.println("Error Reading Saved Logs.");
		}
		
		return logs;
	}
	
	public static FoodTracker[] menu(FoodTracker[] logs) {
		Scanner input = new Scanner(System.in);
		boolean done = false;
		do {
			System.out.println("1.) Log A Meal");
			System.out.println("2.) Delete A Meal");
			System.out.println("3.) View Meals");
			System.out.println("4.) View Potential Food Intolerances");
			System.out.println("5.) Exit");
			System.out.println("\nEnter Choice: ");
			String choice = input.nextLine();
			switch(choice) {
				case "1":
					logs = addLog(input, logs); 
					break;
				case "2":
					logs = deleteLog(input, logs);
					break;
				case "3":
					viewLogs(input, logs);
					break;
				case "5":
					done = true;
					System.out.println("Exiting.");
					break;
			}
			
		}while(!done);
		input.close();
		return logs;
	}

	public static void main(String[] args) {
		FoodTracker[] logs = readLogs();
		logs = menu(logs);
		saveLogs(logs);
		}

}
