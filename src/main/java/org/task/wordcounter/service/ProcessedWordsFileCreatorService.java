package org.task.wordcounter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.task.wordcounter.model.SplitWordsResultMap;

@Service
public class ProcessedWordsFileCreatorService {

    private final FileStorageService storageService;

    @Autowired
    public ProcessedWordsFileCreatorService(FileStorageService storageService) {
        this.storageService = storageService;
    }

    public void createSeparatedWordsFiles(SplitWordsResultMap wordCount) {
        storageService.store("A-G_words.txt", wordCount.getAgWords().toString().getBytes());
        storageService.store("H-N_words.txt", wordCount.getHnWords().toString().getBytes());
        storageService.store("O-U_words.txt", wordCount.getOuWords().toString().getBytes());
        storageService.store("V-Z_words.txt", wordCount.getVzWords().toString().getBytes());
    }

}