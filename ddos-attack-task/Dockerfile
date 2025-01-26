# FROM alpine:3.15

# ENV VERSION=4.1.1

# RUN apk add fping && apk add hping3 --update-cache --repository http://dl-cdn.alpinelinux.org/alpine/edge/testing

# RUN apk update \
#     && apk add curl g++ make \
#     && curl http://download.joedog.org/siege/siege-$VERSION.tar.gz > siege-$VERSION.tar.gz \
#     && tar -xf siege-${VERSION}.tar.gz \
#     && cd siege-${VERSION} \
#     && ./configure --with-ssl=/usr/local/ssl \
#     && make install

FROM ubuntu:20.04

RUN apt update -y \
    && apt install siege -y \
    && apt install hping3 -y \
    && apt install slowhttptest -y \
    && apt install fping -y

