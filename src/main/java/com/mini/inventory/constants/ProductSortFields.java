package com.mini.inventory.constants;

import java.util.Set;

public final class ProductSortFields {
    private ProductSortFields() {}

    public static final Set<String> ALLOWED_FIELDS = Set.of(
            "id",
            "name",
            "price",
            "category",
            "createdAt"
    );
}