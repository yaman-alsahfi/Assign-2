
package asgn2Manifests;

import java.util.ArrayList;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.ManifestException;

/**
 * A class for managing a container ship's cargo manifest.  It
 * allows freight containers of various types to be loaded and
 * unloaded, within various constraints.
 * <p>
 * In particular, the ship's captain has set the following rules
 * for loading of new containers:
 * <ol>
 * <li>
 * New containers may be loaded only if doing so does not exceed
 * the ship's weight limit.
 * </li>
 * <li>
 * New containers are to be loaded as close to the bridge as possible.
 * (Stack number zero is nearest the bridge.)
 * </li>
 * <li>
 * A new container may be added to a stack only if doing so will
 * not exceed the maximum allowed stack height.
 * <li>
 * A new container may be loaded only if a container with the same
 * code is not already on board.
 * </li>
 * <li>
 * Stacks of containers must be homogeneous, i.e., each stack must
 * consist of containers of one type (general,
 * refrigerated or dangerous goods) only.
 * </li>
 * </ol>
 * <p>
 * Furthermore, since the containers are moved by an overhead
 * crane, a container can be unloaded only if it is on top of
 * a stack.
 *  
 * @author fatimah-n8631000
 * @version 1.0
 */
public class CargoManifest {

	private ArrayList<ArrayList<FreightContainer>> manifest;
	private Integer maxHeight;
	private Integer maxWeight;
	private Integer currentWeight; // the current total weight of all containers
	
	/**
	 * Constructs a new cargo manifest in preparation for a voyage.
	 * When a cargo manifest is constructed the specific cargo
	 * parameters for the voyage are set, including the number of
	 * stack spaces available on the deck (which depends on the deck configuration
	 * for the voyage), the maximum allowed height of any stack (which depends on
	 * the weather conditions expected for the
	 * voyage), and the total weight of containers allowed onboard (which depends
	 * on the amount of ballast and fuel being carried).
	 * 
	 * @param numStacks the number of stacks that can be accommodated on deck
	 * @param maxHeight the maximum allowable height of any stack
	 * @param maxWeight the maximum weight of containers allowed on board 
	 * (in tonnes)
	 * @throws ManifestException if negative numbers are given for any of the
	 * parameters
	 */
	public CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)
	throws ManifestException {
		if (numStacks <= 0 || maxHeight <= 0 || maxWeight <= 0)
			throw new ManifestException("Numbers must not be negative");
		
		manifest = new ArrayList<ArrayList<FreightContainer>>(numStacks);
		for (int i = 0; i < numStacks; i++)
			manifest.add(new ArrayList<FreightContainer>());
		this.maxHeight = maxHeight;
		this.maxWeight = maxWeight;
		this.currentWeight = 0;
	}

	/**
	 * Loads a freight container onto the ship, provided that it can be
	 * accommodated within the five rules set by the captain.
	 * 
	 * @param newContainer the new freight container to be loaded
	 * @throws ManifestException if adding this container would exceed
	 * the ship's weight limit; if a container with the same code is
	 * already on board; or if no suitable space can be found for this
	 * container
	 */
	public void loadContainer(FreightContainer newContainer) throws ManifestException {
		// check weight
		if (currentWeight + newContainer.getGrossWeight() > maxWeight)
			throw new ManifestException("Adding this container would exceed the ship's weight limit");
		
		// check exist
		if (whichStack(newContainer.getCode()) != null)
			throw new ManifestException("A container with the same code is already on board");
		
		// load container
		for (int i = 0; i < manifest.size(); i++){
			ArrayList<FreightContainer> currentStack = manifest.get(i);
			if (currentStack.isEmpty()){
				currentStack.add(newContainer);
				currentWeight += newContainer.getGrossWeight(); 
				return;
			}
			else if (currentStack.size() < maxHeight){
				FreightContainer topContainer = currentStack.get(currentStack.size()-1);
				if (topContainer.getClass() == newContainer.getClass()){
					currentStack.add(newContainer);
					currentWeight += newContainer.getGrossWeight();
					return;
				}
			}
		}
		
		// no suitable space can be found
		throw new ManifestException("No suitable space can be found for this container");
	}

	/**
	 * Unloads a particular container from the ship, provided that
	 * it is accessible (i.e., on top of a stack).
	 * 
	 * @param containerId the code of the container to be unloaded
	 * @throws ManifestException if the container is not accessible because
	 * it's not on the top of a stack (including the case where it's not on board
	 * the ship at all)
	 */
	public void unloadContainer(ContainerCode containerId) throws ManifestException {
		Integer stackNo = whichStack(containerId);
		if (stackNo == null)
			throw new ManifestException("No such container");
		
		// check if the container is on top of a stack
		Integer height = howHigh(containerId);
		if (height < manifest.get(stackNo).size()-1)
			throw new ManifestException("The container is not accessible");
		
		// unload it
		FreightContainer removedContainer = manifest.get(stackNo).remove(height.intValue());
		currentWeight -= removedContainer.getGrossWeight();
	}

	
	/**
	 * Returns which stack holds a particular container, if any.  The
	 * container of interest is identified by its unique
	 * code.  Constant <code>null</code> is returned if the container is
	 * not on board.
	 * 
	 * @param queryContainer the container code for the container of interest
	 * @return the number of the stack with the container in it, or <code>null</code>
	 * if the container is not on board
	 */
	public Integer whichStack(ContainerCode queryContainer) {
		for (int i = 0; i < manifest.size(); i++){
			ArrayList<FreightContainer> currentStack = manifest.get(i);
			for (int j = 0; j < currentStack.size(); j++)
				if (currentStack.get(j).getCode().equals(queryContainer)) // found
					return i;
		}
		return null; // not found
	}

	
	/**
	 * Returns how high in its stack a particular container is.  The container of
	 * interest is identified by its unique code.  Height is measured in the
	 * number of containers, counting from zero.  Thus the container at the bottom
	 * of a stack is at "height" 0, the container above it is at height 1, and so on.
	 * Constant <code>null</code> is returned if the container is
	 * not on board.
	 * 
	 * @param queryContainer the container code for the container of interest
	 * @return the container's height in the stack, or <code>null</code>
	 * if the container is not on board
	 */
	public Integer howHigh(ContainerCode queryContainer) {
		Integer stackNo = whichStack(queryContainer);
		if (stackNo == null)
			return null; // not found
		
		Integer height = null;
		ArrayList<FreightContainer> currentStack = manifest.get(stackNo);
		for (int j = 0; j < currentStack.size(); j++){
			if (currentStack.get(j).getCode().equals(queryContainer)){ // found
				height = j;
				break;
			}			
		}
		return height;
	}


	/**
	 * Returns the contents of a particular stack as an array,
	 * starting with the bottommost container at position zero in the array.
	 * 
	 * @param stackNo the number of the stack of interest
	 * @return the stack's freight containers as an array
	 * @throws ManifestException if there is no such stack on the ship
	 */
	public FreightContainer[] toArray(Integer stackNo) throws ManifestException {
		if (stackNo == null || stackNo < 0 || stackNo >= manifest.size())
			throw new ManifestException("There is no such stack on the ship");
		
		ArrayList<FreightContainer> currentStack = manifest.get(stackNo);
		FreightContainer[] contents = new FreightContainer[currentStack.size()];
		contents = currentStack.toArray(contents);
		return contents;
	}

	/**
	 * Returns the number of stacks
	 * @return
	 */
	public int getNumStacks(){
		return manifest.size();
	}
