package com.mikejacks.international_currency_converter.landedcost.model;

/**
 * Model class representing the landed cost of a product.
 *
 * <p>The {@code LandedCost} class encapsulates the total cost associated with a product,
 * including all relevant fees, duties, and taxes. It provides getter and setter methods
 * for accessing and modifying the total cost.</p>
 */
public class LandedCost {
    private Double totalCost;

    /**
     * Default constructor.
     */
    public LandedCost() {

    }

    /**
     * Constructs a new {@code LandedCost} with the specified total cost.
     *
     * @param totalCost The total cost of the product.
     */
    public LandedCost(Double totalCost) {
        this.setTotalCost(totalCost);
    }

    /**
     * Gets the total cost of the product.
     *
     * @return The total cost of the product.
     */
    public Double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the total cost of the product.
     *
     * @param totalCost The new total cost of the product.
     */
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}

