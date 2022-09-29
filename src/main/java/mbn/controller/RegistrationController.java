package mbn.controller;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import mbn.dtos.ClientDTO;
import mbn.model.Registration;
import mbn.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/{clientId}")
    public ResponseEntity<Registration> createRegistration(@RequestParam(value = "files",required = false) MultipartFile[] files,
                                                           @RequestParam(value = "registration", required = false) String registration,
                                                           @PathVariable Long clientId) throws Exception {
        ObjectMapper mapper = getJsonParserMapper();
        Registration createdRegistration = mapper.readValue(registration, Registration.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.createRegistration(createdRegistration, clientId, files));
    }
    private ObjectMapper getJsonParserMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return mapper;
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<Registration> getRegistration(@PathVariable Long registrationId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.getRegistration(registrationId));
    }

}
