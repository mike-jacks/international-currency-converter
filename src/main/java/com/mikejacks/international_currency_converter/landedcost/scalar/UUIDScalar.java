package com.mikejacks.international_currency_converter.landedcost.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.util.UUID;

@DgsScalar(name = "UUID")
public class UUIDScalar implements Coercing<UUID, String> {
    @Override
    public String serialize(Object dataFetcherResult) {
        if (dataFetcherResult instanceof UUID) {
            return dataFetcherResult.toString();
        }
        throw new CoercingSerializeException("Unable to serialize object as UUID");
    }

    @Override
    public UUID parseValue(Object input) {
        try {
            return UUID.fromString(input.toString());
        } catch (IllegalArgumentException e) {
            throw new CoercingParseValueException("Unable to parse value as UUID: " + input);
        }
    }

    @Override
    public UUID parseLiteral(Object input) {
        if (input instanceof StringValue) {
            try {
                return UUID.fromString(((StringValue) input).getValue());
            } catch (IllegalArgumentException e) {
                throw new CoercingParseLiteralException("Unable to parse literal as UUID: " + input);
            }
        }
        throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was '" + input.getClass().getSimpleName() + "'.");

    }
}
