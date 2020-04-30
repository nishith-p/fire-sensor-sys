import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import SensorTable from "./components/SensorTable";

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      alarmId: "",
      floorNo: "",
      roomNo: "",
      status: false,
    };
  }

  render() {
    return (
      <div className="container">
        <br />
        <br />
        <h1 className="text-center">Sensor Monitoring System</h1>
        <br />
        <div className="text-center">
          <br />
          <SensorTable />
        </div>
      </div>
    );
  }
}

export default App;
