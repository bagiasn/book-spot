package main

import (
	"flag"
	"fmt"
	"net"
	"os"
	"time"

	pb "github.com/bagiasn/bookspot/announcement/api"

	log "github.com/sirupsen/logrus"
	"google.golang.org/grpc"
)

var (
	port = flag.Int("port", 12000, "The server's port")
)

type announcementServer struct {
}

func init() {
	// Output to stdout instead of the default stderr
	log.SetOutput(os.Stdout)
}

// Start a gRPC server that produces fake announcements.
func main() {
	log.Println("Staring Announcement Server.")

	lis, err := net.Listen("tcp", fmt.Sprintf("localhost:%d", *port))
	if err != nil {
		log.Fatalf("Failed to bind port %d: %v", *port, err)
	}

	grpcServer := grpc.NewServer()

	pb.RegisterAnnouncementServer(grpcServer, newServer())
	grpcServer.Serve(lis)
}

func newServer() *announcementServer {
	s := &announcementServer{}
	return s
}

func (s *announcementServer) Listen(in *pb.Request, stream pb.Announcement_ListenServer) error {
	log.WithFields(log.Fields{
		"clientId": in.ClientId,
	}).Info("Received client request")

	for {
		if err := stream.Send(&pb.AnnouncementMessage{Message: "Hello there chap"}); err != nil {
			return err
		}
		time.Sleep(5 * time.Second)
	}
}
