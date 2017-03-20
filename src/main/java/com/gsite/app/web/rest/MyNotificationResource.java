package com.gsite.app.web.rest;

import com.gsite.app.domain.Notification;
import com.gsite.app.service.NotificationService;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyNotificationResource {

    private final Logger log = LoggerFactory.getLogger(MyNotificationResource.class);


    private final NotificationService notificationService;

    public MyNotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/mynotifications")
    @Timed
    public ResponseEntity<List<Notification>> getAllNotificationsByUserId(@ApiParam String userId)
        throws URISyntaxException {
        log.debug("REST request to get a page of Notifications by user: {}" ,userId);
        List<Notification> list = notificationService.findAllBySentUser(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
