# Met:Ajou 인증서버



### 1. Summary

이 프로젝트는 기본적으로 인증과 인가만을 수행합니다.

다른 로직들은 네트워크 상에서 분리되어 `Http`방식으로 통신 및 연동되도록 설계됐습니다.

이 코드는 기본적으로 프론트 서버가 여러 `API`를 호출하는 방식을 가정하며, 이에 따라 `CORS`와 도메인 세팅을 추가적으로 해주어야합니다.



### 2. How to Start

이 프로젝트를 사용하기 위해선 다음과 같은 기술이 필요합니다.

* `MySql`
* `Redis` <= (추가될 예정)
* `Jdk-17`

위의 내용들을 모두 설치했다면,

 `src.main.resources` 위치에 `secret.properties`를 만듭니다.

| # secret.properties ...<br/><br/>#Service Secret Key<br/>spring.project.role.adminkey={String}<br/><br/>#Service Authkey Settings<br/>spring.project.jjwt.secretkey={String}<br/>spring.project.jjwt.expiration=28800<br/>spring.project.jjwt.tokenname=MSGACCESSTOKEN<br/><br/>#R2DBC Settings<br/>spring.datasource.user.name={String} ex:).name=AccountDB<br/>spring.datasource.user.driver=mysql<br/>spring.datasource.user.url=127.0.0.1<br/>spring.datasource.user.port=3306<br/>spring.datasource.user.username=root<br/>spring.datasource.user.password={String}<br/><br/>#OAuth2 Settings<br/>spring.security.oauth2.client.registration.google.client-id: {String}<br/>spring.security.oauth2.client.registration.google.client-secret: {String}<br/><br/>#Email Sender Settings<br/>spring.mail.host=smtp.gmail.com<br/>spring.mail.port=587<br/>spring.mail.username={String} minshigee@gmail.com<br/>spring.mail.password={String}<br/>spring.mail.properties.mail.smtp.auth=true<br/>spring.mail.properties.mail.smtp.starttls.enable=true<br/>spring.mail.propertirs.mail.smtp.ssl.enable = true<br/><br/>#Service Root Domain<br/>spring.service.root.domain= {String} <= ex:)metajou.kro.kr<br/><br/>#CORS Domain Settings<br/>spring.client.webserver.url={String} <= ex:)http://www.metajou.kro.kr:3000<br/>spring.client.cors.url={String} <= ex:)http://www.metajou.kro.kr:3000 |
| :----------------------------------------------------------- |



### 3. How to Use

이 프로젝트의 `API`는 `Swagger`를 이용하여 확인하실 수 있습니다.

프로젝트를 실행한 뒤 아래의 `URL`과 같이 `/api` 로 접근하면 `Swagger`페이지로 리다이렉트할 수 있습니다.

| http://localhost:8080/api |
| :------------------------ |

