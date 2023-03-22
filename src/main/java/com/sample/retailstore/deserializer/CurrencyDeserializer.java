package com.sample.retailstore.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author irfan.nagoo
 */
public class CurrencyDeserializer extends JsonDeserializer<BigDecimal> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyDeserializer.class);

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TextNode node = jsonParser.getCodec().readTree(jsonParser);
        String value = node.asText();
        Number number = 0;
        try {
            number = NumberFormat.getCurrencyInstance().parse(node.asText());
        } catch (ParseException e) {
            LOGGER.error(String.format("The Currency value [$s] is invalid", value), e);
        }
        return new BigDecimal(number.toString());
    }
}
