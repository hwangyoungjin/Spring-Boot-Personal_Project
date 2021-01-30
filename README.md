# Spring-boot-Personal_Project
## 스프링부트로 웹 출시하기
### 기술스택 : SpringBoot, thymeleaf, [Bootstrap](https://getbootstrap.com/docs/4.4/getting-started/introduction/), JPA, MySQL, Spring Security

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

[3. thymeleaf, Bootstrap을 통해 레이아웃 만들기]
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
	```
	*ORM (Object Relational Mapping) 무엇인가?

	RDB 테이블을 객체지향적으로 사용하기 위한 기술입니다. RDB 은 객체지향적 (상속, 다형성, 레퍼런스, 오브젝트 등)으로 접근하기 쉽지 않습니다.
	때문에 ORM을 사용해 오브젝트와 RDB 사이에 객체지향적으로 다루기 위한 기술입니다.


	*JPA (Java Persistence API) 무엇인가?

	ORM 전문가가 참여한 EJB 3.0 스펙 작업에서 기존 EJB ORM이던 Entity Bean을 JPA라고 바꾸고 
	JavaSE, JavaEE를 위한 영속성(persistence) 관리와 ORM을 위한 표준 기술입니다. 
	JPA는 ORM 표준 기술로 Hibernate, OpenJPA, EclipseLink, TopLink Essentials과 같은 구현체가 있고 이에 표준 인터페이스가 바로 JPA입니다.


	*HIBERNATE 무엇인가?
	
	Boss에서 개발한 ORM(Object Relational Mapping) 프레임워크 입니다.

	*Hibernate 장점

	Hibernate는 특정 클래스에 매핑되어야 하는 데이터베이스의 테이블에 대한 관계 정의가 되어 있는 XML 파일의 메타데이터로 객체관계 매핑을 간단하게 수행시킵니다.
	Hibernate를 사용하면 데이터베이스가 변경되더라도 SQL 스크립트를 수정하는등의 작업을 할 필요가 없습니다.
	애플리케이션에서 사용되는 데이터베이스를 변경시키고자 한다면 설정파일의 dialect 프로퍼티를 수정함으로서 쉽게 처리할 수 있습니다.
	Hibernate는 MySQL, Oracle, Sybase, Derby, PostgreSQL를 포함한 많은 데이터베이스를 지원하며 POJO기반의 모델과도 원활하게 동작합니다.
	```
![image](https://user-images.githubusercontent.com/60174144/103418511-5965ef00-4bd2-11eb-9f7d-20b53e12c5d2.png)
1. MySQL 테이블 생성
	1. springboot_personal_project 스키마 생성
	2. 사용자 계정(sbadmin) 추가 하여 해당 계정으로 mysql connection
	3. 게시판 테이블(board) 생성

2. Spring Boot에서 mysql 데이터소스 설정
	1. [jpa의존성추가](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
	**JPA를 구현한 것중 가장 유명한 hibernate를 사용**
	2. [mysql 의존성 추가](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.21)
	3. application.properties에 mysql [데이터소스](http://blog.naver.com/PostView.nhn?blogId=ndarkness75&logNo=220991437798&categoryNo=0&parentCategoryNo=8&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView) 설정
	```java
	[time zone 관련 에러]
	java.sql.SQLException: The server time zone value '´ëÇÑ¹Î±¹ Ç¥ÁØ½Ã' is unrecognized or represents more than one time zone. 
	You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
	[원인 :  MySQL 버전 5.1.23보다 높은 버전을 사용하면 MySQL 타임존의 시간표현 포맷이 달라져서 connector 에서 인식을 하지 못한다]
	[해결방법](https://irerin07.tistory.com/14)
	1. application.properties의 mysql 데이터 소스 url 맨뒤인 '스키마이름?' 뒤에
	useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
	추가
	2. MySQL 버전을 5.1.23으로 낮추기
	```

3. Model, Repository 클래스 생성 및 어노테이션 설정
	1. Model (Board Class) 추가
		- 클래스의 @Entity 추가
		- PK에 해당하는 값의 @ID, @GeneratedValue(strategy = GenerationType.IDENTITY) 설정
	2. Repository (BoardRepository Interface) 추가 [참고](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#reference)
		- BoardRepository는  JpaRepository interface를 상속받는다
		```java
		@Repository
		public interface BoardRepository extends JpaRepository<Board, Long> {
		  //JPA 규칙에 따라 인터페이스만 정의하면 JPA가 알아서 조회
		}
		```
	3. BoardController 수정
		- BoardRepository를  @Autowired로 의존성 주입하여 데이터 접근 사용
	4. Front : list.html 수정 [참고](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)


4. 게시판 데이터 조회 후 화면에 출력
	1. bootstrap에서 [테이블 레이아웃](http://bootstrapk.com/examples/theme/) 이용
	2. thymeleaf 리스트 이용
		- simple expressions 사용
		```html
		<!--Variable Expressions:  ${...} // controller 에서 전달해주는 모델(객체) 받기-->
		<!--Link URL Expressions:  @{...} // 현재 html 위치에 맞게 classpath 자동 적용됨-->
		
		<!-- controller에서 담은 boards 이름의 모델 객체(리스트) 받기 -->
		<tr th:each="board : ${boards}">
		 <!-- list각 entry를 board이름으로 받아서 id 속성 값 사용 -->
              	 <td th:text="${board.id}">제목</td>
             			.
			.
			.
		```
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
		2. GETMapping (/form)에 폼에서 전달한 파라미터 전달받기(@RequestParam 이용)
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
3. 정리
	1. RestAPI
	- EndPoint (API가 서버에서 리소스에 접근할 수 있도록 가능하게 하는 URL)

	| Methods | Urls | Actions |
	|---|:---:|:---:|
	| GET | /api/boards | 게시판(페이지로 변환) 반환 |
	| POST | /api/boards | 게시판에 게시글 추가 |
	| GET | /api/boards{id} | 해당 id에 맞는 게시글 반환 |
	| PUT | /api/boards{id} | 해당 id에 맞는 게시글 수정 |
	| DELETE | /api/boards{id} | 해당 id에 맞는 게시글 삭제 |

	2. **Spring에서 Controller의 전달인자 2가지 사용방법** (복합적으로 사용도 가능하다)
		1. Type 1 => http://localhost8080/api/boards?title=123123&content=내용
			- 매개변수단에 **@RequestParam** 사용
			- 파라미터의 이름(ex>title)과 값(ex>123123)을 함께 전달
			- 페이지 및 검색 정보를 함꼐 전달할때 많이 사용
			```java
			@GetMapping("/boards")
			List<Board> all(@RequestParam(required = false) String title,
			                     @RequestParam(required = false) String content) {
			//url ?와 &를 통해 들어오는 boards의 제목orcontent 검색을 받기 위해
			//required = false 이면 파라미터 안들어와도 에러x (true가 기본값 = 안들어오면 에러!)
			  if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) { //둘다 전달이 안 된경우
			      return repository.findAll();
			  }
			  else { //title 이나 content 둘중 하나만이라고 전달이 된 경우
			      //http://localhost:8080/api/boards?title=Hello&content=123123 으로 요청시
			      return repository.findByTitleOrContent(title, content);
			  }
			}
			```
		2. Type 2 => http://localhost8080/api/boards/1
			- uri에 {idx}사용 후 매개변수단에 **@PathVariable** 사용
			- Rest api에서 값을 호출할때 주로 많이 사용
			```java
			// Single item
			@GetMapping("/boards/{id}")
			Board one(@PathVariable Long id) {
			   //http://localhost:8080/api/boards/19 으로 요청시 id 19에 해당하는 데이터 json으로 반환
			   return repository.findById(id).orElse(null);
			}			
			```
	3. 참고
		- **요청 본문(Body)에 들어있는 데이터 받기**
			- 매개변수단에 **@RequestBody** 이용
			1. postman 에서 POST 전송
			![POST json형식](https://user-images.githubusercontent.com/60174144/99869104-cd909880-2c0b-11eb-9ddb-57a2c2c5894c.png)
			2. @RequestBody Board newBoard 매개변수로 HttpMessageConverter를 통해 변환한 객체가 들어간다.
			```java
			@PostMapping("/boards") 
			Board newEmployee(@RequestBody Board newBoard) { //@Valid를 통해 검증 가능, BindingResult 아규먼트를 사용해 코드로 검증에러 확인가능
			   return repository.save(newBoard);
			}
			```


[8. Spring Security 활용해서 login,register,logout]
---
##### 사용자 테이블을 만들고 Spring Security를 적용 -> 인증 및 권한 처리, 로그인, 회원가입, 로그아웃, 페이지 권한 처리
---
1. pom.xml의 Spring Security 의존성추가 [참고](https://spring.io/guides/gs/securing-web/)
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
	- **Access 관련 부분은 모두 여기에 설정**
	```java
	@Override
	protected void configure(HttpSecurity http) throws Exception {
                    http
                            //어떤 보안설정을 할것인지 정한다.
                            .authorizeRequests()
                                //css 경로 추가
                                .antMatchers("/","/account/register","/css/**").permitAll() // permitAll을 통해 누구나 접근 할 수 있다고 설정
                                .anyRequest().authenticated() // Matching이 안된 요청은 모두 여기에 걸리고 authenticate(로그인)가 있어야만 볼 수 있도록
                                .and()//이어서
                            .formLogin()//로그인설정
                                .loginPage("/account/login")//로그인 폼 클릭시 자동으로 redirect 되어 login 폼으로 이동
                                .permitAll() // 로그인 되지 않은 사용자이므로 모두 접근 가능하도록
                                .and()//이어서
                            .logout()//로그아웃
                                .permitAll();
                }
	```

3. DB 사용자,권한 테이블 생성 **user_role : User와 Role테이블 ManyToMany**
	- user(id,username,password,enabled) [ PK : id ]
	- role(id, name) [PK : id]
	- user_role(user_id,role_id) [PK : user_id,role_id], [FK : user_id(user참조), role_id(role참조)]

4. JDBC mysql 인증 설정 [샘플예제참고](https://www.baeldung.com/spring-security-jdbc-authentication)
	- *시큐리티에서 사용자, 권한 관련 DB는 Config 클래스에서 직접 처리
		- 다른방법으로 @Service단에서 UserDetailsService 인터페이스를 구현하여 인증처리 가능
	- **SecurityConfig에서 AuthenticationManagerBuilder를 주입해서 인증에 대한 처리**
	- 만든 DB의 User테이블에 대해  jdbcAuthentication 설정
	* 사용자가 입력한 id/pw와 DB(User테이블)의 내용을 비교
	```java
	@Autowired
	// application.properties에 있는 정보를 인스턴스로 받아온다.
	private DataSource dataSource;	

	/**
	* 참고
	* Authentication : 로그인의 관한 설정 : 로그인 하는 과정을 의미
	* Authroization : 권한의 관한 설정 : 허용(Access) 하는 것을 의미
	*/
	@Autowired 
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
	    auth.jdbcAuthentication() // 권한 방식 jdbcAuthentication으로 지정
	      .dataSource(dataSource) //스프링이 해당 dataSource를 사용하여 인증처리
	      .passwordEncoder(passwordEncoder()) // 스프링에서 제공하는 passwordEncoder 적용하여 알아서 pw 암호화
	      .usersByUsernameQuery("select username,password,enabled "
	        + "from user "
	        + "where username = ?") // 파라미터에 알아서 username이 들어간다.
	      .authoritiesByUsernameQuery("select u.username, r.name " //권한의 관한 설점
                    + "from user_role as ur "
                    + "inner join user as u on ur.user_id = u.id "
                    + "inner join role as r on ur.role_id = r.id "
                    + "where u.username = ? ");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	```
	```java
	* AuthenticationManagerBuilder : 인증 매니저를 생성하는 빌더
	  - 메모리상 정보를 이용하거나, JDBC, LDAP등의 정보를 이용해서 인증 처리가 가능

	* authoritiesByUsernameQuery(sql) : 권한을 불러오는 쿼리를 작성
	  - 출력결과는 [사용자이름], [권한명]을 순서대로 지정

	* UsersByUsernameQuery(sql) : 사용자 목록을 가져오는 쿼리를 지정
	  - 출력결과는  [사용자이름], [비밀번호], [Enabled] 3개를 순서대로 지정
	```
	
5. 로그인 화면 만들기
	- [login.html](https://getbootstrap.com/docs/4.4/examples/sign-in/)
	- id와 pw 의 input에서 name 속성 추가 + [error처리 코드](https://spring.io/guides/gs/securing-web/) 추가
	```html
	<form class="form-signin" th:action="@{/account/login}" method="post">
	  <!-- error처리-->
	  <div th:if="${param.error}" class="alert alert-danger" role="alert">
	        Invalid username and password.
	  </div>
	   div th:if="${param.logout}" class="alert alert-primary" role="alert">
	        You have been logged out.
	  </div>  
	  	.
		.
	  <!-- input 요소의 이름을 명시 -> 타임리프가 controller단에 parameter로 넘길때 name값으로 넘긴다. -->
	  <input type="email" id="Username" name="username"  class="form-control" placeholder="Username" required autofocus>
     	  <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
      		.	
		.
		.
	```
	
6. 회원 가입 화면 만들기
	- register.html
	```java
	[에러발생]
	: form에서 username과 password 받아서 db에 저장할때 발생
	[에러문구]
	: java.sql.SQLException: Field 'id' doesn't have a default value
	[해결](https://blog.naver.com/PostView.nhn?blogId=imf4&logNo=220762181574)
	- form에서 User id값 안받는데 id 컬럼이 AUTO_INCREMENT가 아니기 떄문에 id값 찾으려는에러 발생
	- User 테이블의 id 컬럼 AUTO_INCREMENT으로 수정
	```

7. Model class 만들기 [JPA 연관관계 매핑]

	- @JoinTable

	| 옵션 | 설명 |
	|---|:---:|
	| `name` | 조인 테이블 명 |
	| `joinColumns` | 현재 엔티티를 참조하는 외래키 |
	| `inverseJoinColumns` | 반대방향 엔티티를 참조하는 외래키 |
	
	- @JoinColumn

	| 옵션 | 설명 |
	|---|:---:|
	| `name` | 매핑할 외래 키 이름 |
	| `referencedColumnName` | 	외래 키가 참조하는 대상 테이블의 컬럼명 |

	- User, Role, 의 Model class 만들기 [ManyToMany의 user_role 이용](https://www.baeldung.com/jpa-many-to-many)

8. 로그인 관리 하는 Controller 만들기
	- AccountController
	```java
                @Controller
                @RequestMapping("/account")
                public class AccountController {
                
                    @Autowired
                    UserService userService;
                
                    @GetMapping("/login")
                    public String login(){
                        return "account/login";
                    }
                
                    @PostMapping("/register")
                    public String register(User user){
                        userService.save(user);
                        return "redirect:/"; //가입 완료시 바로 로그인되어 home(index.html)으로 이동
                    }
                
                    @GetMapping("/register")
                    public String register(){
                        return "account/register";
                    }
                }
	```
8. Repository 만들기
	- UserRepository
	```java
                @Repository
                public interface UserRepository extends JpaRepository<User, Long> {
                
                    //JPA 규칙에 따라 인터페이스만 정의하면 JPA가 알아서 조회해준다.
                
                }
	```

9. 권한 및 패스워드 암호화를 하는 비지니스 로직이 필요하므로 Service  추가
	- UserService 
	```java
                @Service
                public class UserService {
                
                    @Autowired
                    UserRepository userRepository;
                
                    @Autowired
                    PasswordEncoder passwordEncoder;
                
                    public User save(User user){ // service에서 패스워드인코더 와 enable값 넣고 저장
                        String encodedPassword = passwordEncoder.encode(user.getPassword()); //패스워드 인코더
                        user.setPassword(encodedPassword);
                        user.setEnabled(true);
                        Role role = new Role();// Role테이블의 ROLE_USER을 검색해서 가져올 수 있지만 그냥 간편하게 하드코딩
                        role.setId(Long.valueOf(1));
                        user.getRoles().add(role); //해당 user를 save하면 user_role 테이블에 해당 user_id와 role_id가 저장된다
                        return userRepository.save(user);
                    }
                }
	```

10. 메인화면(common)에 [조건](https://www.thymeleaf.org/doc/articles/springsecurity.html)에 맞는 로그인/로그아웃 버튼 설정
	- pom.xml 에 [Spring Security integration module](https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-springsecurity5/3.0.4.RELEASE) 의존성 추가
	- common.html의 securitu namespace 추가
	```html
	<html xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
	```
	```html
	[sec 동작안하는 문제] 
	<div sec:authorize="isAuthenticated()">
	  This content is only shown to authenticated users.
	</div>
	Logged user: <span sec:authentication="name">Bob</span>
	Roles: <span sec:authentication="principal.authorities">[ROLE_USER, ROLE_ADMIN]</span>
	[해결방법?]
	<dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        	</dependency>
	이었다가
	<!-- https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-springsecurity4 -->
	<dependency>
	    <groupId>org.thymeleaf.extras</groupId>
	    <artifactId>thymeleaf-extras-springsecurity4</artifactId>
	    <version>2.1.3.RELEASE</version>
	</dependency>
	으로 바꿨다가
	다시
	<dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        	</dependency>	
	으로 다시 바꾸니깐 됨...
	```
---
**Security 간단 정리**
---
![Security정리](https://user-images.githubusercontent.com/60174144/98073146-04944980-1eab-11eb-9ff7-0fa492de59d4.png)

![spring-Security](https://user-images.githubusercontent.com/60174144/103727757-6d20b380-501f-11eb-9ec5-de0a34e9dabb.png)
```
* 모든 인증은 인증 매니저를 통해 이루어 진다
* 인증 매니저를 생성하기 위해서는 인증 매니저 빌드(AuthenticationManagerBuilder)를 이용
* 인증 매니저를 이용해 인증이라는 작업을 수행
* 인증 매니저들은 인증/인가를 위한 UserDetailsService를 통해서 필요한 정보들을 가져온다
* UserDetails는 사용자의 정보 + 권한 정보들의 묶음
* Authentication - Principal과 GrantAuthority 제공 이를 통해 현재 인증된 객체를 가져올 수 있다.
   - Principal
      : “누구"에 해당하는 정보. 
      : UserDetailsService에서 리턴한 그 객체.
      : 객체는 UserDetails 타입. 
```
---

[9. JPA를 이용하여 @OneToMany 관계 설정하기]
---
##### One(게시자) To Many(게시글) 
---
1. @ManyToOne 어노테이션을 이용하여 Board 조회시 User테이블 조회 하도록 설정하기
	1. Board클래스의 User필드 추가
	```java
	@ManyToOne
	//이때 name은 어떠한 col이 User테이블과 연결이 될지결정,
	@JoinColumn(name = "user_id")
	//해당 변수는 board의 id와 연결된 user객체를 의미
    	private User user;
	```
	2. Board 테이블에서 User테이블과 연결될 **user_id** 컬럼 추가 
	  (해당컬럼은 FK으로 user테이블의 id값 참조)
	3. list.html에서 작성자 부분에 타임리프 board.user_id 추가
	```html
	list.html 파싱할때 에러발생
	[에러문구]
	Caused by: org.thymeleaf.exceptions.TemplateProcessingException: Exception evaluating SpringEL expression: "board.user.username" (template: "board/list" - line 44, col 19)
	[에러이유]
	* Board 테이블 User필드의 대한 getter,setter 가 없어서 발생
	[해결]
    	public User getUser() {
    	    	return user;
    	}

    	public void setUser(User user) {
     	   	this.user = user;
    	} 
	```
2. 게시판의 글 작성시 작성자의 로그인한 username들어가도록 설정
	```java
	form에서 user_id를 직접 전달 하게 되면 다른사람이 개발자도구를 활용해서 다른사람의 user_id를 보낼 수 있다 
	-> 사용자가보낸 인증정보는 절대 믿어선 안되므로 서버쪽에서 가지고 있는 인증 정보를 활용해야한다 
	-> Controller에서 직접 Board의 user_id 확인 후 담아준다 
	```
	- cf.현재 로그인 한 사용자 정보 받아오기 2개 방법 [Spring Security의 Authentication 활용](https://dzone.com/articles/how-to-get-current-logged-in-username-in-spring-se)
		```java
		* 첫번째 Controller에서 매개변수로 받아오기 [해당 프로젝트에서 이용]
		    @PostMapping("/form")
    		    public String create(@Valid Board board,
                         		BindingResult bindingResult,
                         		Authentication authentication){ 
			// authentication에 알아서 현재 인증된 객체의 정보가 담겨온다.
	
		* 두번째 매개변수가 아닌 SecurityContextHolder.getContext 전역변수 활용하기
		    Authentication a = SecurityContextHolder.getContext().getAuthentication();
		    String user_name = a.getName();
		```

	1. Controller에서 현재 사용자의 username을 Authentication을 이용하여 받고 이를 service를 통해 저장 
	2. BoardService 만들어서 username과 board를 매개변수로 받고 username에서 user찾아서 Board에 넣는다
	3. BoardRepository에서 해당 board 저장 
	
3. @OneToMany, @ManyToOne 어노테이션을 이용하여 양방향 매핑 설정
4. Cascade, OrphanRemoval 속성을 이용하여 매핑된 데이터 조작
	
	
	
