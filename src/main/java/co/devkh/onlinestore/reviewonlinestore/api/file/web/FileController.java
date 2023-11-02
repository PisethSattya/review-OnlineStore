package co.devkh.onlinestore.reviewonlinestore.api.file.web;

import co.devkh.onlinestore.reviewonlinestore.api.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @GetMapping(value = "/download/{name}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> download(@PathVariable String name){
        Resource resource = fileService.downloadByName(name);
        return ResponseEntity.ok()
             //   .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition",
                        "attachment; filename="+resource.getFilename())
                .body(resource);
    }
    @GetMapping
    public List<FileDto> findAll(){
        return fileService.findAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteAll(){
        fileService.deleteAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name){
        fileService.deleteByName(name);
    }
    @GetMapping("/{name}")
    public FileDto findByName(@PathVariable String name){
        return fileService.findByName(name);
    }
    @PostMapping(value = "/single",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileDto uploadSingle(@RequestPart MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return fileService.uploadSingle(file);
    }

    @PostMapping(value = "/multiple",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileDto> uploadMultiple(@RequestPart List<MultipartFile> files){
        return fileService.uploadMultiple(files);
    }
}