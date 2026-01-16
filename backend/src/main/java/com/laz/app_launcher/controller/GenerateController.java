package com.laz.app_launcher.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laz.app_launcher.model.GenerateRequest;
import com.laz.app_launcher.service.FileGenerator;

@RestController
@RequestMapping("/api")
public class GenerateController {

    private final FileGenerator fileGeneratorService;

    public GenerateController(FileGenerator fileGeneratorService) {
        this.fileGeneratorService = fileGeneratorService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generate(@RequestBody GenerateRequest request) {
        byte[] fileContent = fileGeneratorService.generateBat(request.getProfileName(), request.getApps());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + request.getProfileName() + ".bat")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileContent);
    }
}
