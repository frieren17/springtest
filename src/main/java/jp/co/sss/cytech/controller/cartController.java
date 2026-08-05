package jp.co.sss.cytech.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.cytech.entity.Cart;
import jp.co.sss.cytech.entity.Product;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.repository.CartRepository;
import jp.co.sss.cytech.repository.ProductRepository;
import jp.co.sss.cytech.service.LoginUserDetails;

@Controller
public class cartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public cartController(CartRepository cartRepository,
                          ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/cart/add/{id}")
    public String addCart(
            @PathVariable Integer id,
            @AuthenticationPrincipal LoginUserDetails loginUser) {

        User user = loginUser.getUser();

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return "redirect:/";
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(1);

        cartRepository.save(cart);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(
            @AuthenticationPrincipal LoginUserDetails loginUser,
            Model model) {

        List<Cart> cartList =
                cartRepository.findByUser_UserId(
                        loginUser.getUser().getUserId());

        model.addAttribute("cartList", cartList);

        return "checkoutDetail";
    }

}