package com.vlsi.chatbot.repository;

import com.vlsi.chatbot.entity.QAEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QAEntryRepository extends JpaRepository<QAEntry, Long> {
    
    List<QAEntry> findByCategory(String category);
    
    @Query("SELECT q FROM QAEntry q WHERE LOWER(q.question) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<QAEntry> searchByKeyword(String keyword);
    
    @Query("SELECT DISTINCT q.category FROM QAEntry q WHERE q.category IS NOT NULL")
    List<String> findAllCategories();
}
