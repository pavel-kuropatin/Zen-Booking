package com.kuropatin.zenbooking;

import com.kuropatin.zenbooking.config.CacheConfig;
import com.kuropatin.zenbooking.config.JdbcTemplateConfig;
import com.kuropatin.zenbooking.config.JwtConfig;
import com.kuropatin.zenbooking.config.SecurityConfig;
import com.kuropatin.zenbooking.config.SwaggerConfig;
import com.kuropatin.zenbooking.controller.BookingController;
import com.kuropatin.zenbooking.controller.HostingController;
import com.kuropatin.zenbooking.controller.LoginAndRegistrationController;
import com.kuropatin.zenbooking.controller.OrderController;
import com.kuropatin.zenbooking.controller.ProfileController;
import com.kuropatin.zenbooking.controller.ReviewController;
import com.kuropatin.zenbooking.exception.handler.ApplicationExceptionHandler;
import com.kuropatin.zenbooking.exception.handler.HttpExceptionHandler;
import com.kuropatin.zenbooking.repository.AdminRepository;
import com.kuropatin.zenbooking.repository.JdbcSearch;
import com.kuropatin.zenbooking.repository.JdbcTemplateSearch;
import com.kuropatin.zenbooking.repository.OrderRepository;
import com.kuropatin.zenbooking.repository.PropertyImageRepository;
import com.kuropatin.zenbooking.repository.PropertyRepository;
import com.kuropatin.zenbooking.repository.ReviewRepository;
import com.kuropatin.zenbooking.repository.SearchRepository;
import com.kuropatin.zenbooking.repository.UserRepository;
import com.kuropatin.zenbooking.security.service.CustomUserDetailsService;
import com.kuropatin.zenbooking.security.service.LoginService;
import com.kuropatin.zenbooking.service.AdminService;
import com.kuropatin.zenbooking.service.OrderService;
import com.kuropatin.zenbooking.service.PropertyImageService;
import com.kuropatin.zenbooking.service.PropertyService;
import com.kuropatin.zenbooking.service.ReviewService;
import com.kuropatin.zenbooking.service.SearchService;
import com.kuropatin.zenbooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ZenBookingStarterTests {

    @Autowired
    private CacheConfig cacheConfig;

    @Autowired
    private JdbcTemplateConfig jdbcTemplateConfig;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private SwaggerConfig swaggerConfig;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JdbcSearch jdbcSearch;

    @Autowired
    private JdbcTemplateSearch jdbcTemplateSearch;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PropertyImageRepository propertyImageRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PropertyImageService propertyImageService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingController bookingController;

    @Autowired
    private HostingController hostingController;

    @Autowired
    private LoginAndRegistrationController loginAndRegistrationController;

    @Autowired
    private OrderController orderController;

    @Autowired
    private ProfileController profileController;

    @Autowired
    private ReviewController reviewController;

    @Autowired
    private ApplicationExceptionHandler applicationExceptionHandler;

    @Autowired
    private HttpExceptionHandler httpExceptionHandler;

    @Test
    void contextLoads() {
        // Config loads
        assertNotNull(cacheConfig);
        assertNotNull(jdbcTemplateConfig);
        assertNotNull(jwtConfig);
        assertNotNull(securityConfig);
        assertNotNull(swaggerConfig);

        // Repository loads
        assertNotNull(adminRepository);
        assertNotNull(jdbcSearch);
        assertNotNull(jdbcTemplateSearch);
        assertNotNull(orderRepository);
        assertNotNull(propertyImageRepository);
        assertNotNull(propertyRepository);
        assertNotNull(reviewRepository);
        assertNotNull(searchRepository);
        assertNotNull(userRepository);

        // Service loads
        assertNotNull(adminService);
        assertNotNull(customUserDetailsService);
        assertNotNull(loginService);
        assertNotNull(orderService);
        assertNotNull(propertyImageService);
        assertNotNull(propertyService);
        assertNotNull(reviewService);
        assertNotNull(searchService);
        assertNotNull(userService);

        // Controller loads
        assertNotNull(bookingController);
        assertNotNull(hostingController);
        assertNotNull(loginAndRegistrationController);
        assertNotNull(orderController);
        assertNotNull(profileController);
        assertNotNull(reviewController);

        // Exception handlers loads
        assertNotNull(applicationExceptionHandler);
        assertNotNull(httpExceptionHandler);
    }
}