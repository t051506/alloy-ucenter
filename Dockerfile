FROM java:8
MAINTAINER tankechao
ADD ./target/alloy-ucenter-biz.jar alloy-ucenter.jar
EXPOSE 9997
ENTRYPOINT ["java","-jar","alloy-ucenter.jar"]
