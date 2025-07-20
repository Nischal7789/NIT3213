package com.nit3213app.nit3213finalproject;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardActivityTest {

    private static final String TEST_USERNAME = "Nischal";
    private static final String TEST_PASSWORD = "s8187282";
    private static final String EXPECTED_KEYPASS = "photography";

    private static final String SAMPLE_RESPONSE = "{\"entities\":[" +
            "{\"technique\":\"Long Exposure\",\"equipment\":\"Tripod\",\"subject\":\"Night sky\"," +
            "\"pioneeringPhotographer\":\"Michael Kenna\",\"yearIntroduced\":1975," +
            "\"description\":\"Long exposure technique description\"}," +
            "{\"technique\":\"Macro Photography\",\"equipment\":\"Macro lens\",\"subject\":\"Insects\"," +
            "\"pioneeringPhotographer\":\"Roman Vishniac\",\"yearIntroduced\":1930," +
            "\"description\":\"Macro photography description\"}" +
            "],\"entityTotal\":2}";

    @Test
    public void testJsonParsingWithActualData() {
        DashboardActivity.DashboardResponse response = new Gson().fromJson(
                SAMPLE_RESPONSE, DashboardActivity.DashboardResponse.class);

        assertNotNull("Response should not be null", response);
        assertEquals("Should have 2 entities", 2, response.entities.size());
        assertEquals("First technique should match",
                "Long Exposure", response.entities.get(0).technique);
        assertEquals("First equipment should match",
                "Tripod", response.entities.get(0).equipment);
        assertEquals("Second photographer should match",
                "Roman Vishniac", response.entities.get(1).pioneeringPhotographer);
    }

    @Test
    public void testEntityStructure() {
        DashboardActivity.Entity entity = new DashboardActivity.Entity();
        entity.technique = "Test Technique";
        entity.equipment = "Test Equipment";
        entity.subject = "Test Subject";
        entity.pioneeringPhotographer = "Test Photographer";
        entity.yearIntroduced = 2000;
        entity.description = "Test Description";

        assertEquals("Test Technique", entity.technique);
        assertEquals("Test Equipment", entity.equipment);
        assertEquals("Test Subject", entity.subject);
        assertEquals("Test Photographer", entity.pioneeringPhotographer);
        assertEquals(2000, entity.yearIntroduced);
        assertEquals("Test Description", entity.description);
    }

    @Test
    public void testCredentialsAndKeypass() {
        assertEquals("Username should match", "Nischal", TEST_USERNAME);
        assertEquals("Password should match", "s8187282", TEST_PASSWORD);
        assertEquals("Keypass should match", "photography", EXPECTED_KEYPASS);
    }
}