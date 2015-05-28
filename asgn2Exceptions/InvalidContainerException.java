package asgn2Exceptions;


/**
 * Exception thrown when an attempt is made to construct an
 * invalid freight container.
 * 
 * @author CAB302-Yaman-n8719471 & Fatimah-n8631000
 * @version 1.0
 */
@SuppressWarnings("serial") // We're not interested in binary i/o here
public class InvalidContainerException extends CargoException {

	/**
	 * Constructs a new InvalidContainerException object.
	 * 
	 * @param message an informative message describing the problem encountered
	 */
	public InvalidContainerException(String message) {
		super(message);
	}

}
