package mbn.controller;

import mbn.dtos.FilterDTO;
import mbn.exceptions.CnpException;
import mbn.model.Client;
import mbn.model.ClientErrorResponse;
import mbn.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws Exception {
        logger.info("Request create client: " + client);

        return ResponseEntity.ok(clientService.createClient(client));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientByCodePatient(@PathVariable Long clientId) {
        logger.info("Request get client with ID: " + clientId);
        return ResponseEntity.ok(clientService.getClientByCodPatient(clientId));
    }

    @PostMapping("/search")
    public ResponseEntity<List<Client>> findClients(@RequestBody FilterDTO filterDTO) {
        logger.info("Request search client for value: " + filterDTO.getFilterText());
        return ResponseEntity.ok(clientService.findClients(filterDTO));
    }

    @PatchMapping("/update/{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable Long clientId, @RequestBody Client client) {
        logger.info("Request update information for client with ID: " + clientId);
        return ResponseEntity.ok(clientService.updateClient(clientId, client));
    }

    @PostMapping("/update/images/{clientId}")
    public ResponseEntity<Client> updateClientImages(@PathVariable Long clientId, @RequestParam(value = "files", required = false) MultipartFile[] files) {
        logger.info("Request update images for client with ID: " + clientId);
        return ResponseEntity.ok(clientService.updateClientImages(clientId, files));
    }

    @ExceptionHandler(CnpException.class)
    public final ResponseEntity handlePaymentBadRequestException(HttpServletRequest req, Exception ex) {
        logger.error("Request with URL [" + req.getRequestURL() + "] caused an exception to occur: " + ex);
        ClientErrorResponse clientErrorResponse = new ClientErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(clientErrorResponse);
    }
}
