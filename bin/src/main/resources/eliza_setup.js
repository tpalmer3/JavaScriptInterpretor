exports = {};
run(dir+"elizabot.js");
elizabot = exports;
bot = new ElizaBot(false);

function run_eliza() {
    console.print("ELIZA> " + elizabot.reply("hello"));
    elizabot.start(); // initializes eliza and returns a greeting message

    var running = true;
    while(running) {
        rep = console.input(" USER> ") // returns a eliza-like reply based on the message text passed into it
        if(bot.elizaQuits.indexOf(rep) >= 0) {
            running = false;
        } else
            console.print("ELIZA> " + elizabot.reply(rep));
    }

    elizabot.bye(); // returns a farewell message
}

console.log("Eliza Setup Complete")