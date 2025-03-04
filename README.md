# TodoServer Application : 스프링부트 프로젝트 실습하기

TodoServer는 할일 관리 웹 애플리케이션을 위한 RESTful API 서버 프로젝트입니다. 패스트캠퍼스의 시그니처 백엔드 Path Part 18 강의를 기반으로 학습하였습니다. 이번 프로젝트를 통해 기본적인 CRUD 기능 구현, 그리고 할일 생성 기능에 대한 단위 테스트 작성을 연습해보았습니다.

<br>

### 개발환경
JDK 22, Gradle 8.13, Spring Boot 3.0.4, H2, JPA, Lombok

<br>

### 테스트환경
Spring Boot Test, Mockito, JUnit 5

<br>

### 주요기능
- 할일 추가, 삭제, 수정, 조회
- 할일의 상태 변경
- 할일 겸색 기능
- 기한별 할일 관리
<br>

### 리소스 표현
- 할 일 (Task)
  - id : Long
  - title: String
  - description: String
  - dueDate: LocalDate
  - status: TaskStatus
 
  
- 할 일 상태 (TaskStatus): Enum
  - TODO: 할 일이 등록되었지만 아직 작업이 시작되지 않은 상태
  - IN_PROGRESS: 현재 진행 중인 상태
  - ON_HOLD: 작업이 일시 중단된 상태
  - COMPLETED: 작업이 완료된 상태
  - CANCELLED: 작업이 취소된 상태로 더 이상 진행하지 않음
 
    
<br>

### API 명세서

| **Method** | **Endpoint**              | **Request Body**                                                                                  | **Response Body**                                                                                                          | **Description**                             |
|------------|---------------------------|---------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|---------------------------------------------|
| POST       | `/tasks`                  | `{ "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01" }`                   | `{ "id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "TODO" }`                 | 새로운 할 일 생성                         |
| GET        | `/tasks`                  | `dueDate (optional)`                                                                                | `[{"id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "TODO"}, ...]`             | 모든 할 일 조회(마감일이 있을 경우 마감일에 해당하는 할 일 조회) |
| GET        | `/tasks/{id}`             | `N/A`                                                                                              | `{ "id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "TODO" }`                 | 특정 ID에 해당하는 할 일 조회             |
| GET        | `/tasks/status/{status}`  | `N/A`                                                                                              | `[{"id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "TODO"}, ...]`             | 특정 상태에 해당하는 할 일 모두 조회      |
| PUT        | `/tasks/{id}`             | `{ "title": "Updated Task 1", "description": "Do something else", "dueDate": "2023-05-02" }`       | `{ "id": 1, "title": "Updated Task 1", "description": "Do something else", "dueDate": "2023-05-02", "status": "TODO" }`     | 특정 ID에 해당하는 할 일 수정             |
| PATCH      | `/tasks/{id}/status`      | `{ "status": "IN_PROGRESS" }`                                                                       | `{ "id": 1, "title": "Task 1", "description": "Do something", "dueDate": "2023-05-01", "status": "IN_PROGRESS" }`           | 특정 ID에 해당하는 할 일의 상태 변경      |
| DELETE     | `/tasks/{id}`             | `N/A`                                                                                              | `{ "success": true }`                                                                                                      | 특정 ID에 해당하는 할 일 삭제             |

