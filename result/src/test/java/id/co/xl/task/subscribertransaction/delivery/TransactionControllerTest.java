package id.co.xl.task.subscribertransaction.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.xl.task.subscribertransaction.model.request.GetMonthlyTransactionRq;
import id.co.xl.task.subscribertransaction.model.response.GenericResponse;
import jakarta.annotation.Nullable;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @SuppressWarnings({ "rawtypes" })
  void getTransactionSummarySuccess() throws Exception {
    GetMonthlyTransactionRq req = new GetMonthlyTransactionRq();
    req.setMsisdn("62819123456");
    req.setPin("1234");

    mockMvc.perform(
        post("/api/transaction/summary")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
        .andExpectAll(status().isOk()).andDo(result -> {

          GenericResponse response = objectMapper.readValue(
              result.getResponse().getContentAsString(),
              new TypeReference<GenericResponse>() {
              });

          assertEquals("00", response.getCode());
          assertEquals("ok", response.getStatus());
          assertEquals("success", response.getMessage());
          assertNotEquals(null, response.getData());
        });
  }

  @Test
  @SuppressWarnings({ "rawtypes" })
  void getTransactionSummaryWrongMSISDN() throws Exception {
    GetMonthlyTransactionRq req = new GetMonthlyTransactionRq();
    req.setMsisdn("62819123456s");
    req.setPin("1234");

    mockMvc.perform(
        post("/api/transaction/summary")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
        .andExpectAll(status().isNotFound()).andDo(result -> {
          GenericResponse response = objectMapper.readValue(
              result.getResponse().getContentAsString(),
              new TypeReference<GenericResponse>() {
              });
          assertEquals("01", response.getCode());
          assertEquals("error", response.getStatus());
          assertEquals("Unable to Get PIN", response.getMessage());
          assertEquals(null, response.getData());
        });
  }

  @Test
  @SuppressWarnings({ "rawtypes" })
  void getTransactionSummaryWrongPIN() throws Exception {
    GetMonthlyTransactionRq req = new GetMonthlyTransactionRq();
    req.setMsisdn("62819123456");
    req.setPin("x1234");

    mockMvc.perform(
        post("/api/transaction/summary")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
        .andExpectAll(status().isUnauthorized()).andDo(result -> {

          GenericResponse response = objectMapper.readValue(
              result.getResponse().getContentAsString(),
              new TypeReference<GenericResponse>() {
              });

          assertEquals("01", response.getCode());
          assertEquals("Unauthorized", response.getStatus());
          assertEquals("Invalid Pin", response.getMessage());
          assertEquals(null, response.getData());
        });
  }

  @ParameterizedTest
  @SuppressWarnings({ "rawtypes" })
  @CsvSource({ ",", "112233,", ",11234", })
  void getTransactionSummaryBadRequest(@Nullable String msisdn, @Nullable String pin) throws Exception {
    GetMonthlyTransactionRq req = new GetMonthlyTransactionRq();

    mockMvc.perform(
        post("/api/transaction/summary")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
        .andExpectAll(status().isBadRequest()).andDo(result -> {

          GenericResponse response = objectMapper.readValue(
              result.getResponse().getContentAsString(),
              new TypeReference<GenericResponse>() {
              });

          assertEquals("01", response.getCode());
          assertEquals(null, response.getData());
          assertNotEquals(null, response.getMessage());
          assertEquals("Bad Request", response.getStatus());
        });
  }
}
