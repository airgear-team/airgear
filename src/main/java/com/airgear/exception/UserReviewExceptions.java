package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserReviewExceptions {
    public static ResponseStatusException reviewerNotReviewed(Long reviewerId) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reviewer with id: "+reviewerId+" can't set review for himself!");
    }

    public static ResponseStatusException onlyOneReview(Long reviewerId) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reviewer with id: "+reviewerId+" can do only one review!");
    }

}