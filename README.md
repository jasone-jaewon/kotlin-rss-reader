# kotlin-rss-reader

## 기능 구현 사항
- RSS 1개 read(동기)
- RSS n개 read(동기)
- n개 read를 비동기로 refactor

## 기능 요구 사항
- 기술 블로그를 모아 나만의 블로그 모음을 만들어 보자.
- 각 기술 블로그에 새로운 글이 게시되면 해당 사이트의 RSS가 업데이트된다.
- 각 기술 블로그의 글을 수집하여 작성된 날짜별로 내림차순으로 정렬하여 최대 10개의 글을 표시한다.
- 찾고자 하는 단어를 입력하면 제목에 해당 단어가 포함된 게시물이 표시된다.
## 선택 요구 사항
- 한 번 실행하면 10분마다 기술 블로그의 RSS 업데이트를 확인한다.
- 새 글이 발견되면 사용자에게 메시지로 알린다.
- 기존 기능 요구 사항은 계속 작동할 수 있어야 한다.
## 힌트
```kotlin
val factory = DocumentBuilderFactory.newInstance()
val xml = factory.newDocumentBuilder()
.parse("https://techblog.woowahan.com/feed")
val channel = xml.getElementsByTagName("channel").item(0)
```


// ---
// RssController -> Rss -> Post
// Post 를 저장
// Post 목록을 조회
// 출력
// ---
// 10분 주기로 돌면서 새 게시글 노티
// 신규 post = Rss 가져온 것 - 기존 Post 목록
// 신규 Post 저장
// 신규 post 노티
// ---