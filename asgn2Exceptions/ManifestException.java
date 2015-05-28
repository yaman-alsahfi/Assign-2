package asgn2Exceptions;


/**
 * Exception thrown when an attempt is made to construct an
 * invalid ship's manifest or perform an incorrect operation
 * on a manifest.
 * 
 * @author CAB302-Yaman-n8719471 & Fatimah-n8631000 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ManifestException extends CargoException {

	/**
	 * Constructs a new ManifestException object.
	 * 
	 * @param message an informative message describing the problem encountered
	 */
	public ManifestException(String message) {
		super(message);
	}

}
