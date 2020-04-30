const express = require("express");
const router = express.Router();
const Sensor = require("../models/sensor");

//Get a list from DB
router.get("/sensors", function (req, res, next) {
  Sensor.find({}).then(function (sensor) {
    res.send(sensor);
  });
});

//Add a new item to DB
router.post("/sensors", function (req, res, next) {
  Sensor.create(req.body)
    .then(function (sensor) {
      res.send(sensor);
    })
    .catch(next);
});

//Update an item in DB
router.put("/sensors/:id", function (req, res, next) {
  Sensor.findByIdAndUpdate({ _id: req.params.id }, req.body).then(function () {
    Sensor.findOne({ _id: req.params.id }).then(function (sensor) {
      res.send(sensor);
    });
  });
});

//Delete an item from DB
router.delete("/sensors/:id", function (req, res, next) {
  Sensor.findByIdAndRemove({ _id: req.params.id }).then(function (sensor) {
    res.send(sensor);
  });
});

module.exports = router;
