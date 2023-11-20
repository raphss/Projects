package com.ms.email.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.email.dtos.EmailDTO;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import com.ms.email.services.EmailService;

import jakarta.validation.Valid;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    EmailRepository emailRepository;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDTO emailDTO) {

        EmailModel emailModel = new EmailModel();

        BeanUtils.copyProperties(emailDTO, emailModel);
        emailService.sendEmail(emailModel);

        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @GetMapping("/emails-list")
    public ResponseEntity<List<EmailModel>> getAllEmails() {
        
        List<EmailModel> emailsList = emailRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(emailsList);
    }

    @GetMapping("/email/{emailId}")
    public ResponseEntity<Object> getOneEmail(@PathVariable(value = "emailId") Long emailId) {

        Optional<EmailModel> email0 = emailRepository.findById(emailId);
        return ResponseEntity.status(HttpStatus.OK).body(email0.get());
    }
}