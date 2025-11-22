package com.familyspences.processorfamilyapi.messaging.pets;

import com.familyspences.processorfamilyapi.config.messages.pets.petsDTO;  // ✅ Importa PetDTO
import com.familyspences.processorfamilyapi.service.pet.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PetsConsumer {

    private static final Logger log = LoggerFactory.getLogger(PetsConsumer.class);
    private final PetService petService;

    public PetsConsumer(PetService petService) {
        this.petService = petService;
        log.info("========================================");
        log.info("PetsConsumer INITIALIZED!");
        log.info("========================================");
    }

    @RabbitListener(queues = "q.pet.create")
    public void handlePetCreate(petsDTO petDTO){  // ✅ PetDTO con mayúscula
        log.info("✅ Received Pet CREATE event: {}", petDTO.getId());
        try {
            petService.saveFromProducer(petDTO);  // ✅ Usa petDTO (variable)
            log.info("✅ Pet saved successfully: {}", petDTO.getId());
        } catch (Exception e) {
            log.error("❌ Error processing Pet CREATE event. Pet ID: {}. Error: {}",
                    petDTO.getId(), e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "q.pet.update")
    public void handlePetUpdate(petsDTO petDTO) {  // ✅ PetDTO con mayúscula
        log.info("✅ Received Pet UPDATE event: {}", petDTO.getId());
        petService.updateFromProducer(petDTO);  // ✅ Usa petDTO (variable)
    }

    @RabbitListener(queues = "q.pet.delete")
    public void handlePetDelete(Map<String, String> data) {
        log.info("✅ Received Pet DELETE event: {}", data);
        petService.deleteFromProducer(data);
    }
}