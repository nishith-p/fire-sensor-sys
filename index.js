const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const mongoose = require("mongoose");
const routes = require("./routes/api");
const userRoutes = require("./routes/userapi");

//Setup Express
const app = express();

//Connect to DB
mongoose.connect("mongodb://localhost/sensorDB");
mongoose.Promise = global.Promise;

//Page Content
app.use(express.static("public"));

app.use(cors());

app.use(bodyParser.urlencoded({ extends: true }));
app.use(bodyParser.json());

//Initialize Routes
app.use("/api", routes);
app.use("/users", userRoutes);

//Error Handling
app.use(function (err, req, res, next) {
  res.status(422).send({ error: err.message });
});

//Listening to Requests
app.listen(4000, function () {
  console.log("Now listening for requests");
});
