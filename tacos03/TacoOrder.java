package tacos.tacos03;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date placedAt;

    // 🛵 Delivery Info
    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Use 2-letter province code (e.g. GP)")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    // 💳 Payment Info
    @Pattern(regexp = "^(\\d{4} ?){4}$", message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])/([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    // 🌮 Taco Items
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    // 🧼 Optional: Normalize credit card input before saving
    public void normalizeCardNumber() {
        if (ccNumber != null) {
            ccNumber = ccNumber.replaceAll("\\s+", "");
        }
    }
}