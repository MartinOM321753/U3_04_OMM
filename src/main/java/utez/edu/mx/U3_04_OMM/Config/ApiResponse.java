package utez.edu.mx.U3_04_OMM.Config;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@NoArgsConstructor
@Transactional(rollbackFor = Exception.class)

public class ApiResponse {

    private Object data;
    private HttpStatus status;
    private String message;
    private boolean error;

    public ApiResponse(Object object, HttpStatus status, String message, boolean error) {
        this.data = object;
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public ApiResponse(Object object, HttpStatus status, String message) {
        this.data = object;
        this.status = status;
        this.message = message;
    }

}