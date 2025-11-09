package com.hardy.SpringEcom.model.dto;

import java.util.List;

public record OrderRequest(
        String name,
        String email,
        List<OrderItemRequest> items)
{

}
