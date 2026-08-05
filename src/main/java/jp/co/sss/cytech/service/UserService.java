/*package jp.co.sss.cytech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.cytech.dto.UserRequest;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(UserRequest request) {

    	User existUser =
                userRepository.findByEmail(
                        request.getEmail());

        if (existUser != null) {
            throw new RuntimeException(
                    "既に登録されているメールアドレスです");
        }
        
        User user = new User();

        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);
    }
}*/
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
        user.setUserNameKana(request.getUserNameKana()); // ★追加
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());               // ★追加
        user.setUserAddress(request.getUserAddress());   // ★追加

        // パスワード（BCrypt）
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }
}