package com.example.board.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    //      로그(logback) 객체 만드는 방법 1.
    @Slf4j
public class LogController {


    //      로그(logback) 객체 만드는 방법 2.
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);


    @GetMapping("/log/test")
    public String logTest() {
        //      기존의 systemPrint는 실무에서 사용하지 않음.
        //      사용하지 않는 이유
        //      1. 성능이 좋지 않음. 2. 로그 분류 작업이 불가하다.
        System.out.println("system print log test");
        //      로그레벨 :trace < debug < info < error
        logger.trace("tracer 로그입니다");
        logger.debug("debug 로그입니다.");
        logger.info("info 로그입니다.");
        logger.error("error 로그입니다.");
        logger.warn("warn 로그입니다");


        //      Slf4j 어노테이션 선언 시 , log라는 변수로 longback 객체 사용 가능
        log.info("slf4j 테스트입니다,");
        log.error("slf4j error입니다,");

        //      error은 에러가 텨졌을 때 사용, info는 정보성 로그 출력 시 사용. debug는 테스트 목적으로 사용
        try {
            log.info("에러테스트 시작");
            log.debug("최영재 테스트 중입니다.");
            throw new RuntimeException("error test");
        }catch (RuntimeException e) {
            log.error(e.getMessage());
        }

        return "OK";
    }
}
