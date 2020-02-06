package cl.mym.api.user.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cl.mym.api.user.jwt.entity.UserEntity;
import cl.mym.api.user.jwt.model.User;
import cl.mym.api.user.jwt.repository.UserRepository;
import cl.mym.api.user.jwt.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
	
	@Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void findAllUserTest() {
        User userOne = UtilTest.createUserAdminOk();
        this.userService.save(userOne);
        List<UserEntity> users = userRepository.findAll();
        assertThat(userRepository.findAll()).isNotEmpty();
        assertEquals(userOne.getEmail(), users.get(0).getEmail());
    }
    
    @AfterAll
    public void updateUserTest() {
        User userOne = UtilTest.createUserAdminOk();
        UserEntity userEntity = userService.findByEmail(userOne.getEmail());
        userOne.setName("User");
        
        UserEntity updateUserEntity = userService.update(userEntity.getId(), userOne);
        
        assertEquals(userOne.getName(), updateUserEntity.getName());
       
    }
    
    @AfterAll
    public void deleteUserTest() {
        User userOne = UtilTest.createUserAdminOk();
        UserEntity userEntity = userService.findByEmail(userOne.getEmail());
        userOne.setName("User");
        
        String status = userService.delete(userEntity.getId());
        
        assertEquals(status, UtilTest.MESSAGE_USER_DELETE);
       
    }

    

}
