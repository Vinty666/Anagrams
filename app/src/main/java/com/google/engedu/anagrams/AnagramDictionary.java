/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    ArrayList<String> wordList = new ArrayList<>();
    HashSet<String> wordSet = new HashSet<>();
    HashMap<String,ArrayList<String>> lettersToWords = new HashMap();
    HashMap<Integer,ArrayList<String>> sizeToWords = new HashMap<>();
    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            String aKey = new String(sortLetters(word));

            if(lettersToWords.containsKey(aKey)){
                lettersToWords.get(aKey).add(word);
            }
            else{
                ArrayList<String> list1 = new ArrayList<>();
                list1.add(word);
                lettersToWords.put(aKey,list1);
            }
            Integer length = word.length();
            if(sizeToWords.containsKey(length)){
                sizeToWords.get(length).add(word);
            }
            else{
                ArrayList<String> list2 = new ArrayList<>();
                list2.add(word);
                sizeToWords.put(length,list2);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word)){
            if(word.contains(base)){
                return false;
            }
            return true;
        }
        return false;
    }


    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String aKey = sortLetters(targetWord);
        result = lettersToWords.get(aKey);
        return result;
    }

    public String sortLetters(String input){
        char[] charArray = input.toCharArray();
        Arrays.sort(charArray);
        String output = new String(charArray);
        return output;
    }

    public boolean isAnagrams(String wordFromList, String target){
        return sortLetters(wordFromList).length() == sortLetters(target).length();
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(int i =0; i < alphabet.length; i++){
            String theWord = word + alphabet[i];
            String aKey = sortLetters(theWord);
            if(lettersToWords.containsKey(aKey)){
                ArrayList<String> temp;
                temp = lettersToWords.get(aKey);
                result.addAll(temp);
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        ArrayList<String> list1 = sizeToWords.get(wordLength);
        String chosen = list1.get(random.nextInt(list1.size()));
        int count = 0;
        while(chosen.length() == wordLength){

        }

        while(lettersToWords.get(sortLetters(chosen)).size() < MIN_NUM_ANAGRAMS){
            chosen = list1.get(random.nextInt(list1.size()));
            count ++;
            if (count == sizeToWords.get(wordLength).size()){
                wordLength++;
                ArrayList<String> list2 = sizeToWords.get(wordLength);
                chosen = list2.get(random.nextInt(list2.size()));
            }
        }
        wordLength ++;
        return chosen;
    }
}
