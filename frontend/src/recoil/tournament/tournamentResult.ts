import { atom } from 'recoil';

export const tournamentResult = atom({
  key: 'tournamentResult',
  default: {
    tournamentId: 0,
    firstSongId: 0, // 월드컵 우승 곡 Id
    secondSongId: 0, // 월드컵 준우승곡 Id
    semiFinalSongOneId: 0, // 월드컵 4강 탈락 곡 Id
    semiFinalSongTwoId: 0, // 월드컵 4강 탈락 곡 Id
    songs: [{ id: '' }], // spotify id 배열
  },
});
