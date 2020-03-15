# Using global ARG in order to access main class on the second build stage.
# More info: https://github.com/moby/moby/issues/37345
ARG MAIN_CLASS

FROM gradle:5.2.1-jdk8-alpine AS build

LABEL maintainer="mai18043@uom.edu.gr"

# Gradle needs permission to build
COPY --chown=gradle:gradle . /workspace/src

WORKDIR /workspace/src

RUN gradle clean build --no-daemon

# Inflate application jar to take advantage of layered dependencies
RUN mkdir -p build/dependency && \
    cd build/dependency && \
    jar -xf ../libs/*.jar

# Use JRE version on runtime
FROM openjdk:8-jre-alpine

ARG MAIN_CLASS
ENV ENV_MAIN_CLASS=${MAIN_CLASS}

ARG SRC_PATH=/workspace/src/build/dependency
COPY --from=build ${SRC_PATH}/BOOT-INF/lib /app/lib
COPY --from=build ${SRC_PATH}/META-INF /app/META-INF
COPY --from=build ${SRC_PATH}/BOOT-INF/classes /app

ENTRYPOINT java -cp "app:app/lib/*" com.github.bagiasn.bookspot.${ENV_MAIN_CLASS}