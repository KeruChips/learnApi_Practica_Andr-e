package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
public class ImageService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()){
            throw new IllegalArgumentException("El archivo esta vacío.");
        }

        if(file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El tamaño del archivo no debe ser mayor a 5 megas.");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null){
            throw new IllegalArgumentException("Nombre de archivo invalido");
        }

        String extension = originalFileName.subString(originalFileName.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)){
            throw new IllegalArgumentException("Solo se permite archivos JPG, JPEG, PNG");
        }

        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El archivo debe ser una imagen valida.");
        }
    }
}
