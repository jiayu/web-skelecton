package me.jamc.skeleton.service;

import com.google.common.collect.ImmutableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import me.jamc.skeleton.dao.UserRepository;
import me.jamc.skeleton.model.User;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

    @Autowired
    private UserRepository userRepo;

    @Override
    public User addUser(String firstName, String lastName) {
        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        User savedUser = userRepo.save(u);
        return savedUser;
    }

    @Override
    public boolean updateUser(int id, String firstName, String lastName) {
        User u = userRepo.findOne(id);
        if (null != u) {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            userRepo.save(u);
            return true;
        }
        return false;
    }

    @Override
    public User getUser(int id) {
        return userRepo.findOne(id);
    }

    @Override
    public boolean deleteUser(int id) {
        User u = userRepo.findOne(id);
        if (null != u) {
            userRepo.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new LinkedList<User>();
        userRepo.findAll().forEach( u -> list.add(u));
        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {

                return ImmutableList.of(new SimpleGrantedAuthority(user.getRole()));
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
