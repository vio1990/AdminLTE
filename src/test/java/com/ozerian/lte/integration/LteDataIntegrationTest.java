package com.ozerian.lte.integration;

import com.ozerian.lte.AdminLteApplication;
import com.ozerian.lte.model.LteData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminLteApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LteDataIntegrationTest {

    @Inject
    TestRestTemplate restTemplate;

    @Test
    @Sql(scripts = "/createLteData.sql")
    public void addLteDataIntegrationTest() {
        ResponseEntity<LteData[]> beforeResponseEntity =
                restTemplate.getForEntity("/tables/data", LteData[].class);
        LteData[] beforeLteDataList = beforeResponseEntity.getBody();

        assertEquals(3, beforeLteDataList.length);

        ResponseEntity<LteData> responseEntity =
                restTemplate.postForEntity("/tables/data/add",
                        new LteData(4L, "Webkit", "Safari 1.2", "S60", "4.3", "A"),
                        LteData.class);
        LteData lteData = responseEntity.getBody();

        assertEquals(Long.valueOf(4L), lteData.getId());
        assertEquals("Webkit", lteData.getRenderingEngine());
        assertEquals("Safari 1.2", lteData.getBrowser());
        assertEquals("S60", lteData.getPlatform());
        assertEquals("4.3", lteData.getEngineVersion());
        assertEquals("A", lteData.getCssGrade());

        //check that new LteData has really bean added.
        ResponseEntity<LteData[]> nextResponseEntity =
                restTemplate.getForEntity("/tables/data", LteData[].class);
        LteData[] afterLteDataList = nextResponseEntity.getBody();

        assertEquals(4, afterLteDataList.length);
    }

    @Test
    @Sql(scripts = "/createLteData.sql")
    public void getLteDataByIdIntegrationTest() {
        ResponseEntity<LteData> responseEntity =
                restTemplate.getForEntity("/tables/data/2", LteData.class);
        LteData lteData = responseEntity.getBody();

        assertEquals(Long.valueOf(2), lteData.getId());
        assertEquals("Gecko", lteData.getRenderingEngine());
        assertEquals("Chrome 3.1", lteData.getBrowser());
        assertEquals("60S", lteData.getPlatform());
        assertEquals("3.2", lteData.getEngineVersion());
        assertEquals("A", lteData.getCssGrade());
    }

    @Test
    @Sql(scripts = "/createLteData.sql")
    public void getAllLteDataIntegrationTest() {
        ResponseEntity<LteData[]> responseEntity =
                restTemplate.getForEntity("/tables/data", LteData[].class);
        LteData[] lteDataList = responseEntity.getBody();

        assertEquals(3, lteDataList.length);

        //assert first LteData
        assertEquals(Long.valueOf(1), lteDataList[0].getId());
        assertEquals("Webkit", lteDataList[0].getRenderingEngine());
        assertEquals("FireFox 3.1", lteDataList[0].getBrowser());
        assertEquals("iPod", lteDataList[0].getPlatform());
        assertEquals("3.1", lteDataList[0].getEngineVersion());
        assertEquals("A", lteDataList[0].getCssGrade());

        //assert second LteData
        assertEquals(Long.valueOf(2), lteDataList[1].getId());
        assertEquals("Gecko", lteDataList[1].getRenderingEngine());
        assertEquals("Chrome 3.1", lteDataList[1].getBrowser());
        assertEquals("60S", lteDataList[1].getPlatform());
        assertEquals("3.2", lteDataList[1].getEngineVersion());
        assertEquals("A", lteDataList[1].getCssGrade());

        //assert third LteData
        assertEquals(Long.valueOf(3), lteDataList[2].getId());
        assertEquals("Trident", lteDataList[2].getRenderingEngine());
        assertEquals("Safari 2.0", lteDataList[2].getBrowser());
        assertEquals("OSX.3", lteDataList[2].getPlatform());
        assertEquals("3.2", lteDataList[2].getEngineVersion());
        assertEquals("A", lteDataList[2].getCssGrade());
    }

    @Test
    @Sql(scripts = "/createLteData.sql")
    public void getAllOrderedLteDataIntegrationTest() {
        ResponseEntity<LteData[]> responseEntity =
                restTemplate.getForEntity("/tables/data/ordered", LteData[].class);
        LteData[] lteDataList = responseEntity.getBody();

        assertEquals(3, lteDataList.length);

        // assert that LteData list has become ordered by RenderingEngine

        //assert first LteData after ordering
        assertEquals(Long.valueOf(2), lteDataList[0].getId());
        assertEquals("Gecko", lteDataList[0].getRenderingEngine());
        assertEquals("Chrome 3.1", lteDataList[0].getBrowser());
        assertEquals("60S", lteDataList[0].getPlatform());
        assertEquals("3.2", lteDataList[0].getEngineVersion());
        assertEquals("A", lteDataList[0].getCssGrade());

        //assert second LteData after ordering
        assertEquals(Long.valueOf(3), lteDataList[1].getId());
        assertEquals("Trident", lteDataList[1].getRenderingEngine());
        assertEquals("Safari 2.0", lteDataList[1].getBrowser());
        assertEquals("OSX.3", lteDataList[1].getPlatform());
        assertEquals("3.2", lteDataList[1].getEngineVersion());
        assertEquals("A", lteDataList[1].getCssGrade());

        //assert third LteData after ordering
        assertEquals(Long.valueOf(1), lteDataList[2].getId());
        assertEquals("Webkit", lteDataList[2].getRenderingEngine());
        assertEquals("FireFox 3.1", lteDataList[2].getBrowser());
        assertEquals("iPod", lteDataList[2].getPlatform());
        assertEquals("3.1", lteDataList[2].getEngineVersion());
        assertEquals("A", lteDataList[2].getCssGrade());
    }

    @Test
    @Sql(scripts = "/createLteData.sql")
    public void removeLteDataIntegrationTest() {
        ResponseEntity<LteData[]> beforeResponseEntity =
                restTemplate.getForEntity("/tables/data", LteData[].class);
        LteData[] beforeLteDataList = beforeResponseEntity.getBody();

        assertEquals(3, beforeLteDataList.length);

        //remove the LteData with id "2"
        restTemplate.delete("/tables/data/2");

        //check that new LteData has really bean added.
        ResponseEntity<LteData[]> afterResponseEntity =
                restTemplate.getForEntity("/tables/data", LteData[].class);
        LteData[] afterLteDataList = afterResponseEntity.getBody();

        assertEquals(2, afterLteDataList.length);
    }

    @Test
    @Sql(scripts = "/createLteData.sql")
    public void updateLteDataIntegrationTest() {
        ResponseEntity<LteData[]> beforeResponseEntity =
                restTemplate.getForEntity("/tables/data", LteData[].class);
        LteData[] beforeLteDataList = beforeResponseEntity.getBody();

        assertEquals(3, beforeLteDataList.length);

        ResponseEntity<LteData> responseEntity =
                restTemplate.postForEntity("/tables/data/update",
                        new LteData(2L, "Trident", "Chrome 3.1", "iPod", "14.3", "A"),
                        LteData.class);
        LteData lteData = responseEntity.getBody();

        //assert updated LteData
        assertEquals(Long.valueOf(2), lteData.getId());
        assertEquals("Trident", lteData.getRenderingEngine());
        assertEquals("Chrome 3.1", lteData.getBrowser());
        assertEquals("iPod", lteData.getPlatform());
        assertEquals("14.3", lteData.getEngineVersion());
        assertEquals("A", lteData.getCssGrade());

        //check that updated LteData is not a new.
        ResponseEntity<LteData[]> nextResponseEntity =
                restTemplate.getForEntity("/tables/data", LteData[].class);
        LteData[] afterLteDataList = nextResponseEntity.getBody();

        assertEquals(3, afterLteDataList.length);
    }
}
