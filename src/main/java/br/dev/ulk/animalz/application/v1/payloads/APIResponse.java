package br.dev.ulk.animalz.application.v1.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy - HH:mm:ss")
    private LocalDateTime timeStamp;
    private int statusCode;
    private String message;
    private T payload;

    public APIResponse(String message, T payload) {
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.payload = payload;
    }

}