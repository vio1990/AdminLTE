package com.ozerian.lte.service;

import com.ozerian.lte.model.LteData;
import com.ozerian.lte.repository.LteDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CrudLteDataServiceTest {

    @InjectMocks
    private CrudLteDataService dataService;

    @Mock
    private LteDataRepository dataRepository;

    @Test
    public void saveLteData() throws Exception {
        LteData testLteData = new LteData(1L, "Gecko", "FireFox 1.0",
                "OSX.3+", "1.7", "A");
        when(dataRepository.save(testLteData)).thenReturn(testLteData);

        LteData savedLteData = dataService.saveLteData(testLteData);

        verify(dataRepository, times(1)).save(savedLteData);
        assertEquals("Gecko", savedLteData.getRenderingEngine());
        assertEquals("FireFox 1.0", savedLteData.getBrowser());
        assertEquals("OSX.3+", savedLteData.getPlatform());
        assertEquals("1.7", savedLteData.getEngineVersion());
        assertEquals("A", savedLteData.getCssGrade());
    }

    @Test
    public void getLteDataById() throws Exception {
        Long id = 2L;
        LteData testLteData = new LteData(id, "Trident", "Safari 1.2",
                "S60", "4", "A");
        when(dataRepository.findOne(id)).thenReturn(testLteData);

        LteData gotLteData = dataService.getLteDataById(id);

        verify(dataRepository, times(1)).findOne(id);
        assertEquals("Trident", gotLteData.getRenderingEngine());
        assertEquals("Safari 1.2", gotLteData.getBrowser());
        assertEquals("S60", gotLteData.getPlatform());
        assertEquals("4", gotLteData.getEngineVersion());
        assertEquals("A", gotLteData.getCssGrade());
    }

    @Test
    public void deleteLteDataById() throws Exception {
        Long id = 3L;
        LteData testLteData = new LteData(id, "Trident", "Safari 1.2",
                "S60", "4", "A");

        dataService.deleteLteDataById(testLteData.getId());

        verify(dataRepository, times(1)).delete(id);
    }

    @Test
    public void getAllLteData() throws Exception {
        LteData firstLteData = new LteData(4L, "Trident", "Safari 1.2",
                "S60", "4", "A");
        LteData secondLteData = new LteData(5L, "Gecko", "FireFox 1.0",
                "OSX.3+", "1.7", "A");
        List<LteData> allLteDataList = new ArrayList<>();
        allLteDataList.add(firstLteData);
        allLteDataList.add(secondLteData);

        when(dataRepository.findAll()).thenReturn(allLteDataList);

        List<LteData> gotLteDataList = dataService.getAllLteData();

        verify(dataRepository, times(1)).findAll();
        assertEquals(2, gotLteDataList.size());

        //assert first LteData
        assertEquals("Trident", gotLteDataList.get(0).getRenderingEngine());
        assertEquals("Safari 1.2", gotLteDataList.get(0).getBrowser());
        assertEquals("S60", gotLteDataList.get(0).getPlatform());
        assertEquals("4", gotLteDataList.get(0).getEngineVersion());
        assertEquals("A", gotLteDataList.get(0).getCssGrade());

        //assert second LteData
        assertEquals("Gecko", gotLteDataList.get(1).getRenderingEngine());
        assertEquals("FireFox 1.0", gotLteDataList.get(1).getBrowser());
        assertEquals("OSX.3+", gotLteDataList.get(1).getPlatform());
        assertEquals("1.7", gotLteDataList.get(1).getEngineVersion());
        assertEquals("A", gotLteDataList.get(1).getCssGrade());
    }

    @Test
    public void getAllLteDataGroupedByRenderingName() throws Exception {
        LteData firstLteData = new LteData(6L, "Webkit", "Safari 1.2",
                "S60", "4.3", "A");
        LteData secondLteData = new LteData(7L, "Gecko", "FireFox 1.0",
                "iPod", "1.7", "A");
        List<LteData> allLteDataList = new ArrayList<>();
        allLteDataList.add(firstLteData);
        allLteDataList.add(secondLteData);

        when(dataRepository.findAll()).thenReturn(allLteDataList);

        List<LteData> gotLteDataList = dataService.getAllLteData();

        verify(dataRepository, times(1)).findAll();
        assertEquals(2, gotLteDataList.size());

        //assert first LteData
        assertEquals("Webkit", gotLteDataList.get(0).getRenderingEngine());
        assertEquals("Safari 1.2", gotLteDataList.get(0).getBrowser());
        assertEquals("S60", gotLteDataList.get(0).getPlatform());
        assertEquals("4.3", gotLteDataList.get(0).getEngineVersion());
        assertEquals("A", gotLteDataList.get(0).getCssGrade());

        //assert second LteData
        assertEquals("Gecko", gotLteDataList.get(1).getRenderingEngine());
        assertEquals("FireFox 1.0", gotLteDataList.get(1).getBrowser());
        assertEquals("iPod", gotLteDataList.get(1).getPlatform());
        assertEquals("1.7", gotLteDataList.get(1).getEngineVersion());
        assertEquals("A", gotLteDataList.get(1).getCssGrade());
    }

}