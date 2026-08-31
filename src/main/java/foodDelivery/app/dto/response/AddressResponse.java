package foodDelivery.app.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
    private Long id;

    private String label;

    private String addressLine;

    private String city;

    private String state;

    private String pincode;

    private Double latitude;

    private Double longitude;
}
