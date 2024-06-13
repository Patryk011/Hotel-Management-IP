package org.example.hotelmanagementip.services;

import jakarta.mail.MessagingException;
import org.example.hotelmanagementip.dto.EmailDTO;
import org.example.hotelmanagementip.email.EmailReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailReceiver emailReceiver;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String message = "Test Message";

        emailService.sendEmail(to, subject, message);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testGetEmails_Success() throws MessagingException, IOException {
        List<EmailDTO> emails = Arrays.asList(new EmailDTO(), new EmailDTO());

        when(emailReceiver.receiveEmails()).thenReturn(emails);

        List<EmailDTO> result = emailService.getEmails();

        assertEquals(emails, result);
        verify(emailReceiver, times(1)).receiveEmails();
    }

    @Test
    void testGetEmails_MessagingException() throws MessagingException, IOException {
        when(emailReceiver.receiveEmails()).thenThrow(new MessagingException());

        List<EmailDTO> result = emailService.getEmails();

        assertEquals(Collections.emptyList(), result);
        verify(emailReceiver, times(1)).receiveEmails();
    }

    @Test
    void testGetEmails_IOException() throws MessagingException, IOException {
        when(emailReceiver.receiveEmails()).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> emailService.getEmails());
        verify(emailReceiver, times(1)).receiveEmails();
    }

    @Test
    void testDeleteEmail_Success() throws MessagingException {
        int index = 1;

        emailService.deleteEmail(index);

        verify(emailReceiver, times(1)).deleteEmail(index);
    }

    @Test
    void testDeleteEmail_MessagingException() throws MessagingException {
        int index = 1;

        doThrow(new MessagingException()).when(emailReceiver).deleteEmail(index);

        assertThrows(RuntimeException.class, () -> emailService.deleteEmail(index));
        verify(emailReceiver, times(1)).deleteEmail(index);
    }
}
