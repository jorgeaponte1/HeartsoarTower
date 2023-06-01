package com.tlg.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class TextParser {
    private final TreeMap<String, ArrayList<String>> VERBS;
    private final TreeMap<String, ArrayList<String>> NOUNS;

    public TextParser(TreeMap<String, ArrayList<String>> verbs, TreeMap<String, ArrayList<String>> nouns) {
        NOUNS = nouns;
        VERBS = verbs;
    }

    public String[] validCombo(){
        String verb = null;
        String noun = null;
        Scanner scanner = new Scanner(System.in);

        while (verb == null && noun == null) {
            System.out.println("Enter a valid command: ");
            String input = scanner.nextLine();
//        Split input into words.  Strip out punctuation. Convert to lower case.
            input = input.replaceAll("\\W+", " ").toLowerCase().strip();
            String[] words = input.split("\\s+");
//        Check each word in words to see if it is a verb or noun.
            for (String word : words) {
                for (Map.Entry<String, ArrayList<String>> entry : VERBS.entrySet()) {
                    if (entry.getValue().contains(word)) {
                        verb = entry.getKey();
                        break;
                    }
                }
                for (Map.Entry<String, ArrayList<String>> entry : NOUNS.entrySet()) {
                    if (entry.getValue().contains(word)) {
                        noun = entry.getKey();
                        break;
                    }
                }
            }
            // If user entered a single word noun
            if (verb == null && noun != null) {
                System.out.println("You must enter a verb before the noun. Please try again.");
                noun = null;
            }
            // If user entered a single word verb
            else if (verb != null && noun == null && words.length == 1) {
                return new String[]{verb, null};
            }
            // If user entered a non-valid command
            else if (verb == null || noun == null) {
                System.out.println("Command not recognized. Please try again.");
                verb = null;
                noun = null;
            }
        }
        return new String[]{verb, noun};
    }
}