package com.sample.retailstore.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author irfan.nagoo
 */
public class CurrencySerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        jsonGenerator.writeString(numberFormat.format(bigDecimal));
    }
}
