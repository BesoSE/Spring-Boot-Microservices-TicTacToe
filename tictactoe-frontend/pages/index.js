import Head from 'next/head'
import LoginRegister from '../components/LoginRegister'
import TicTacToe from '../components/TicTacToe'


export default function Home() {
  return (
    <div style={{backgroundColor:'gray'}} >
         <header>
        <h1 className="text-center">Tic Tac Toe</h1>
      </header>
     <LoginRegister/>
    </div>
  )
}
