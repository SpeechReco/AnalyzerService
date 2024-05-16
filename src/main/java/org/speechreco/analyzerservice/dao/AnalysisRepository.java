package org.speechreco.analyzerservice.dao;

import org.speechreco.analyzerservice.model.Analysis;
import org.speechreco.analyzerservice.model.Recording;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnalysisRepository extends CrudRepository<Analysis, Integer> {
    @Query("SELECT a FROM analysis a WHERE a.recordingID = ?1")
    List<Analysis> getByRecordingID(int recordingID);

    @Query("UPDATE analysis a SET a.textURI = ?2 WHERE a.id = ?1")
    @Modifying
    @Transactional
    void updateUrlById(int id, String url);
}
