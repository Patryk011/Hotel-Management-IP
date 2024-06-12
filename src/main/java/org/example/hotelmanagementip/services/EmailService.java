package org.example.hotelmanagementip.services;

import jakarta.mail.MessagingException;
import org.example.hotelmanagementip.dto.EmailDTO;


import java.util.List;

public interface EmailService {

    List<EmailDTO> getEmails();

    void deleteEmail(int index);

    void sendEmail(String to, String subject, String message);
}