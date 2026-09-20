package jp.co.sss.cytech.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.sss.cytech.dto.UserRequest;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRequest request) {

        // ① メール重複チェック
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("メールアドレスは既に登録されています");
        }

        User user = new User();

        user.setUserName(request.getUserName());
        user.setUserNameKana(request.getUserNameKana()); 
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());               
        user.setUserAddress(request.getUserAddress());   

        // パスワード（BCrypt）
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }
}