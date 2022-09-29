package mbn.controller;

import mbn.dtos.ClientDTO;
import mbn.model.Client;
import mbn.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.createClient(client));
    }
    @GetMapping("/get")
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok(clientService.getClients());
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientByCodePatient(@PathVariable Long clientId) {
        return ResponseEntity.ok(clientService.getClientByCodPatient(clientId));
    }

    @PostMapping("/search")
    public List<Client> findEmployeeProjectsExampleMatcher(@RequestBody ClientDTO employeeRequestDTO)
    {
        return clientService.findEmployeeProjectsExampleMatcher(employeeRequestDTO);
    }

    @PatchMapping("/update/{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable Long clientId, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(clientId, client));
    }
}
