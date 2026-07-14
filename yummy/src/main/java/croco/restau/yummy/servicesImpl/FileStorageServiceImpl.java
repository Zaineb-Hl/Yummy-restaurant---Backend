package croco.restau.yummy.servicesImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import croco.restau.yummy.services.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService{

	// Chemin de base configurable via application.properties (app.upload.dir=/var/yummy/uploads)
    // Par défaut "uploads" (relatif au répertoire de lancement), comme avant.
    @Value("${app.upload.dir:uploads}")
    private String baseDir;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif"
    );

    @Override
    public String store(MultipartFile file, String subDir) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier fourni");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Type de fichier non autorisé : seules les images (jpeg, png, webp, gif) sont acceptées");
        }

        Path uploadPath = Paths.get(baseDir, subDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        String fileName = UUID.randomUUID() + "_" + sanitizeFileName(file.getOriginalFilename());

        // Protection contre la traversée de chemin : le fichier final doit rester
        // à l'intérieur du dossier d'upload prévu.
        Path targetPath = uploadPath.resolve(fileName).normalize();
        if (!targetPath.startsWith(uploadPath)) {
            throw new IllegalArgumentException("Nom de fichier invalide");
        }

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public void delete(String fileName, String subDir) {
        if (fileName == null || fileName.isBlank()) return;

        try {
            Path uploadPath = Paths.get(baseDir, subDir).toAbsolutePath().normalize();
            Path targetPath = uploadPath.resolve(fileName).normalize();
            if (!targetPath.startsWith(uploadPath)) return; // sécurité, ne devrait pas arriver
            Files.deleteIfExists(targetPath);
        } catch (IOException e) {
            // On ne bloque jamais une mise à jour/suppression à cause d'un fichier
            // qui n'a pas pu être effacé (déjà absent, droits, etc.) — juste best-effort.
        }
    }

    private String sanitizeFileName(String originalFilename) {
        String cleaned = StringUtils.cleanPath(originalFilename == null ? "image" : originalFilename);
        // On ne garde que le nom du fichier (jamais de sous-dossier fourni par le client)
        String justTheName = Paths.get(cleaned).getFileName().toString();
        // On restreint aux caractères sûrs pour un nom de fichier web
        return justTheName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
