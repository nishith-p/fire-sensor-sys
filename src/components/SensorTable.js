import React, { Component } from "react";
import SensorTableRow from "./SensorTableRow";

class SensorTable extends Component {
  constructor(props) {
    super(props);
    this.state = { sensors: [] };
  }

  componentDidMount = async () => {
    try {
      setInterval(async () => {
        fetch("http://localhost:4000/api/sensors/")
          .then(function (data) {
            return data.json();
          })
          .then((json) => {
            this.setState({
              sensors: json,
            });
          });
      }, 10000);
    } catch (err) {
      console.log(err);
    }
  };

  tableRow() {
    console.log(this.state.sensors);
    return this.state.sensors.map(function (oneSensor, i) {
      return <SensorTableRow oneSensor={oneSensor} key={i} />;
    });
  }

  render() {
    return (
      <div className="col-12 text-center">
        <div className="card">
          <table className="table table-hover">
            <thead>
              <tr>
                <th scope="col">Sensor</th>
                <th scope="col">Floor Number</th>
                <th scope="col">Room Number</th>
                <th scope="col">Smoke Level</th>
                <th scope="col">CO2 Level</th>
                <th scope="col">Status</th>
              </tr>
            </thead>
            <tbody>{this.tableRow()}</tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default SensorTable;
