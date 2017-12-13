console.log("helloWorld");
console.log(console.add(1,2));

var x = "JankyAF";
x;

//setup base functions
run = files.runFileWithReturn;
require = run;
setInterval = timer.setInterval;

function a() {console.log("goodbye");}

console.log(dir+"test.js\")");

bubbleSort([47,4,53,2,3,5,64,234,543,7563,76543,6,7,564756,7,6,456,377]);

function fib(x) {
    if(x <= 1) return 1;
    return fib(x-1) + fib(x-2);
}

function bubbleSort(list) {
    changed = true;
    while(changed) {
        changed = false;
        for(i = 0; i < list.length-1; i++) {
            if(list[i] > list[i+1]) {
                temp = list[i];
                list[i] = list[i+1];
                list[i+1] = temp;
                changed = true;
            }
        }
    }
    return list;
}