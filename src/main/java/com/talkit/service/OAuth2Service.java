package com.talkit.service;

public interface OAuth2Service {
    String kakao(String code);

    String naver(String code);
}
