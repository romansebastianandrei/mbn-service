package mbn.controller;

import mbn.model.FileRequest;
import mbn.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static mbn.util.ApplicationUtils.PATH;

@RestController
@RequestMapping(value = "/file")
public class FileController {


    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/client/{clientId}/registration/{registrationId}")
    public ResponseEntity<List<FileRequest>> saveFiles(@RequestParam(value = "files",required = false) MultipartFile[] files,
                                                               @PathVariable Long clientId, @PathVariable Long registrationId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.saveFileForRegistration(files, clientId, registrationId));
    }

    @GetMapping(value= "/image/{clientId}/{fileId}", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
    private ResponseEntity<byte[]> getFile(@PathVariable Long clientId, @PathVariable UUID fileId, HttpServletResponse response) throws IOException {
        File file = ResourceUtils.getFile(PATH+ clientId + "/"+fileId+".png");
        byte[] array = method(file);

        return ResponseEntity
                .ok()
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(array);
    }

    public static byte[] method(File file) throws IOException {

        FileInputStream fl = new FileInputStream(file);

        byte[] arr = new byte[(int)file.length()];

        fl.read(arr);

        fl.close();

        return arr;
    }
}
