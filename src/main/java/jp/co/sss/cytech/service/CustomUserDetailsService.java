package jp.co.sss.cytech.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.sss.cytech.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        jp.co.sss.cytech.entity.User dbUser =
                userRepository.findByEmail(email);

        if (dbUser == null) {
            throw new UsernameNotFoundException("ユーザーが存在しません");
        }

//        return new User(
//                dbUser.getEmail(),
//                dbUser.getPassword(),
//                new ArrayList<>()
//        );
        return new LoginUserDetails(dbUser);
    }
}