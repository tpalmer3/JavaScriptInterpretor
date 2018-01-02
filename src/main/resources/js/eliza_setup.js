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
bot._init();
bot.reset();

function run_eliza() {
    console.print(" ELIZA> " + elizabot.start());

    var running = true;
    while(running) {
        var rep = console.input("USER> ");
        if(bot.elizaQuits.indexOf(rep) >= 0) {
            running = false;
        } else
            console.print(" ELIZA> " + elizabot.reply(rep));
    }

    console.print(" ELIZA> " + elizabot.bye());
}

console.log("Eliza Setup Complete")