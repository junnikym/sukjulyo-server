<center>
	<img src="./readme/images/head_image_1.png" width="100%" alter="석줄요 소개 1" />
	<img src="./readme/images/head_image_2.png" width="100%" alter="석줄요 소개 2" />
	<img src="./readme/images/head_image_3.png" width="100%" alter="석줄요 소개 3" />
</center>

<br/><br/>

# Introduce

<h3>오늘의 소식을 3줄로 소식을 쉽고 빠르게!</h3>
 최근 1분만, 1분미만 등.. 바쁜 현대인들을 위해 짧은 시간 안에 유익한 정보를 설명해주는 유튜브 채널이 늘고 있습니다. 이처럼 10~15분 채 안 되는 영상매체도 꺼리는 사람에겐 긴 글로 이루어진 뉴스는 쥐약일 것입니다. 긴 글로 이루어진 뉴스를 짧게 3줄로 짧게 요약하여 오늘의 소식을 간단하게 전달한다면 좀 더 많은 사람이 세상 소식에 대해 귀 기울일 수 있지 않을까 하여 프로젝트를 진행하게 되었습니다.

# System Architecture
<center>
	<img src="./readme/images/system_architecture_1.png" width="100%" alter="Database ERD" />
</center>
각 언론사의 RSS feed를 통해 업데이트 된 뉴스를 수집하여 각 뉴스의 키워드, 요약본을 데이터를 서버를 통해 DB에 저장합니다. 사용자가 서비스 이용 시 사용자 정보를 기반으로 뉴스를 추천받아, 해당 뉴스에 저장된 요약본을 사용자에게 전달해주게 됩니다.

<br/><br/>

| Part | Link |
|---|---|
| 요약 밑 추천 기능 | <https://github.com/junnikym/sukjulyo> |
| 클라이언트 앱 | <https://github.com/solarpark7346/sukjulyo-app>

<br/>

## Tech Stacked
 - Spring Boot 2.5.5 ( Java 11 )
 - JPA with mySQL
 - Spring Security & OAuth2 ( Kakao Login )
  
<br/>

## Database ERD
<center>
	<img src="./readme/images/db_erd.png" width="100%" alter="Database ERD" />
</center>

<br/>

-----
Sukjulyo is [MIT licensed](LICENSE).