package rut.miit.airportweb.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rut.miit.airportweb.dao.entity.UserEntity;
import rut.miit.airportweb.dao.repository.UserRepository;
import rut.miit.airportweb.mapper.UserEntityToCustomUserDetailsMapper;


/**
 * {@link CustomUserDetails} это реализация {@link UserDetails}
 * для того, чтобы в этом сервисе достать из базы пользователя, конвертнуть его в UserDetails и сделать аутентификацию
 * с авторизацией
 * Т.е. в этом коде должна быть логика получения пользователя по какому-либо параметру из базы и перевод в нужный класс
 * */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = this.userRepository.findByUsername(username).get();
        if (entity == null) {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }

        return UserEntityToCustomUserDetailsMapper.map(entity);
    }
}
