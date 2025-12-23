# Dùng JRE (Runtime) thay vì JDK để giảm dung lượng image (~120MB so với ~350MB)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy file jar
COPY target/*.jar app.jar

EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]