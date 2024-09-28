package com.javenock.book_service.docs;

public class Examples {
    public static final String CREATE_BOOK_REQUEST = "{\"title\":\"Title\",\"description\":\"Description\",\"type\":\"Trade_Books\",\"datePublished\":\"2021-09-28\",\"price\":100,\"authorIds\":[\"7a40fd66-36a6-4487-9509-e9b12a61bff9\"]}";
    public static final String GET_BOOK_OK_RESPONSE = "{\"publicId\":\"ff06fe30-7990-4be7-903b-a877d3df945a\",\"dateCreated\":\"2024-09-28 10:52:27\",\"createdBy\":null,\"modifiedBy\":null,\"title\":\"Title\",\"description\":\"Description\",\"type\":\"Trade_Books\",\"datePublished\":\"2021-09-28\",\"price\":100,\"author\":[{\"publicId\":\"7a40fd66-36a6-4487-9509-e9b12a61bff9\",\"firstName\":\"peterKason\"}]}";
    public static final String GET_BOOKS_OK_RESPONSE = "{\"totalElements\":1,\"pageSize\":1,\"totalPages\":1,\"last\":true,\"first\":true,\"pageNumber\":0,\"content\":[{\"publicId\":\"7a40fd66-36a6-4487-9509-e9b12a61bff1\",\"dateCreated\":null,\"createdBy\":null,\"modifiedBy\":null,\"title\":\"New Covent\",\"description\":\"description\",\"type\":\"Trade_Books\",\"datePublished\":\"2024-09-28\",\"price\":null,\"author\":[{\"publicId\":\"7a40fd66-36a6-4487-9509-e9b12a61bff9\",\"firstName\":\"peterKason\"}]}]}";
}
