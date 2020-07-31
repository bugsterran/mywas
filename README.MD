##### 1. HTTP/1.1 의 Host 헤더를 해석하세요.
```
o 예를 들어, a.com 과 b.com 의 IP 가 같을지라도 설정에 따라 서버에서 다른 데이터를 제공할 수 있어야 합니다.
```
```
config.json 파일에 (VirtualHosts)설정으로 분리하여 Header를 해석하여 서로 다른 데이터가 제공 가능하도록 구현 완료
```
##### 2. 다음 사항을 설정 파일로 관리하세요.
```
o 파일 포맷은 JSON 으로 자유롭게 구성하세요. 
o 몇 번 포트에서 동작하는지 
o HTTP/1.1 의 Host 별로 
  ▪ HTTP_ROOT 디렉터리를 다르게
  ▪ 403, 404, 500 오류일 때 출력할 HTML 파일 이름
```
```
config.json 파일에 port 설정및 host 별로 ROOT 디렉토리 및 오류에 따른 HTML 파일 처리 완료
```
##### 4. 다음과 같은 보안 규칙을 둡니다.
```
o 다음 규칙에 걸리면 응답 코드 403 을 반환합니다.
▪ HTTP_ROOT 디렉터리의 상위 디렉터리에 접근할 때, 예, http://localhost:8000/../../../../etc/passwd
▪ 확장자가 .exe 인 파일을 요청받았을 때
추후 규칙을 추가할 것을 고려해주세요.
```
```
enum에 규칙을 추가하거나 삭제 가능하도록 했으며, config.json 파일을 활용하여 보안 규칙에 대한 설정 또는 미설정이 가능 하도록 처리 

```
##### 5. logback 프레임워크 http://logback.qos.ch/를 이용하여 다음의 로깅 작업을 합니다.
```
o 로그 파일을 하루 단위로 분리합니다.
o 로그 내용에 따라 적절한 로그 레벨을 적용합니다.
o 오류 발생 시, StackTrace 전체를 로그 파일에 남깁니다.
```
```
logback을 활용하여 하루 단위로 파일 분리, 단순 정보 로그는 INFO 에러의 경우 ERROR 처리 기본 모드는 INFO, 오률 발생 시 StackTrace 처리
```
##### 6. 간단한 WAS 를 구현합니다.
```
o 다음과 같은 SimpleServlet 구현체가 동작해야 합니다.
▪ 다음 코드에서 SimpleServlet, HttpRequet, HttpResponse 인터페이스나 객체는 여러분이 보다 구체적인 인터페이스나 구현체를 제공해야 합니다. 표준 Java Servlet 과는 무관합니다.
o URL 을 SimpleServlet 구현체로 매핑합니다. 규칙은 다음과 같습니다.
▪ http://localhost:8000/Hello --> Hello.java 로 매핑
▪ http://localhost:8000/service.Hello --> service 패키지의 Hello.java 로 매핑
o 과제는 URL 을 바로 클래스 파일로 매핑하지만, 추후 설정 파일을 이용해서 매핑하는 것도
고려해서 개발하십시오.
▪ 추후 확장을 고려하면 됩니다. 설정 파일을 이용한 매핑을 구현할 필요는
없습니다.
▪ 설정 파일을 이용한 매핑에서 사용할 수 있는 설정의 예, 
{“/Greeting”: “Hello”, “/super.Greeting”: “service.Hello”}
```
```
config.json에서 UseURLMapping 옵션에 따라 처리 하도록 하였으며, url 과 sevletClass 맵핑 가능하도록 처리(자바 리플렉션을 활용)
```
##### 7.현재 시각을 출력하는 SimpleServlet 구현체를 작성하세요.
```
o 앞서 구현한 WAS 를 이용합니다.
o WAS 와 SimpleServlet 인터페이스를 포함한 SimpleServlet 구현 객체가 하나의 JAR 에
있어도 괜찮습니다.
▪ 분리하면 더 좋습니다.
```
```
SimpleServlet > MyHttpServet에 현재 시각을 출력하는 구현체 작성 하였으며, MyHttpServlet2에는 일반 메시지로 작성
6번을 위한 작업 완료

```
#### 8. 앞에서 구현한 여러 스펙을 검증하는 테스트 케이스를 JUnit4 를 이용해서 작성하세요.
```
Config 간단 검증 구현 밖에 하지 못함.
```