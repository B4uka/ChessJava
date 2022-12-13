package pl.chessWebApp.security.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.chessWebApp.ChessApplication;
import pl.chessWebApp.security.config.H2JpaConfig;
import pl.chessWebApp.security.entity.User;

import javax.persistence.EntityManager;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {
        ChessApplication.class,
        H2JpaConfig.class})
@ActiveProfiles("test")
@EnableJpaRepositories
@EnableTransactionManagement
public class SpringBootH2IntegrationTest {

//    @Mock
//    private EntityManager entityManager;
//    @InjectMocks
//    private UserDaoImpl repo;
//
//    @Test
//    public void findAllMustReturnAnything () {
//        repo.save(new User("name", "test", "surname", "pass", "op@op.pl"));
//        User user = repo.findByUserName("name");
//        assert user.getUserName().equals("name");
//    }
}