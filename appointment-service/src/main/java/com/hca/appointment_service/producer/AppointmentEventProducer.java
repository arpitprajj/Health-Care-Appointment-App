package com.hca.appointment_service.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hca.appointment_service.events.AppointmentConfirmedEvent;
import com.hca.appointment_service.events.AppointmentReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppointmentEventProducer {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void publishReserved(
            AppointmentReservedEvent event){

        try {
            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(
                    "appointment-reserved",
                    event.getAppointmentId().toString(),
                    json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void publishConfirmed(
            AppointmentConfirmedEvent event){

        try {
            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(
                    "appointment-confirmed",
                    event.getAppointmentId().toString(),
                    json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}