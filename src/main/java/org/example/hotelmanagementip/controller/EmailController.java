package org.example.hotelmanagementip.controller;

import org.example.hotelmanagementip.dto.EmailDTO;
import org.example.hotelmanagementip.email.EmailMessage;
import org.example.hotelmanagementip.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {


    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage) {
        this.emailService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getMessage());
        return ResponseEntity.ok(emailMessage);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmailDTO>> getEmails() {
        List<EmailDTO> emails = emailService.getEmails();
        return ResponseEntity.ok(emails);
    }
    @DeleteMapping("/{index}")
    public ResponseEntity<String> deleteEmail(@PathVariable int index) {
        try {
            emailService.deleteEmail(index);
            return ResponseEntity.ok("Email deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid email index");
        }
    }
}