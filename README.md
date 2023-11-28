# 📺 MY_Media


## :scroll: 목차
1. 프로젝트 소개
2. 개발 기간

## :telephone: 프로젝트 소개
Youtube API를 받아와 인기동영상, 구독리스트, 검색창 을 구현하였습니다.

## :alarm_clock: 개발 기간 
2023.09.25 ~ 2023.10.05 


:runner: 이수진
- [x]  HomeFragment 인기동영상, 카테고리별 인기동영상
- [x]  README 작성
      


## 기능 설명
 #### MainActivity
- BottomNavigationView 

 #### HomeFragment
- 인기동영상 (키워드에 해당하는 인기동영상 recyclerview로 출력)(가로이동)
- 구독리스트 (현재 계정의 구독 계정 recyclerview로 출력(StahheredGridLayout)
- 동영상 클릭 시, videoDeatilFragment 로 이동

 #### SearchFragment
- 검색어 입력 시 검색 결과창에 recyclerview로 출력
- 동영상 클릭 시, videoDetailFragment 로 이동

 #### VideoDetailFragment
- 좋아요, 조회수 데이터 출력
- 공유버튼
  
 #### MyVideoFragment
- DetailFragment에서 '좋아요' 클릭한 영상


