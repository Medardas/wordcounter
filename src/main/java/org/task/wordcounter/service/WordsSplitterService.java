package org.task.wordcounter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.task.wordcounter.model.SplitWordsResultMap;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordsSplitterService {

    private final WordCounterService wordCounter;

    @Autowired
    public WordsSplitterService(WordCounterService wordCounter) {
        this.wordCounter = wordCounter;
    }

    /**
     * Splits words by comparing each first character ASCII decimal value
     *
     * @param files
     * @return
     */
    public SplitWordsResultMap splitWords(MultipartFile[] files) {
        SplitWordsResultMap resultMap = new SplitWordsResultMap(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
        Map<String, Integer> allWordsMap = wordCounter.asyncWordCount(files);
        for (String word : allWordsMap.keySet()) {
            int firstLetter = word.charAt(0);
            if ((firstLetter >= 65 && firstLetter <= 71) || (firstLetter >= 97 && firstLetter <= 103))
                resultMap.getAgWords().put(word, allWordsMap.get(word));
            if ((firstLetter >= 72 && firstLetter <= 78) || (firstLetter >= 104 && firstLetter <= 110))
                resultMap.getHnWords().put(word, allWordsMap.get(word));
            if ((firstLetter >= 79 && firstLetter <= 85) || (firstLetter >= 111 && firstLetter <= 117))
                resultMap.getOuWords().put(word, allWordsMap.get(word));
            if ((firstLetter >= 86 && firstLetter <= 90) || (firstLetter >= 118 && firstLetter <= 122))
                resultMap.getVzWords().put(word, allWordsMap.get(word));
        }

        return resultMap;
    }
}