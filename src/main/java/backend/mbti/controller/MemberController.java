package backend.mbti.controller;


import backend.mbti.service.FileService;
import backend.mbti.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final FileService fileService;

    @PostMapping("/profileimage")
    public ResponseEntity<?> UploadProfileImage(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        fileService.upload(authentication.getName(), multipartFile);
        return ResponseEntity.ok().body("UPLOADED");
    }

    @GetMapping("/{filename}")
    public ResponseEntity<UrlResource> serveProfileImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("C:/Users/simpl/Downloads/").resolve(filename).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = URLConnection.guessContentTypeFromName(filename);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
