package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import return_a.tcat.dto.file.ImageDto;
import return_a.tcat.service.FileUploadService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/images")
    public ResponseEntity<ImageDto> upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        String fileUrl = fileUploadService.upload(multipartFile, "static");
        return ResponseEntity.status(HttpStatus.OK).body(new ImageDto(fileUrl));
    }
}
