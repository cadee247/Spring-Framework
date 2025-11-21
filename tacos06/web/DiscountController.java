package tacocloud.tacos06.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacocloud.tacos06.DiscountCodeProps;

/**
 * Controller for displaying discount codes.
 * Loads codes from DiscountCodeProps and passes them to the view.
 */
@Controller
@RequestMapping("/discounts")
public class DiscountController {

    private final DiscountCodeProps discountProps;

    public DiscountController(DiscountCodeProps discountProps) {
        this.discountProps = discountProps;
    }

    @GetMapping
    public String displayDiscountCodes(Model model) {
        Map<String, Integer> codes = discountProps.getCodes();
        model.addAttribute("codes", codes);
        return "discountList";
    }
}