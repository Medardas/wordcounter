package org.task.wordcounter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class SplitWordsResultMap {
    private Map<String, Integer> agWords;
    private Map<String, Integer> hnWords;
    private Map<String, Integer> ouWords;
    private Map<String, Integer> vzWords;
}
