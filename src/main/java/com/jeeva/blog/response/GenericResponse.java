package com.jeeva.blog.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GenericResponse<T>{
        private List<T> content;

        private int pageNo;

        private int pageSize;

        private long totalElements;

        private int totalPages;

        private boolean last;
}
