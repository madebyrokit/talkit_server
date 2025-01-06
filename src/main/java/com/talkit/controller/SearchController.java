package com.talkit.controller;

import com.talkit.dto.PostDto;
import com.talkit.service.PostService;
import com.talkit.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<PostDto.ListResponse>> getSearchList(@RequestParam int page, @RequestParam int size, @RequestParam String q) {
        List<PostDto.ListResponse> listResponses = searchService.getPostListByKeyword(page, size, q);
        return ResponseEntity.ok().body(listResponses);
    }
}
