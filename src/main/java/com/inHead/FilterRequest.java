package com.inHead;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterRequest {
    private int page;
    private int size;

}
