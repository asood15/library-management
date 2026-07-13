
package com.management.library.controller;

import com.management.library.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.test.tester.ExecutionGraphQlServiceTester;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class ReviewControllerTests {

    private final GraphQlTester graphQlTester;

    @Autowired
    ReviewControllerTests(ExecutionGraphQlService graphQlService) {
        this.graphQlTester = ExecutionGraphQlServiceTester.builder(graphQlService).build();
    }

    @Test
    void shouldGetReviewById() {
        String query = """
                query($id: ID!) {
                    review(id: $id) {
                        id
                        reviewerName
                        rating
                        comment
                        verified
                        book {
                            id
                            title
                        }
                    }
                }
                """;

        graphQlTester.document(query)
                .variable("id", 1L)
                .execute()
                .errors()
                .verify()
                .path("review")
                .entity(Review.class)
                .satisfies(returnedReview -> {
                    assertThat(returnedReview.getReviewerName().equals("Sarah Chen"));
                    assertThat(returnedReview.getRating().intValue()).isEqualTo(5);
                });
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldGetReviewsWithFilter() {
        String query = """
                query($filter: ReviewFilter) {
                    reviews(filter: $filter) {
                        id
                        reviewerName
                        rating
                        verified
                    }
                }
                """;

        graphQlTester.document(query)
                .variable("filter", Map.of("rating", 5, "verified", true))
                .execute()
                .errors()
                .verify()
                .path("reviews")
                .entityList(Review.class)
                .satisfies(reviews -> {
                    assertThat(reviews).isNotNull();
                    assertThat(reviews.getFirst().getReviewerName().equals("Sarah Chen"));
                    assertThat(reviews.getFirst().getRating().equals(5));
                });
    }
}
