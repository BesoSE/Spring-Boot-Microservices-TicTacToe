import cookie from "js-cookie";
import "stompjs";
import SockJS from "sockjs-client";
import style from '../styles/TicTacToe.module.css'
import { useState, useEffect } from "react";

const url = "http://localhost:8765";
var playerType;
var turns = [
  ["#", "#", "#"],
  ["#", "#", "#"],
  ["#", "#", "#"],
];
var gameId;
var gameOn = false;
var Stomp = require("stompjs");



function reset() {
  for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
      document.getElementById(i + "_" + j).innerHTML = "#";
    }
  }
}

const getData = async (dataa, urlWord) => {
  const res = await fetch(url + urlWord, {
    body: JSON.stringify(dataa),
    headers: {
      "Content-Type": "application/json",
      Authorization: cookie.get("token"),
    },
    method: "POST",
  });
  const data = await res.json();
  //console.log(data);

  return data;
};




const TicTacToe = ({setUser}) => {

  const [game, setGame] = useState(false);
  const [code, setCode] = useState(0);
  const [player,setPlayer]=useState(null);

  const connectToSocket = (gameId) => {
    console.log("connectin to the game");

    let socket = new SockJS("http://localhost:8300" + "/gameplay");

    //provide url and from web config endpoint
    let stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
      console.log("connnect to the " + frame);
      stompClient.subscribe(
        "/topic/game-progress/" + gameId,
        function (response) {
          let data = JSON.parse(response.body);
         
       
          if (data.winner !== null) {
            gameOn = false;
          } else {
            gameOn = true;
          }

          displayResponse(data);
        }
      );
    });
  };




  const logout = async () => {
    if (cookie.get("username") != null) {
      let data = {
        jwt: cookie.get("token"),
        username: cookie.get("username"),
      };
      finishedGame();

      const d = await getData(data, "/api/logout");

      //session

      cookie.remove("token");
      cookie.remove("username");
      setUser(false);
    } else {
      alert("Nije moguce logout izvrsit");
    }
  };

  const createGame = async () => {
    if (cookie.get("username") != null) {
      let data = {
        userName: cookie.get("username"),
      };
      const d = await getData(data, "/api/start");
      playerType = "X";
      reset();
      gameId = d.id;
      setGame(true);
      setCode(d.id);
      connectToSocket(d.id);
    } else {
      alert("Molim vas ulogujte se");
    }
  };

  const connectToRandom = async () => {
    if (cookie.get("username") != null) {
      let data = {
        userName: cookie.get("username"),
      };
      let d = await getData(data, "/api/connect/random");
      playerType = "O";
      gameId = d.id;
      gameOn = true;
      setGame(true);
      reset();
      connectToSocket(d.id);
    } else {
      alert("Molim vas ulogujte se");
    }
  };

  const connectToSpceificGame = async () => {
    let id = document.getElementById("game_id").value;
    if (cookie.get("username") != null) {
      if (id == null || id === "") {
        alert("Pleas enter gameid");
      } else {
        let data = {
          player: {
            userName: cookie.get("username"),
          },
          gameid: id,
        };
        let d = await getData(data, "/api/connect");
        playerType = "O";
        gameId = d.id;
        gameOn = true;
        setGame(true);
        reset();
        connectToSocket(id);
      }
    } else {
      alert("Molim vas ulogujte se");
    }
  };

  const playerTurn = async (id) => {
    if (gameOn) {
      let spotTaken = document.getElementById(id).textContent;

      if (spotTaken === "#") {
        makeAMove(playerType, id.split("_")[0], id.split("_")[1]);
      }
    }
  };



  const leaveGame = async () => {
    finishedGame();
    for (let i = 0; i < 3; i++) {
      for (let j = 0; j < 3; j++) {
        let id = i + "_" + j;
        document.getElementById(id).innerHTML = "";
      }
    }
  };

  const finishedGame = async () => {
    setGame(false);
    setCode(0);
    if (gameId != null) {
      let data = {
        id: gameId,
      };
  
      const d = await getData(data, "/api/finish");
    }
  };
  


  const makeAMove = async (type, xCoordinate, yCoordinate) => {
    let data = {
      type: type,
      coordinateX: xCoordinate,
      coordinateY: yCoordinate,
      game: {
        id: gameId,
      },
    };
    let d = await getData(data, "/api/gameplay");
    gameOn = false;
    displayResponse(d);
  };

  const displayResponse = (d) => {
    let board = d.board;

    for (let i = 0; i < board.length; i++) {
      for (let j = 0; j < board[i].length; j++) {
        if (board[i][j] === 1) {
          turns[i][j] = "X";
        } else if (board[i][j] === 2) {
          turns[i][j] = "O";
        }
        let id = i + "_" + j;
        document.getElementById(id).innerHTML = turns[i][j];
      }
    }
    if (d.winner !== null) {
      setGame(false);
      setCode(0);
      alert("Winner is " + d.winner);
      for (let i = 0; i < board.length; i++) {
        for (let j = 0; j < board[i].length; j++) {
          turns[i][j] = "#";
        }
      }
    }
  };






  useEffect(() => {
    window.onbeforeunload = function () {
      finishedGame();
      if (gameOn == true) {
        finishedGame();
      }
    };

    window.onbeforeunload();
  }, []);

return (
 <div className="">
    <div className=" m-2 float-right">
         
            <button onClick={logout} className="btn btn-dark ">
              Logout
            </button>
          </div>
          <br/>

          {code != 0 && <div className={style.centerDiv} ><span className="p-2" style={{border:"1px solid black",borderRadius:"5%",width:"25%"}}>Code for game : <b>{code}</b></span></div>}
          {!game ? (
            <div className="mt-5 row  d-flex justify-content-center" >
            <div className="col-lg-4 mb-3 ">
            <div className={style.centerDivM}>
              <button onClick={createGame} className="btn btn-dark ">
                Create a new game
              </button>
              </div>
              <div className={style.centerDivM}>
              <button onClick={connectToRandom} className="btn btn-dark">
                Connect to random game
              </button>
              </div>
              </div>
              <div className="col-lg-9 mr-1"></div>
              <div className="col-lg-4 col-md-6 form-group "  >
             
              <input
                  id="game_id"
                  placeholder="Enter game id"
                  className="form-control mb-1 "
                  
                  
                />
                <div className={style.centerDivM}>
                <button
                  onClick={connectToSpceificGame}
                  className="btn btn-dark " 
                  >
                  Connect by game id
                </button>
                </div>
              </div>
            </div>
          ) : (
            
            <div className={style.centerDiv}>
              <button onClick={leaveGame} className="btn btn-dark">
                Leave game
              </button>
            </div>
         
            )} 
        
        
          <div className="col-lg-12 d-flex justify-content-center">
            <ul
              id="gameBoard"
              className="row m-0 p-0 bg-dark  h-100"
              style={{listStyle:"none"}}
            >
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("0_0")}
                id="0_0"
          
              ></li>
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("0_1")}
                id="0_1"
              ></li>
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("0_2")}
                id="0_2"
              ></li>
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("1_0")}
                id="1_0"
              ></li>
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("1_1")}
                id="1_1"
              ></li>
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("1_2")}
                id="1_2"
              ></li>
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("2_0")}
                id="2_0"
              ></li>
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("2_1")}
                id="2_1"
              ></li>
              <li
                className="tic col-4 border p-4 text-center text-light click"
                onClick={() => playerTurn("2_2")}
                id="2_2"
              ></li>
            </ul>
          
          </div>
        
      
 </div>
    

  );
};

export default TicTacToe;
