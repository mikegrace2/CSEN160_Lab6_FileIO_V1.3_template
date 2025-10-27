package com.csen160.datamanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataManager implements com.csen160.datamanager.helper.DataGetter {
	private Map<String, com.csen160.datamanager.helper.Sales> sales = new HashMap<String, com.csen160.datamanager.helper.Sales>();

	public void readDataFromFile(String fileName) {
		com.csen160.datamanager.helper.Sales saleByQ;
		BufferedReader reader = null;
		
		try {
			File inFile = null; // ToDo: change me
			reader      = null; // ToDo: change me
			String line = null;

			try { // Loop as long as there are input lines.
				while ((line = reader.readLine()) != null) {
					// split each line into tokens
					String[] fields = line.split(":");
					// the String to int conversion
					int year = Integer.parseInt(fields[0].trim());

					// Do the same for quarter and year
					int quarter     = -1; // ToDo: implement me
					int salesAmount = -1; // ToDo: implement me

					// Create an instance of Sales
					saleByQ = null; // ToDo: implement me
					
					// place the sale object in the hashmap, sales
					sales.put(makeKey(saleByQ), saleByQ);
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println("NumberFormatException: ");
			System.exit(1);
		}
	}

	public Map<String, com.csen160.datamanager.helper.Sales> getData() {
		return sales;
	}

	public String makeKey(com.csen160.datamanager.helper.Sales oneSale) {
		// Complete this method as follows
		// Return the oneSale.year concatenated with "-" and oneSale.quarter
        throw new UnsupportedOperationException( "Not implemented yet." );
	}

	public static void main(String[] args) {
		DataManager manager = new DataManager();
		manager.readDataFromFile("sales.txt");
		Map<String, com.csen160.datamanager.helper.Sales> data = manager.getData();
		System.out.println(data.toString());
	}
}