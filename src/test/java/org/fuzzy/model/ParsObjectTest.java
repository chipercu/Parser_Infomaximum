package org.fuzzy.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class ParsObjectTest {

    @Test
    public void convertObjectToJson() throws JsonProcessingException {
        final ParsObject parsObject = new ParsObject("group1", "type1", 132132132L, BigInteger.valueOf(545346734523L));
        System.out.println(new ObjectMapper().writeValueAsString(parsObject));

    }




}