package skillforge.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import skillforge.api.dto.VideoDTO;
import skillforge.api.services.VideoService;

import java.awt.*;

@RestController
@RequestMapping("/conteudos/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VideoDTO> uploadVideo(
            @RequestParam("titulo") String titulo,
            @RequestParam("duracao") Integer duracao,
            @RequestParam("aulaId") Long aulaId,
            @RequestParam("arquivo")MultipartFile arquivo

        ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(videoService.salvarVideo(titulo,duracao, aulaId, arquivo));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        videoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
