package century.edu.pa1;


import java.util.*;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class PayCalculator implements Constants{
	
	//Instance Variables
	private String name;
	private int reportId;
	private static int idIncrement;
	private double hourlyWage;
	
	
	//Constructor
	public PayCalculator(String name, double hourlyWage) {
		this.name = name;
		this.reportId = 1000 + idIncrement;
		this.hourlyWage = hourlyWage;
		idIncrement+=10;
	}
	
	
	//Method will increase the wage by a given percentage
	//Will take in a rate that will change the wage
	//the rate must be positive
	//Will return the new hourly wage
	//Method doesn't throw anything
	public void increaseHourlyWage(double rate) {
		if(rate >= 0) {
		double newWage;
		newWage = getHourlyWage() + (getHourlyWage() * (rate/100));
		setHourlyWage(newWage);
		}
	}
	
	//Method caluclates the amount of overtime worked
	//Will take in the amount of hours worked 
	//Hours worked must be greater than full time to return a value greater than 0
	//Will return the amount of overtime worked
	//Method doesn't throw anything
	public double overTimeHoursWorked(double hoursWorked) {
		double overTime;
		if(hoursWorked > FULL_TIME) {
		overTime = hoursWorked - FULL_TIME;
		return overTime;
		}
		return 0;
	}
	
	
	
	//Method calculates the amount of overtime pay
	//will take in amount of overtime hours
	//Overtime hours must be postivie
	//Will return the pay for overtime
	//Method doesn't throw anything
	public double overTimePay(double overTimeHours) {
		if(overTimeHours >= 0) {
		double overTimePay = overTimeHoursWorked(overTimeHours)* getHourlyWage() * OVERTIME_RATE;
		return overTimePay;
		}
		return 0;
	}
	
	//Method calculates the amount of pay for a period
	//Will take in the amount of hours worked
	//Hours must be positive
	//Will return the pay for the period
	//Method doesn't throw anything
	public double grossPay(double hoursWorked) {
		if(hoursWorked >= 0) {
		double pay = (hoursWorked-overTimeHoursWorked(hoursWorked)) * getHourlyWage() + overTimePay(overTimeHoursWorked(hoursWorked));
		return pay;
		}
		return 0;
	}
	
	
	//Method calcualtes the amount of feder deductions
	//Will take in the gross pay
	//Gross pay must be positive
	//Will returnt the amount of federal deductions 
	//Method doesn't throw anything
	public double federalDeductions(double grossPay) {
		if(grossPay >= 0) {
		double federalDeductions = grossPay * FEDERAL_TAX_RATE;
		return federalDeductions;
		}
		return 0;
	}
	
	
	//Method calcualtes the amount of state deductions
	//Will take in the gross pay
	//Gross pay must be positive
	//Will returnt the amount of state deductions 
	//Method doesn't throw anything
	public double stateDeductions(double grossPay) {
		if(grossPay >= 0) {
		double stateDeductions = grossPay * STATE_TAX_RATE;
		return stateDeductions;
		}
		return 0;
	}
	
	//Method calculates the total deductions
	//Will take in federal and state deductions
	//Deductions must be postivie
	//Will return the total amount of deductions
	//Method doesn't throw anything
	public double totalDeductions(double federalDeductions, double stateDeductions) {
		if(federalDeductions >= 0 && stateDeductions >=0) {
		double totalDeductions = federalDeductions + stateDeductions;
		return totalDeductions;
		}
		return 0;
	}
	
	//Method calculates the net pay. Takes into account deductions that rely on the gross pay
	//Will take in hours worked
	//Hours must be positive
	//Will return the net pay
	//Method doesn't throw anything
	public double netPay(double hoursWorked) {
		if(hoursWorked >= 0) {
		double netPay = grossPay(hoursWorked) - ((stateDeductions(grossPay(hoursWorked)) + federalDeductions(grossPay(hoursWorked))));
		return netPay;
		}
		return 0;
	}
	
	//Method calculates the gross for the year based on the amount of hours worked each pay period
	//Will take in an array of hours worked per pay period
	//The integer values of the array must be positive
	//Will return the gross year income
	//Method doesn't throw anything
	public double grossYearIncome(int[] hoursPerPayPeriod) {
		for(int i = 0; i < hoursPerPayPeriod.length; i++) {
			if(hoursPerPayPeriod[i] >= 0) {
				double grossYearIncome = 0;
				for(int j = 0; j < hoursPerPayPeriod.length; j++) {
					double pay = grossPay(hoursPerPayPeriod[j]);
					grossYearIncome+=pay;
				}
					
				return grossYearIncome;
			}
		}
		return 0;
		
		
	}
	
	//Method calculates the net pay for the year based on the amount of hours worked each pay period
	//Will take in an array of hours worked per pay period
	//The integer values of the array must be positive. Checked by Calling grossYearIncome
	//Will return the net year income
	//Method doesn't throw anything
	public double netYearIncome(int[] hoursPerPayPeriod) {
		double grossYearIncome = grossYearIncome(hoursPerPayPeriod);
		double netYearIncome = grossYearIncome - ((stateDeductions(grossYearIncome) + federalDeductions(grossYearIncome)));
		return netYearIncome;
	}

	
	
	
	
	//Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportID) {
		this.reportId = reportID;
	}

	public double getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(double hourlyWage) {
		this.hourlyWage = hourlyWage;
	}
	
	//ToString method
	//Method uses number format to make sure double values aren't unnecessarily long
	public String toString() {
		NumberFormat format = NumberFormat.getInstance();
		format.setRoundingMode(RoundingMode.DOWN);
	    format.setMaximumFractionDigits(2);
        
		System.out.println("Name        |ID   |Hours Worked  |Over Time  |Regular Pay  |Overtime Pay |Federal Deductions"
				+ " |State Deductions  |Total Deductions   |Net Pay");
		netYearIncome(HOURS_WORKED);
		for(int i = 0; i < HOURS_WORKED.length; i++) {
			if(HOURS_WORKED[i] == 0) {
				System.out.println("********** Vacation Time :-) **********");
				i++;
			}
			System.out.println(getName() + "   " + getReportId() + "    " + HOURS_WORKED[i] + "             " 
			+ overTimeHoursWorked(HOURS_WORKED[i]) + "         " + grossPay(HOURS_WORKED[i]) + "        " 
			+overTimePay(HOURS_WORKED[i]) + "         " +  format.format(federalDeductions(grossPay(HOURS_WORKED[i]))) +
			"               " + format.format(stateDeductions(grossPay(HOURS_WORKED[i]))) + "                " 
			+  format.format(totalDeductions(federalDeductions(grossPay(HOURS_WORKED[i])), stateDeductions(grossPay(HOURS_WORKED[i]))))
			+ "                " + format.format(netPay(HOURS_WORKED[i]))); 
			
			
		}
	
		return "";
		
	
	
    }



	public static void main(String[] args) {
		PayCalculator payCalculator = new PayCalculator("John Smith", 41);
		payCalculator.toString();
    }
	

	
	


	

	

	
	

	
	

}
