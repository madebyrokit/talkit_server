package com.talkit.controller;

import com.talkit.dto.PostDto;
import com.talkit.service.PostService;
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
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto.ListResponse>> getSearchList(@RequestParam String q) {
        return ResponseEntity.ok().body(postService.getPostListByKeyword(q));
    }
}
