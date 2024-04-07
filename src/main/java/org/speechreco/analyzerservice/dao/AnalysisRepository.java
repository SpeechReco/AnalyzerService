package org.speechreco.analyzerservice.dao;

import org.speechreco.analyzerservice.model.Analysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends CrudRepository<Analysis, Integer> {
}
