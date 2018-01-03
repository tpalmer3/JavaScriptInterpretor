import socket
# import _thread

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

engine.setProperty("rate", 150)
engine.setProperty("voice", engine.getProperty("voices")[0].id)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(("127.0.0.1", 5462))
s.listen(5)

text = ""
def recieve(client):
    ret = client.recv(1024).decode("utf-8")
    return ret[:len(ret)-2]

def send(client, txt):
    client.sendall(txt.encode("utf-8"))

execute = False
evaluate = False

while text != "exit":
    c, addr = s.accept()
    try:
        while True:
            text = recieve(c)

            if text == "exit":
                exit()
            elif text == "exec":
                execute = not execute;
            elif text == "eval":
                evaluate = not evaluate;
            else:
                if execute:
                    exec(text)
                elif evaluate:
                    send(c, eval(text))
                else:
                    if text[0] == "0" or text[0] == "1" or text[0] == "2":
                        engine.setProperty("voice", engine.getProperty("voices")[int(text[0])].id)
                        text = text[1:]

                    engine.say(text)
                    engine.runAndWait()
                    #print(text)
                    #c.sendall("Hello From Python\n".encode("utf-8"))
        # text = listen() #_thread.start_new_thread(listen, (c))
    except:
        pass
