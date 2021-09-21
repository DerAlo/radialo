package de.riedhammer.radialo.cw.rx.sub;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class MorseDictionary
{
    Map<String, String> dictionary = new HashMap<String, String>();

    public MorseDictionary()
    {

       this.dictionary.put(".-",       "A");
       this.dictionary.put("-...",     "B");
       this.dictionary.put("-.-.",     "C");
       this.dictionary.put("-..",      "D");
       this.dictionary.put(".",        "E");
       this.dictionary.put("..-.",     "F");
       this.dictionary.put("--.",      "G");
       this.dictionary.put("....",     "H");
       this.dictionary.put("..",       "I");
       this.dictionary.put(".---",     "J");
       this.dictionary.put("-.-",      "K");
       this.dictionary.put(".-..",     "L");
       this.dictionary.put("--",       "M");
       this.dictionary.put("-.",       "N");
       this.dictionary.put("---",      "O");
       this.dictionary.put(".--.",     "P");
       this.dictionary.put("--.-",     "Q");
       this.dictionary.put(".-.",      "R");
       this.dictionary.put("...",      "S");
       this.dictionary.put("-",        "T");
       this.dictionary.put("..-",      "U");
       this.dictionary.put("...-",     "V");
       this.dictionary.put(".--",      "W");
       this.dictionary.put("-..-",     "X");
       this.dictionary.put("-.--",     "Y");
       this.dictionary.put("--..",     "Z");
       this.dictionary.put("-----",    "0");
       this.dictionary.put(".----",    "1");
       this.dictionary.put("..---",    "2");
       this.dictionary.put("...--",    "3");
       this.dictionary.put("....-",    "4");
       this.dictionary.put(".....",    "5");
       this.dictionary.put("-....",    "6");
       this.dictionary.put("--...",    "7");
       this.dictionary.put("---..",    "8");
       this.dictionary.put("----.",    "9");
       this.dictionary.put(".-.-.-",   ".");
       this.dictionary.put("--..--",   ",");
       this.dictionary.put("---...",   ":");
       this.dictionary.put("..--..",   "?");
       this.dictionary.put("-...-",    "=");
       this.dictionary.put("-....-",   "-");
       this.dictionary.put("-.--.",    "(");
       this.dictionary.put("-.--.-",   ")");
       this.dictionary.put(".-..-.",  "\"");
       this.dictionary.put(".----.",   "'");
       this.dictionary.put("-..-.",    "/");
       this.dictionary.put(".--.-.",   "@");
       this.dictionary.put("-.-.--",   "!");
       this.dictionary.put(" ",         " ");
    }

    public String translate(String cw_char)
    {
        if ( this.dictionary.containsKey((Object) cw_char) ) {
           return this.dictionary.get(cw_char);
        } else {
            return "*";
        }
    }

   public String translateBack(String plain)
   {
      if ( this.dictionary.containsValue( plain) ) {
         return this.dictionary.entrySet()
                 .stream()
                 .filter(entry -> Objects.equals(entry.getValue(), plain))
                 .map(Map.Entry::getKey).findAny().orElseThrow();
      } else {
         return "*";
      }
   }

}
