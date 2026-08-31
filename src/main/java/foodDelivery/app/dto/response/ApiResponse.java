package foodDelivery.app.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse <T>{
    private int statusCode;
    private boolean success;
    private String message;
    private T data;
}
