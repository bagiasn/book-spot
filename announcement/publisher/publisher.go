package main

import (
	"context"
	"flag"
	"io"
	"net/http"
	"os"
	"strings"

	pb "github.com/bagiasn/book-spot/announcement/api"
	"github.com/gorilla/websocket"
	log "github.com/sirupsen/logrus"
	"google.golang.org/grpc"
)

var (
	grpcServerAddr = flag.String("grpc_server_addr", "localhost:12000", "The GRPC server address (host:port)")
	addr           = flag.String("addr", "localhost:8585", "The WS server address (host:port)")
)

var upgrader = websocket.Upgrader{
	ReadBufferSize:  1024,
	WriteBufferSize: 1024,
	CheckOrigin: func(r *http.Request) bool {
		return strings.Compare(r.Header.Get("Origin"), "http://localhost:8080") == 0
	},
}

// MessageBus acts as a communication channel between websocket server and announcement receiver.
type MessageBus struct {
	messages chan string
}

func (m *MessageBus) receiveAnnouncements(client pb.AnnouncementClient) {
	log.Info("Starting announcement client.")

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
			log.Fatalf("%v.Recv(_) = _, %v", client, err)
		}

		log.Debug(announcement.Message)

		m.messages <- announcement.Message
	}
}

func (m *MessageBus) publishAnnouncements(w http.ResponseWriter, r *http.Request) {
	conn, err := upgrader.Upgrade(w, r, nil)
	if err != nil {
		log.Print("upgrade:", err)
		return
	}
	defer conn.Close()

	for {
		announcement := []byte(<-m.messages)
		err = conn.WriteMessage(websocket.TextMessage, announcement)
		if err != nil {
			log.Error(err)
		}
	}
}

func init() {
	// Output to stdout instead of the default stderr
	log.SetOutput(os.Stdout)
}

func main() {
	flag.Parse()

	log.WithFields(log.Fields{
		"serverUri":    *grpcServerAddr,
		"webSocketUri": *addr,
	}).Info("Connecting to announcement server")

	messageBus := &MessageBus{messages: make(chan string)}

	var opts []grpc.DialOption
	opts = append(opts, grpc.WithInsecure())
	opts = append(opts, grpc.WithBlock())
	// Connect to announcement server.
	conn, err := grpc.Dial(*grpcServerAddr, opts...)
	if err != nil {
		log.Fatalf("fail to dial: %v", err)
	}
	defer conn.Close()

	client := pb.NewAnnouncementClient(conn)

	go messageBus.receiveAnnouncements(client)

	http.HandleFunc("/listen", messageBus.publishAnnouncements)

	log.Fatal(http.ListenAndServe(*addr, nil))
}
