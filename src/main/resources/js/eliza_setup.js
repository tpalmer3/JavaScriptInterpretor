exports = {};
run(dir+"elizabot.js");
var elizabot = exports;
var non = elizabot.start();
var bot = elizabot.bot;

bot.elizaQuits.push("sleep");
bot.elizaKeywords.push (
		["search", 0, [
		 ["*", [
		     "I  found these results: \r\n"// + web.google("How to tie a tie")
		  ]]
		]]);
//bot._init();
//bot.reset();

function run_eliza_with_audio() {
    console.run("python C:\\Users\\t\\Desktop\\JavaScriptInterpretor\\src\\main\\resources\\python\\tts.py");
    sock.connect("127.0.0.1", 5462);
    run_eliza(sock);
    sock.send("exit");
    sock.stop();
}

function run_eliza(output) {

    eliza_print(elizabot.start(), output);

    var running = true;
    while(running) {
        var rep = console.input("USER> ");
        if(bot.elizaQuits.indexOf(rep) >= 0) {
            running = false;
        } else
            eliza_print(elizabot.reply(rep), output);
    }

    eliza_print(elizabot.bye(), output);
}

function eliza_print(str, output) {
    if(typeof output !== "undefined")
        output.send(str);
    console.print(" ELIZA> " + str);
}

console.log("Eliza Setup Complete")