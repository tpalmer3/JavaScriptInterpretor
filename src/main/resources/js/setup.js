var dir = "";
run = files.runFileWithReturn;
require = run;
setInterval = timer.setInterval;

function setup(home) {
    dir = home;

    run(dir+'redis_setup.js');
    run(dir+'eliza_setup.js');
}

function test() {
    run(dir+"test.js")
}