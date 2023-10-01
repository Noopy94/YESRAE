import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from '../src/pages/user/Login';
import Regist from '../src/pages/user/Regist';
import Main from '../src/pages/main/Main';
import Nomantle from './pages/nomantle/Nomantle';
import NomantleRank from './pages/nomantle/NomantleRank';
import TournamentResult from './pages/tournament/TournamentResult';
import TournamentRanking from './pages/tournament/TournamentRanking';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/user/login" element={<Login />} />
        <Route path="/user/regist" element={<Regist />} />
        <Route path="/quiz" element={<Nomantle />} />
        <Route path="/quiz/rank" element={<NomantleRank />} />
        <Route path="/tournament/result" element={<TournamentResult />} />
        <Route path="/tournament/ranking" element={<TournamentRanking />} />
      </Routes>
    </Router>
  );
}

export default App;
