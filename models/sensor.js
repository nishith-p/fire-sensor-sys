const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const SensorSchema = new Schema({
  sensorID: {
    type: String,
    required: [true, "ID field is required"],
  },
  floorNo: {
    type: Number,
    required: [true, "Floor number is required"],
  },
  roomNo: {
    type: Number,
    required: [true, "Room number is required"],
  },
  c_level: {
    type: Number,
  },
  s_level: {
    type: Number,
  },
  status: {
    type: Boolean,
    default: false,
  },
});

const Sensor = mongoose.model("sensor", SensorSchema);

module.exports = Sensor;
