package org.mowitnow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;


public class Processor {

	public int topY ;
	public int rightX;
	public String inputFile;
	public String outputFile;
	protected static Logger log = Logger.getLogger(Processor.class);

	/**
	 * Constructor Of the Processor; topY and rightX
	 * are initilized after parsing the file
	 * @param inputFile
	 * @param outputFile
	 */
	public Processor(String inputFile,String outputFile) {
		super();
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	/**
	 * Method for processing the entry file
	 * it executes the sequences of instructions
	 * and create the output file
	 * 
	 * @return boolean
	 */
	public boolean processFile(){
		Position position = new Position();
		String[] strLineSplit;
		int lineNumber = 0;//Helps parsing the entry file
		String outputData="";//data that will be written inside output file
		try{
			// Open the file &  Get the object of DataInputStream
			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {

				//First Line contains bounderies
				if(lineNumber ==0){
					strLineSplit = strLine.split("\\s");
					setRightX(new Integer(strLineSplit[0]));
					setTopY(new Integer(strLineSplit[1]));
				}
				// Lines containing first positions
				else if(lineNumber%2==1) {
					strLineSplit = strLine.split("\\s");
					position.setX(new Integer(strLineSplit[0]));
					position.setY(new Integer(strLineSplit[1]));
					position.setOrientation(strLineSplit[2].charAt(0));
				}
				// Lines containing the instructions
				else{
					outputData += processListInstructions(strLine,position,rightX,topY).toFormatedString();
				}
				lineNumber++;
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			log.error("Failed parsing input file !\n"+e.getMessage());
		}

		//Write results in output file
		File file = new File(outputFile);
		boolean exist;
		try {
			exist = file.createNewFile();
			if (!exist)
			{
				log.error("File already exists !");
				System.exit(0);
			}
			else
			{
				FileWriter fstream = new FileWriter(outputFile);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(outputData);
				out.close();
			}
			return true;
		} catch (IOException e) {
			log.error("Failed creating output file !");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method executes the list of instructions 
	 * starting from the current Position and
	 * return the last postions of the mower.
	 * @param instructionString
	 * @param currentPosition
	 * @param rightX
	 * @param topY
	 * @return Las Postion
	 */
	public static Position processListInstructions( String instructionString,Position currentPosition, int rightX, int topY){
		char[] instructionList = instructionString.toCharArray();
		for (char instruction : instructionList){
			log.info("Instruction: "+instruction+" on : "+currentPosition); 
			Position newPosition = currentPosition.processAction(instruction, topY, rightX);
			if(newPosition!=null){
				currentPosition = newPosition;
				log.info("Results are =>    : "+currentPosition);
			}
			else
				log.warn("Impossible to process instruction");
		}
		log.info("Last : "+currentPosition+"\n");
		return currentPosition;
	}


	public int getTopY() {
		return topY;
	}


	public void setTopY(int topY) {
		this.topY = topY;
	}


	public int getRightX() {
		return rightX;
	}


	public void setRightX(int rightX) {
		this.rightX = rightX;
	}


	public String getInputFile() {
		return inputFile;
	}


	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}


	public String getOutputFile() {
		return outputFile;
	}


	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

}
