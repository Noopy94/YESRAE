import { AxiosError, AxiosResponse } from 'axios';
import axios from './securityIndex';

export interface PlaylistSongRegistPostReq {
  playlistId: number;
  songId: string;
}

// Playlist에 노래 등록 Api
export async function registPlaylistSongApi(
  playlistSongRegistPostReq: PlaylistSongRegistPostReq,
) {
  try {
    const apiUrl = '/registsong';

    const response: AxiosResponse = await axios.post(
      apiUrl,
      playlistSongRegistPostReq,
    );

    console.log('성공:', response.data);
    return response.data; // 성공 시 응답 데이터를 반환
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}
