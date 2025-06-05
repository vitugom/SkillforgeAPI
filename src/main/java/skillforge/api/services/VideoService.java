package skillforge.api.services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import skillforge.api.cursos.Aula;
import skillforge.api.cursos.Video;
import skillforge.api.dto.VideoCreateDTO;
import skillforge.api.dto.VideoDTO;
import skillforge.api.repository.AulaRepository;
import skillforge.api.repository.VideoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final AulaRepository aulaRepository;

    private final Path pastaVideos = Paths.get("storage/videos");

    public VideoService(VideoRepository videoRepository, AulaRepository aulaRepository){
        this.videoRepository = videoRepository;
        this.aulaRepository = aulaRepository;

        try {
            Files.createDirectories(pastaVideos);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possivel criar a pasta de videos", e);
        }
    }

    public VideoDTO salvarVideo(String titulo, Integer duracao, Long aulaId, MultipartFile arquivo){
        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(()-> new IllegalArgumentException("Aula não encontrada"));

        String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
        Path caminhoArquivo = pastaVideos.resolve(nomeArquivo);

        try{
            Files.copy(arquivo.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new RuntimeException("Erro ao salvar video", e);
        }

        String url = "/videos/" + nomeArquivo;

        Video video = new Video();
        video.setTitulo(titulo);
        video.setDuracao(duracao);
        video.setUrl(url);
        video.setAula(aula);

        video = videoRepository.save(video);

        return new VideoDTO(video.getId(), video.getTitulo(), video.getUrl(), video.getDuracao());

    }

    public void deletar(Long id){
        if(!videoRepository.existsById(id)){
            throw new NoSuchElementException("Video não encontrado");
        }
        videoRepository.deleteById(id);
    }






}
