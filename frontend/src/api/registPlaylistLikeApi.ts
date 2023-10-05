import { AxiosError, AxiosResponse } from 'axios';
import axios from './securityIndex';

export interface PlaylistLikeRegistPostReq {
  userId: number;
  playlistId: number;
}

// Playlist에 노래 등록 Api
export async function registPlaylistLikeApi(
  playlistLikeRegistPostReq: PlaylistLikeRegistPostReq,
) {
  try {
    const apiUrl = '/registlike';

    const response: AxiosResponse = await axios.post(
      apiUrl,
      playlistLikeRegistPostReq,
    );

    console.log('성공:', response.data);
    return response.data; // 성공 시 응답 데이터를 반환
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}
