# backend
## application.yml
```yml
spring:
  servlet:
    multipart:
      maxFileSize: 50MB
      maxRequestSize: 50MB
      
  datasource:
    url: jdbc:mysql://[AWS_RDS_ENDPOINT]:[PORT]/[DATABASE_NAME]
    username: [USERNAME]
    password: [PASSWORD]
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        format_sql: true

  security:
    oauth2:
      client:
        registration:
          google:
            client-id: [GOOGLE_CLIENT_ID]
            client-secret: [GOOGLE_CLIENT_SECRET]
            redirectUri: "{baseUrl}/oauth2/callback/{registrationId}"
            scope:
              - profile
              - email

logging:
  level:
    org.hibernate.SQL: debug
    # org.hibernate.type: trace

cloud:
  aws:
    credentials:
      accessKey: [AWS_ACCESS_KEY]
      secretKey: [AWS_SECRET_KEY]
    s3:
      bucket: [BUCKET_NAME]
    region:
      static: ap-northeast-2
    stack:
      auto: false

front:
  url: [FRONT_SERVER_URL]

app:
  tokenSecret: [TOKEN_SECRET]
  tokenExpirationMsec: 864000000
  authorizedRedirectUris:
    - [AUTHORIZED_REDIRECT_URI]
```

## Getting Started
```
gradlew build

cd ./build/libs/
java -jar tcat-0.0.1-SNAPSHOT.jar
```
