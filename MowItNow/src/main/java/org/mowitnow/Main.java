/** ------------------------
 * Main.java.java
 * ------------------------
 * (C) Copyright 2011, by Rguig Saad.
 *
 **/
package org.mowitnow;

import org.apache.log4j.Logger;

/**
 * Main Class
 * @author saadrguig
 *
 */
public class Main {
	
	protected static Logger log = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {

		System.out.println("\n!--------------------------------------------------------!");
		System.out.println("\n!----------------- Welcome to MowItNow ------------------!");
		System.out.println("\n!--------------------------------------------------------!");
		
		//asking user for input file
		java.util.Scanner entryInputFileName =   new java.util.Scanner(System.in);
		System.out.println("\nEnter location of file you'd like to process !\n");
		String fileInputPath = entryInputFileName.next();	
		System.out.println("File you have chosen : "+fileInputPath);
		
		//asking user for putput file
		java.util.Scanner entryOutputFileName =   new java.util.Scanner(System.in);
		System.out.println("\nEnter location of file you'd like to process !\n");
		String fileOutputPath = entryOutputFileName.next();	
		System.out.println("File you have chosen : "+fileOutputPath);
		
		//Perform the processing
		System.out.println("\nStart Processing ... !\n");
		Processor processor = new Processor(fileInputPath,fileOutputPath);
		boolean results = processor.processFile();
		System.out.println("End Of Processing ... !");
		System.out.println("Processing succeeded :"+results);
	}
	
}
