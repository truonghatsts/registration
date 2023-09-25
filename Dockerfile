FROM eclipse-temurin:17.0.8.1_1-jre-alpine
LABEL authors="truonghatsts@gmail.com"
COPY target/*.war app.war
ENTRYPOINT ["java","-jar","/app.war"]