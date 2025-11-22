package com.familyspences.processorfamilyapi.messaging.vacation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.familyspences.processorfamilyapi.config.messages.vacation.VacationDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class VacationConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "vacation-events")
    public void listenVacationEvents(String message) {
        try {
            System.out.println("📩 Mensaje recibido en vacation-events: " + message);

            // Convertir JSON a DTO
            VacationDTO dto = objectMapper.readValue(message, VacationDTO.class);

            System.out.println("➡️ DTO procesado:");
            System.out.println("Título: " + dto.getTituloVacation());
            System.out.println("Lugar: " + dto.getLugarVacation());
            System.out.println("Presupuesto: " + dto.getPresupuestoVacation());

        } catch (Exception e) {
            System.err.println("❌ Error procesando mensaje: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "vacation-events.dlq")
    public void listenVacationEventsDLQ(String message) {
        System.out.println("⚠️ Mensaje enviado a DLQ: " + message);
        // Aquí puedes agregar lógica para manejar errores
    }
}
