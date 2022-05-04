package com.epam.esm.generator;

import com.epam.esm.service.dto.tag.TagDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObjectGeneratorRunner implements CommandLineRunner {

    private final ObjectGenerator generator;

    @Value("${app.objects.generation}")
    private boolean generationFlag;

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectGeneratorRunner.class);

    @Autowired
    public ObjectGeneratorRunner(ObjectGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(String... args) throws Exception {
        if (generationFlag) {
            List<String> wordsForGenerations = generator.getGeneratedWords(5000);
            List<TagDTO> generatedTags = generator.generateTags(1000);
            List<Integer> gcIds = generator.generateCertificates(10000, generatedTags, wordsForGenerations);
            List<Integer> userIds = generator.generateUsers(1000, wordsForGenerations);
            int ordersCount = generator.generateOrders(gcIds, userIds);

            if (LOGGER.isDebugEnabled()) {
                StringBuilder message = new StringBuilder("Generated: tags - ");
                message.append(generatedTags.size())
                        .append(", certificates - ")
                        .append(gcIds.size()).append(", users - ")
                        .append(userIds.size()).append(", orders - ").append(ordersCount);

                LOGGER.debug(message.toString(), message);
            }
        }
    }
}
