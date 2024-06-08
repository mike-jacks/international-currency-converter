package com.mikejacks.international_currency_converter.landedcost.datafetcher.mutation;

import com.mikejacks.international_currency_converter.landedcost.entity.Product;
import com.mikejacks.international_currency_converter.landedcost.model.DeleteItemResponse;
import com.mikejacks.international_currency_converter.landedcost.model.ProductCreateInput;
import com.mikejacks.international_currency_converter.landedcost.model.ProductUpdateInput;
import com.mikejacks.international_currency_converter.landedcost.service.ProductService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@DgsComponent
public class ProductMutationDateFetcher {

    private final ProductService productService;

    @Autowired
    public ProductMutationDateFetcher(ProductService productService) {
        this.productService = productService;
    }

    @DgsMutation
    public Product addProduct(@InputArgument ProductCreateInput product) {
        return productService.addProduct(product);
    }

    @DgsMutation
    public Product updateProduct(@InputArgument UUID productId, @InputArgument String name, @InputArgument ProductUpdateInput product) {
        return productService.updateProduct(productId, name, product);
    }

    @DgsMutation
    public Product updateProductById(@InputArgument UUID productId, @InputArgument ProductUpdateInput product) {
        return productService.updateProduct(productId, null, product);
    }

    @DgsMutation
    public Product updateProductByName(@InputArgument String name, @InputArgument ProductUpdateInput product) {
        return productService.updateProduct(null, name, product);
    }

    @DgsMutation
    public DeleteItemResponse deleteProductById(@InputArgument UUID productId) {
        return productService.deleteProductById(productId);
    }
}
