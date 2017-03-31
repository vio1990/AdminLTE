package com.ozerian.lte.service;

import com.ozerian.lte.model.LteData;
import com.ozerian.lte.repository.LteDataRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * CrudLteDataService which implements LteDataService interface
 * and has a list of implemented interface's methods.
 */
@Service
@Transactional
public class CrudLteDataService implements LteDataService{

    @Inject
    private LteDataRepository lteDataRepository;

    @Override
    public LteData saveLteData(LteData data) {
        return lteDataRepository.save(data);
    }

    @Override
    public LteData getLteDataById(Long id) {
        return lteDataRepository.findOne(id);
    }

    @Override
    public void deleteLteDataById(Long id) {
        lteDataRepository.delete(id);
    }

    @Override
    public List<LteData> getAllLteData() {
        return (List<LteData>) lteDataRepository.findAll();
    }

    @Override
    public List<LteData> getAllLteDataGroupedByRenderingName() {
        return lteDataRepository.findAllByOrderByRenderingEngine();
    }
}
