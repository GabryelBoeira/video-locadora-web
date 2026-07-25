package com.gabryel.notificationservice.service;

import com.gabryel.notificationservice.dto.EventNotification;
import com.gabryel.notificationservice.dto.PayloadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    public void process(EventNotification event) {
        switch (event.eventType()) {
            case CUSTOMER_CREATED -> processCustomerCreated(event.payload());
            case CUSTOMER_DELETED -> processCustomerDeleted(event.payload());

            case RENTAL_CREATED -> processRentalCreated(event.payload());
            case RENTAL_RETURNED -> processRentalReturned(event.payload());
            case RENTAL_EXPIRED -> processRentalExpired(event.payload());

            case MOVIE_AVAILABLE_FOR_RENT -> processAvailableRent(event.payload());
            default -> LOG.info("Evento ignorado: {}", event.eventType());
        }
    }

    private void processCustomerCreated(PayloadEvent payload) {
        String name = payload.customerName();
        String email = payload.customerEmail();

        LOG.info("Notificação de boas-vindas para {} <{}>", name, email);
    }

    private void processCustomerDeleted(PayloadEvent payload) {
        String name = payload.customerName();
        String email = payload.customerEmail();

        LOG.info("Notificação de cancelamento com sucesso da sua conta {} <{}>", name, email);
    }

    private void processRentalCreated(PayloadEvent payload) {
        Long rentalId = payload.rentalId();
        String customerName = payload.customerName();
        String email = payload.customerEmail();
        LocalDateTime dueAt = payload.rentalDueAt();

        LOG.info(
                "Notificação de locação criada. rentalId={}, cliente={}, email={}, vencimento={}",
                rentalId,
                customerName,
                email,
                dueAt.toString()
        );
    }

    private void processRentalReturned(PayloadEvent payload) {
        Long rentalId = payload.rentalId();
        String email = payload.customerEmail();

        LOG.info("Notificação de devolução registrada. rentalId={}, email={}", rentalId, email);
    }

    private void processRentalExpired(PayloadEvent payload) {
        Long rentalId = payload.rentalId();
        String email = payload.customerEmail();

        LOG.info("Notificação de locação expirada. rentalId={}, email={}", rentalId, email);
    }

    private void processAvailableRent(PayloadEvent payload) {

        if (payload.movieNames().size() == 1) {
            LOG.info("Filme {} da sua lista de interesse disponíveil {}",
                    payload.movieNames().getFirst(),
                    payload.customerName()
            );
        } else {
            LOG.info("Filmes {} da sua lista de interesse disponíveis {}",
                    String.join(", ", payload.movieNames()),
                    payload.customerName()
            );
        }
    }

}
