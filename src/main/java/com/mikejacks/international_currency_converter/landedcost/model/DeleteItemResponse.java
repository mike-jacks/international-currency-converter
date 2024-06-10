package com.mikejacks.international_currency_converter.landedcost.model;

import java.util.UUID;

/**
 * Record class representing the response of a delete item operation.
 *
 * <p>The {@code DeleteItemResponse} record encapsulates the result of a delete operation,
 * providing information about the success of the operation, a message detailing the result,
 * and the ID of the deleted item, if applicable.</p>
 *
 * @param success       Indicates whether the delete operation was successful.
 * @param message       A message detailing the result of the delete operation.
 * @param deletedItemId The UUID of the deleted item, if the operation was successful.
 */
public record DeleteItemResponse(Boolean success, String message, UUID deletedItemId) {
}
