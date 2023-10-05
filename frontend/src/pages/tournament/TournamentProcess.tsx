import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getTournamentId, getTournamentSong } from '../../api/tournamentApi';
import Youtube from 'react-youtube';
import { useSetRecoilState } from 'recoil';
import { tournamentResult } from '../../recoil/tournament/tournamentResult';
import ButtonComponent from '../../components/common/ButtonComponent';

type Song = {
  id: number;
  title: string;
  singer: string;
  url: string;
  spotifyId: string;
};

const TournamentProcess = () => {
  const { round } = useParams();
  const navigate = useNavigate();

  const [totalRound, setTotalRound] = useState<number>(Number(round));
  const [currentRound, setCurrentRound] = useState(2);

  const [tournamentSongs, setTournamentSongs] = useState<Song[]>([]);
  const [tempSongs, setTempSongs] = useState<Song[]>([]);

  const [result, setResult] = useState<Song[]>([]);
  const setTournamentResult = useSetRecoilState(tournamentResult);

  useEffect(() => {
    const getSong = async (round: number) => {
      const songs = await getTournamentSong(round);
      setTournamentSongs(songs);
      setTempSongs(songs);
    };
    getSong(Number(round));
  }, []);

  const handleLeftWin = async () => {
    const copySongs = [...tournamentSongs];
    copySongs.splice(copySongs.indexOf(tempSongs[currentRound - 1]), 1);
    setTournamentSongs(copySongs);
    setCurrentRound(currentRound + 2);

    if (totalRound === 4) {
      const copyResult = [...result];
      copyResult.push(tempSongs[currentRound - 1]); // 3,4등
      setResult(copyResult);
    }

    if (totalRound === 2) {
      const copyResult = [...result];
      copyResult.push(tempSongs[currentRound - 1]); // 2등
      copyResult.push(tempSongs[currentRound - 2]); // 1등

      setResult(copyResult);

      const tournamentId = await getTournamentId();

      const resultValue = {
        tournamentId: tournamentId,
        firstSongId: copyResult[3].id,
        secondSongId: copyResult[2].id,
        semiFinalSongOneId: copyResult[1].id,
        semiFinalSongTwoId: copyResult[0].id,
        songs: [
          { id: copyResult[0].spotifyId },
          { id: copyResult[1].spotifyId },
          { id: copyResult[2].spotifyId },
          { id: copyResult[3].spotifyId },
        ],
      };
      setTournamentResult(resultValue);

      navigate('/tournament/result');
    }

    if (currentRound === totalRound) {
      setCurrentRound(2);
      copySongs.sort(() => Math.random() - 0.5);
      setTempSongs(copySongs);
      setTotalRound(totalRound / 2);
    }
  };

  const handleRightWin = async () => {
    const copySongs = [...tournamentSongs];
    copySongs.splice(copySongs.indexOf(tempSongs[currentRound - 2]), 1);
    setTournamentSongs(copySongs);
    setCurrentRound(currentRound + 2);

    if (totalRound === 4) {
      const copyResult = [...result];
      copyResult.push(tempSongs[currentRound - 2]); // 3,4등
      setResult(copyResult);
    }

    if (totalRound === 2) {
      const copyResult = [...result];
      copyResult.push(tempSongs[currentRound - 2]); // 2등
      copyResult.push(tempSongs[currentRound - 1]); // 1등

      setResult(copyResult);

      const tournamentId = await getTournamentId();

      const resultValue = {
        tournamentId: tournamentId,
        firstSongId: copyResult[3].id,
        secondSongId: copyResult[2].id,
        semiFinalSongOneId: copyResult[1].id,
        semiFinalSongTwoId: copyResult[0].id,
        songs: [
          { id: copyResult[0].spotifyId },
          { id: copyResult[1].spotifyId },
          { id: copyResult[2].spotifyId },
          { id: copyResult[3].spotifyId },
        ],
      };
      setTournamentResult(resultValue);

      navigate('/tournament/result');
    }

    if (currentRound === totalRound) {
      setCurrentRound(2);
      copySongs.sort(() => Math.random() - 0.5);
      setTempSongs(copySongs);
      setTotalRound(totalRound / 2);
    }
  };

  return (
    <div>
      <div className="flex justify-center mt-16 text-6xl font-bold gap-x-36">
        <div>{totalRound === 2 ? '결승' : `${totalRound}강`}</div>
        <div>
          {currentRound / 2}/{totalRound / 2}
        </div>
      </div>
      <div className="flex justify-center mt-14 gap-x-10">
        {tempSongs.length > currentRound - 2 && (
          <Youtube videoId={tempSongs[currentRound - 2].url} />
        )}
        {tempSongs.length > currentRound - 1 && (
          <Youtube videoId={tempSongs[currentRound - 1].url} />
        )}
      </div>
      <div className="flex justify-center mt-14 gap-x-96">
        <ButtonComponent type="isBig" onClick={handleLeftWin}>
          선택
        </ButtonComponent>
        <ButtonComponent type="isBig" onClick={handleRightWin}>
          선택
        </ButtonComponent>
      </div>
    </div>
  );
};

export default TournamentProcess;
