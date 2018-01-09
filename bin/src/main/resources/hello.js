:bin/src/main/resources/hello.js




function fillArray(x){
	var array = [];
	for(var i = 0; i <= x; i++){
		while(array[i] === undefined){
			var y = Math.floor(Math.random() * 1000000) + 1
			if(y > 900000){
				array.push(y);
			}
		}
	}

	return array;
}
var myVar = fillArray(4);
myVar;
<<<<<<< HEAD:bin/src/main/resources/hello.js



console.log("sort function added from hello.js")


