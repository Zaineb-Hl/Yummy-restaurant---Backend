package croco.restau.yummy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import croco.restau.yummy.services.FileStorageService;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileStorageController {


	@Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/meal-image")
    public ResponseEntity<?> uploadMealImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.store(file, "meals");
            return ResponseEntity.ok(Map.of("fileName", fileName));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("message", "Erreur lors de l'upload"));
        }
    }

    @PostMapping("/chef-image")
    public ResponseEntity<?> uploadChefImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.store(file, "chefs");
            return ResponseEntity.ok(Map.of("fileName", fileName));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("message", "Erreur lors de l'upload"));
        }
    }
}
