package com.xyz.bank.controller;

import com.xyz.bank.exception.AccountAlreadyPresent;
import com.xyz.bank.exception.CustomerNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest{

    @Autowired
    private MockMvc mvc;

    @Test
    void getCustomerDetails() throws Exception {
        String customerId = "1";
        mvc.perform(MockMvcRequestBuilders.get("/customer/{id}",customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Dua")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Lipa")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account.balance", Matchers.is(1000.0)));
    }

    @Test
    void getCustomerDetailsError() throws Exception {
        String customerId = "10";
        mvc.perform(MockMvcRequestBuilders.get("/customer/{id}",customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(CustomerNotFoundException.message+customerId)));
    }

    @Test
    void openAccountNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/account").content("{\"customerId\":10,\"initialCredit\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(CustomerNotFoundException.message+10)));
    }

    @Test
    void openAccount() throws Exception {
        String customerId = "3";
        mvc.perform(MockMvcRequestBuilders.post("/account").content("{\"customerId\":3,\"initialCredit\":10}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance", Matchers.is(10.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions.[0].amount", Matchers.is(10.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions.[0].description", Matchers.is("initial credit")));

        mvc.perform(MockMvcRequestBuilders.get("/customer/{id}",customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Shawn")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Mendes")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account.balance", Matchers.is(10.0)));
    }

    @Test
    void accountAlreadyPresent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/account").content("{\"customerId\":1,\"initialCredit\":100}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(AccountAlreadyPresent.message+1)));
    }

    @Test
    void accountWithInvalidData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/account").content("{\"customerId\":0,\"initialCredit\":null}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.containsString("customer Id can not be less than 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.containsString("initial credit can not be null")));
    }
}