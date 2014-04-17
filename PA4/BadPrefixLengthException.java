import java.io.IOException;

/**
 * This class reports bad sourceFile length.
 */
public class BadPrefixLengthException extends IOException {
	public BadPrefixLengthException() {}

	public BadPrefixLengthException(String message) {
		super(message);
	}
}
