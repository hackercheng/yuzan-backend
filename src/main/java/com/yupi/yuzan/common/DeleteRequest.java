package com.yupi.yuzan.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {
    private Long id;

    private static final long serialVersion = 1L;
}
