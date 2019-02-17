package org.task.wordcounter.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class WordCounterService {

    @Async
    public CompletableFuture<Map<String, Integer>> getWordCountFromAFile(MultipartFile multipartFile) {
        Map<String, Integer> wordMap = new HashMap<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (word.matches("[a-zA-Z]+")) //Sanity check
                wordMap.put(word, wordMap.get(word) != null ? wordMap.get(word) + 1 : 1);
        }

        return CompletableFuture.completedFuture(wordMap);
    }

    public Map<String, Integer> asyncWordCount(MultipartFile[] files) {
        return Arrays.asList(files).parallelStream()
                .map(file -> {
                    try {
                        return getWordCountFromAFile(file).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .reduce((allWords, currentWords) -> {
                    for (String word : currentWords.keySet())
                        allWords.put(word, allWords.get(word) != null ? allWords.get(word) + currentWords.get(word) : currentWords.get(word));
                    return allWords;
                })
                .get();
    }

}