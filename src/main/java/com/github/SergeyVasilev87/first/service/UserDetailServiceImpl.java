package com.github.SergeyVasilev87.first.service;

import com.github.SergeyVasilev87.first.entity.Status;
import com.github.SergeyVasilev87.first.entity.User;
import com.github.SergeyVasilev87.first.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveuser(User user) { //todo
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    //UserDetailsService используется, чтобы создать UserDetails объект путем реализации единственного метода этого интерфейса
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("user не найден"));
        return userDetailsFromUser(user);
    }

    //получаем userdetails из userentity. UserDetails предоставляет необходимую информацию для построения объекта Authentication
    //из DAO объектов приложения или других источников данных системы безопасности.
    public static UserDetails userDetailsFromUser (User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getRole().getAuthorities()
        );
    }
}
