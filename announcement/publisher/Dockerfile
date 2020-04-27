# Dockerfile for announcement publisher.
# Using multistage build.

# Using global ARG in order to access server url on the second build stage.
# More info: https://github.com/moby/moby/issues/37345
ARG SERVER_URI

FROM golang:1.14.1-alpine AS builder

LABEL maintainer="mai18043@uom.edu.gr"

ENV GO111MODULE=on \
    CGO_ENABLED=0

# Use a different path to avoid mod conflicts.
WORKDIR /app

# Git is needed for dependency installation.
RUN apk update && apk add --no-cache git

COPY go.mod .
COPY go.sum .

# Fetch dependencies.
RUN go mod download
# Verify dependencies.
RUN go mod verify

COPY . .

# Build the executable. 
# Target linux and omit debug info
RUN GOOS=linux GOARCH=amd64 go build -ldflags="-w -s"

FROM alpine:3.11

ARG SERVER_URI
ENV ENV_SERVER_URI=${SERVER_URI}

# Copy the executable from the previous stage.
COPY --from=builder /app/publisher /

EXPOSE 8585

ENTRYPOINT /publisher --grpc_server_addr "$ENV_SERVER_URI" --addr ":8585"