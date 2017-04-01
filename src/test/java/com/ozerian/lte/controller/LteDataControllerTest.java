package com.ozerian.lte.controller;

import com.google.gson.Gson;
import com.ozerian.lte.AdminLteApplication;
import com.ozerian.lte.model.LteData;
import com.ozerian.lte.service.CrudLteDataService;
import com.sun.org.apache.xpath.internal.operations.Lte;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import static org.mockito.Mockito.*;
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
        LteData firstLteData = new LteData(6L, "Webkit", "Safari 1.2", "S60", "4.3", "A");
        LteData secondLteData = new LteData(7L, "Gecko", "FireFox 1.0", "iPod", "1.7", "A");
        List<LteData> allLteDataList = new ArrayList<>();
        allLteDataList.add(firstLteData);
        allLteDataList.add(secondLteData);

        when(lteDataService.getAllLteData()).thenReturn(allLteDataList);

        MvcResult result = mockMvc.perform(get("/tables/data")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(lteDataService, times(1)).getAllLteData();

        String actualResult = result.getResponse().getContentAsString();

        assertNotNull(actualResult);
        assertTrue(actualResult.contains("6"));
        assertTrue(actualResult.contains("Webkit"));
        assertTrue(actualResult.contains("Safari 1.2"));
        assertTrue(actualResult.contains("S60"));
        assertTrue(actualResult.contains("4.3"));
        assertTrue(actualResult.contains("A"));

        assertTrue(actualResult.contains("7"));
        assertTrue(actualResult.contains("Gecko"));
        assertTrue(actualResult.contains("FireFox 1.0"));
        assertTrue(actualResult.contains("iPod"));
        assertTrue(actualResult.contains("1.7"));
        assertTrue(actualResult.contains("A"));
    }

    @Test
    public void getAllLteDataWhenNotFound() throws Exception {
        when(lteDataService.getAllLteData()).thenReturn(null);

        mockMvc.perform(get("/tables/data")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(lteDataService, times(1)).getAllLteData();
    }

    @Test
    public void getLteDataById() throws Exception {
        Long id = 1L;
        LteData testLteData = new LteData(id, "Gecko", "FireFox 1.0", "OSX.3+", "1.7", "A");

        when(lteDataService.getLteDataById(id)).thenReturn(testLteData);

        MvcResult result = mockMvc.perform(get("/tables/data/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();

        assertNotNull(actualResult);
        assertTrue(actualResult.contains("1"));
        assertTrue(actualResult.contains("Gecko"));
        assertTrue(actualResult.contains("FireFox 1.0"));
        assertTrue(actualResult.contains("OSX.3+"));
        assertTrue(actualResult.contains("1.7"));
        assertTrue(actualResult.contains("A"));
    }

    @Test
    public void getLteDataByIdWhenNotFound() throws Exception {
        when(lteDataService.getLteDataById(2L)).thenReturn(null);

        mockMvc.perform(get("/tables/data/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(lteDataService, times(1)).getLteDataById(2L);
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
        Long id = 1L;
        LteData testLteData = new LteData(id, "Gecko", "FireFox 1.0", "OSX.3+", "3.5", "A");

        when(lteDataService.saveLteData(testLteData)).thenReturn(testLteData);

        Gson gson = new Gson();
        String jsonLteData = gson.toJson(testLteData);

        MvcResult result = mockMvc.perform(post("/tables/data/add")
                .content(jsonLteData)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();

        assertNotNull(actualResult);
        assertTrue(actualResult.contains("1"));
        assertTrue(actualResult.contains("Gecko"));
        assertTrue(actualResult.contains("FireFox 1.0"));
        assertTrue(actualResult.contains("OSX.3+"));
        assertTrue(actualResult.contains("3.5"));
        assertTrue(actualResult.contains("A"));
    }

    @Test
    public void addLteDataByIdWhenBadRequest() throws Exception {

        mockMvc.perform(post("/tables/data/update")
                // content is empty.
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(lteDataService, times(0)).saveLteData(any(LteData.class));
    }

    @Test
    public void updateLteData() throws Exception {
        Long id = 2L;
        LteData testLteData = new LteData(id, "Webkit", "Safari 1.2", "OSX.3+", "2.5", "A");

        when(lteDataService.saveLteData(testLteData)).thenReturn(testLteData);

        Gson gson = new Gson();
        String jsonLteData = gson.toJson(testLteData);

        MvcResult result = mockMvc.perform(post("/tables/data/update")
                .content(jsonLteData)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();

        assertNotNull(actualResult);
        assertTrue(actualResult.contains("2"));
        assertTrue(actualResult.contains("Webkit"));
        assertTrue(actualResult.contains("Safari 1.2"));
        assertTrue(actualResult.contains("OSX.3+"));
        assertTrue(actualResult.contains("2.5"));
        assertTrue(actualResult.contains("A"));
    }

    @Test
    public void updateLteDataByIdWhenBadRequest() throws Exception {
        mockMvc.perform(post("/tables/data/update")
                //content is empty.
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(lteDataService, times(0)).saveLteData(any(LteData.class));
    }

    @Test
    public void getOrderedByRenEngineLteData() throws Exception {
        LteData firstLteData = new LteData(8L, "Trident", "Safari 1.2", "OSX.3", "4.3", "A");
        LteData secondLteData = new LteData(9L, "Gecko", "FireFox 1.0", "iPod", "1.7", "A");
        List<LteData> allLteDataList = new ArrayList<>();
        allLteDataList.add(firstLteData);
        allLteDataList.add(secondLteData);

        when(lteDataService.getAllLteDataGroupedByRenderingName()).thenReturn(allLteDataList);

        MvcResult result = mockMvc.perform(get("/tables/data/ordered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();

        assertNotNull(actualResult);
        assertTrue(actualResult.contains("8"));
        assertTrue(actualResult.contains("Trident"));
        assertTrue(actualResult.contains("Safari 1.2"));
        assertTrue(actualResult.contains("OSX.3"));
        assertTrue(actualResult.contains("4.3"));
        assertTrue(actualResult.contains("A"));

        assertTrue(actualResult.contains("9"));
        assertTrue(actualResult.contains("Gecko"));
        assertTrue(actualResult.contains("FireFox 1.0"));
        assertTrue(actualResult.contains("iPod"));
        assertTrue(actualResult.contains("1.7"));
        assertTrue(actualResult.contains("A"));
    }

    @Test
    public void getOrderedByRenEngineLteDataWhenNotFound() throws Exception {
        when(lteDataService.getAllLteDataGroupedByRenderingName()).thenReturn(null);

        mockMvc.perform(get("/tables/data/ordered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(lteDataService, times(0)).getAllLteData();
    }

}