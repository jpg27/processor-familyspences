package com.familyspences.processorfamilyapi.consumer;

import com.familyspences.processorfamilyapi.domain.goals.Goals;
import com.familyspences.processorfamilyapi.dto.goals.GoalMessageDTO;
import com.familyspences.processorfamilyapi.service.goals.GoalService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GoalsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(GoalsConsumer.class);
    private final GoalService goalService;

    public GoalsConsumer(GoalService goalService) {
        this.goalService = goalService;
    }

    @RabbitListener(queues = "cola_goals", ackMode = "MANUAL")
    public void processGoalMessage(GoalMessageDTO message,
                                   Channel channel,
                                   @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            logger.info("═══════════════════════════════════════════════════════════");
            logger.info("📬 Nuevo mensaje recibido en cola_goals");
            logger.info("   Delivery Tag: {}", tag);
            logger.info("   Operación: {}", message.getOperation());

            boolean success = false;

            switch (message.getOperation().toUpperCase()) {
                case "CREATE":
                    success = processCreate(message.getData());
                    break;
                case "UPDATE":
                    success = processUpdate(message.getData());
                    break;
                case "DELETE":
                    success = processDelete(message.getData());
                    break;
                case "GET":
                case "RETRIEVE":
                    success = processGet(message.getData());
                    break;
                default:
                    logger.warn("⚠️  Operación desconocida: {}", message.getOperation());
            }

            if (success) {
                channel.basicAck(tag, false);
                logger.info("✅ Mensaje procesado y confirmado (ACK)");
            } else {
                channel.basicNack(tag, false, true);
                logger.warn("⚠️  Mensaje rechazado y reencolado (NACK)");
            }

            logger.info("═══════════════════════════════════════════════════════════");

        } catch (Exception e) {
            logger.error("❌ Error inesperado al procesar mensaje: {}", e.getMessage(), e);
            try {
                channel.basicNack(tag, false, true);
            } catch (Exception nackError) {
                logger.error("❌ Error al enviar NACK: {}", nackError.getMessage());
            }
        }
    }

    private boolean processCreate(GoalMessageDTO.GoalData data) {
        try {
            logger.info("📝 CREATE - Procesando nueva meta");
            logger.info("   Datos recibidos: name={}, savingsCap={}, deadline={}",
                    data.getName(), data.getSavingsCap(), data.getDeadline());

            if (data.getName() == null || data.getName().isBlank()) {
                logger.error("❌ Nombre no puede estar vacío");
                return false;
            }
            if (data.getDescription() == null || data.getDescription().isBlank()) {
                logger.error("❌ Descripción no puede estar vacía");
                return false;
            }
            if (data.getCategoryId() == null) {
                logger.error("❌ Category ID no puede estar vacío");
                return false;
            }
            if (data.getSavingsCap() == null || data.getSavingsCap() <= 0) {
                logger.error("❌ Tope de ahorro debe ser mayor que 0");
                return false;
            }
            if (data.getDeadline() == null) {
                logger.error("❌ Fecha límite no puede estar vacía");
                return false;
            }
            if (data.getDailyGoal() == null || data.getDailyGoal() <= 0) {
                logger.error("❌ Meta diaria debe ser mayor que 0");
                return false;
            }

            Goals goal = new Goals();
            goal.setName(data.getName());
            goal.setDescription(data.getDescription());
            goal.setCategoryId(data.getCategoryId());
            goal.setSavingsCap(data.getSavingsCap());
            goal.setDeadline(data.getDeadline());
            goal.setDailyGoal(data.getDailyGoal());

            Goals savedGoal = goalService.createGoal(goal);
            logger.info("✅ Meta '{}' creada exitosamente con ID: {}",
                    savedGoal.getName(), savedGoal.getId());

            return true;

        } catch (Exception e) {
            logger.error("❌ Error al crear meta: {}", e.getMessage(), e);
            return false;
        }
    }

    private boolean processUpdate(GoalMessageDTO.GoalData data) {
        try {
            logger.info("🔄 UPDATE - Procesando actualización de meta");
            logger.info("   ID: {}", data.getId());

            if (data.getId() == null) {
                logger.error("❌ ID no proporcionado para actualización");
                return false;
            }

            Goals goalDetails = new Goals();
            if (data.getName() != null) {
                goalDetails.setName(data.getName());
            }
            if (data.getDescription() != null) {
                goalDetails.setDescription(data.getDescription());
            }
            if (data.getCategoryId() != null) {
                goalDetails.setCategoryId(data.getCategoryId());
            }
            if (data.getSavingsCap() != null) {
                goalDetails.setSavingsCap(data.getSavingsCap());
            }
            if (data.getDeadline() != null) {
                goalDetails.setDeadline(data.getDeadline());
            }
            if (data.getDailyGoal() != null) {
                goalDetails.setDailyGoal(data.getDailyGoal());
            }

            Optional<Goals> updated = goalService.updateGoal(data.getId(), goalDetails);

            if (updated.isPresent()) {
                logger.info("✅ Meta ID {} actualizada exitosamente", data.getId());
                return true;
            } else {
                logger.warn("⚠️  Meta con ID {} no encontrada", data.getId());
                return false;
            }

        } catch (Exception e) {
            logger.error("❌ Error al actualizar meta: {}", e.getMessage(), e);
            return false;
        }
    }

    private boolean processDelete(GoalMessageDTO.GoalData data) {
        try {
            logger.info("🗑️  DELETE - Procesando eliminación de meta");
            logger.info("   ID: {}", data.getId());

            if (data.getId() == null) {
                logger.error("❌ ID no proporcionado para eliminación");
                return false;
            }

            boolean deleted = goalService.deleteGoal(data.getId());

            if (deleted) {
                logger.info("✅ Meta ID {} eliminada exitosamente", data.getId());
                return true;
            } else {
                logger.warn("⚠️  Meta con ID {} no encontrada", data.getId());
                return false;
            }

        } catch (Exception e) {
            logger.error("❌ Error al eliminar meta: {}", e.getMessage(), e);
            return false;
        }
    }

    private boolean processGet(GoalMessageDTO.GoalData data) {
        try {
            logger.info("🔍 GET - Procesando consulta de meta(s)");

            if (data.getId() != null) {
                logger.info("   Buscando meta con ID: {}", data.getId());
                Optional<Goals> goal = goalService.getGoalById(data.getId());

                if (goal.isPresent()) {
                    logger.info("✅ Meta encontrada: {}", goal.get().getName());
                } else {
                    logger.warn("⚠️  Meta con ID {} no encontrada", data.getId());
                }
            } else {
                logger.info("   Obteniendo todas las metas");
                List<Goals> goals = goalService.getAllGoals();
                logger.info("✅ Se encontraron {} metas", goals.size());
            }
            return true;

        } catch (Exception e) {
            logger.error("❌ Error al consultar meta(s): {}", e.getMessage(), e);
            return false;
        }
    }
}
