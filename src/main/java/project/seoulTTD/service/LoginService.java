package project.seoulTTD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.seoulTTD.entity.User;
import project.seoulTTD.repository.UserRepository;
import project.seoulTTD.web.login.RegisterForm;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(String loginId, String password) {
        return userRepository.findUserByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public void join(RegisterForm registerForm) { // 폼을 받아 회원 저장
        User user = new User();
        user.setLoginId(registerForm.getLoginId());
        user.setPassword(registerForm.getPassword());
        user.setName(registerForm.getName());
        user.setEmail(registerForm.getEmail());
        user.setPhoneNum(registerForm.getPhoneNum());

        userRepository.save(user);
    }
}
