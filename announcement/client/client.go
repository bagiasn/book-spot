package main

import (
	"context"
	"flag"
	"io"

	pb "github.com/bagiasn/bookspot/announcement/api"
	log "github.com/sirupsen/logrus"
	"google.golang.org/grpc"
)

var (
	serverAddr = flag.String("server_addr", "localhost:12000", "The server address in the format of host:port")
)

func startListening(client pb.AnnouncementClient) {
	log.Println("Starting streaming announcements")

	stream, err := client.Listen(context.Background(), &pb.Request{ClientId: int32(23)})
	if err != nil {
		log.Fatalf("%v.Listen(_) = _, %v", client, err)
	}

	for {
		announcement, err := stream.Recv()
		if err == io.EOF {
			break
		}
		if err != nil {
			log.Fatalf("%v.ListFeatures(_) = _, %v", client, err)
		}
		log.Println(announcement)
	}
}

func main() {
	flag.Parse()

	var opts []grpc.DialOption
	opts = append(opts, grpc.WithInsecure())
	opts = append(opts, grpc.WithBlock())
	// Connect to server.
	conn, err := grpc.Dial(*serverAddr, opts...)
	if err != nil {
		log.Fatalf("fail to dial: %v", err)
	}
	defer conn.Close()

	client := pb.NewAnnouncementClient(conn)

	startListening(client)
}
