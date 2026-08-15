package jp.co.sss.cytech.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    public cartController(
            CartRepository cartRepository,
            ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/cart/add/{id}")
    public String addCart(
            @PathVariable Integer id,
            @RequestParam("quantity") Integer quantity,
            @AuthenticationPrincipal LoginUserDetails loginUser) {

        User user = loginUser.getUser();

        // 商品を取得
        Product product =
                productRepository.findById(id).orElse(null);

        if (product == null) {
            return "redirect:/";
        }

        // 数量チェック
        if (quantity == null || quantity <= 0) {
            return "redirect:/product/" + id;
        }

        // 在庫チェック
        if (quantity > product.getStock()) {
            return "redirect:/product/" + id;
        }

        // 同じ商品がカートにあるか確認
        Optional<Cart> optionalCart =
                cartRepository.findByUser_UserIdAndProduct_ProductId(
                        user.getUserId(),
                        product.getProductId()
                );

        if (optionalCart.isPresent()) {

            // すでにカートにある場合
            Cart cart = optionalCart.get();

            int newQuantity =
                    cart.getQuantity() + quantity;

            // 合計数量が在庫を超える場合
            if (newQuantity > product.getStock()) {
                return "redirect:/product/" + id;
            }

            cart.setQuantity(newQuantity);

            cartRepository.save(cart);

        } else {

            // 初めてカートに入れる場合
            Cart cart = new Cart();

            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);

            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(
            @AuthenticationPrincipal LoginUserDetails loginUser,
            Model model) {

        List<Cart> cartList =
                cartRepository.findByUser_UserId(
                        loginUser.getUser().getUserId()
                );

        model.addAttribute("cartList", cartList);

        return "checkout";
    }
    
    @PostMapping("/cart/update")
    public String updateCart(
            @RequestParam("cartId") Integer cartId,
            @RequestParam("quantity") Integer quantity,
            @AuthenticationPrincipal LoginUserDetails loginUser) {

        // カートを取得
        Cart cart = cartRepository.findById(cartId).orElse(null);

        // カートが存在しない場合
        if (cart == null) {
            return "redirect:/cart";
        }

        // ログインユーザーのカートか確認
        if (!cart.getUser().getUserId()
                .equals(loginUser.getUser().getUserId())) {

            return "redirect:/cart";
        }

        // 数量チェック
        if (quantity == null || quantity <= 0) {
            return "redirect:/cart";
        }

        // 商品の在庫数を取得
        Product product = cart.getProduct();

        // 在庫を超えていないか確認
        if (quantity > product.getStock()) {
            return "redirect:/cart";
        }

        // 数量を更新
        cart.setQuantity(quantity);

        cartRepository.save(cart);

        return "redirect:/cart";
    }
    
    @PostMapping("/cart/delete")
    public String deleteCart(
            @RequestParam("cartId") Integer cartId,
            @AuthenticationPrincipal LoginUserDetails loginUser) {

        // カートを取得
        Cart cart = cartRepository.findById(cartId).orElse(null);

        // カートが存在しない場合
        if (cart == null) {
            return "redirect:/cart";
        }

        // ログインユーザーのカートか確認
        if (!cart.getUser().getUserId()
                .equals(loginUser.getUser().getUserId())) {

            return "redirect:/cart";
        }

        // カートから削除
        cartRepository.delete(cart);

        return "redirect:/cart";
    }
}