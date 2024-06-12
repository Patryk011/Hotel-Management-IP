package org.example.hotelmanagementip.services;

import jakarta.mail.MessagingException;
import org.example.hotelmanagementip.dto.EmailDTO;
import org.example.hotelmanagementip.email.EmailReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final EmailReceiver emailReceiver;




    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, EmailReceiver emailReceiver) {
        this.mailSender = mailSender;
        this.emailReceiver = emailReceiver;
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("hotelapp12@gmail.com");

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        this.mailSender.send(simpleMailMessage);



    }

    @Override
    public List<EmailDTO> getEmails() {
        try {
            return emailReceiver.receiveEmails();
        } catch (MessagingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteEmail(int index){
        try {
            emailReceiver.deleteEmail(index);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
