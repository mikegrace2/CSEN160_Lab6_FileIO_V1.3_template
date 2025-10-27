package com.csen160.fileprocessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileProcessor {
	public static void fileCopy(String inputFileName, String outputFileName) throws IOException {
		// Copy text from an input file to an output file
		Scanner in      = null;
		PrintWriter out = null;
		String line     = null;
		
		int n;
		in  = new Scanner(new File(inputFileName));
		out = new PrintWriter(new FileWriter(outputFileName));
		
		// Line delimiter is a new line
		in.useDelimiter("\n");
		
		while (in.hasNext()) {
			// ToDO: implement me
            throw new UnsupportedOperationException( "Not implemented yet." );
		}
		
		in.close();
		out.close();
	}

	public static void wordCount(String inputFileName, String outputFileName) throws IOException {
		// Write line numbers and the number of words per line in the input file, to the output file
		Scanner in      = null;
		PrintWriter out = null;
		String line     = null;

		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(new FileWriter(outputFileName));

		// Line delimiter is a new line
		in.useDelimiter("\n");
		for (int i=0;in.hasNext();i++) {
            // ToDO: implement me
            throw new UnsupportedOperationException( "Not implemented yet." );
		}
		
		in.close();
		out.close();
	}

	public static void main(String[] args) {
		String inputFile   = "input.txt";
		String outputFile1 = "input_copy.txt";
		String outputFile2 = "input_wc.txt";
		
		try {
			FileProcessor.fileCopy(inputFile, outputFile1);
			FileProcessor.wordCount(inputFile, outputFile2);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}