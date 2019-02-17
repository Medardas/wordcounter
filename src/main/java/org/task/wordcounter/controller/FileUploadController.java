package org.task.wordcounter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.task.wordcounter.exception.StorageFileNotFoundException;
import org.task.wordcounter.model.SplitWordsResultMap;
import org.task.wordcounter.service.FileStorageService;
import org.task.wordcounter.service.ProcessedWordsFileCreatorService;
import org.task.wordcounter.service.WordsSplitterService;

@Controller
public class FileUploadController {

    private final FileStorageService storageService;
    private final WordsSplitterService wordSplitter;
    private final ProcessedWordsFileCreatorService fileCreator;

    @Autowired
    public FileUploadController(FileStorageService storageService, WordsSplitterService wordSplitter, ProcessedWordsFileCreatorService fileCreator) {
        this.storageService = storageService;
        this.wordSplitter = wordSplitter;
        this.fileCreator = fileCreator;
    }

    @GetMapping
    public String listUploadedFiles(Model model) {
        model.addAttribute("files", storageService.generateAllFilesURIs());

        return "index";
    }

    @GetMapping("/files/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Resource file = storageService.loadAsResource(fileName);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile[] files,
                                   RedirectAttributes redirectAttributes) {
        SplitWordsResultMap wordCount = wordSplitter.splitWords(files);
        fileCreator.createSeparatedWordsFiles(wordCount);
        redirectAttributes.addFlashAttribute("wordCount", wordCount);

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

}