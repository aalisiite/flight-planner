FROM gradle:5.2.1-jdk8

VOLUME /tmp

COPY . /home/gradle/flight-planner
WORKDIR /home/gradle/flight-planner

ENTRYPOINT ["gradle"]
