package project.seoulTTD.web.login;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.seoulTTD.entity.User;
import project.seoulTTD.service.LoginService;
import project.seoulTTD.service.UserService;
import project.seoulTTD.web.session.SessionConst;



@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) { //@Valid 오류시 처리
            return "login/loginForm";
        }

        User loginUser = loginService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        // setAttribute는 name, value 쌍으로 객체 Object를 저장하는 메서드
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        // 로그인한 User 정보 전달
        User findUser = userService.findUserByLoginId(form.getLoginId());
        model.addAttribute("userId", findUser.getId());

        return "redirect:/mainForm/" + findUser.getId();
    }

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("registerForm") RegisterForm registerForm) {
        return "login/registerForm";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm registerForm, BindingResult bindingResult,
                           @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        loginService.join(registerForm);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션 생성 x -> false
        if (session != null) {
            session.invalidate();
        }
        return "redirect:";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
