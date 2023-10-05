import axios from 'axios';

const BASE_URL = 'http://localhost:8080/';

export const getTournamentSong = async (round: number) => {
  const response = await axios.get(BASE_URL + 'tournament/song', {
    params: {
      round: round,
    },
  });
  if (response.data.success) {
    return response.data.content;
  } else {
    alert(response.data.error.message);
    return null;
  }
};

export const getTournamentId = async () => {
  const response = await axios.post(BASE_URL + 'tournament');

  return response.data.content.id;
};

export const getTournamentResult = async (result: {
  tournamentId: number;
  firstSongId: number;
  secondSongId: number;
  semiFinalSongOneId: number;
  semiFinalSongTwoId: number;
}) => {
  const response = await axios.post(BASE_URL + 'tournament/result', result);

  return response.data.content;
};
