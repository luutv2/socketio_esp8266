module.exports =(req, res)=>{
  let value_esp = req.query.value_esp;
  console.log(" api: " + value_esp);
  res.send(value_esp);
}
