package com.gsite.app.web.rest;

import com.gsite.app.domain.Feedback;
import com.gsite.app.service.FeedbackService;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyFeedbackResource {

    private final Logger log = LoggerFactory.getLogger(MyFeedbackResource.class);

    @Inject
    private FeedbackService feedbackService;

    @GetMapping("/myfeedbacks")
    @Timed
    public ResponseEntity<List<Feedback>> getAllFeedbacks(@ApiParam String userId)
        throws URISyntaxException {
        log.debug("REST request to get a page of Feedbacks with user: {}",userId);
        List<Feedback> list = feedbackService.findAllByUser(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
