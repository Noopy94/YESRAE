import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from '../src/pages/user/Login';
import Regist from '../src/pages/user/Regist';
import Main from '../src/pages/main/Main';
import PlayList from './pages/playlist/PlayList';
import PlayListDetail from './pages/playlist/PlayListDetail';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/user/login" element={<Login />} />
        <Route path="/user/regist" element={<Regist />} />
        <Route path="/playlist" element={<PlayList />} />
        <Route path="/playlist/:userId" element={<PlayList />} />
        <Route
          path="/playlistdetail/:playListId"
          element={<PlayListDetail />}
        />
      </Routes>
    </Router>
  );
}

export default App;
