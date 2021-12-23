// Exercise2.java
package com.exercise.two;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;

public class Exercise2 
{
	private static ObjectInputStream inputTable;
	private static ObjectOutputStream outputTable;
	private static ObjectInputStream inputConfig;
	private static ObjectOutputStream outputConfig;
	
	private static TableData[][] dataTable;
	private static int elementLen = 3;
	private static int dataTableRow = 0;
	private static int dataTableCol = 0;
	
	private static Scanner scanner;
	private static Scanner inputTableDetails;
	
	public static void main(String[] args)
	{
		scanner = new Scanner(System.in);
		init();
		// printTable();
		
		System.out.print("Pick a command to proceed [search, edit, print, reset, addrow, sortrow, exit]: ");
		String command = scanner.nextLine();
		// System.out.println();
		
		while (true) {
			
			switch (command.toLowerCase()) {
				case "search":
					searchTable();
					break;
					
				case "edit":
					editTable();
					break;
				
				case "print":
					printTable();
					break;
					
				case "reset":
					createTable();
					break;
				
				case "addrow":
					addRow();
					break;
					
				case "sortrow":
					sortRow();
					break;
					
				case "exit":
					System.out.println("Exiting the program..");
					System.exit(0);
					
				default:
					System.out.println("Invalid command");
			}
			System.out.print("\nPick a command to proceed [search, edit, print, reset, addrow, sortrow, exit]: ");
			command = scanner.nextLine();
		}
	}
	
	// commands
	
	public static void printTable() 
	{
		System.out.println("--Printing the table\n");
		
		for (int i = 0; i < dataTable.length; i++) {
			for (int j = 0; j < dataTable[i].length; j++) {
				System.out.print("[ "+dataTable[i][j].concatString()+" ]");
			}
			System.out.println("_");
		}
	}
	
	public static void searchTable() 
	{
		System.out.print("Enter search term: ");
		String query = scanner.nextLine();
		System.out.println();
		String found = "false";
		
		for (int i = 0; i < dataTable.length; i++) {
			for (int j = 0; j < dataTable[i].length; j++) {
				String[] searchResult = dataTable[i][j].searchDataTable(query).split(",");
				if (searchResult[0].equals("true")){
					if (searchResult[1].equals("key")) {
						found = "";
						System.out.println("Found " + query + " on (" + i + ", "+j+
										") with " + searchResult[3] + " instances. Found in " + searchResult[1] + ".");
										
						if (searchResult[2].equals("value")) {
							System.out.println("Found " + query + " on (" + i + ", "+j+
										") with " + searchResult[4] + " instances. Found in " + searchResult[2] + ".");
						}
						
						found = "true";
						break;
					}
					
					if (searchResult[2].equals("value")) {
						found = "";
						System.out.println("Found " + query + " on (" + i + ", "+j+
										") with " + searchResult[4] + " instances. Found in " + searchResult[2] + ".");
						found = "true";
						break;
					}
				}
			} // end inner for loop
		} // end outer for loop
		
		if (found.equals("false")) {
			System.out.println("Nothing found.");
		}
	} // end searchTable method
	
	public static void editTable()
	{
		int[] index = getCellToEdit();
		
		while (true) {
			
			if ((dataTableRow-1 >= index[0]) && (dataTableCol-1 >= index[1])) {
				System.out.print("Enter the new key:");
				String keyString = scanner.nextLine();
				dataTable[index[0]][index[1]].setKey(keyString);
				System.out.print("Enter the new value: ");
				String valueString = scanner.nextLine();
				dataTable[index[0]][index[1]].setValue(valueString);
				
				// Save to file
				openWriteFile();
				
				try
				{
					outputTable.writeObject(dataTable);
				}
				catch (IOException ioException)
				{
					System.err.println("Error writing to file. Please try again.");
				}
				
				closeWriteFile();	
				
				break;
			} else {
				System.out.println("Index out of bounds. Try again.\n");
			}
			
			index = getCellToEdit();
			
		} // end while loop
	} // end editTable method
	
	public static void addRow()
	{
		TableData[][] tempTable = new TableData[dataTableRow][dataTableCol];
		generateTable(tempTable);
		tempTable = dataTable;
		
		dataTable = new TableData[++dataTableRow][dataTableCol];
		generateTable(dataTable);
		
		for (int i = 0; i<tempTable.length; i++) {
			for (int j = 0; j<tempTable[i].length; j++) {
				dataTable[i][j].setKey(tempTable[i][j].getKey());
				dataTable[i][j].setValue(tempTable[i][j].getValue());
			}
		}
		
		randomStringPerRow(dataTableRow-1, dataTable);
		System.out.println("Row added..");
		
		// Save to file
		openWriteFile();
		
		try
		{
			outputTable.writeObject(dataTable);
		}
		catch (IOException ioException)
		{
			System.err.println("Error writing to file. Please try again.");
		}
		
		closeWriteFile();	
	} // end addRow method
	
	public static void sortRow()
	{
		int rowToSort = 0;
			
		do {
			rowToSort = getIntInput("Enter which row should be sorted(row starts at 1): ");
			if (rowToSort < 1)
				System.out.println("Invalid input. Try again.\n");				
		} while (rowToSort < 1);
		
		String[] data = new String[dataTable[rowToSort].length];
		
		for (int i = 0; i < dataTable[rowToSort].length; i++) {
			data[i] = dataTable[rowToSort][i].getKey() + ": :"+ dataTable[rowToSort][i].getValue();
		}
		
		Arrays.sort(data);

		for (int i = 0; i < dataTable[rowToSort].length; i++) {
			String[] sortedStr = data[i].split(": :");
			dataTable[rowToSort][i].setKey(sortedStr[0]);
			dataTable[rowToSort][i].setValue(sortedStr[1]);
		}
		
		// Save to file
		openWriteFile();
		
		try
		{
			outputTable.writeObject(dataTable);
		}
		catch (IOException ioException)
		{
			System.err.println("Error writing to file. Please try again.");
		}
		
		closeWriteFile();
	} // end sortRow method
	
	// utils
	public static void init()
	{
		Path path = Paths.get("tabledetails.txt");
		if (Files.exists(path)) {
			try
			{
				inputTableDetails = new Scanner(path);
				String[] dimensions = inputTableDetails.nextLine().split(",");
				dataTableRow = Integer.parseInt(dimensions[0]);
				dataTableCol = Integer.parseInt(dimensions[1]);
				readTable();
			}
			catch (IOException ioException)
			{
				System.err.println("Error opening file. Terminating.");
				System.exit(1);
			}
		} else {
			createTable();
		}
	}
	
	public static void openWriteFile() // write object
	{
		try
		{
			outputTable = new ObjectOutputStream(
				Files.newOutputStream(Paths.get("tabledata.ser")));
		}
		catch (IOException ioException)
		{
			System.err.println("Error opening file. Terminating. line 92.");
			System.exit(1);
		}
	} // end openWriteFile method
	
	public static void openReadFile() // read object
	{
		try
		{
			inputTable = new ObjectInputStream(
				Files.newInputStream(Paths.get("tabledata.ser")));
		}
		catch (IOException ioException)
		{
			System.err.println("Error opening file. Terminating. line 82.");
			System.exit(1);
		}
	} // end openReadFile method
	
	public static void createTable()
	{
		openWriteFile();
		dataTableRow = getIntInput("Enter input N for table row: ");
		dataTableCol = getIntInput("Enter input N for table col: ");
				
		dataTable = new TableData[dataTableRow][dataTableCol];
		
		generateTable(dataTable);
		
		for (int i = 0; i < dataTable.length; i++) {
			randomStringPerRow(i, dataTable);
		}
		
		try
		{
			outputTable.writeObject(dataTable);
			FileWriter outputTableDetails = new FileWriter("tabledetails.txt");
			outputTableDetails.write(dataTableRow+","+dataTableCol);
			outputTableDetails.close();
		}
		catch (IOException ioException)
		{
			System.err.println("Error writing to file. Please try again. line 135.");
		}
		
		closeWriteFile();		
	} // end createTable method
	
	public static void readTable()
	{
		openReadFile();
		dataTable = new TableData[dataTableRow][dataTableCol];
		
		generateTable(dataTable);
		try
		{
			dataTable = (TableData[][]) inputTable.readObject();
		}
		catch (ClassNotFoundException classNotFoundException)
		{
			System.err.println("Invalid object type. Terminating.");
		}
		catch (IOException ioException)
		{
			System.err.println("Error reading from file. Terminating. line 131.");
		}
		closeReadFile();
	}
	
	public static void closeWriteFile()
	{
		try
		{
			if (outputTable != null)
				outputTable.close();
		}
		catch (IOException ioException)
		{
			System.err.println("Error closing file. Terminating. line 144.");
			System.exit(1);
		}
	}
	
	public static void closeReadFile()
	{
		try
		{
			if (inputTable != null)
				inputTable.close();
		}
		catch (IOException ioException)
		{
			System.err.println("Error closing file. Terminating. line 158.");
			System.exit(1);
		}
	}
	
	private static int getIntInput(String q)
	{
		Pattern p = Pattern.compile("\\d+");
		Matcher m;
		String input;
		int num;
		
		do {
			System.out.print(q);
			input = scanner.nextLine();
			m = p.matcher(input);
			
			if (!m.matches()) {
				System.out.println("Invalid input. Try again.\n");
			}
		} while (!m.matches());
		
		num = Integer.parseInt(input);

		return num;
	}

	private static void generateTable(TableData[][] tableData)
	{
		for (int i = 0; i < tableData.length; i++) {
			for (int j = 0; j < tableData[i].length; j++) {
				tableData[i][j] = new TableData();
			}
		}
	} // end generateTable method
	
	private static void randomStringPerRow(int row, TableData[][] tableData) 
	{
		for (int j = 0; j < tableData[row].length; j++) {
			tableData[row][j].setKey(threeStrings());
			tableData[row][j].setValue(threeStrings());
		}
	} // end randomStringPerRow method
	
	private static String threeStrings()
	{		
		int leftLimit = 33;
		// int leftLimit = 97; // 'a'
		// int rightLimit = 122; // 'z'
		int rightLimit = 126;
		String cell = "";
		
		for (int k = 0; k < elementLen; k++) {
			int randomLimitedInt = leftLimit + 
				(int) (Math.random() * (rightLimit - leftLimit + 1));
			
			cell += (char) randomLimitedInt;
		}
		
		return cell;
	} // end threeStrings method
	
	private static int[] getCellToEdit() 
	{
		String s = "\\d+,\\d+";
		String q = "--Enter the index of the cell separated by comma(,): ";
		int idx1 = getIntInput("Enter input N for index 1: ");
		int idx2 = getIntInput("Enter input N for index 2: ");
		int[] indexArr = {idx1, idx2};
		// validatePattern(s, ",", q);
		
		return indexArr;
	} // end getCellToEdit method
}