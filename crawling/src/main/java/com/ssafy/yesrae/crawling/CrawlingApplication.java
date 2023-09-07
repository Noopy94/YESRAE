package com.ssafy.yesrae.crawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class CrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlingApplication.class, args);

		// 여기서 엑셀 읽고 데이터 받는다.
		// 그다음 그거 api에 던져서 노래 받는다.
		// 그거 메소드 만든거 써서 db에 넣는다.
		// 중간중간 한시간마다 리프레시 토큰 잊지말 것.
		// 스케쥴 사용 가능한가?
	}

}
