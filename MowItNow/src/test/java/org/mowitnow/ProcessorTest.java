/** ------------------------
 * ProcessorTest.java.java
 * ------------------------
 * (C) Copyright 2011, by Rguig Saad.
 *
 **/
package org.mowitnow;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;


/**
 * @author saadrguig
 *
 */
public class ProcessorTest {
	protected static Logger log = Logger.getLogger(ProcessorTest.class);
	@Test
	public void testProcessListInstructions(){
		String instructionsList = "GAGAGAGAA";
		Position positionExpected = new Position(1,3,'N');
		Position positionStart = new Position(1,2,'N');
		Position positionActual = Processor.processListInstructions(instructionsList,positionStart,5,5);
		log.info("Position Actual  : "+positionActual);
		log.info("Position Expected: "+positionExpected);
		assertEquals(positionExpected.toString(),positionActual.toString());
	}

	@Test
	public void testProcessFile(){
		String inputFile = "./data/testIn.txt";
		String outputFile = "./data/testOut.txt";
		new File(inputFile).deleteOnExit();
		new File(outputFile).deleteOnExit();


		//Write  input file
		File file = new File(inputFile);
		boolean exist;
		String data = "5 5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA\n";

		try {
			exist = file.createNewFile();
			if (!exist)
			{
				log.error("File already exists.");
				System.exit(0);
			}
			else
			{
				FileWriter fstream = new FileWriter(inputFile);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(data);
				out.close();
			}
		} catch (IOException e) {
			log.error("Failed creating output file !"+ e.getMessage());
		}

		Processor processor = new Processor(inputFile,outputFile);		
		processor.processFile();

		//Read output of the file
		byte[] buffer = new byte[(int) new File(outputFile).length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(outputFile));
			f.read(buffer);
			if (f != null)
				f.close(); 
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
		}
		String outputDataActual = new  String(buffer);
		String outputDataExpected = "1 3 N\n5 1 E\n";
		log.info(outputDataActual+outputDataExpected);
		assertEquals(outputDataExpected.toString(), outputDataActual.toString());
	}
}

