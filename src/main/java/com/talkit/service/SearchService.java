package com.talkit.service;

import com.talkit.dto.PostDto;

import java.util.List;

public interface SearchService {
    List<PostDto.ListResponse> getPostListByKeyword(int page, int size, String keyword);
}
