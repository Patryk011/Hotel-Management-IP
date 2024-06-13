package org.example.hotelmanagementip.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class EmailDTOTest {

    @Test
    void testNoArgsConstructor() {
        EmailDTO emailDTO = new EmailDTO();
        assertThat(emailDTO.getId()).isEqualTo(0);
        assertThat(emailDTO.getSubject()).isNull();
        assertThat(emailDTO.getFrom()).isNull();
        assertThat(emailDTO.getReceivedDate()).isNull();
        assertThat(emailDTO.getContent()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Date date = new Date();
        EmailDTO emailDTO = new EmailDTO(1, "Subject", "from@example.com", date, "Content");

        assertThat(emailDTO.getId()).isEqualTo(1);
        assertThat(emailDTO.getSubject()).isEqualTo("Subject");
        assertThat(emailDTO.getFrom()).isEqualTo("from@example.com");
        assertThat(emailDTO.getReceivedDate()).isEqualTo(date);
        assertThat(emailDTO.getContent()).isEqualTo("Content");
    }

    @Test
    void testSettersAndGetters() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setId(1);
        emailDTO.setSubject("Subject");
        emailDTO.setFrom("from@example.com");

        Date date = new Date();
        emailDTO.setReceivedDate(date);
        emailDTO.setContent("Content");

        assertThat(emailDTO.getId()).isEqualTo(1);
        assertThat(emailDTO.getSubject()).isEqualTo("Subject");
        assertThat(emailDTO.getFrom()).isEqualTo("from@example.com");
        assertThat(emailDTO.getReceivedDate()).isEqualTo(date);
        assertThat(emailDTO.getContent()).isEqualTo("Content");
    }
}
