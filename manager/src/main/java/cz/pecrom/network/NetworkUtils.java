package cz.pecrom.network;

import java.util.regex.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 10.12.13
 * Time: 14:48
 */
public class NetworkUtils {
  public static String parseIPv4(String input){
    if(input!=null){
      Pattern pattern = Pattern.compile("\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}");
      Matcher matcher = pattern.matcher(input);
      if(matcher.find())
        return matcher.group();
    }
    return null;
  }
}
