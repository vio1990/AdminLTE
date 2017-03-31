package com.ozerian.lte.controller;

import com.ozerian.lte.AdminLteApplication;
import com.ozerian.lte.model.LteData;
import com.ozerian.lte.service.CrudLteDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AdminLteApplication.class)
@WebMvcTest(LteDataController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class LteDataControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private CrudLteDataService lteDataService;

    @Test
    public void getAllLteData() throws Exception {
        LteData firstLteData = new LteData(6L, "Webkit", "Safari 1.2",
                "S60", "4.3", "A");
        LteData secondLteData = new LteData(7L, "Gecko", "FireFox 1.0",
                "iPod", "1.7", "A");
        List<LteData> allLteDataList = new ArrayList<>();
        allLteDataList.add(firstLteData);
        allLteDataList.add(secondLteData);

        when(lteDataService.getAllLteData()).thenReturn(allLteDataList);

        MvcResult result = mockMvc.perform(get("/tables/data")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResult = new StringBuilder()
                .append("[{")
                .append("\"id\":6,")
                .append("\"renderingEngine\":\"Webkit\",")
                .append("\"browser\":\"Safari 1.2\",")
                .append("\"platform\":\"S60\",")
                .append("\"engineVersion\":\"4.3\",")
                .append("\"cssGrade\":\"A\"")
                .append("},{")
                .append("\"id\":7,")
                .append("\"renderingEngine\":\"Gecko\",")
                .append("\"browser\":\"FireFox 1.0\",")
                .append("\"platform\":\"iPod\",")
                .append("\"engineVersion\":\"1.7\",")
                .append("\"cssGrade\":\"A\"")
                .append("}]")
                .toString();

        String actualResult = result.getResponse().getContentAsString();
        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getLteDataById() throws Exception {
        Long id = 1L;
        LteData testLteData = new LteData(id, "Gecko", "FireFox 1.0",
                "OSX.3+", "1.7", "A");

        when(lteDataService.getLteDataById(id)).thenReturn(testLteData);

        MvcResult result = mockMvc.perform(get("/tables/data/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResult = new StringBuilder()
                .append("{")
                .append("\"id\":1,")
                .append("\"renderingEngine\":\"Gecko\",")
                .append("\"browser\":\"FireFox 1.0\",")
                .append("\"platform\":\"OSX.3+\",")
                .append("\"engineVersion\":\"1.7\",")
                .append("\"cssGrade\":\"A\"")
                .append("}")
                .toString();

        String actualResult = result.getResponse().getContentAsString();
        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeLteDataById() throws Exception {
        mockMvc.perform(delete("/tables/data/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(lteDataService, times(1)).deleteLteDataById(2L);
    }

    @Test
    public void addLteData() throws Exception {

    }

    @Test
    public void updateLteData() throws Exception {
    }

    @Test
    public void getOrderedByRenEngineLteData() throws Exception {
        LteData firstLteData = new LteData(8L, "Trident", "Safari 1.2",
                "OSX.3", "4.3", "A");
        LteData secondLteData = new LteData(9L, "Gecko", "FireFox 1.0",
                "iPod", "1.7", "A");
        List<LteData> allLteDataList = new ArrayList<>();
        allLteDataList.add(firstLteData);
        allLteDataList.add(secondLteData);

        when(lteDataService.getAllLteDataGroupedByRenderingName()).thenReturn(allLteDataList);

        MvcResult result = mockMvc.perform(get("/tables/data/ordered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResult = new StringBuilder()
                .append("[{")
                .append("\"id\":8,")
                .append("\"renderingEngine\":\"Trident\",")
                .append("\"browser\":\"Safari 1.2\",")
                .append("\"platform\":\"OSX.3\",")
                .append("\"engineVersion\":\"4.3\",")
                .append("\"cssGrade\":\"A\"")
                .append("},{")
                .append("\"id\":9,")
                .append("\"renderingEngine\":\"Gecko\",")
                .append("\"browser\":\"FireFox 1.0\",")
                .append("\"platform\":\"iPod\",")
                .append("\"engineVersion\":\"1.7\",")
                .append("\"cssGrade\":\"A\"")
                .append("}]")
                .toString();

        String actualResult = result.getResponse().getContentAsString();
        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(expectedResult, actualResult);
    }

}