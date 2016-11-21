package generator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import algorithm.util.RandomUtils;

public class RandomOrganizationGenerator {
	
	private int minAmountOfBuildings = 1;
	private int maxAmountOfBuildings = 1;
	
	private int minAmountOfFloors = 1;
	private int maxAmountOfFloors = 5;
	
	private int minAmountOfRooms = 1;
	private int maxAmountOfRooms = 3;
	
	private int minAmountOfTables = 1;
	private int maxAmountOfTables = 5;
	
	private int minAmountOfWorkstation = 1;
	private int maxAmountOfWorkstation = 8;
	
	private float workstationOccupiedPercentage = 1;
	private int minAmountOfEmployeesPerProject = 3;
	private int maxAmountOfEmployeesPerProject = 8;
	
	public void createRandomOrganization(String organizationPath, String employeesPath) throws IOException {
		int numberOfWorkstation = generateRandomOrganization(organizationPath);
		generateEmployees(numberOfWorkstation, employeesPath);
	}
	
	private int generateRandomOrganization(String organizationPath) throws IOException {
		File file = new File(organizationPath);
		if (!file.exists())
			file.createNewFile();
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		
		int buildingNumber = 1;
		int floorNumber = 1;
		int roomNumber = 1;
		int tableNumber = 1;
		int workstationNumber = 1;
		
		int amountOfBuildings = RandomUtils.randomBetween(minAmountOfBuildings, maxAmountOfBuildings);
		while(amountOfBuildings > 0) {
			writer.println("Building_" + buildingNumber);
			int amountOfFloors = RandomUtils.randomBetween(minAmountOfFloors, maxAmountOfFloors);
			while(amountOfFloors > 0) {
				writer.println("\tFloor_" + floorNumber);
				int amountOfRooms = RandomUtils.randomBetween(minAmountOfRooms, maxAmountOfRooms);
				while(amountOfRooms > 0) {
					writer.println("\t\tRoom_" + roomNumber);
					int amountOfTables = RandomUtils.randomBetween(minAmountOfTables, maxAmountOfTables);
					while(amountOfTables > 0) {
						writer.println("\t\t\tTable_" + tableNumber);
						int amountOfWorkstation = RandomUtils.randomBetween(minAmountOfWorkstation, maxAmountOfWorkstation);
						while(amountOfWorkstation > 0) {
							writer.println("\t\t\t\tworkstation_" + workstationNumber);
							amountOfWorkstation--;
							workstationNumber++;
						}
						amountOfTables--;
						tableNumber++;
					}
					amountOfRooms--;
					roomNumber++;
				}
				amountOfFloors--;
				floorNumber++;
			}
			amountOfBuildings--;
			buildingNumber++;
		}
		
		writer.close();
		return workstationNumber-1;
	}
	
	private void generateEmployees(int workstationNumber, String employeesPath) throws IOException {
		File file = new File(employeesPath);
		if (!file.exists())
			file.createNewFile();
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		
		int numberOfEmployees = (int) (workstationNumber * workstationOccupiedPercentage);
		int projectNumber = 1;
		int employeedNumber = 1;
		
		while (numberOfEmployees > 0 && numberOfEmployees >= maxAmountOfEmployeesPerProject) {
			int projectSize = RandomUtils.randomBetween(minAmountOfEmployeesPerProject, maxAmountOfEmployeesPerProject);
			int auxProjectSize = projectSize;
			while (auxProjectSize > 0) {
				writer.println("Employeed_" + employeedNumber + " #project" + projectNumber);
				employeedNumber++;
				auxProjectSize--;
			}
			numberOfEmployees -= projectSize;
			projectNumber++;
		}
		if (numberOfEmployees != 0) {
			while (numberOfEmployees > 0) {
				writer.println("Employeed_" + employeedNumber + " #project" + projectNumber);
				employeedNumber++;
				numberOfEmployees--;
			}
		}
		
		writer.close();
	}
	
	public static class Builder {
		
		RandomOrganizationGenerator generator;

		public Builder() {
			generator = new RandomOrganizationGenerator();
		}
		
		public Builder withMinAmountOfBuildings(int min) {
			generator.minAmountOfBuildings = min;
			return this;
		}
		
		public Builder withMaxAmountOfBuildings(int max) {
			generator.maxAmountOfBuildings = max;
			return this;
		}
		
		public Builder withMinAmountOfFloors(int min) {
			generator.minAmountOfFloors = min;
			return this;
		}
		
		public Builder withMaxAmountOfFloors(int max) {
			generator.maxAmountOfFloors = max;
			return this;
		}
		
		public Builder withMinAmountOfRooms(int min) {
			generator.minAmountOfRooms = min;
			return this;
		}
		
		public Builder withMaxAmountOfRooms(int max) {
			generator.maxAmountOfRooms = max;
			return this;
		}
		
		public Builder withMinAmountOfTables(int min) {
			generator.minAmountOfTables = min;
			return this;
		}
		
		public Builder withMaxAmountOfTables(int max) {
			generator.maxAmountOfTables = max;
			return this;
		}
		
		public Builder withMinAmountOfWorkstation(int min) {
			generator.minAmountOfWorkstation = min;
			return this;
		}
		
		public Builder withMaxAmountOfWorkstation(int max) {
			generator.maxAmountOfWorkstation = max;
			return this;
		}
		
		public Builder withWorkstationOccupiedPercentage(float percentage) {
			generator.workstationOccupiedPercentage = percentage;
			return this;
		}
		
		public Builder withMinAmountOfEmployeesPerProject(int min) {
			generator.minAmountOfEmployeesPerProject = min;
			return this;
		}
		
		public Builder withMaxAmountOfEmployeesPerProject(int max) {
			generator.maxAmountOfEmployeesPerProject = max;
			return this;
		}
		
		public RandomOrganizationGenerator build() {
			return generator;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		RandomOrganizationGenerator rog = new RandomOrganizationGenerator();
		rog.createRandomOrganization("./org.txt", "./emp.txt");
	}
	
}
