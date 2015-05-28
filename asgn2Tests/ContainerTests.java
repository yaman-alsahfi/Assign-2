package asgn2Tests;

/** 
 *  @author fatimah-n8631000 
 *   @version 1.0
 */

/* Some valid container codes used in the tests below:
 * INKU2633836
 * KOCU8090115
 * MSCU6639871
 * CSQU3054389
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.InvalidContainerException;


public class ContainerTests { 
	private ContainerCode cc;
	private FreightContainer ggContainer;
	private DangerousGoodsContainer dgContainer;
	private RefrigeratedContainer rContainer;
	
	@Before
	public void setUp() throws Exception {
		cc = new ContainerCode("MSCU6639871");
		ggContainer = new GeneralGoodsContainer(cc, 10);
		dgContainer = new DangerousGoodsContainer(cc, 20, 1);
		rContainer = new RefrigeratedContainer(cc, 30, 25);
	}

	@After
	public void tearDown() throws Exception {
		cc = null;
		ggContainer = null;
		dgContainer = null;
		rContainer = null;
	}
	
	@Test
	public void testContainerCodeConstructor() {
		// valid codes
		try {
			new ContainerCode("INKU2633836");
			new ContainerCode("KOCU8090115");
			new ContainerCode("MSCU6639871");
			new ContainerCode("CSQU3054389");
		} catch (InvalidCodeException e) {
			fail(e.getMessage());
		}
		
		// invalid codes
		try {
			new ContainerCode(null);
			fail("Should throw exception");
		} catch (InvalidCodeException e) {			
		}
		
		try {
			new ContainerCode("123456");
			fail("Should throw exception");
		} catch (InvalidCodeException e) {			
		}
		
		try {
			new ContainerCode("abcU1234567");
			fail("Should throw exception");
		} catch (InvalidCodeException e) {			
		}
		
		try {
			new ContainerCode("ABCF1234567");
			fail("Should throw exception");
		} catch (InvalidCodeException e) {			
		}
		
		try {
			new ContainerCode("KOCU8090117");
			fail("Should throw exception");
		} catch (InvalidCodeException e) {			
		}
	}
	
	@Test
	public void testContainerCodeToString() {
		assertTrue(cc.toString().equals("MSCU6639871"));
	}
	
	@Test
	public void testContainerCodeEquals() {
		assertFalse(cc.equals(null));
		assertFalse(cc.equals(new String()));
		try {
			assertTrue(cc.equals(new ContainerCode("MSCU6639871")));
		} catch (InvalidCodeException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testContainerConstructor() {
		// valid cases
		// GeneralGoodsContainer
		try {
			new GeneralGoodsContainer(cc, 4);
			new GeneralGoodsContainer(cc, 15);
			new GeneralGoodsContainer(cc, 30);
		} catch (InvalidContainerException e) {
			fail(e.getMessage());
		}
		
		// DangerousGoodsContainer
		try {
			new DangerousGoodsContainer(cc, 4, 1);
			new DangerousGoodsContainer(cc, 15, 5);
			new DangerousGoodsContainer(cc, 30, 9);
		} catch (InvalidContainerException e) {
			fail(e.getMessage());
		}
		
		// RefrigeratedContainer
		try {
			new RefrigeratedContainer(cc, 4, 10);
			new RefrigeratedContainer(cc, 15, 12);
			new RefrigeratedContainer(cc, 30, 20);
		} catch (InvalidContainerException e) {
			fail(e.getMessage());
		}
		
		
		// invalid cases
		// GeneralGoodsContainer
		try {
			new GeneralGoodsContainer(cc, 3);
			fail("Should throw exception");
		} catch (InvalidContainerException e) {			
		}
		
		try {
			new GeneralGoodsContainer(cc, 31);
			fail("Should throw exception");
		} catch (InvalidContainerException e) {			
		}
		
		// DangerousGoodsContainer
		try {
			new DangerousGoodsContainer(cc, 3, 1);
			fail("Should throw exception");
		} catch (InvalidContainerException e) {
		}
		
		try {
			new DangerousGoodsContainer(cc, 31, 1);
			fail("Should throw exception");
		} catch (InvalidContainerException e) {
		}
		
		try {
			new DangerousGoodsContainer(cc, 10, 0);
			fail("Should throw exception");
		} catch (InvalidContainerException e) {
		}
		
		try {
			new DangerousGoodsContainer(cc, 10, 10);
			fail("Should throw exception");
		} catch (InvalidContainerException e) {
		}
				
		// RefrigeratedContainer
		try {
			new RefrigeratedContainer(cc, 3, 10);
			fail("Should throw exception");
		} catch (InvalidContainerException e) {
		}
		
		try {
			new RefrigeratedContainer(cc, 31, 10);
			fail("Should throw exception");
		} catch (InvalidContainerException e) {
		}
	}
	
	@Test
	public void testContainerGetCode() {
		assertTrue(ggContainer.getCode().equals(cc));
		assertTrue(dgContainer.getCode().equals(cc));
		assertTrue(rContainer.getCode().equals(cc));
	}
	
	@Test
	public void testContainerGetGrossWeight() {
		assertTrue(ggContainer.getGrossWeight().equals(new Integer(10)));
		assertTrue(dgContainer.getGrossWeight().equals(new Integer(20)));
		assertTrue(rContainer.getGrossWeight().equals(new Integer(30)));
	}
	
	@Test
	public void testDGContainerGetCategory() {
		assertTrue(dgContainer.getCategory().equals(new Integer(1)));
	}
	
	@Test
	public void testRContainerGetTemperature() {
		assertTrue(rContainer.getTemperature().equals(new Integer(25)));
	}
	
	@Test
	public void testRContainerSetTemperature() {
		assertTrue(rContainer.getTemperature().equals(new Integer(25)));
		rContainer.setTemperature(new Integer(50));
		assertTrue(rContainer.getTemperature().equals(new Integer(50)));
	}
}
