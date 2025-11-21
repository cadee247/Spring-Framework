package tacocloud.tacos06.web;

import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacocloud.tacos06.TacoOrder;
import tacocloud.tacos06.User;
import tacocloud.tacos06.data.OrderRepository;

/**
 * Handles order-related requests.
 * Uses session attributes to persist TacoOrder across multiple requests.
 * Integrates with Spring Security to associate orders with authenticated users.
 */
@Controller
@RequestMapping("/orders")
@SessionAttributes("order") // Keeps TacoOrder in session until completed
public class OrderController {

    private OrderRepository orderRepo;
    private OrderProps props;

    public OrderController(OrderRepository orderRepo, OrderProps props) {
        this.orderRepo = orderRepo;
        this.props = props;
    }

    /**
     * Displays the order form and pre-fills delivery info from the authenticated user.
     * Mapped to GET /orders/current
     */
    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute TacoOrder order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }

        return "orderForm";
    }

    /**
     * Processes the submitted order form.
     * Validates input, associates the order with the current user, and saves it.
     * Clears the session attribute after successful submission.
     */
    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);
        orderRepo.save(order);
        sessionStatus.setComplete(); // Ends session for "order"

        return "redirect:/";
    }

    /**
     * Displays a paginated list of recent orders for the authenticated user.
     * Uses OrderProps to determine page size.
     */
    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }

  /*
  // Alternate version with hardcoded page size
  @GetMapping
  public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
    Pageable pageable = PageRequest.of(0, 20);
    model.addAttribute("orders",
        orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
    return "orderList";
  }
  */

  /*
  // Non-paginated version (not recommended for large datasets)
  @GetMapping
  public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
    model.addAttribute("orders",
        orderRepo.findByUserOrderByPlacedAtDesc(user));
    return "orderList";
  }
  */
}