# B2A3_M2S
## mini MES Program

#### 요구사항 개요
* 수주 : 수주 등록 시 예상 납품일 계산 기능, 수주 등록 시 확정처리되며 취소/변경 불가
* 재고 : 원/부자재 및 완제품의 입/출고 데이터 수집 기능
* 발주 : 수주 등록 시 자동 발주 시스템
* 생산 : 수주 정보 및 설비/공정 상태에 따른 자동 생산 계획 수립 기능
* 출하 : 출하 일정, 수량, 목적지 등의 정보 조회 기능
* 현황(모니터링) : 공정별 진행률 및 수주/발주 현황, 생산량 현황 모니터링 기능



#### * 현황(메인) 페이지

![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/6f814ffc-3af8-44c3-8dc1-0454e096fc9d)
* 공정별 진행률/수주 현황/발주 현황/일일 생산량/월간 생산량 조회 기능

#### * 수주 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/79aaad39-65db-40c4-bb80-d36fad2e8b84)
* 다중 조건 검색 기능
* 업체명/품목명 자동 완성 기능
* 수주 목록 Excel 저장 기능
* 수주 클릭시 해당 수주의 상세 내용 조회 기능

#### * 수주 등록
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/3c420e6c-44df-49e9-b333-07b196c683d6)
* 업체명/품목명 자동 완성 기능
* 수주 등록시 자동발주 및 생산계획/예상납기일 자동 계산 기능
* 등록시 수주 번호 자동 넘버링 기능

#### * 발주 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/efa71b6d-a3ab-431e-ba53-361f6ee526ad)
* 다중 조건 검색 기능
* 업체명/품목명 자동 완성 기능
* 발주 목록 Excel 저장 기능
* 발주 클릭시 해당 발주의 상세 내용 조회 기능

#### * 자재 입고 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/d94250c3-c367-40f7-9517-cac4d5b9d1c7)
* 다중 조건 검색 기능
* 업체명/품목명 자동 완성 기능
* 자재 입고 목록 Excel 저장 기능

#### * 작업 계획 및 작업 실적
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/95b03951-2554-4fb3-99b3-73591dd5da64)
* 다중 조건 검색 기능
* 품목명 자동 완성 기능
* 작업 계획 및 작업 실적 상세 내용 조회 기능
* 수주 등록시 예상 작업 시간 계산하여 자동 입력 처리

#### * 출하 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/767b8a1b-d548-4b96-9423-edfd8391641b)
* 다중 조건 검색 기능
* 품목명/업체명 자동 완성 기능


#### * LOT Tracking 정방향/역방향
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/d3b8a790-1da8-4d36-a950-cdf93a75cd37)
* 자재, 공정별 현황에 LOT 번호를 부여하여 불량 발생 등의 문제가 발생했을시 상품의 자재 및 공정 추적 가능하게 함
* 다중 조건 검색 기능
* 품목명 자동 완성 기능
* Lot 목록 상세 조회 기능


#### * 공정 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/a45e0218-d5c2-4b48-a9db-8296ba425843)
* 공정 목록 조회
* 다중 조건 검색 기능
* 공정명 자동 완성 기능
* 공정 목록 Excel 저장 기능

#### * 공정 등록
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/e1ea953d-c100-4568-91c4-bbb0038d7f4b)
* 입력폼의 항목에 값을 넣으면 공정이 등록됨
* 공정상태는 공통코드로 관리

#### * 설비 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/25307787-8820-4e25-9167-72b2d9e63184)
* 설비 목록 조회 기능
* 설비명 자동 완성 기능
* 다중 조건 검색 기능
* Excel 저장 기능


#### * 설비 등록
![설비등록](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/5329438e-d3c8-4493-a95b-7da24ce03bae)
* 입력폼의 항목에 값을 넣으면 설비가 등록됨
* 생산단위, 준비시간 단위, 생산시간 단위, 설비 상태는 공통코드로 관리
* 동일한 설비 코드로 등록시 중복 alert 경고 기능


#### * BOM 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/b8b27d1c-0a87-4228-a13b-1b0381274f7e)
* BOM 목록 조회
* 제품, 자재 자동 완성 기능
* 다중 조건 검색 기능
* Excel 저장 기능

#### * 품목 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/cdc9ca42-fa0b-47ca-833a-e45e60225275)
* 품목 목록 조회
* 제품, 자재 자동 완성 기능
* 다중 조건 검색 기능
* Excel 저장 기능


#### * 품목 등록
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/0c70a849-f1bb-44e5-afc1-21da13976487)
* 입력폼의 항목에 값을 넣으면 품목이 등록됨
* 품목 구분, 품목 타입, 단위는 공통코드로 관리

#### * 라우팅 조회
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/34613ad5-aab9-4fc5-988c-e7ab4659ca68)
* 완제품 품목 조회 기능
* 품목명 자동 완성 기능
* 품목 클릭 시 품목의 라우팅 조회 기능
* 라우팅별 투입 및 생산 자재 조회 기능

#### * 라우팅 등록
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/7925ec6a-e126-4faf-a5db-aeb1cd6a2355)
* 공정, 용량, 수율, 비고 순서에 맞게 입력하여 등록

#### * 코드 관리
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/d00ce01c-18f7-4cf3-b754-57393a82a8a6)
* 다중 조건 검색 기능
* 그룹 코드 목록 조회
* 그룹 코드 클릭 시 코드 상세 조회 기능

#### * 업체 관리
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/d30b0813-8fde-4769-abc0-ebc0a804c4eb)

* 업체 목록 조회 기능
* 업체명 자동 완성 기능
* 다중 조건 검색 기능
* 업체 클릭 시 상세 조회 기능
* Excel 저장 기능

  
#### * 업체 등록
![image](https://github.com/daseuldaseul/B2A3_M2S/assets/121841821/981c65e2-4f48-411c-9080-dddecc4d9910)
* 입력폼의 항목에 값을 넣으면 업체가 등록됨



