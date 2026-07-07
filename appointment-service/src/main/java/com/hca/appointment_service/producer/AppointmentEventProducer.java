package com.hca.appointment_service.producer;

import com.hca.appointment_service.events.AppointmentConfirmedEvent;
import com.hca.appointment_service.events.AppointmentReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentEventProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void publishReserved(
            AppointmentReservedEvent event){

        kafkaTemplate.send(
                "appointment-reserved",
                event.getAppointmentId().toString(),
                event);
    }

    public void publishConfirmed(
            AppointmentConfirmedEvent event){

        kafkaTemplate.send(
                "appointment-confirmed",
                event.getAppointmentId().toString(),
                event);
    }

}