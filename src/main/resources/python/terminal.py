import socket
import threading

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(("127.0.0.1", 8521))
s.listen(5)

class Input:
    def __init__(self, c):
        self.running = True
        self.c = c

    def print_recieved(self):
        while self.running:
            text = self.recieve()
            print(text, end='')

            if text == "exit()":
                running = False
                self.running = False
                break

    def stop(self):
        self.running = False

    def recieve(self):
        ret = self.c.recv(1024).decode("utf-8")
        return ret[:len(ret)-2]


text = ""

def send(client, txt):
    client.sendall((txt+"\n").encode("utf-8"))

running = True

while text != "exit":
    c, addr = s.accept()
    print("Found Java")
    try:
        printer = Input(c)
        printing = threading.Thread(target=printer.print_recieved)
        printing.daemon = True
        printing.start()

        while running:
            send(c, input())

    except:
        pass
