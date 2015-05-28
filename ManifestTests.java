package asgn2Tests;

/**
 * @author yaman-n8719471
 * @version 1.0
 */
/* Some valid container codes used in the tests below:
 * INKU2633836
 * KOCU8090115
 * MSCU6639871
 * CSQU3054389
 * QUTU7200318
 * IBMU4882351
 */



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
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;
import static org.junit.Assert.*;

public class ManifestTests {
	private static final String[] CODES = {
		"INKU2633836",
		"KOCU8090115",
		"MSCU6639871",
		"CSQU3054389",
		"QUTU7200318",
		"IBMU4882351"
		};
	private static final int NUM_STACKS = 3;
	private static final int MAX_HEIGHT = 2;
	private static final int MAX_WEIGHT = 120;
	
	private ContainerCode[] cc;
	private CargoManifest manifest;
	private GeneralGoodsContainer[] ggContainers;
	private DangerousGoodsContainer[] dgContainers;
	private RefrigeratedContainer[] rContainers;
	
	@Before
	public void setUp() throws Exception {
		cc = new ContainerCode[CODES.length];
		for (int i = 0; i < cc.length; i++)
			cc[i] = new ContainerCode(CODES[i]);
		
		ggContainers = new GeneralGoodsContainer[MAX_HEIGHT];
		ggContainers[0] = new GeneralGoodsContainer(cc[0], 10);
		ggContainers[1] = new GeneralGoodsContainer(cc[1], 10);
		
		dgContainers = new DangerousGoodsContainer[MAX_HEIGHT];
		dgContainers[0] = new DangerousGoodsContainer(cc[2], 20, 1);
		dgContainers[1] = new DangerousGoodsContainer(cc[3], 20, 2);
		
		rContainers = new RefrigeratedContainer[MAX_HEIGHT];
		rContainers[0] = new RefrigeratedContainer(cc[4], 30, 25);
		rContainers[1] = new RefrigeratedContainer(cc[5], 30, 35);
		
		manifest = new CargoManifest(NUM_STACKS, MAX_HEIGHT, MAX_WEIGHT);
		manifest.loadContainer(ggContainers[0]);			
		manifest.loadContainer(ggContainers[1]);			
		manifest.loadContainer(dgContainers[0]);			
		manifest.loadContainer(rContainers[0]);			
		manifest.loadContainer(dgContainers[1]);			
		manifest.loadContainer(rContainers[1]);
	}

	@After
	public void tearDown() throws Exception {
		cc = null;
		manifest = null;
		ggContainers = null;
		dgContainers = null;
		rContainers = null;
	}
	
	@Test
	public void testConstructor() {
		// valid cases
		try {
			new CargoManifest(1, 2, 30);
			new CargoManifest(4, 3, 50);
		} catch (ManifestException e) {
			fail(e.getMessage());
		}
		
		// invalid cases
		try {
			new CargoManifest(-1, 2, 30);
			fail("Should throw exception");
		} catch (ManifestException e) {			
		}
		
		try {
			new CargoManifest(0, 2, 30);
			fail("Should throw exception");
		} catch (ManifestException e) {			
		}
		
		try {
			new CargoManifest(1, -1, 30);
			fail("Should throw exception");
		} catch (ManifestException e) {			
		}
		
		try {
			new CargoManifest(1, 0, 30);
			fail("Should throw exception");
		} catch (ManifestException e) {			
		}
		
		try {
			new CargoManifest(1, 2, -1);
			fail("Should throw exception");
		} catch (ManifestException e) {			
		}
		
		try {
			new CargoManifest(1, 2, 0);
			fail("Should throw exception");
		} catch (ManifestException e) {			
		}
	}
	
	@Test
	public void testLoadContainer() {
		// valid cases
		try {
			CargoManifest cm = new CargoManifest(5, 4, 150);
			cm.loadContainer(ggContainers[0]);			
			cm.loadContainer(ggContainers[1]);			
			cm.loadContainer(dgContainers[0]);			
			cm.loadContainer(rContainers[0]);			
			cm.loadContainer(dgContainers[1]);			
			cm.loadContainer(rContainers[1]);
		} catch (ManifestException e) {
			fail(e.getMessage());
		}
		
		// invalid cases
		// exceed weight
		try {
			manifest.loadContainer(new GeneralGoodsContainer(new ContainerCode(CODES[0]), 30));
			fail("Should throw ManifestException");
		} catch (ManifestException e) {			
		} catch (InvalidContainerException e) {
			fail(e.getMessage());
		} catch (InvalidCodeException e) {
			fail(e.getMessage());
		}
		
		// already exist
		try {
			manifest.loadContainer(new GeneralGoodsContainer(new ContainerCode(CODES[0]), 5));
			fail("Should throw ManifestException");
		} catch (ManifestException e) {			
		} catch (InvalidContainerException e) {
			fail(e.getMessage());
		} catch (InvalidCodeException e) {
			fail(e.getMessage());
		}
		
		// no space
		try {
			manifest.loadContainer(new GeneralGoodsContainer(new ContainerCode("HBMU4882350"), 5));
			fail("Should throw ManifestException");
		} catch (ManifestException e) {			
		} catch (InvalidContainerException e) {
			fail(e.getMessage());
		} catch (InvalidCodeException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUnloadContainer() {
		// valid cases
		try {
			manifest.unloadContainer(ggContainers[1].getCode());
		} catch (ManifestException e) {
			fail(e.getMessage());
		}
		
		try {
			manifest.unloadContainer(ggContainers[0].getCode());
		} catch (ManifestException e) {
			fail(e.getMessage());
		}
		
		try {
			manifest.unloadContainer(dgContainers[1].getCode());
		} catch (ManifestException e) {
			fail(e.getMessage());
		}
		
		
		// invalid cases
		ContainerCode code = null;
		try {
			code = new ContainerCode("HBMU4882350");
		} catch (InvalidCodeException e) {
			fail(e.getMessage());
		}
		
		// no such container
		try {
			manifest.unloadContainer(code);
			fail("Should throw ManifestException");
		} catch (ManifestException e) {			
		}
		
		// not accessible
		try {
			manifest.unloadContainer(rContainers[0].getCode());
			fail("Should throw ManifestException");
		} catch (ManifestException e) {			
		}
	}
	
	@Test
	public void testWhichStack() {
		assertTrue(manifest.whichStack(ggContainers[0].getCode()).intValue() == 0);
		assertTrue(manifest.whichStack(ggContainers[1].getCode()).intValue() == 0);
		assertTrue(manifest.whichStack(dgContainers[0].getCode()).intValue() == 1);
		assertTrue(manifest.whichStack(dgContainers[1].getCode()).intValue() == 1);
		assertTrue(manifest.whichStack(rContainers[0].getCode()).intValue() == 2);
		assertTrue(manifest.whichStack(rContainers[1].getCode()).intValue() == 2);
		
		// not on board
		ContainerCode code = null;
		try {
			code = new ContainerCode("HBMU4882350");
		} catch (InvalidCodeException e) {
			fail(e.getMessage());
		}
		assertTrue(manifest.whichStack(code) == null);		
	}
	
	@Test
	public void testHowHigh() {
		assertTrue(manifest.howHigh(ggContainers[0].getCode()).intValue() == 0);
		assertTrue(manifest.howHigh(ggContainers[1].getCode()).intValue() == 1);
		assertTrue(manifest.howHigh(dgContainers[0].getCode()).intValue() == 0);
		assertTrue(manifest.howHigh(dgContainers[1].getCode()).intValue() == 1);
		assertTrue(manifest.howHigh(rContainers[0].getCode()).intValue() == 0);
		assertTrue(manifest.howHigh(rContainers[1].getCode()).intValue() == 1);
		
		// not on board
		ContainerCode code = null;
		try {
			code = new ContainerCode("HBMU4882350");
		} catch (InvalidCodeException e) {
			fail(e.getMessage());
		}
		assertTrue(manifest.howHigh(code) == null);		
	}
