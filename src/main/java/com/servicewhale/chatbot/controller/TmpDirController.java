package com.servicewhale.chatbot.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TmpDirController {

    @GetMapping("/tmp")
    public List<String> listTmpDir() {
        File tmpDir = new File("/tmp");
        if (tmpDir.exists() && tmpDir.isDirectory()) {
            return Arrays.stream(tmpDir.listFiles())
                    .map(File::getName)
                    .collect(Collectors.toList());
        } else {
            return List.of("TMPDIR does not exist or is not a directory");
        }
    }

    @GetMapping("/tmp/{filename}")
    public ResponseEntity<byte[]> readTmpFile(@PathVariable String filename) {
        String filePath = "/tmp/" + filename;
        File file = new File(filePath);
        
        if (file.exists() && file.isFile()) {
            try {
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}