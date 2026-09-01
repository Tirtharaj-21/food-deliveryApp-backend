package foodDelivery.app.util;

public class SortColumnMapper {
    private SortColumnMapper() {
        // utility class, no instances
    }

    public static String toColumnName(String field) {
        return switch (field) {
            case "deliveryTime" -> "delivery_time";
            case "deliveryFee" -> "delivery_fee";
            case "createdAt" -> "created_at";
            default -> field; // id, name, rating are already same in both
        };
    }
}
