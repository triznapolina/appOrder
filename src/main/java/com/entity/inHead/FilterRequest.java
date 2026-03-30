package com.entity.inHead;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterRequest {

    private int page;
    private int size;


}
