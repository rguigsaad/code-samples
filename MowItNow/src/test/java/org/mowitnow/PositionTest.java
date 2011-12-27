/** ------------------------
 * PositionTest.java.java
 * ------------------------
 * (C) Copyright 2011, by Rguig Saad.
 *
 **/
package org.mowitnow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * @author saadrguig
 *
 */
public class PositionTest {
	
	public void testProcessAction() {
		Position positionTester = new Position(1,2,'N');
		Position positionExpected = new Position(1,2,'W');
		Position positionActual = positionTester.processAction('G',5,5);
		assertEquals("Results :",positionExpected.toString(),positionActual.toString() );
	}

	@Test
	public void testGetNewOrientation(){
		Position positionTester = new Position(1,2,'S');
		char instruction = 'G';
		char orientationActual = positionTester.getNewOrientation(instruction); 
		char orientationExpected = 'E';
		assertEquals(orientationExpected,orientationActual);
	}
	
	@Test
	public void testCheckCondition(){
		Position positionTester = new Position(-1,2,'S');
		assertEquals(false,Position.checkCondition(positionTester,5, 5));;
	}
}
