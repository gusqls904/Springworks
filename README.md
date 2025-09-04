# Springworks 프로젝트 - Maven 설정

Spring Boot 3.5.5 + Java 21, MariaDB 기반 사이드 프로젝트를 위한 Maven 설정 문서입니다.

---

## 1. 프로젝트 정보

| 항목             | 값                           |
|-----------------|-----------------------------|
| **GroupId**     | `com.study.work`            |
| **ArtifactId**  | `springworks`               |
| **Version**     | `0.0.1-SNAPSHOT`           |
| **Packaging**   | `war`                      |
| **Java 버전**   | 21                          |
| **Spring Boot** | 3.5.5                      |
| **프로젝트 이름** | springworks                |
| **설명**        | Demo project for Spring Boot |

---

## 2. Maven Parent

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.5</version>
    <relativePath/>
</parent>
