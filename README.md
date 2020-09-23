# Spring-boot-Personal_Project
## 스프링부트로 웹 출시하기
## SpringBoot, thymeleaf, Bootstrap, JPA, MySQL, Spring Security

---
[1.Spring Boot 환경세팅]
---
1. Spring Boot 프로젝트(maven, java8, jar, web-dependency) [완료]

[2. thymeleaf를 통해 화면 작성]
---
1. thymeleaf, devtools 의존성 추가
2. Visual Studio Code로 resource폴더 수정
3. 실시간으로 디자인 볼 수 있도록 visual studio code에 LiveServer 확장프로그램 설치
4. @RequestParam을 통해 url 요청 매개변수 받기

[3. thymeleaf를 통해 레이아웃 만들기]
---
1. Bootstrap[반응형 웹 제작을 도와주는 프레임워크](https://gmlwjd9405.github.io/2018/05/02/bootstrap-download-and-setting.html)을 이용하여 반응형 웹 페이지 구성하기 **완료**
	1. [기본템플릿](http://bootstrapk.com/getting-started/) 적용  - index.html
	2. [기본템플릿 예제 적용](http://bootstrapk.com/examples/starter-template/)
	3. 2의 CSS 파일 추가	
2. Fragment를 이용해서 공통 화면(**common.html**) 레이아웃 구성하기
	1. templates/fragments/common.html을 추가하여 -> head와 body 공통부분처리 (th:fragment , th:replace 이용)
	2. fragment의 parameter 이용하기 (ex. th:fragment="head(para)")
	3. 게시판 list추가 -> board/list 이므로 th:href 이용
	4. 상단 네비게이션 메뉴 condition에 따라 class 동적으로 추가 -> th:classappend 이용