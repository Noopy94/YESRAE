import { AxiosError, AxiosResponse } from 'axios';
import axios from './securityIndex';

export interface PlaylistDeletePutReq {
  playlistId: number;
}

// Playlist에 노래 등록 Api
export async function deletePlaylistApi(
  playlistDeletePutReq: PlaylistDeletePutReq,
) {
  try {
    const apiUrl = 'delete';

    const response: AxiosResponse = await axios.put(
      apiUrl,
      playlistDeletePutReq,
    );

    console.log('성공:', response.data);
    return response.data; // 성공 시 응답 데이터를 반환
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}
