package tacos.tacos02;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Digits;
import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer's taco order, including delivery and payment details.
 */
@Data
@NoArgsConstructor
public class TacoOrder {

    /** Full name of the person receiving the delivery */
    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    /** Street address for delivery */
    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    /** City for delivery */
    @NotBlank(message = "City is required")
    private String deliveryCity;

    /** State or province for delivery */
    @NotBlank(message = "State is required")
    private String deliveryState;

    /** Postal code for delivery */
    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    /** Credit card number for payment */
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    /** Credit card expiration date in MM/YY format */
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    /** 3-digit CVV code */
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    /** List of tacos included in the order */
    private List<Taco> tacos;

    /**
     * Adds a taco to the order.
     * @param taco the taco to add
     */
    public void addTaco(Taco taco) {
        if (this.tacos == null) {
            this.tacos = new ArrayList<>();
        }
        this.tacos.add(taco);
    }

    /**
     * Gets the list of tacos, initializing if null.
     * @return list of tacos
     */
    public List<Taco> getTacos() {
        if (this.tacos == null) {
            this.tacos = new ArrayList<>();
        }
        return this.tacos;
    }
}