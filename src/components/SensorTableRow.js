import React, { Component } from "react";

class SensorTableRow extends Component {
  render() {
    return (
      <tr>
        <td>{this.props.oneSensor.sensorID}</td>
        <td>{this.props.oneSensor.floorNo}</td>
        <td>{this.props.oneSensor.roomNo}</td>
        <td>{this.props.oneSensor.s_level}</td>
        <td>{this.props.oneSensor.c_level}</td>
        <td>
          {this.props.oneSensor.s_level > 5 ||
          this.props.oneSensor.c_level > 5 ? (
            <i className="fa fa-circle" style={{ color: "red" }} />
          ) : (
            <i className="fa fa-circle" style={{ color: "green" }} />
          )}
        </td>
      </tr>
    );
  }
}

export default SensorTableRow;
