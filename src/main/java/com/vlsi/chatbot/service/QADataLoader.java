package com.vlsi.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlsi.chatbot.entity.QAEntry;
import com.vlsi.chatbot.repository.QAEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class QADataLoader implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(QADataLoader.class);
    
    @Autowired
    private QAEntryRepository qaEntryRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Only load if database is empty
        if (qaEntryRepository.count() > 0) {
            logger.info("Q&A data already loaded. Found {} entries.", qaEntryRepository.count());
            return;
        }
        
        logger.info("Loading Q&A data from vlsi_faq.json...");
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        
        // Try to load from classpath first
        try {
            ClassPathResource resource = new ClassPathResource("vlsi_faq.json");
            if (resource.exists()) {
                try (InputStream is = resource.getInputStream()) {
                    rootNode = mapper.readTree(is);
                }
            }
        } catch (Exception e) {
            logger.debug("Could not load from classpath: {}", e.getMessage());
        }
        
        // Try to load from project root
        if (rootNode == null) {
            Path jsonFile = Paths.get(System.getProperty("user.dir"), "vlsi_faq.json");
            if (Files.exists(jsonFile)) {
                rootNode = mapper.readTree(jsonFile.toFile());
            }
        }
        
        if (rootNode == null) {
            logger.warn("vlsi_faq.json not found. Creating sample Q&A entries...");
            createSampleData();
            return;
        }
        
        JsonNode dataNode = rootNode.get("data");
        if (dataNode == null || !dataNode.isArray()) {
            logger.warn("Invalid JSON structure. Expected 'data' array.");
            createSampleData();
            return;
        }
        
        int count = 0;
        for (JsonNode entry : dataNode) {
            String question = entry.has("question") ? entry.get("question").asText() : null;
            String answer = entry.has("answer") ? entry.get("answer").asText() : null;
            
            if (question != null && !question.isEmpty() && answer != null && !answer.isEmpty()) {
                QAEntry qaEntry = new QAEntry(question, answer);
                qaEntryRepository.save(qaEntry);
                count++;
            }
        }
        
        logger.info("Successfully loaded {} Q&A entries from vlsi_faq.json", count);
    }
    
    private void createSampleData() {
        // Create some sample VLSI Q&A entries
        qaEntryRepository.save(new QAEntry(
                "What does VLSI stand for?",
                "VLSI = Very Large Scale Integration\nIt means integrating millions of transistors on a single silicon chip to make a complete circuit."
        ));
        
        qaEntryRepository.save(new QAEntry(
                "What is Moore's Law?",
                "Gordon Moore observed that the number of transistors on a chip doubles every 18–24 months, leading to higher performance and lower cost per transistor."
        ));
        
        qaEntryRepository.save(new QAEntry(
                "Explain the difference between ASIC and FPGA.",
                "ASIC (Application Specific IC): Fixed for one task, faster, high cost for small quantity.\nFPGA (Field Programmable Gate Array): Reconfigurable, slower, low cost for small quantity."
        ));
        
        qaEntryRepository.save(new QAEntry(
                "What is CMOS technology?",
                "CMOS = Complementary MOS (NMOS + PMOS)\nIt is low power, high noise immunity, and high density. Used in almost all modern ICs."
        ));
        
        qaEntryRepository.save(new QAEntry(
                "What is a semiconductor?",
                "A semiconductor is a material whose electrical conductivity lies between a conductor and an insulator. Example: Silicon (Si) and Germanium (Ge)."
        ));
        
        logger.info("Created 5 sample Q&A entries");
    }
}
