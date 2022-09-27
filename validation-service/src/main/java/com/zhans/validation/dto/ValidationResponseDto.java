package com.zhans.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponseDto {

    private Long postId;
    private Boolean isValid;
    private List<BadWord> badWords;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BadWord {

        private String badWord;
        private Integer startIndex;
        private Integer endIndex;
    }
}
