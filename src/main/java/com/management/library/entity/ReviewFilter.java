package com.management.library.entity;

public record ReviewFilter(Integer rating, Boolean verified, String reviewerName) {
}
