package br.dev.ulk.animalz.application.v1.payloads.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
    private List<ApiSubError> details;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ApiSubError {
        private String field;
        private String message;
    }

}
