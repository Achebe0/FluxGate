FROM ubuntu:latest
LABEL authors="acheb"

ENTRYPOINT ["top", "-b"]