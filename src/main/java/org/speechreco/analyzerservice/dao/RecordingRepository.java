package org.speechreco.analyzerservice.dao;

import org.speechreco.analyzerservice.model.Recording;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordingRepository extends CrudRepository<Recording, Long> {
    @Query("SELECT r FROM Recording r WHERE r.userID = ?1")
    List<Recording> getByUserID(long uid);
}
