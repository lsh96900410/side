# 프로젝트 소개
### 학원에서 배웠던 기술들과 inflearn 김영한님의 강의를 복습하며 
### 공부하고 싶었던 querydsl , Spring Security ,AWS , CI/CD 기술들을 학습하며 만든 프로젝트 입니다.

# 개발 환경

<img alt="Html" src ="https://img.shields.io/badge/springboot-6DB33F.svg?&style=for-the-badge&logo=springboot&logoColor=white"/> <img alt="Html" src ="https://img.shields.io/badge/springsecurity-6DB33F.svg?&style=for-the-badge&logo=springsecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=amazondocumentdb&logoColor=white"> <img src="https://img.shields.io/badge/querydsl-007396?style=for-the-badge"> 

<img src="https://img.shields.io/badge/gradle 8.3-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">

 <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
 <img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">

 <img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> <img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"> <img src="https://img.shields.io/badge/codedeploy-amazons3?style=for-the-badge&logo=amazonec2&logoColor=white">
 <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> 



# 🖼️ Architecture

![아키텍처 ](https://github.com/lsh96900410/studyProject/assets/133841235/e7b8e06c-bc34-40a5-9011-e9897179da8c)

###

![순서도](https://github.com/lsh96900410/studyProject/assets/133841235/b83030cc-d9fd-495a-b972-c2eae8a457ac)

# 🔎 도메인 모델 및 엔티티 설계

![jpashop 도메인 모델](https://github.com/lsh96900410/studyProject/assets/133841235/89160ab3-5283-4c4f-be33-3469fa45fb96)

###

![jpashop 엔티티 설계](https://github.com/lsh96900410/studyProject/assets/133841235/5d0d7d4a-e5d4-4086-80b4-4db29c9f9587)

# 🔎 시퀀스 다이어그램 

![jpashop 시퀀스 다이어그램](https://github.com/lsh96900410/studyProject/assets/133841235/12d8f41f-e373-44ac-8899-839516b215a5)

![쿼리 dsl](https://github.com/lsh96900410/Danaga/assets/133841235/8a38b2af-179a-4d1a-8a43-5c0b2e92be18)


# 🔎 Check

![ERROR](https://github.com/lsh96900410/practice/assets/133841235/fd796ece-6061-460f-b61a-ef3b0e183e4f)

CD 작업 중, EC2의 CodeDeploy-agent 가 정상적으로 S3 Bucket에 접근하여 파일을 가져오지만, 실행이 안되는 상황.
CodeDeploy 로그 상에서도 빌드,배포 과정에서 오류난 부분이 없었다.
deploy.sh 파일에서 8라인까지는 작업 완료된 것을 확인 후, 13 라인을 체크하기위해 EC2에 배포된 프로젝트를 실행하며 실행 포트를 확인 해보기로했다.

###
![EC2 netstat](https://github.com/lsh96900410/practice/assets/133841235/3897c163-b998-4d24-b384-2dc62273e32f)

실행 포트 번호를 확인 하니 .jar 가 아닌 .java 로 실행 중이였다...
그렇기에 13라인에서의 .jar 파일로 진행되는 부분이 정상 실행이 안되고 있었다.

###
![해결](https://github.com/lsh96900410/practice/assets/133841235/f454e043-03cf-40dd-b8b1-47b120b9ce5e)

.jar -> .java로 바꾸니 해결이 되긴 하였지만, 왜 .jar 파일이 아닌 .java로 실행되어지는지는 더 알아봐야겠다.

# 👨🏻‍🎓 Todo

### 1. csrf ,cors 공부 후 적용 + JWT 적용 해보기
### 2. Item 상속 구조 + Category 적용
### 3. 새로운 기술 학습 할 때 마다 적용해보기

