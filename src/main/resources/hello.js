/**
 * 
 */

function sort(data) {
  if ( data.length === 1 ) {
    return [data[0]];
  } else if (data.length === 2 ) {
    if ( data[1] < data[0] ) {
      return [data[1],data[0]];
    } else {
      return data;
    }
  }
  var mid = Math.floor(data.length / 2);
  var first = data.slice(0, mid);
  var second = data.slice(mid);
  return merge(_sort( first ), _sort( second ) );
};