package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"io/ioutil"
	"math/rand"
	"net"
	"os"
	"time"

	pb "github.com/bagiasn/bookspot/announcement/api"
	"github.com/bxcodec/faker/v3"

	log "github.com/sirupsen/logrus"
	"google.golang.org/grpc"
)

var (
	port     = flag.Int("port", 12000, "The server's port")
	jsonFile = flag.String("json_file", "./book_names.json", "The local json file to read book names from")
)

var bookData bookNames

type bookNames struct {
	Version int
	Names   []string
}

type fakeAnnouncement struct {
	Username string `faker:"first_name"`
	Rating   int    `faker:"boundary_start=1, boundary_end=6"`
	Delay    int    `faker:"boundary_start=10, boundary_end=20"`
}

type announcementServer struct {
}

func init() {
	// Output to stdout instead of the default stderr
	log.SetOutput(os.Stdout)

	// Read book names from local json file.
	data, err := ioutil.ReadFile(*jsonFile)
	if err != nil {
		log.Error("File reading error", err)
	}

	err = json.Unmarshal(data, &bookData)
	if err != nil {
		log.Error("Json deserialization error", err)
	}
}

// Start a gRPC server that produces fake announcements.
func main() {
	log.Info("Staring Announcement Server.")

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

	data := fakeAnnouncement{}

	for {
		// Populate announcement with fake data.
		_ = faker.FakeData(&data)
		// Get a random int as book index.
		index := rand.Intn(len(bookData.Names))
		message := fmt.Sprintf("%v rated \"%v\" with %d", data.Username, bookData.Names[index], data.Rating)
		// Send to client.
		if err := stream.Send(&pb.AnnouncementMessage{Message: message}); err != nil {
			return err
		}

		time.Sleep(time.Duration(data.Delay) * time.Second)
	}
}
