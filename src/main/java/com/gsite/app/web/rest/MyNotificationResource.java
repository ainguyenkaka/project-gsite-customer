package com.gsite.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gsite.app.domain.Notification;
import com.gsite.app.service.NotificationService;
import com.gsite.app.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/mynotifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable String id) {
        log.debug("REST request to delete Notification : {}", id);
        notificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("notification", id.toString())).build();
    }

}
