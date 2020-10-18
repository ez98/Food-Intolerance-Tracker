package personalOne;


import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class FoodTracker {
	private String name;
	private ArrayList<String> foods = new ArrayList<String>();
	private ArrayList<String> symptoms = new ArrayList<String>();
	private int hour;
	private int minute;
	private DayOfWeek day; 
	public static DecimalFormat intFormat = new DecimalFormat("00");
	
	public FoodTracker() {
		name = "Added Meal";
		foods.add("Undefined");
		symptoms.add("Undefined");
		hour = 0;
		minute = 0;
		day = LocalDate.now().getDayOfWeek();
		
	}
	public FoodTracker(String name, String foods, String symptoms) {
		this.name = name;
		this.day = LocalDate.now().getDayOfWeek();
		setFood(foods);
		setSymptoms(symptoms);
	}
	public FoodTracker(String name, String foods, String symptoms, int hour, int minute) {
		this.name = name;
		this.day = LocalDate.now().getDayOfWeek();
		setFood(foods);
		setSymptoms(symptoms);
		setHour(hour); 
		setMinute(minute);
	}
	public FoodTracker(DayOfWeek day, String name, String foods, String symptoms, int hour, int minute) {
		this.name = name;
		this.day = day;
		setFood(foods);
		setSymptoms(symptoms);
		setHour(hour); 
		setMinute(minute);
	}
	public void setName(String n) {
		this.name = n;
	}
	public void setFood(String i) {
		if( i.isEmpty() ) {
			this.foods.add("Undefined");
		}
		else if(!(i.isEmpty())) {
			try {
				String[] list = i.split(",");
				for(int j = 0; j <list.length; j++) {
					this.foods.add(list[j]);
				}
			}catch(Exception e) {
				System.out.println("Failed to add ingredients. Try Again.");
			}
		}
	}
	public void setSymptoms(String s) {
		if( s.isEmpty() ) {
			this.symptoms.add("Undefined");
		}
		else if (!(s.isEmpty())){
			try {
				String[] list = s.split(",");
				for(int j = 0; j <list.length; j++) {
					this.symptoms.add(list[j]);
				}
			}catch(Exception e) {
				System.out.println("Failed to add symptoms. Try Again.");
			}
		}
		
	}
	public void setHour(int h) {
		if (h > 0 && h <= 24) {
			this.hour = h;
		}
	}
	public void setMinute(int m) {
		if (m >=0 && m <= 59) {
			this.minute = m;
		}
	}
	public void setDay(DayOfWeek d) {
		this.day = d;
	}
	public String getName() {
		return name;
	}
	public String getFoods() {
		String list = "";
		for (String str : foods) {
			list = list.concat(str + ",");
		}
		return list;
		
	}
	public String getSymptoms() {
		String list = "";
		for (String str : symptoms) {
			list = list.concat(str+ ",");
		}
		return list;
	}
	public int getHour(){
		return hour;
	}
	public int getMinute() {
		return minute;
	}
	public DayOfWeek getDay() {
		return day;
	}
	public String getTime() {
		return hour + ":" + intFormat.format(minute);
	}
	public boolean hasSymptoms() {
		return symptoms.size() > 0 ? true : false;
	}
	public String toString() {
		return "\n" + getDay() + " @ " + getHour() + ":" + getMinute() + 
				"\n=============" + 
				"\nMeal: " + getName() + 
				"\nFoods: " + getFoods() + 
				"\nSymptoms: " + getSymptoms() + 
				""; 
	}
	public static LocalTime parseTime(String t) throws Exception {
		String[] userTime = t.split(":");
		if (userTime.length == 2) { // Hour and Minute
			int hour = Integer.parseInt(userTime[0]);
			int minute = Integer.parseInt(userTime[1]);
			return LocalTime.of(hour, minute);
		} 
		return null;
	}
	public String toCSV() {
		return getDay() + "," + getHour() + ":" + getMinute() + 
				"," + name + "," + getFoods() + ","+ getSymptoms();
	}
}
