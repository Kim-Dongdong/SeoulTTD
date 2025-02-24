package project.seoulTTD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import project.seoulTTD.entity.User;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.exception.DuplicateFavoriteException;
import project.seoulTTD.exception.DuplicateUserException;
import project.seoulTTD.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public Long join(User user) {
        validDuplicateUser(user); // 중복 검색
        User saveduser = userRepository.save(user);
        return saveduser.getId();
    }

    // 회원 중복 검색
    public void validDuplicateUser(User user) {
        List<User> findUser = userRepository.findByPhoneNum(user.getPhoneNum());
        if (!findUser.isEmpty()) {
            throw new DuplicateUserException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public User findUserByLoginId(String loginId) {
        return userRepository.findUserByLoginId(loginId).get();
    }

    // 회원 전체 조회
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    // 회원 단건 조회
    public User findOne(User user) {
        return userRepository.findById(user.getId()).get();
    }


    // 즐겨찾기 등록
    @Transactional
    public void addFavorite(Long userId, Place place) { // Controller에 사용할 예정

        User findUser = userRepository.findById(userId).get();
        Long pleId = place.getId();

        findUser.setFavorite(place);
        // 이후는 변경 감지에 의해 자동 업데이트된다.

    }

    @Transactional
    public void removeFavorite(User findUser, Place findPlace) {
        findUser.getFavorites().remove(findPlace);
    }

}
