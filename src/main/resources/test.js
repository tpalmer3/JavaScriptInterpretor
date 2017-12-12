console.log("helloWorld");
console.log(console.add(1,2));

var x = "JankyAF";
x;

run = files.runFileWithReturn;
console.log("run(\"C:\\\\Users\\\\t\\\\Desktop\\\\JavaScriptInterpretor\\\\src\\\\main\\\\resources\\\\test.js\")")

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