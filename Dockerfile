FROM java:8

ARG JAR_RELEASE_PATH

ARG JAR_POSE=8080

RUN echo "ARGS is ${JAR_RELEASE_PATH}"

COPY ${JAR_RELEASE_PATH}  app.jar

ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+8","/app.jar"]

EXPOSE ${JAR_POSE}
