package ua.com.alevel;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.alevel.facade.PostFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.entity.user.BaseUser;
import ua.com.alevel.web.dto.request.PostRequestDto;
import ua.com.alevel.web.dto.request.UserRequestDto;

@SpringBootTest
class TravelsSiteApplicationTests {
}
