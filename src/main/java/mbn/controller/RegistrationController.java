package mbn.controller;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import mbn.dtos.FilterDTO;
import mbn.model.Registration;
import mbn.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/{clientId}")
    public ResponseEntity<Registration> createRegistration(@RequestParam(value = "files",required = false) MultipartFile[] files,
                                                           @RequestParam(value = "registration", required = false) String registration,
                                                           @PathVariable Long clientId) throws Exception {
        logger.info("Request create registration for client IO: "+clientId);
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
        logger.info("Request get registration with IO: "+registrationId);
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.getRegistration(registrationId));
    }

    @PostMapping("/client/{clientId}/search")
    public  ResponseEntity<List<Registration>> findRegistrations(@PathVariable Long clientId, @RequestBody FilterDTO filterDTO) throws ParseException {
        logger.info("Request search registration for value: " + filterDTO.getFilterText());
        return ResponseEntity.ok(registrationService.findRegistrations(filterDTO, clientId));
    }

}
