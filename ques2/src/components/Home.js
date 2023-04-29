import React, { useState } from "react";
import ResponsiveNavbar from "./ResponsiveNavbar";
import "./Navbar.css";

const Home = () => {
  const [trains, setTrains] = useState([
    {
      trainName: "Chennai Exp",
      trainNumber: "2344",
      departureTime: {
        Hours: 21,
        Minutes: 35,
        Seconds: 0,
      },
      seatsAvailable: {
        sleeper: 3,
        AC: 1,
      },
      price: {
        sleeper: 2,
        AC: 5,
      },
      delayedBy: 15,
    },
    {
      trainName: "Hyderabad Exp",
      trainNumber: "2341",
      departureTime: {
        Hours: 23,
        Minutes: 55,
        Seconds: 0,
      },
      seatsAvailable: {
        sleeper: 6,
        AC: 7,
      },
      price: {
        sleeper: 554,
        AC: 1854,
      },
      delayedBy: 5,
    },
  ]);
  return (
    <>
      <ResponsiveNavbar />
      <div className="container">
        <div className="Card">
          {trains.map((train) => (
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{train.trainName}</h5>
                <h6 className="card-subtitle mb-2 text-muted">
                  {train.trainNumber}
                </h6>
                <p className="card-text">
                  Departure Time: {train.departureTime.Hours}:
                  {train.departureTime.Minutes}:{train.departureTime.Seconds}
                </p>
                <p className="card-text">
                  Seats Available: Sleeper: {train.seatsAvailable.sleeper} AC:{" "}
                  {train.seatsAvailable.AC}
                </p>
                <p className="card-text">
                  Price: Sleeper: {train.price.sleeper} AC: {train.price.AC}
                </p>
                <p className="card-text">
                  Delayed By: {train.delayedBy} minutes
                </p>
                <a href="#" className="card-link">
                  Book
                </a>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default Home;
