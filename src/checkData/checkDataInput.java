/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkData;

import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class checkDataInput {
     public static boolean isVietnameseAlphabet(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        // Biểu thức chính quy để kiểm tra xem chuỗi chỉ chứa các ký tự chữ cái tiếng Việt
        String vietnameseAlphabetRegex = "^[\\p{L}\\s]+$";

        return Pattern.matches(vietnameseAlphabetRegex, input);
    }
public String capitalizeFirstLetter(String input) {
    if (input == null || input.isEmpty()) {
        return input;
    }

    String[] words = input.trim().split("\\s+");
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < words.length; i++) {
        String word = words[i];
        if (!word.isEmpty()) {
            char firstLetter = Character.toUpperCase(word.charAt(0));
            String restOfWord = "";
            if (word.length() > 1) {
                restOfWord = word.substring(1).toLowerCase();
            }
            result.append(firstLetter).append(restOfWord);

            if (i < words.length - 1) {
                result.append(" ");
            }
        }
    }

    return result.toString();
}

        
 
 
 public static boolean containsNonNumericCharacters(String input) {
    return input.matches("-?\\d+(\\.\\d+)?");
}
}
