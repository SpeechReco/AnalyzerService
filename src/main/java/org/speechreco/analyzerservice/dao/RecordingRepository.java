package org.speechreco.analyzerservice.dao;

import org.speechreco.analyzerservice.model.Recording;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecordingRepository extends CrudRepository<Recording, Long> {
    @Query("SELECT r FROM Recording r WHERE r.userID = ?1")
    List<Recording> getByUserID(long uid);

    @Query("UPDATE Recording r SET r.recordingURI = ?2 WHERE r.id = ?1")
    @Modifying
    @Transactional
    void updateUrlById(int id, String url);
}
