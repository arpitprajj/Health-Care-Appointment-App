//package com.hca.notification_service;
//
//import com.hca.notification_service.service.impl.EmailServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Disabled
//@ExtendWith(MockitoExtension.class)
//class EmailServiceImplTest {
//
//    @Mock
//    private JavaMailSender mailSender;
//
//    @InjectMocks
//    private EmailServiceImpl emailService;
//
//    @BeforeEach
//    void setUp() {
//
//        ReflectionTestUtils.setField(
//                emailService,
//                "fromEmail",
//                "healthcare@gmail.com");
//    }
//
//    @Test
//    void shouldSendEmailSuccessfully() {
//
//        emailService.sendEmail(
//                "patient@gmail.com",
//                "Appointment Confirmed",
//                "Your appointment has been confirmed.");
//
//        ArgumentCaptor<SimpleMailMessage> captor =
//                ArgumentCaptor.forClass(SimpleMailMessage.class);
//
//        verify(mailSender, times(1))
//                .send(captor.capture());
//
//        SimpleMailMessage mail =
//                captor.getValue();
//
//        assertEquals(
//                "healthcare@gmail.com",
//                mail.getFrom());
//
//        assertArrayEquals(
//                new String[]{"patient@gmail.com"},
//                mail.getTo());
//
//        assertEquals(
//                "Appointment Confirmed",
//                mail.getSubject());
//
//        assertEquals(
//                "Your appointment has been confirmed.",
//                mail.getText());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenMailSenderFails() {
//
//        doThrow(new RuntimeException("SMTP Error"))
//                .when(mailSender)
//                .send(any(SimpleMailMessage.class));
//
//        RuntimeException exception =
//                assertThrows(
//                        RuntimeException.class,
//                        () -> emailService.sendEmail(
//                                "patient@gmail.com",
//                                "Subject",
//                                "Body"));
//
//        assertEquals(
//                "Unable to send email",
//                exception.getMessage());
//
//        verify(mailSender, times(1))
//                .send(any(SimpleMailMessage.class));
//    }
//}
