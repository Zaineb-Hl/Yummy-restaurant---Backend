package croco.restau.yummy.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {


    /**
     * Enregistre le fichier dans le sous-dossier donné ("meals" ou "chefs") et
     * retourne le nom de fichier généré (à stocker en base, pas le chemin complet).
     */
    String store(MultipartFile file, String subDir) throws IOException;

    /** Supprime silencieusement un ancien fichier (remplacement ou suppression d'entité). */
    void delete(String fileName, String subDir);
}
