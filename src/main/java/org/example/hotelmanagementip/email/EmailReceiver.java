package org.example.hotelmanagementip.email;

import jakarta.mail.*;

import org.example.hotelmanagementip.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class EmailReceiver {


    private JavaMailSender javaMailSender;


    @Value("hotelapp12@gmail.com")
    private String email;

    @Value("${PASSWORD}")
    private String password;

    public EmailReceiver(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public List<EmailDTO> receiveEmails() throws MessagingException, IOException {
        List<EmailDTO> emails = new ArrayList<>();

        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol", "imap");

        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", email, password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        Message[] messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setId(i);
            emailDTO.setSubject(message.getSubject());
            emailDTO.setFrom(message.getFrom()[0].toString());
            emailDTO.setReceivedDate(message.getReceivedDate());
            emailDTO.setContent(getEmailContent(message));


            emails.add(emailDTO);
        }

        folder.close(false);
        store.close();

        return emails;
    }


    private String getEmailContent(Message message) throws MessagingException, IOException {

        Object content = message.getContent();
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    sb.append(bodyPart.getContent());
                }
            }
            return sb.toString();
        }
        return null;
    }

    public void deleteEmail(int index) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol", "imap");

        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", email, password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        Message[] messages = folder.getMessages();
        if (index >= 0 && index < messages.length) {
            Message message = messages[index];
            message.setFlag(Flags.Flag.DELETED, true);
        }

        folder.close(true);
        store.close();
    }

}