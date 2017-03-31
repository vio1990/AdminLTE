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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
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
    }

    @Test
    public void addLteData() throws Exception {

    }

    @Test
    public void updateLteData() throws Exception {
    }

    @Test
    public void getOrderedByRenEngineLteData() throws Exception {
    }

}