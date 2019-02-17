package org.task.wordcounter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.task.wordcounter.model.SplitWordsResultMap;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WordsSplitterServiceTest {

    @InjectMocks
    WordsSplitterService splitterService;

    @Mock
    private WordCounterService wordCounter;

    @Test
    public void testSplitWords_allLetterWords_correctSplit() {
        when(wordCounter.asyncWordCount(any())).thenReturn(createAllLetterWords());

        SplitWordsResultMap actual = splitterService.splitWords(any());

        assertEquals("{ag=1}", actual.getAgWords().toString());
        assertEquals("{hn=1}", actual.getHnWords().toString());
        assertEquals("{ou=1}", actual.getOuWords().toString());
        assertEquals("{vz=1, zv=2}", actual.getVzWords().toString());
    }

    private Map<String, Integer> createAllLetterWords() {
        Map<String, Integer> allLetterWords = new HashMap<>();
        allLetterWords.put("ag", 1);
        allLetterWords.put("hn", 1);
        allLetterWords.put("ou", 1);
        allLetterWords.put("vz", 1);
        allLetterWords.put("zv", 2);

        return allLetterWords;
    }

}
