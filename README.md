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

[4. JPA, MySQL을 이용해 게시판 조회하기] [JPA<->mybatis](https://jar100.tistory.com/25)
---
1. MySQL 테이블 생성
	1. springboot_personal_project 스키마 생성
	2. 사용자 계정(sbadmin) 추가 하여 해당 계정으로 mysql connection
	3. 게시판 테이블(board) 생성

2. Spring Boot에서 mysql 데이터소스 설정
	1. [jap의존성추가](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
	**JPA를 구현한 것중 가장 유명한 hibernate를 사용**
	2. [mysql 의존성 추가](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.21)
	3. application.properties에 mysql [데이터소스](http://blog.naver.com/PostView.nhn?blogId=ndarkness75&logNo=220991437798&categoryNo=0&parentCategoryNo=8&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView) 설정
	<pre>
	<code>
	[**time zone 관련 에러**]
	java.sql.SQLException: The server time zone value '´ëÇÑ¹Î±¹ Ç¥ÁØ½Ã' is unrecognized or represents more than one time zone. 
	You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
	[**원인 :  MySQL 버전 5.1.23보다 높은 버전을 사용하면 MySQL 타임존의 시간표현 포맷이 달라져서 connector 에서 인식을 하지 못한다**]
	[**해결방법**](https://irerin07.tistory.com/14)
	1. mysql 데이터 소스 url 맨뒤에 "스키마이름?" 뒤에
	useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
	추가
	2. MySQL 버전을 5.1.23으로 낮추기
	</code>
	</pre>

3. Model, Repository 클래스 생성 및 어노테이션 설정
	1. Model (Board Class) 추가
		- 클래스의 @Entity, PK에 해당하는 값의 @ID @GeneratedValue(strategy = GenerationType.IDENTITY) 설정
	2. Repository (BoardRepository Interface) 추가 [참고](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#reference)
	3. BoardController 수정
	4. list.html 수정 [참고](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

4. 게시판 데이터 조회 후 화면에 출력
	1. bootstrap에서 [테이블 레이아웃](http://bootstrapk.com/examples/theme/) 이용
	2. thymeleaf 리스트 이용
