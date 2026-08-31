package foodDelivery.app.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Boolean isActive;
}
