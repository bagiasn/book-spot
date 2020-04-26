# Dockerfile for announcement server.
# Using multistage build.

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

FROM scratch

# Copy the executable from the previous stage.
COPY --from=builder /app/server /
COPY --from=builder /app/book_names.json /

EXPOSE 12000

ENTRYPOINT ["/server"]