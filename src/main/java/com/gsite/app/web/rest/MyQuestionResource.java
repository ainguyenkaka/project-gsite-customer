package com.gsite.app.web.rest;

import com.gsite.app.domain.Question;
import com.gsite.app.service.QuestionService;
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
public class MyQuestionResource {

    private final Logger log = LoggerFactory.getLogger(MyQuestionResource.class);

    @Inject
    private QuestionService questionService;

    @GetMapping("/myquestions")
    @Timed
    public ResponseEntity<List<Question>> getAllQuestionsByUser(@ApiParam String userId)
        throws URISyntaxException {
        log.debug("REST request to get all question of user: {}",userId);
        List<Question> list = questionService.findAllByUser(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
