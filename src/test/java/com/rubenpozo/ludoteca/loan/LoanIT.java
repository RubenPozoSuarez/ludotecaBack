package com.rubenpozo.ludoteca.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.rubenpozo.ludoteca.client.model.ClientDto;
import com.rubenpozo.ludoteca.dto.PageableDto;
import com.rubenpozo.ludoteca.game.model.GameDto;
import com.rubenpozo.ludoteca.loan.model.LoanDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {
    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan/";

    private static final String GAME_PARAM = "game";
    private static final String CLIENT_PARAM = "client";
    private static final String DATE_PARAM = "date";

    private static final LocalDate DATE = LocalDate.of(2022, 01, 10);
    private static final LocalDate DATE_END = LocalDate.of(2022, 01, 20);
    private static final Long DELETE_LOAN_ID = 1L;

    private static final int PAGE_SIZE = 5;
    private static final int TOTAL_LOANS = 12;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<Page<LoanDto>> responseTypePage = new ParameterizedTypeReference<Page<LoanDto>>() {
    };

    private String getUrlWithParams() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(GAME_PARAM, "{" + GAME_PARAM + "}").queryParam(CLIENT_PARAM, "{" + CLIENT_PARAM + "}")
                .queryParam(DATE_PARAM, "{" + DATE_PARAM + "}").encode().toUriString();
    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoans() {
        Map<String, Object> params = new HashMap<>();
        params.put(GAME_PARAM, null);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, null);

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(pageableDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoansWithDate() {
        Map<String, Object> params = new HashMap<>();
        params.put(GAME_PARAM, null);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, DATE);

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(pageableDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(4, response.getBody().getTotalElements());
        assertEquals(4, response.getBody().getContent().size());
    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoansWithClient() {
        Map<String, Object> params = new HashMap<>();
        params.put(GAME_PARAM, null);
        params.put(CLIENT_PARAM, 1L);
        params.put(DATE_PARAM, null);

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(pageableDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(3, response.getBody().getTotalElements());
        assertEquals(3, response.getBody().getContent().size());
    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoansWithGame() {
        Map<String, Object> params = new HashMap<>();
        params.put(GAME_PARAM, 1L);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, null);

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(pageableDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(6, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {
        int elementsCount = 2;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_PARAM, null);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, null);

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(PageRequest.of(2, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(pageableDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(12, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void findThirtyPageWithFiveSizeShouldReturnLastResult() {
        int elementsCount = 2;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_PARAM, null);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, null);

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(PageRequest.of(1, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(pageableDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(5, response.getBody().getContent().size());
    }

    @Test
    public void saveWithoutIdShouldCreateNewLoan() {
        long newLoanId = TOTAL_LOANS + 1;
        long newLoanSize = TOTAL_LOANS + 1;

        LoanDto loanDto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(5L);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(5L);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(DATE);
        loanDto.setRepaymentDate(DATE_END);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(loanDto), Void.class);

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_PARAM, null);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, null);

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(PageRequest.of(0, (int) newLoanSize));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(pageableDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());
    }

    @Test
    public void saveWithoutIdShouldCreateNewLoanWithThrowExceptionExcessDays() {
        LoanDto loanDto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(5L);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(5L);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(DATE);
        loanDto.setRepaymentDate(LocalDate.of(2022, 01, 30));

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveWithoutIdShouldCreateNewLoanWithThrowExceptionGameAlreadyBorrowed() {
        LoanDto loanDto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(5L);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(DATE);
        loanDto.setRepaymentDate(DATE_END);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveWithoutIdShouldCreateNewLoanWithThrowExceptionClientTwoGames() {
        LoanDto loanDto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(3L);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(4L);

        loanDto.setGame(gameDto);
        loanDto.setClient(clientDto);
        loanDto.setStartDate(LocalDate.of(2022, 07, 13));
        loanDto.setRepaymentDate(LocalDate.of(2022, 07, 22));

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(loanDto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteLoan() {

        long newLoansSize = TOTAL_LOANS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + DELETE_LOAN_ID, HttpMethod.DELETE, null, Void.class);

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_PARAM, null);
        params.put(CLIENT_PARAM, null);
        params.put(DATE_PARAM, null);

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(pageableDto), responseTypePage, params);

        assertNotNull(response);
        assertEquals(newLoansSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {

        long deleteLoanId = TOTAL_LOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + deleteLoanId,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
