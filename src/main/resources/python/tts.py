import socket

engine = 0

try:
    import pyttsx
    engine = pyttsx.init()
except:
    try:
        import pyttsx3
        engine = pyttsx3.init()
    except:
        exit()

engine.setProperty("rate", 70)
engine.setProperty("voice", engine.getProperty("voices")[2])

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(("127.0.0.1", 5462))
s.listen(5)

text = ""

while text != "exit":
    c, addr = s.accept()
    try:
        while True:
            text = c.recv(1024).decode("utf-8")
            text = text[:len(text)-2]
            if(text == "exit"):
                exit()
            else:
                engine.say(text)
                engine.runAndWait()
            #print(text)
            #c.sendall("Hello From Python\n".encode("utf-8"))
    except:
        pass