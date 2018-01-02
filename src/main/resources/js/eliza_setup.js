exports = {};
run(dir+"elizabot.js");
var elizabot = exports;
var bot = new ElizaBot(false);

//console.log(bot.elizaKeywords);

bot.elizaQuits.push("sleep");
bot.elizaKeywords.push (
		["search", 0, [
		 ["*", [
		     "I  found these results: \r\n"// + web.google("How to tie a tie")
		  ]]
		]]);
bot._init();
bot.reset();
//console.log(bot.elizaKeywords);

function run_eliza() {
    console.print("ELIZA> " + elizabot.start());

    var running = true;
    while(running) {
        rep = console.inputV(" USER> ");
//        console.log(bot.elizaKeywords);
//        console.print(elizabot.elizaKeywords);
//        console.print(elizabot.elizaQuits);
        if(bot.elizaQuits.indexOf(rep) >= 0) {
            running = false;
        } else
            console.print("ELIZA> " + elizabot.reply(rep));
    }

    console.print("ELIZA> " + elizabot.bye());
}

console.log("Eliza Setup Complete")