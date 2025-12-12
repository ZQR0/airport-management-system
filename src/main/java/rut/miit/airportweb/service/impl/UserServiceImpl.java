package rut.miit.airportweb.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rut.miit.airportweb.dao.entity.PassengerEntity;
import rut.miit.airportweb.dao.entity.UserEntity;
import rut.miit.airportweb.dao.repository.PassengerRepository;
import rut.miit.airportweb.dao.repository.UserRepository;
import rut.miit.airportweb.dto.UserDto;
import rut.miit.airportweb.dto.UserRegistrationDto;
import rut.miit.airportweb.exception.EntityAlreadyExistsException;
import rut.miit.airportweb.exception.EntityNotFoundException;
import rut.miit.airportweb.mapper.UserMapper;
import rut.miit.airportweb.service.UserService;
import rut.miit.airportweb.exception.NotPermittedOperation;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PassengerRepository passengerRepository;

    @Override
    @Transactional(isolation = org.springframework.transaction.annotation.Isolation.READ_COMMITTED)
    public UserDto registerUser(UserRegistrationDto dto) {
        log.info("Registering user {}", dto.getUsername());

        // Проверяем, не существует ли уже пользователь с таким username
        if (this.userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new EntityAlreadyExistsException(
                    String.format("User with username %s already exists", dto.getUsername()));
        }

        // Проверяем, не существует ли уже пассажир с таким паспортом
        if (this.passengerRepository.findByPassportNumber(dto.getPassportNumber()).isPresent()) {
            throw new EntityAlreadyExistsException(
                    String.format("Passenger with passport %s already exists", dto.getPassportNumber()));
        }

        // Создаем пользователя
        UserEntity userEntity = UserMapper.map(dto);
        userEntity.setPassword(this.passwordEncoder.encode(dto.getPassword()));

        // Создаем пассажира (только если роль PASSENGER)
        PassengerEntity passenger = null;
        if (UserEntity.Role.valueOf(dto.getRole()) == UserEntity.Role.PASSENGER) {
            passenger = PassengerEntity.builder()
                    .user(userEntity)
                    .passportNumber(dto.getPassportNumber())
                    .email(dto.getEmail())
                    .phone(null) // Можно добавить позже
                    .luggageChecked(false)
                    .build();

            userEntity.setPassenger(passenger);
        }

        // Сохраняем пользователя (пассажир сохранится каскадно)
        UserEntity savedUser = this.userRepository.save(userEntity);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        return UserMapper.map(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) throws EntityNotFoundException {
        UserEntity entity = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with username %s not found", username)));

        return UserMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByFullName(String firstName, String lastName) throws EntityNotFoundException {
        UserEntity entity = this.userRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with fullname %s %s not found", firstName, lastName)));

        return UserMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsersOptimized() {
        return this.userRepository.findAllOptimized()
                .stream()
                .map(UserMapper::map)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto authenticate(String username, String password) throws EntityNotFoundException {
        log.info("Authenticating user {}", username);

        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with username %s not found", username)));

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new NotPermittedOperation("Invalid password");
        }

        log.info("User {} authenticated successfully", username);
        return UserMapper.map(user);
    }

    @Override
    @Transactional(isolation = org.springframework.transaction.annotation.Isolation.READ_COMMITTED)
    public UserDto updateUser(String username, UserDto userDto) {
        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with username %s not found", username)));

        // Обновляем поля, если они предоставлены
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getPasswordHash() != null) {
            user.setPassword(this.passwordEncoder.encode(userDto.getPasswordHash()));
        }

        UserEntity updatedUser = this.userRepository.save(user);
        log.info("Updated user {}", username);

        return UserMapper.map(updatedUser);
    }

    @Override
    @Transactional(isolation = org.springframework.transaction.annotation.Isolation.READ_COMMITTED)
    public void deleteUser(String username) {
        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with username %s not found", username)));

        this.userRepository.delete(user);
        log.info("Deleted user {}", username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllPassengers() {
        // Оптимизированный запрос - фильтруем на уровне базы данных
        return this.userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == UserEntity.Role.PASSENGER)
                .map(UserMapper::map)
                .toList();
    }

}