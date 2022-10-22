package ma.octo.assignement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDto implements Serializable {

    private Integer code;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    public ErrorResponseDto() {timestamp = new Date();}

    public ErrorResponseDto(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = new Date();
    }

}
