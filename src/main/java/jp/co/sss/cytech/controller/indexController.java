package jp.co.sss.cytech.controller;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.cytech.entity.Product;
import jp.co.sss.cytech.entity.Review;
import jp.co.sss.cytech.form.LoginForm;
import jp.co.sss.cytech.repository.ProductRepository;
import jp.co.sss.cytech.repository.ReviewRepository;
import jp.co.sss.cytech.repository.UserRepository;

@Controller
public class indexController {
	
	@Autowired
    ProductRepository productRepository;
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
	    return "loginOnSession";
	}
	
	@RequestMapping(path = "/doLogin", method = RequestMethod.GET)
	public String doLoginGet(Integer userId) {
		System.out.println("ユーザーID:" + userId);
		return "login";
	}
		
	@RequestMapping(path = "/loginUsingForm", method = RequestMethod.GET)
	public String loginUsingForm() {
		return "loginUsingForm";
	}
	
	@RequestMapping(path = "/doLoginUsingForm", method = RequestMethod.POST)
	public String doLoginUsingForm(LoginForm form) {
		System.out.println("ユーザーID：" + form.getUserId());
		System.out.println("ユーザーID：" + form.getPassword());
		return "loginUsingForm";
	}
	
	@RequestMapping(path = "/loginOnRequest", method = RequestMethod.GET)
	public String doLoginOnRequest() {
		return "loginOnRequest";
	}
	
	@RequestMapping(path = "/doLoginOnRequest", method = RequestMethod.POST)
	public String doLoginOnRequest(LoginForm form, Model model) {
		model.addAttribute("userId", form.getUserId());
		return "loginOnRequest";
	}
	
	@RequestMapping(path = "/loginOnSession" , method = RequestMethod.GET)
	public String logOnSession() {
		return "loginOnSession";
	}
	
	@RequestMapping(path = "/logout" , method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(Model model) {
		System.out.println("index page");
		List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
	    return "index";
	}
	
	@RequestMapping(path = "/product/{id}", method = RequestMethod.GET)
	public String showDetail(@PathVariable("id") int id, Model model) {

	    Product product = productRepository.findById(id).orElse(null);

	    // 商品に紐づくレビュー取得
	    List<Review> reviews = reviewRepository.findByProduct_ProductId((long) id);

	    model.addAttribute("product", product);

	    // reviewsをHTMLへ渡す
	    model.addAttribute("reviews", reviews);

	    return "productDetail";
	}
	
	@RequestMapping(path = "/product/list", method = RequestMethod.GET)
	public String productList(Model model) {

	    List<Product> products = productRepository.findAll();

	    model.addAttribute("products", products);

	    return "productList";
	}
	
	@RequestMapping(path = "/product/search", method = RequestMethod.GET)
	public String search(
	        String keyword,
	        Integer categoryId,
	        Model model) {

	    List<Product> products;

	    // 商品名とカテゴリの両方を指定した場合
	    if (keyword != null && !keyword.isBlank()
	            && categoryId != null) {

	        products = productRepository
	                .findByProductNameContainingAndCategoryId(keyword, categoryId);

	    }
	    // 商品名だけ指定した場合
	    else if (keyword != null && !keyword.isBlank()) {

	        products = productRepository.findByProductNameContaining(keyword);

	    }
	    // カテゴリだけ指定した場合
	    else if (categoryId != null) {

	        products = productRepository.findByCategoryId(categoryId);

	    }
	    // 何も指定しない場合
	    else {

	        products = productRepository.findAll();

	    }

	    model.addAttribute("products", products);

	    return "productList";
	}
	
	@RequestMapping(path = "/cart/add", method = RequestMethod.POST)
	public String addCart(
	        @RequestParam("productId") Integer productId,
	        @RequestParam("quantity") Integer quantity,
	        RedirectAttributes redirectAttributes) {

	    // 商品取得
	    Product product =
	            productRepository.findById(productId)
	                    .orElse(null);

	    // 商品が存在しない場合
	    if (product == null) {
	        redirectAttributes.addFlashAttribute(
	                "errorMessage",
	                "商品が存在しません。"
	        );

	        return "redirect:/product/list";
	    }

	    // 数量チェック
	    if (quantity == null || quantity <= 0) {

	        redirectAttributes.addFlashAttribute(
	                "errorMessage",
	                "数量が正しくありません。"
	        );

	        return "redirect:/product/" + productId;
	    }

	    // 在庫チェック
	    if (quantity > product.getStock()) {

	        redirectAttributes.addFlashAttribute(
	                "errorMessage",
	                "在庫が不足しています。"
	        );

	        return "redirect:/product/" + productId;
	    }

	    // ここでカートへ追加
	    // ↓↓↓
	    // Cart処理をここに入れる
	    // ↑↑↑

	    return "redirect:/cart";
	}
}
