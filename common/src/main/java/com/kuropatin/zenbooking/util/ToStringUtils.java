package com.kuropatin.zenbooking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuropatin.zenbooking.exception.ApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ToStringUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonString(final Object object) {
        final DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter().withIndent("  ");
        final DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);
        try {
            return objectMapper.writer(printer).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(e);
        }
    }
}
