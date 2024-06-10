package com.mikejacks.international_currency_converter.landedcost.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.util.UUID;

/**
 * Custom scalar for handling UUIDs in GraphQL.
 *
 * <p>This class provides the necessary methods to serialize, parse value, and parse literal
 * for UUIDs in a GraphQL schema. It is annotated with {@code @DgsScalar} to indicate that it
 * is a custom scalar for the DGS framework.</p>
 */
@DgsScalar(name = "UUID")
public class UUIDScalar implements Coercing<UUID, String> {

    /**
     * Serializes a UUID object into a string.
     *
     * <p>This method is called when the GraphQL response is being prepared and the UUID needs to be converted
     * into a string format.</p>
     *
     * @param dataFetcherResult The UUID object to be serialized.
     * @return The string representation of the UUID.
     * @throws CoercingSerializeException If the object is not a UUID.
     */
    @Override
    public String serialize(Object dataFetcherResult) {
        if (dataFetcherResult instanceof UUID) {
            return dataFetcherResult.toString();
        }
        throw new CoercingSerializeException("Unable to serialize object as UUID");
    }

    /**
     * Parses a value from a client input into a UUID.
     *
     * <p>This method is called when the GraphQL query is being processed and the input value needs to be
     * converted into a UUID object.</p>
     *
     * @param input The input value to be parsed.
     * @return The parsed UUID object.
     * @throws CoercingParseValueException If the input cannot be parsed into a UUID.
     */
    @Override
    public UUID parseValue(Object input) {
        try {
            return UUID.fromString(input.toString());
        } catch (IllegalArgumentException e) {
            throw new CoercingParseValueException("Unable to parse value as UUID: " + input);
        }
    }

    /**
     * Parses a literal value from a GraphQL query into a UUID.
     *
     * <p>This method is called when the GraphQL query is being processed and a literal value (e.g., a hardcoded
     * string in the query) needs to be converted into a UUID object.</p>
     *
     * @param input The input literal to be parsed.
     * @return The parsed UUID object.
     * @throws CoercingParseLiteralException If the literal cannot be parsed into a UUID.
     */
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
