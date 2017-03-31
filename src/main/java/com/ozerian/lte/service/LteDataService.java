package com.ozerian.lte.service;

import com.ozerian.lte.model.LteData;
import com.sun.org.apache.xpath.internal.operations.Lte;

import java.util.List;

/**
 * LteDataService interface with basic necessary methods
 * for LteData operating.
 */
public interface LteDataService {

    LteData saveLteData(LteData data);

    LteData getLteDataById(Long id);

    void deleteLteDataById(Long id);

    List<LteData> getAllLteData();

    List<LteData> getAllLteDataGroupedByRenderingName();






}
