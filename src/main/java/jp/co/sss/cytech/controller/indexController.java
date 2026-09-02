package jp.co.sss.cytech.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.cytech.entity.Category;
import jp.co.sss.cytech.entity.Order;
import jp.co.sss.cytech.entity.OrderItem;
import jp.co.sss.cytech.entity.Product;
import jp.co.sss.cytech.entity.Review;
import jp.co.sss.cytech.entity.SalesItem;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.form.LoginForm;
import jp.co.sss.cytech.repository.CategoryRepository;
import jp.co.sss.cytech.repository.OrderItemRepository;
import jp.co.sss.cytech.repository.OrderRepository;
import jp.co.sss.cytech.repository.ProductRepository;
import jp.co.sss.cytech.repository.ReviewRepository;
import jp.co.sss.cytech.repository.SalesItemRepository;
import jp.co.sss.cytech.repository.UserRepository;


@Controller
public class indexController {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    SalesItemRepository salesItemRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;
    
    

    @Autowired
    private PasswordEncoder passwordEncoder;


    /*
     * =========================
     * ログイン関係
     * =========================
     */

    @RequestMapping(
        path = "/login",
        method = RequestMethod.GET
    )
    public String login() {

        return "loginOnSession";
    }


    @RequestMapping(
        path = "/doLogin",
        method = RequestMethod.GET
    )
    public String doLoginGet(Integer userId) {

        System.out.println("ユーザーID:" + userId);

        return "login";
    }


    @RequestMapping(
        path = "/loginUsingForm",
        method = RequestMethod.GET
    )
    public String loginUsingForm() {

        return "loginUsingForm";
    }


    @RequestMapping(
        path = "/doLoginUsingForm",
        method = RequestMethod.POST
    )
    public String doLoginUsingForm(LoginForm form) {

        System.out.println(
            "ユーザーID：" + form.getUserId()
        );

        System.out.println(
            "ユーザーID：" + form.getPassword()
        );

        return "loginUsingForm";
    }


    @RequestMapping(
        path = "/loginOnRequest",
        method = RequestMethod.GET
    )
    public String doLoginOnRequest() {

        return "loginOnRequest";
    }


    @RequestMapping(
        path = "/doLoginOnRequest",
        method = RequestMethod.POST
    )
    public String doLoginOnRequest(
            LoginForm form,
            Model model) {

        model.addAttribute(
            "userId",
            form.getUserId()
        );

        return "loginOnRequest";
    }


    @RequestMapping(
        path = "/loginOnSession",
        method = RequestMethod.GET
    )
    public String logOnSession() {

        return "loginOnSession";
    }


    @RequestMapping(
        path = "/logout",
        method = RequestMethod.GET
    )
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }


    /*
     * =========================
     * トップページ
     * =========================
     */

//    @RequestMapping(
//        path = "/",
//        method = RequestMethod.GET
//    )
//    public String index(Model model) {
//
//        System.out.println("index page");
//
//        List<Product> products =
//                productRepository.findAll();
//
//        model.addAttribute(
//            "products",
//            products
//        );
//
//        return "index";
//    }
    
    @RequestMapping(
            path = "/",
            method = RequestMethod.GET
    )
    public String index(Model model) {

        System.out.println("index page");

        // 現在日時
        LocalDateTime now = LocalDateTime.now();

        // 現在有効なセール商品を取得
        List<SalesItem> salesItems =
                salesItemRepository
                    .findByStartMonthLessThanEqualAndEndMonthGreaterThanEqual(
                        now,
                        now
                    );

        /*
         * カテゴリごとにセール商品をまとめる
         *
         * key   = カテゴリID
         * value = そのカテゴリのセール商品
         */
        java.util.Map<Integer, List<SalesItem>> salesByCategory =
                new java.util.LinkedHashMap<>();

        for (SalesItem salesItem : salesItems) {

            // セール商品に紐づく商品を取得
            Product product =
                    productRepository
                        .findById(salesItem.getProductId())
                        .orElse(null);

            // 商品が存在しなければスキップ
            if (product == null) {
                continue;
            }

            // 商品のカテゴリID
            Integer categoryId =
                    product.getCategoryId();

            // カテゴリIDがなければスキップ
            if (categoryId == null) {
                continue;
            }

            // カテゴリごとにセール商品を追加
            salesByCategory
                .computeIfAbsent(
                    categoryId,
                    k -> new java.util.ArrayList<>()
                )
                .add(salesItem);
        }

        /*
         * カテゴリ一覧
         *
         * header.htmlで使用するため、
         * List<Category> のまま渡す
         */
        List<Category> categories =
                categoryRepository.findAll();

        /*
         * index.htmlでカテゴリIDから
         * Categoryを取得するためのMap
         */
        java.util.Map<Integer, Category> categoryMap =
                new java.util.LinkedHashMap<>();

        for (Category category : categories) {

            categoryMap.put(
                category.getCategoryId(),
                category
            );
        }

        /*
         * HTMLへ渡す
         */
        model.addAttribute(
            "salesByCategory",
            salesByCategory
        );

        model.addAttribute(
            "categories",
            categories
        );

        model.addAttribute(
            "categoryMap",
            categoryMap
        );

        return "index";
    }







    /*
     * =========================
     * 商品詳細
     * =========================
     */

    @RequestMapping(
        path = "/product/{id}",
        method = RequestMethod.GET
    )
    public String showDetail(
            @PathVariable("id") int id,
            Model model) {

        Product product =
                productRepository
                    .findById(id)
                    .orElse(null);

        /*
         * 商品が存在しない場合
         */
        if (product == null) {
            return "redirect:/";
        }

        /*
         * 商品に紐づくレビュー取得
         */
        List<Review> reviews =
                reviewRepository
                    .findByProduct_ProductId((long) id);

        model.addAttribute(
            "product",
            product
        );

        model.addAttribute(
            "reviews",
            reviews
        );
        
        List<Category> categories =
                categoryRepository.findAll();

        model.addAttribute(
            "categories",
            categories
        );


        return "productDetail";
    }


    /*
     * =========================
     * 商品一覧
     * =========================
     */

    @RequestMapping(
        path = "/product/list",
        method = RequestMethod.GET
    )
    public String productList(Model model) {

        List<Product> products =
                productRepository.findAll();

        model.addAttribute(
            "products",
            products
        );
        
        List<Category> categories =
                categoryRepository.findAll();

        model.addAttribute(
            "categories",
            categories
        );

        return "productList";
    }


    /*
     * =========================
     * 商品検索
     * =========================
     */

    @RequestMapping(
        path = "/product/search",
        method = RequestMethod.GET
    )
    public String search(
            String keyword,
            Integer categoryId,
            Model model) {

        List<Product> products;

        /*
         * 商品名とカテゴリの両方を指定した場合
         */
        if (keyword != null
                && !keyword.isBlank()
                && categoryId != null) {

            products =
                productRepository
                    .findByProductNameContainingAndCategoryId(
                        keyword,
                        categoryId
                    );
        }

        /*
         * 商品名だけ指定した場合
         */
        else if (keyword != null
                && !keyword.isBlank()) {

            products =
                productRepository
                    .findByProductNameContaining(
                        keyword
                    );
        }

        /*
         * カテゴリだけ指定した場合
         */
        else if (categoryId != null) {

            products =
                productRepository
                    .findByCategoryId(
                        categoryId
                    );
        }

        /*
         * 何も指定しない場合
         */
        else {

            products =
                productRepository.findAll();
        }

        model.addAttribute(
            "products",
            products
        );
        
        List<Category> categories =
                categoryRepository.findAll();

        model.addAttribute(
            "categories",
            categories
        );

        return "productList";
    }


    /*
     * ==================================================
     * 購入処理
     * ==================================================
     */


    /*
     * =========================
     * 購入品詳細画面
     * =========================
     *
     * 商品詳細画面の
     * 「単品購入」から呼ばれる
     */
    @RequestMapping(
        path = "/purchase/{id}",
        method = RequestMethod.GET
    )
    public String purchase(
            @PathVariable("id") int id,
            Integer quantity,
            Model model,
            Authentication authentication) {

        /*
         * 商品取得
         */
        Product product =
                productRepository
                    .findById(id)
                    .orElse(null);

        /*
         * 商品が存在しない場合
         */
        if (product == null) {
            return "redirect:/";
        }

        /*
         * 数量が指定されていない場合は1個
         */
        if (quantity == null || quantity < 1) {
            quantity = 1;
        }

        /*
         * 在庫以上の数量を指定した場合
         */
        if (quantity > product.getStock()) {
            quantity = product.getStock();
        }

        /*
         * ログインユーザーのメールアドレスを取得
         */
        String email =
                authentication.getName();

        /*
         * ユーザーを取得
         */
        User user =
                userRepository.findByEmail(email);

        /*
         * ユーザーが存在しない場合
         */
        if (user == null) {
            return "redirect:/login";
        }

        /*
         * purchaseDetail.htmlに渡す
         */
        model.addAttribute(
            "product",
            product
        );

        model.addAttribute(
            "quantity",
            quantity
        );

        model.addAttribute(
            "user",
            user
        );

        /*
         * 購入品詳細画面
         */
        return "purchaseDetail";
    }


    /*
     * =========================
     * 購入確認画面
     * =========================
     */
    @RequestMapping(
        path = "/purchase/confirm",
        method = RequestMethod.POST
    )
    public String purchaseConfirm(
            Integer productId,
            Integer quantity,
            String deliveryAddress,
            String paymentMethod,
            Model model) {

        /*
         * 商品取得
         */
        Product product =
                productRepository
                    .findById(productId)
                    .orElse(null);

        /*
         * 商品が存在しない場合
         */
        if (product == null) {
            return "redirect:/";
        }

        /*
         * 数量チェック
         */
        if (quantity == null || quantity < 1) {
            quantity = 1;
        }

        /*
         * 在庫チェック
         */
        if (quantity > product.getStock()) {

            return "redirect:/purchase/"
                    + productId
                    + "?quantity="
                    + product.getStock();
        }

        /*
         * 選択内容をpurchaseConfirm.htmlへ渡す
         */
        model.addAttribute(
            "product",
            product
        );

        model.addAttribute(
            "quantity",
            quantity
        );

        model.addAttribute(
            "deliveryAddress",
            deliveryAddress
        );

        model.addAttribute(
            "paymentMethod",
            paymentMethod
        );

        /*
         * 購入確認画面
         */
        return "purchaseConfirm";
    }
    
    @RequestMapping(path = "/purchase/complete", method = RequestMethod.POST)
    @Transactional
    public String purchaseComplete(
            Integer productId,
            Integer quantity,
            Authentication authentication,
            Model model) {

        // ログインしているユーザーのメールアドレスを取得
        String email = authentication.getName();

        // ユーザー情報を取得
        User user = userRepository.findByEmail(email);

        // 商品情報を取得
        Product product = productRepository.findById(productId)
                .orElse(null);

        // 商品が存在しない場合
        if (product == null) {
            return "redirect:/";
        }

        // 数量チェック
        if (quantity == null || quantity <= 0) {
            return "redirect:/product/" + productId;
        }

        // 在庫チェック
        if (product.getStock() < quantity) {
            return "redirect:/product/" + productId;
        }

        // 税込み合計金額
        int totalAmount = product.getTaxPrice() * quantity;


        // =========================
        // orders に登録
        // =========================

        Order order = new Order();

        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setStatus("注文受付");

        orderRepository.save(order);


        // =========================
        // order_items に登録
        // =========================

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);

        // 購入時点の税込価格を保存
        orderItem.setPrice(product.getTaxPrice());

        orderItemRepository.save(orderItem);


        // =========================
        // 商品の在庫を減らす
        // =========================

        product.setStock(product.getStock() - quantity);

        productRepository.save(product);


        // =========================
        // 購入完了画面へ渡す
        // =========================

        model.addAttribute("product", product);
        model.addAttribute("quantity", quantity);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("user", user);

        return "purchaseComplete";
    }

}

//package jp.co.sss.cytech.controller;
//import java.util.List;
//
//import jakarta.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import jp.co.sss.cytech.entity.Product;
//import jp.co.sss.cytech.entity.Review;
//import jp.co.sss.cytech.entity.User;
//import jp.co.sss.cytech.form.LoginForm;
//import jp.co.sss.cytech.repository.ProductRepository;
//import jp.co.sss.cytech.repository.ReviewRepository;
//import jp.co.sss.cytech.repository.UserRepository;
//
//@Controller
//public class indexController {
//	
//	@Autowired
//    ProductRepository productRepository;
//	@Autowired
//	ReviewRepository reviewRepository;
//	@Autowired
//	UserRepository userRepository;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	@RequestMapping(path = "/login", method = RequestMethod.GET)
//	public String login() {
//	    return "loginOnSession";
//	}
//	
//	@RequestMapping(path = "/doLogin", method = RequestMethod.GET)
//	public String doLoginGet(Integer userId) {
//		System.out.println("ユーザーID:" + userId);
//		return "login";
//	}
//		
//	@RequestMapping(path = "/loginUsingForm", method = RequestMethod.GET)
//	public String loginUsingForm() {
//		return "loginUsingForm";
//	}
//	
//	@RequestMapping(path = "/doLoginUsingForm", method = RequestMethod.POST)
//	public String doLoginUsingForm(LoginForm form) {
//		System.out.println("ユーザーID：" + form.getUserId());
//		System.out.println("ユーザーID：" + form.getPassword());
//		return "loginUsingForm";
//	}
//	
//	@RequestMapping(path = "/loginOnRequest", method = RequestMethod.GET)
//	public String doLoginOnRequest() {
//		return "loginOnRequest";
//	}
//	
//	@RequestMapping(path = "/doLoginOnRequest", method = RequestMethod.POST)
//	public String doLoginOnRequest(LoginForm form, Model model) {
//		model.addAttribute("userId", form.getUserId());
//		return "loginOnRequest";
//	}
//	
//	@RequestMapping(path = "/loginOnSession" , method = RequestMethod.GET)
//	public String logOnSession() {
//		return "loginOnSession";
//	}
//	
//	@RequestMapping(path = "/logout" , method = RequestMethod.GET)
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}
//	
//	@RequestMapping(path = "/", method = RequestMethod.GET)
//	public String index(Model model) {
//		System.out.println("index page");
//		List<Product> products = productRepository.findAll();
//        model.addAttribute("products", products);
//	    return "index";
//	}
//	
//	@RequestMapping(path = "/product/{id}", method = RequestMethod.GET)
//	public String showDetail(@PathVariable("id") int id, Model model) {
//
//	    Product product = productRepository.findById(id).orElse(null);
//	    
//	    /*
//         * 商品が存在しない場合
//         */
//        if (product == null) {
//            return "redirect:/";
//        }
//
//	    // 商品に紐づくレビュー取得
//	    List<Review> reviews = reviewRepository.findByProduct_ProductId((long) id);
//
//	    model.addAttribute("product", product);
//
//	    // reviewsをHTMLへ渡す
//	    model.addAttribute("reviews", reviews);
//
//	    return "productDetail";
//	}
//	
//	@RequestMapping(path = "/purchase/{id}", method = RequestMethod.GET)
//	public String purchase(
//	        @PathVariable("id") int id,
//	        Integer quantity,
//	        Model model,
//	        Authentication authentication) {
//
//	    // 商品取得
//	    Product product =
//	            productRepository.findById(id).orElse(null);
//
//	    // 商品が存在しない場合
//	    if (product == null) {
//	        return "redirect:/";
//	    }
//
//	    // 数量が指定されていなければ1個
//	    if (quantity == null || quantity < 1) {
//	        quantity = 1;
//	    }
//
//	    // 在庫以上の数量が指定された場合
//	    if (quantity > product.getStock()) {
//	        quantity = product.getStock();
//	    }
//	    
//	    /*
//         * ログインユーザーの情報を取得
//         *
//         * authentication.getName()
//         * ↓
//         * ログイン時に使用している
//         * ユーザー情報
//         */
//        String email =
//                authentication.getName();
//
//
//        /*
//         * UserRepositoryからユーザー取得
//         */
//        User user =
//                userRepository.findByEmail(email);
//
//
//        /*
//         * ユーザーが存在しない場合
//         */
//        if (user == null) {
//            return "redirect:/login";
//        }
//
//
//	    model.addAttribute("product", product);
//	    model.addAttribute("quantity", quantity);
//	    model.addAttribute("user", user);
//
//	    /*
//	     * ログインユーザーの情報を取得
//	     * authentication.getName() は通常ログイン時の
//	     * ユーザー名やメールアドレスになります。
//	     */
////	    String loginUser = authentication.getName();
////
////	    model.addAttribute("loginUser", loginUser);
//
//	    return "purchaseDetail";
//	    
//	    /*
//	     * =========================
//	     * 購入確認画面
//	     * =========================
//	     *
//	     * purchaseDetail.htmlから
//	     * POSTされる
//	     */
//	    @RequestMapping(path = "/purchase/confirm", method = RequestMethod.POST)
//	    public String purchaseConfirm(
//	            Integer productId,
//	            Integer quantity,
//	            String deliveryAddress,
//	            String paymentMethod,
//	            Model model) {
//
//	        /*
//	         * 商品取得
//	         */
//	        Product product = productRepository .findById(productId) .orElse(null);
//
//	        /*
//	         * 商品が存在しない場合
//	         */
//	        if (product == null) {
//	            return "redirect:/";
//	        }
//
//
//	        /*
//	         * 数量チェック
//	         */
//	        if (quantity == null || quantity < 1) {
//	            quantity = 1;
//	        }
//
//
//	        /*
//	         * 在庫チェック
//	         */
//	        if (quantity > product.getStock()) {
//	            return "redirect:/purchase/"
//	                    + productId
//	                    + "?quantity="
//	                    + product.getStock();
//	        }
//
//
//	        /*
//	         * HTMLに渡す
//	         */
//	        model.addAttribute("product", product);
//
//	        model.addAttribute("quantity", quantity);
//
//	        model.addAttribute("deliveryAddress", deliveryAddress);
//
//	        model.addAttribute("paymentMethod", paymentMethod);
//
//	        /*
//	         * purchaseConfirm.htmlへ
//	         */
//	        return "purchaseConfirm";
//	    }
//	}
//	
//	@RequestMapping(path = "/product/list", method = RequestMethod.GET)
//	public String productList(Model model) {
//
//	    List<Product> products = productRepository.findAll();
//
//	    model.addAttribute("products", products);
//
//	    return "productList";
//	}
//	
//	@RequestMapping(path = "/product/search", method = RequestMethod.GET)
//	public String search(
//	        String keyword,
//	        Integer categoryId,
//	        Model model) {
//
//	    List<Product> products;
//
//	    // 商品名とカテゴリの両方を指定した場合
//	    if (keyword != null && !keyword.isBlank()
//	            && categoryId != null) {
//
//	        products = productRepository
//	                .findByProductNameContainingAndCategoryId(keyword, categoryId);
//
//	    }
//	    // 商品名だけ指定した場合
//	    else if (keyword != null && !keyword.isBlank()) {
//
//	        products = productRepository.findByProductNameContaining(keyword);
//
//	    }
//	    // カテゴリだけ指定した場合
//	    else if (categoryId != null) {
//
//	        products = productRepository.findByCategoryId(categoryId);
//
//	    }
//	    // 何も指定しない場合
//	    else {
//
//	        products = productRepository.findAll();
//
//	    }
//
//	    model.addAttribute("products", products);
//
//	    return "productList";
//	}
//	
//}
