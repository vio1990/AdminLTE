package com.ozerian.lte.repository;

import com.ozerian.lte.model.LteData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LTEDataRepository interface with basic CRUD operations and
 * defined method with ordering by Rendering Engine field.
 */
@Repository
public interface LteDataRepository extends CrudRepository<LteData, Long>{

    List<LteData> findAllByOrderByRenderingEngine();

}
