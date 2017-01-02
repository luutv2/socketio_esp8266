
module.exports =  (io)=>{
  io.on('connection',(socket)=>{
      console.log('co nguoi vua ket noi: '+ socket.id);
      console.log('Socket connected: ' + socket.conn.remoteAddress);

      // chờ android emit cho server
      socket.on('android send',  (data)=> {
        io.sockets.emit('led', { onoff: `${data}` }); // send data for esp8266
        console.log(data);
        });
        // chờ esp8266 emit cho server
      socket.on('JSON',  (data)=> {
           var jsonStr = JSON.stringify(data);
           var parsed = ParseJson(jsonStr);
           console.log(parsed);
           console.log(parsed.sensor);
           if(parsed.sensor == "led")
           {
             io.sockets.emit('Led on off', {onoff: parsed.onoff});// send data to android
           }
      });
      socket.on('disconnect', ()=>{
        console.log('co nguoi vua ngat ket noi');
      });
  });
}
function ParseJson(jsondata) {
    try {
        return JSON.parse(jsondata);
    } catch (error) {
        return null;
    }
}
