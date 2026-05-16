package com.backend.jobland.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.lib.AppErrors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image")
    public ResponseEntity<Object> uploadImage(
            @RequestParam("target") String target,
            @RequestParam(value = "image", required = true) MultipartFile image) throws IOException {

        if (image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AppErrors.FILE_IS_EMPTY);
        }

        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AppErrors.ONLY_IMAGES_ALLOWED);
        }

        if (!target.matches("^[a-zA-Z0-9_-]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AppErrors.INVALID_TARGET_DIRECTORY);
        }

        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String path = saveFile(image, target, extension);

        Map<String, Object> response = Map.of("path", path);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private String saveFile(MultipartFile file, String target, String extension) throws IOException {
        String randomName = UUID.randomUUID().toString();
        String relativePath = "uploads/" + target + "/" + randomName + extension;

        Path uploadPath = Paths.get("").toAbsolutePath().resolve(relativePath).normalize();

        if (!Files.exists(uploadPath.getParent())) {
            Files.createDirectories(uploadPath.getParent());
        }

        file.transferTo(uploadPath.toFile());
        return relativePath;
    }
}
