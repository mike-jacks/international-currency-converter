package com.mikejacks.international_currency_converter.landedcost.model;

import java.util.UUID;

public record DeleteItemResponse(Boolean success, String message, UUID deletedItemId) {
}
