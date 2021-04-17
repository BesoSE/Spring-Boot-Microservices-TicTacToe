import React from 'react'
import { useState,useEffect } from "react";
import cookie from "js-cookie";
import TicTacToe from "./TicTacToe";

const url = "http://localhost:8765";

  const register = async () => {
    let username = document.getElementById("uname").value;
    let email = document.getElementById("email").value;
    let pwd = document.getElementById("password").value;
    let rpwd = document.getElementById("rpwd").value;

    if (username == null || username === "") {
      alert("Pleas enter username");
    }else if(username!=null){
      let data={
        username:username
      };
      try{
        const res=await getDataWithoutHeaders(data,"/api/checkUsername");
       if(res==false){
        let data={
            email:email
          };
        const res2=await getDataWithoutHeaders(data,"/api/checkEmail");
        if(res2==false){
        if (pwd == null || pwd === "" || pwd.length<6) {
          alert("Pleas enter password more then 6 character");
        } else if (rpwd == null || rpwd === "") {
          alert("Pleas enter repeat password");
        } else if (email == null || email === "") {
          alert("Pleas enter email");
        } else {
    
          if (pwd !== rpwd) {
            alert("Password need to be ==");
          } else {
            let data = {
              email: email,
              userName: username,
              password: pwd,
              repeatPwd: rpwd,
            };
            try {
              const d = await getDataWithoutHeaders(data, "/api/registration");
              document.getElementById("uname").value = "";
              document.getElementById("email").value = "";
              document.getElementById("password").value = "";
              document.getElementById("rpwd").value = "";
            } catch (e) {
              alert("Doslo je do greske, probajte drugi username ili email");
            }
          }
        }
    }else{
        alert("This Email is used. Please enter other");
    }


        
       }else{
           alert("This Username is used. Please enter other");
       }
      }catch(e){
        console.log(e);
      }

    } 
  };


  const getDataWithoutHeaders = async (dataa, urlWord) => {
    const res = await fetch(url + urlWord, {
      body: JSON.stringify(dataa),
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
    });
    const data = await res.json();
    //console.log(data);

    return data;
  };


const LoginRegister = () => {
  
const [user, setUser] = useState();

const login = async () => {
    if (cookie.get("username") == null) {
      let username = document.getElementById("userName").value;
      let pwd = document.getElementById("pwd").value;

      if (username == null || username === "") {
        alert("Pleas enter username");
      } else if (pwd == null || pwd === "") {
        alert("Pleas enter password");
      } else {
        let data = {
          username: username,
          password: pwd,
        };
        try {
          const d = await getDataWithoutHeaders(data, "/api/login");

          //session
          if (d.username != null) {
            cookie.set("token", d.jwt);
            cookie.set("username", d.username);
            setUser(d.username);
           
          }
          if(d.status==500){
              alert("Enter correct information");
              document.getElementById("userName").value="";
              document.getElementById("pwd").value="";
          }
          
        } catch (e) { alert(e);}
      }
    } else {
      alert("Prvo se morate odjaviti");
    }
  };

  useEffect(() => {
     if(cookie.get("username")!=null){
         setUser(true);
     }else{
         setUser(false);
     }
      return () => {
          setUser(false);
      }
  }, [])

    return (
        <>
        {!user  ? (
            <div className="row d-flex justify-content-center mt-5" style={{  width: "90%", marginRight:'auto',marginLeft:'auto'}}>
             
              <div className="col-lg-5  form-group mr-3 ">
                <input
                  id="uname"
                  name="uname"
                  placeholder="Type username"
                  className="form-control  m-2"
                />
                <input
                  id="email"
                  name="email"
                  placeholder="Type email"
                  className="form-control  m-2"
                />
                <input
                  id="password"
                  type="password"
                  name="password"
                  placeholder="Type password"
                  className="form-control  m-2"
                />
                <input
                  id="rpwd"
                  type="password"
                  name="rpwd"
                  placeholder="Type repeat password"
                  className="form-control  m-2"
                />
                <button onClick={register} className="btn btn-dark m-2 ">
                  Register
                </button>
              </div>
    
              <br />
    
              <div className="col-lg-5 form-group ">
                <input
                  id="userName"
                  name="userName"
                  placeholder="Type username"
                  className="form-control  m-2"
                />
                <input
                  id="pwd"
                  type="password"
                  name="pwd"
                  placeholder="Type password"
                  className="form-control  m-2"
                />
                <button onClick={login} className="btn btn-success m-2">
                  Login
                </button>
              </div>
               
            </div> ) :
               <TicTacToe setUser={setUser}/>
            } 

            </>
      
    )
}

export default LoginRegister
