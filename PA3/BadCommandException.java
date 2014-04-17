import java.io.IOException;

/**
   This class reports bad input command.
*/
public class BadCommandException extends IOException
{
   public BadCommandException() {}
   public BadCommandException(String message)
   {
      super(message);
   }
}