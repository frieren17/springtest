/*package jp.co.sss.cytech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.cytech.dto.ReviewRequest;
import jp.co.sss.cytech.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class reviewController {
	private final ReviewService reviewService;

    public reviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest request) {
        reviewService.createReview(request);
        return ResponseEntity.ok().build();
    }
}*/

package jp.co.sss.cytech.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sss.cytech.dto.ReviewRequest;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.repository.UserRepository;
import jp.co.sss.cytech.service.ReviewService;

@Controller
@RequestMapping("/review")
public class reviewController {

    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/form/{id}")
    public String showForm(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute("productId", id);

        return "reviewForm";
    }

    @PostMapping("/add")
    public String createReview(
            @RequestParam Integer productId,
            @RequestParam(required = false) String dummyUserName,
            @RequestParam Integer rating,
            @RequestParam(required = false) String contactEmail,
            @RequestParam String comment,
            @RequestParam MultipartFile imageFile,
            Principal principal) {

        String email = principal.getName();

        User loginUser = userRepository.findByEmail(email);

        Integer userId = loginUser.getUserId();

        ReviewRequest request = new ReviewRequest();

        request.setUserId(userId);
        request.setProductId(productId);
        request.setDummyUserName(dummyUserName);
        request.setRating(rating);
        request.setContactEmail(contactEmail);
        request.setComment(comment);
        String fileName = null;

        if (imageFile != null && !imageFile.isEmpty()) {

            try {
                fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

                Path uploadPath = Paths.get("src/main/resources/static/upload/review");

                Files.createDirectories(uploadPath);

                imageFile.transferTo(uploadPath.resolve(fileName));

            } catch (Exception e) {
                throw new RuntimeException("画像保存に失敗しました", e);
            }
        }

        request.setReviewImgPath(fileName);

        reviewService.createReview(request);

        /*return "redirect:/product/detail/" + productId;*/
     // 投稿後TOPへ戻る
        return "redirect:/product/list";
    }
}