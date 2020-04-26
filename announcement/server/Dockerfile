# Dockerfile for announcement server.
# Using multistage build.

FROM golang:1.14.1-alpine AS builder

LABEL maintainer="mai18043@uom.edu.gr"

# Git is needed for dependency installation.
RUN apk update && apk add --no-cache git

WORKDIR $GOPATH/src/github.com/bagiasn/bookspot/announcement/server
COPY . .

# Fetch dependencies.
RUN go mod download
RUN go mod verify

# Build the executable. 
# Target linux and omit debug info
RUN GOOS=linux GOARCH=amd64 go build -ldflags="-w -s" -o /go/bin/server

FROM scratch

# Copy the executable from the previous stage.
COPY --from=builder /go/bin/server /go/bin/server

ENTRYPOINT ["/go/bin/server"]