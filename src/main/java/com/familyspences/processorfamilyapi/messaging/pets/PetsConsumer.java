package com.familyspences.processorfamilyapi.messaging.pets;

import com.familyspences.processorfamilyapi.domain.pet.Pet;
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

    @RabbitListener(queues = "q.pet.create")  //
    public void handlePetCreate(Pet pet){
        log.info("Received Pet CREATE event: {}", pet);
        try {
            petService.saveFromProducer(pet);
            log.info("Pet saved successfully: {}", pet.getId());
        } catch (Exception e) {
            log.error("Error processing Pet CREATE event. Pet ID: {}. Error: {}",
                    pet.getId(), e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "q.pet.update")  //
    public void handlePetUpdate(Pet pet) {
        log.info("Received Pet UPDATE event: {}", pet);
        petService.updateFromProducer(pet);
    }

    @RabbitListener(queues = "q.pet.delete")  //
    public void handlePetDelete(Map<String, String> data) {
        log.info("Received Pet DELETE event: {}", data);
        petService.deleteFromProducer(data);
    }
}

