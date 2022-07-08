package com.rubenpozo.ludoteca.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.rubenpozo.ludoteca.category.model.CategoryDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/category/";
    public static final Long NEW_CATEGORY_ID = 4L;
    public static final String NEW_CATEGORY_NAME = "CAT4";
    public static final Long MODIFY_CATEGORY_ID = 3L;
    public static final Long DELETE_CATEGORY_ID = 2L;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<CategoryDto>> responseType = new ParameterizedTypeReference<List<CategoryDto>>() {
    };

    @Test
    public void findAllShouldReturnAllCategories() {

        ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void saveWithoutIdShouldCreateNewCategory() {

        CategoryDto dto = new CategoryDto();
        dto.setName(NEW_CATEGORY_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(4, response.getBody().size());

        CategoryDto categorySearch = response.getBody().stream().filter(item -> item.getId().equals(NEW_CATEGORY_ID))
                .findFirst().orElse(null);
        assertNotNull(categorySearch);
        assertEquals(NEW_CATEGORY_NAME, categorySearch.getName());
    }

    @Test
    public void modifyWithExistIdShouldModifyCategory() {

        CategoryDto dto = new CategoryDto();
        dto.setName(NEW_CATEGORY_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + MODIFY_CATEGORY_ID, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(3, response.getBody().size());

        CategoryDto categorySearch = response.getBody().stream().filter(item -> item.getId().equals(MODIFY_CATEGORY_ID))
                .findFirst().orElse(null);
        assertNotNull(categorySearch);
        assertEquals(NEW_CATEGORY_NAME, categorySearch.getName());
    }

    @Test
    public void modifyWithNotExistIdShouldInternalError() {

        CategoryDto dto = new CategoryDto();
        dto.setName(NEW_CATEGORY_NAME);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + NEW_CATEGORY_ID,
                HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteCategory() {

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + DELETE_CATEGORY_ID, HttpMethod.DELETE, null,
                Void.class);

        ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void deleteWithNotExistsIdShouldInternalError() {

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + NEW_CATEGORY_ID,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
