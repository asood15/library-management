package com.management.library.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private String reviewerName;
    private Boolean verified;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Review() {
    }

    public long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public Boolean getVerified() {
        return verified;
    }

    public Book getBook() {
        return book;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id && Objects.equals(rating, review.rating) && Objects.equals(comment, review.comment) && Objects.equals(createdAt, review.createdAt) && Objects.equals(reviewerName, review.reviewerName) && Objects.equals(verified, review.verified) && Objects.equals(book, review.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, comment, createdAt, reviewerName, verified, book);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", reviewerName='" + reviewerName + '\'' +
                ", verified=" + verified +
                ", book=" + book +
                '}';
    }
}
