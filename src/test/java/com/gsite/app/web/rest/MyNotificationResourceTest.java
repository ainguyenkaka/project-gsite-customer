package com.gsite.app.web.rest;

import com.gsite.app.GsiteCustomerApp;
import com.gsite.app.domain.Notification;
import com.gsite.app.repository.NotificationRepository;
import com.gsite.app.service.NotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.gsite.app.web.rest.TestUtil.sameInstant;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsiteCustomerApp.class)
public class MyNotificationResourceTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";

    @Inject
    private NotificationRepository notificationRepository;

    @Inject
    private NotificationService notificationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restNotificationMockMvc;

    private Notification notification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyNotificationResource myNotificationResource = new MyNotificationResource(notificationService);
        ReflectionTestUtils.setField(myNotificationResource, "notificationService", notificationService);
        this.restNotificationMockMvc = MockMvcBuilders.standaloneSetup(myNotificationResource)
            .setMessageConverters(jacksonMessageConverter).build();
    }


    public static Notification createEntity() {
        Notification notification = new Notification()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .created(DEFAULT_CREATED);
        notification.getSentUsers().add(DEFAULT_USER_ID);
        return notification;
    }

    @Before
    public void initTest() {
        notificationRepository.deleteAll();
        notification = createEntity();
    }

    @Test
    public void getMyNotifications() throws Exception {
        notificationRepository.save(notification);

        restNotificationMockMvc.perform(get("/api/mynotifications").param("userId", DEFAULT_USER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }

}
