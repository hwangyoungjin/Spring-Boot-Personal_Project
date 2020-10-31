# Spring-boot-Personal_Project
## 스프링부트로 웹 출시하기
## SpringBoot, thymeleaf, [Bootstrap](https://getbootstrap.com/docs/4.4/getting-started/introduction/), JPA, MySQL, Spring Security

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
1. Bootstrap[반응형 웹 제작을 도와주는 프레임워크](https://getbootstrap.com/)을 이용하여 반응형 웹 페이지 구성하기 **완료**
	1. [기본템플릿](https://getbootstrap.com/docs/4.5/examples/starter-template/) 적용  - index.html
	2. [기본템플릿 예제 적용](https://getbootstrap.com/docs/4.5/examples/starter-template/)
	3. 2의 [CSS 파일](https://getbootstrap.com/docs/4.5/examples/starter-template/starter-template.css) 추가	
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
	```java
	[time zone 관련 에러]
	java.sql.SQLException: The server time zone value '´ëÇÑ¹Î±¹ Ç¥ÁØ½Ã' is unrecognized or represents more than one time zone. 
	You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
	[원인 :  MySQL 버전 5.1.23보다 높은 버전을 사용하면 MySQL 타임존의 시간표현 포맷이 달라져서 connector 에서 인식을 하지 못한다]
	[해결방법](https://irerin07.tistory.com/14)
	1. mysql 데이터 소스 url 맨뒤에 "스키마이름?" 뒤에
	useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
	추가
	2. MySQL 버전을 5.1.23으로 낮추기
	```

3. Model, Repository 클래스 생성 및 어노테이션 설정
	1. Model (Board Class) 추가
		- 클래스의 @Entity, PK에 해당하는 값의 @ID @GeneratedValue(strategy = GenerationType.IDENTITY) 설정
	2. Repository (BoardRepository Interface) 추가 [참고](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#reference)
	3. BoardController 수정
	4. list.html 수정 [참고](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

4. 게시판 데이터 조회 후 화면에 출력
	1. bootstrap에서 [테이블 레이아웃](http://bootstrapk.com/examples/theme/) 이용
	2. thymeleaf 리스트 이용
	3. 버튼 url 연결 안됨 -> 태그변경(button->a)으로 [해결](https://ofcourse.kr/html-course/a-%ED%83%9C%EA%B7%B8) 

[5. thymleaf에서 form 전송하기]
---
1. Spring Boot, thymeleaf를 이용하여 form 전송 하고 JPA를 이용해서 DB에 데이터 추가, 수정
	1. form.html 작성 [참고](https://araikuma.tistory.com/75)
	2. [thymeleaf form 핸들링](https://spring.io/guides/gs/handling-form-submission/)
	3. get->form[스프링부트form](https://spring.io/guides/gs/handling-form-submission/), [button vs input](https://aboooks.tistory.com/301)->post
	~~~html
	[form에서 post 요청시 postMapping의 필드값이 null인 객체가 들어오는 문제 발생]
	1. textarea안에 값 modeldata로 바인딩 안됨 -> 원인 모르겠음
	~~~

	4. 게시판 글 수정 
		1. list.html에서 title 부분 [파라미터를 통해 링크연결](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
		2. GETMapping (/form)에 파라미터 전달받기(@RequestParam 이용)
		3. POSTMapping 에서 form.html에서 전달 한 model을 받아 key값으로 저장하기 때문에 controller에 key값을 전달하기 위해 form.html의 id값 hidden으로 전달 

2. form 유효값 체크할수 있는 Validator 작성하기
	1. [thymeleaf form Validating](https://spring.io/guides/gs/validating-form-input/)
	2. 서버에서 클라이언트에서 보낸 데이터 체크 하기위해 VO객체(Board)에 @NotNull, @Size, @Valid 설정
	- **@Size(message= "") 를 통해 에러메시지 설정**
	```java
	[javax에 있는 @NotNull, @Size, @Valid import 안됨]
	pom.xml [dependency추가로 해결](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation/2.3.3.RELEASE)
	```

	3. POSTMapping 에 @Valid, BindingResult 추가
	4. form.html에서 [에러있는경우 에러값 표시할 코드 부트스트랩 Server side용](https://getbootstrap.com/docs/4.4/components/forms/#validation) 추가
	5. 4과정 에 [thymeleaf 에러처리부분](https://spring.io/guides/gs/validating-form-input/) 함께 추가

	6. 애노테이션이 아닌 Validator인터페이스를 통해 새로운 [Vaildator클래스](https://lazymankook.tistory.com/86) (BoardValidator) 만들기  


[6. JPA를 이용한 페이지 처리 및 검색]
---
1. JPA의 Page 클래스를 이용해서 페이지 처리 & 검색 기능 구현
	1. 게시판 글 수가 많아지면 처리할 페이지 처리(페이징)
		1. [부트스트랩 Pagnation](https://getbootstrap.com/docs/4.4/components/pagination/#overview)
		2. DB 모든데이터 page로 다 가져오기[Spring Data JAP-pageable](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#reference)
		```java
		// 첫 번째 방법
		// page는 0번 시작하며 page안에 데이터 개수는 20개로 지정
		public String list(Model model, Pageable pageable){
        			Page<Board> boards = boardRepository.findAll(PageRequest.of(0,10));
        			model.addAttribute("boards",boards);
			return "board/list";
		}

		// 두 번째 방법
		// 파라미터로 받아서 url로 페이지 번호, 개수 받기
		// http://localhost:8080/board/list?page=1&size=10
 		// @PageableDefault를 이용하여 사이즈 정해주기
		public String list(Model model, @PageableDefault(size = 5) Pageable pageable){
        			Page<Board> boards = boardRepository.findAll(pageable);
        			model.addAttribute("boards",boards);
			return "board/list";
 		}
		```
		3. [page버튼의 index값 Conteroller에서 받아오기](https://stackoverflow.com/questions/40007190/thymeleaf-loop-until-a-number)
		4. index를 이용해서 해당 페이지 인 경우 버튼 diable - th:classappend
		5. [page버튼 파라미터받아서 url 넣기- th:href]

	2. 게시판 검색 기능 만들기
		1. [부트스트랩 검색 폼-inline](https://getbootstrap.com/docs/4.4/components/forms/)
		2. [검색 form우측정렬할 부트스트랩 class 적용 -justify](https://getbootstrap.com/docs/4.4/utilities/flex/)
		3. [검색 버튼 색 추가 할 부트스트랩 class 적용 - btn-light](https://getbootstrap.com/docs/4.4/components/buttons/#button-tags)
		4. BoardController.java에서 @GetMapping("/list")의 param으로 검색 문자 추가
		```java
		@GetMapping("/list")
    		public String list(Model model, @PageableDefault(size = 5) Pageable pageable ,
                       		@RequestParam(required = false, defaultValue = "") String searchText){  // searchText 파라미터 필수아니므로 false
		```
		5. 검색 문자를 통해 해당하는 값 받기 위해 BoardRepository.java 수정
			- [JPA Containing](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#reference)
			```java
			//파라미터와 일치하는 데이터 page로 반환
			Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
			```
		6. BoardController.java @GetMapping("/list") 에서 5. 이용
		```java
		Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable)
		```
		```html
		[list.form의 검색 input 에서 controller로 (searchText)인자 안넘어가는 오류]
		<!--input의 name속성 추가해서 해결-->
		<input type="text" class="form-control" id="searchText" name="searchText">
		```
		7. search후에도 Previous, 번호, Next 적용될 수 있도록  th:href의 인자 추가 


**게시판 데이터 조회 + 생성 + 수정 과정 form 정리**
---
![Form 동작 과정](https://user-images.githubusercontent.com/60174144/95597932-3f60b880-0a8a-11eb-8bd9-9609a5666eb7.png)

[7. JPA이용한 RESTful API 작성]
---
1. JPA이용해서 MySQL DB의 데이터 조작할 수 있는 컨트롤러 생성
	1. RestController 생성 [스프링 REST 튜토리얼 참고](https://spring.io/guides/tutorials/rest/)
	2. @GET, @POST, @GET{id}, @PUT{id}, @DELETE{id}
	3. @GET의 파라미터 '제목' 받아서 검색(타임리프 uilt.StringUtils 이용)
		1. @RequestParam으로 title,content 받기
		2. BoardRepository 수정 [Spring JAP 참고](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#reference)
		```java
		//JPA 규칙에 따라 인터페이스만 정의하면 JPA가 알아서 조회해준다.

		//title과 일치하는 데이터 반환
		List<Board> findBytitle(String title);
    
		// title이나 content둘중 하나만 일치해도 데이터 반환
		List<Board> findByTitleOrContent(String title, String content);
		```
		3. RestController에서 findByTitleOrContent(title,content)을 통해 해당하는 객체 json으로 반환

2. [PostMan](https://www.postman.com/downloads/)을 이용해서 http요청을 통해 CRUD 데이터 조작하기
	```JSON
	1. C : POST -> url : localhost:8080/api/boards -> body : raw(JSON)으로  데이터 입력 후 **Send**
	2. R : GET -> url : localhost:8080/api/boards -> **Send**
	3. U : PUT -> url : localhost:8080/api/boards/17 -> body : raw(JSON)에서 수정할 데이터 입력 후 **Send**
	4. D : DELETE -> url : localhost:8080/api/boards/17 -> **Send**
	```
[8. 사용자 테이블을 만들고 Spring Security를 적용 -> 인증 및 권한 처리, 로그인, 회원가입, 로그아웃, 페이지 권한 처리]
---
1.pom.xml의 Spring Security 의존성추가[참고](https://spring.io/guides/gs/securing-web/)
	```xml
	<dependency>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-starter-security</artifactId>
	</dependency>
	<dependency>
	  <groupId>org.springframework.security</groupId>
	  <artifactId>spring-security-test</artifactId>
	  <scope>test</scope>
	</dependency>
	```
2. WebSecurityConfigurerAdapter를 상속받은 클래스(WebSecurityConfig)를 구현하여 Security이용하기
3. DB 사용자,권한 테이블 생성
	- User(id,username,password,enabled) [ PK : id ]
	- role(id, name) [PK : id]
4. JDBC mysql 인증 설정 [샘플예제참고](https://www.baeldung.com/spring-security-jdbc-authentication)
	```java
	@Autowired
	// application.properties에 있는 정보를 인스턴스로 받아온다.
	private DataSource dataSource;	

	/**
	* 참고
	* Authentication : 로그인의 관한 설정
	* Authroization : 권한의 관한 설정
	*/
	@Autowired
	//만든 User테이블을 AuthenticationManagerBuilder 설정을 통해 스프링이 알아서 인증처리
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
	    auth.jdbcAuthentication()
	      .dataSource(dataSource) //스프링이 해당 dataSource를 사용하여 인증처리
	      .passwordEncoder(passwordEncoder()) // 스프링에서 제공하는 passwordEncoder 적용하여 알아서 pw 암호화
	      .usersByUsernameQuery("select username,password,enabled "
	        + "from user "
	        + "where username = ?") // 파라미터에 알아서 username이 들어간다.
	      .authoritiesByUsernameQuery("select username, name "
	        + "from user as u, role as r "
                     + "where username = ?");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	```
	
	
	
	
	
	
	
	
